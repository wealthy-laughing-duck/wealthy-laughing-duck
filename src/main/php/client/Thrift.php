<?php

namespace SymfonyWorld;

use \Thrift\Protocol\TBinaryProtocol;
use \Thrift\Transport\TSocket;
use \Thrift\Transport\TBufferedTransport;
use \Thrift\Exception\TException;

class Thrift {

	protected $socket;
	protected $transport;
	protected $protocol;
	protected $client;
	
	protected $args_template = '\SymfonyWorld\MainService_%s_args';

	public function __construct($host, $port) {
		$this->socket = new TSocket($host, $port);
		$this->transport = new TBufferedTransport($this->socket, 1024, 1024);
		$this->protocol = new TBinaryProtocol($this->transport);
		$this->client = new MainServiceClient($this->protocol);
		$this->transport->open();
	}

	/**
	 * Closes thrift transport.
	 */
	public function __destruct() {
		$this->transport->close();
	}

	public function getClient() {
		return $this->client;
	}

	private function populateRequest($class, $parameters) {
		$object = new $class();
		foreach ($class::$_TSPEC as $spec) {
			if (isset($parameters[$spec['var']])) {
				// abstract parameter defined in parameter array
				if ($spec['type'] == \TType::STRUCT) {
					// thrift parameter should be a struct
					$parameters[$spec['var']] = $this->populateRequest($spec['class'], $parameters[$spec['var']]);
				} elseif ($spec['type'] == \TType::LST && $spec['etype'] == \TType::STRUCT) {
					// thrift parameter would be a list of structs
					$populated_array = array();
					foreach ($parameters[$spec['var']] as $element) {
						$populated_array[] = $this->populateRequest($spec['elem']['class'], $element);
					}
					$parameters[$spec['var']] = $populated_array;
				}
			}
		}
		return new $class($parameters);
	}

	private function populateResponse($response) {
		if (is_object($response)) {
			$response_array = get_object_vars($response);
		} elseif (is_array($response)) {
			$response_array = $response;
		} else {
			return $response;
		}
		foreach ($response_array as $key => $value)
			$response_array[$key] = $this->populateResponse($value);
		return $response_array;
	}

	protected function getArgsClassTemplate($command) {
		return sprintf($this->args_template, $command);
	}

	public function executeCommand($command, array $params) {
		if (method_exists($this, $command)) {
			// call custom method and pass parameters
			return $this->$command($params);
		} else {
			// create request thrift object
			$args_class = $this->getArgsClassTemplate($command);
			$args = new $args_class();
			$request_class = $args::$_TSPEC[1]['class'];
			$request = $this->populateRequest($request_class, $params);

			$response = $this->client->$command($request);
			// construct and return response array
			$response_array = $this->populateResponse($response);
			return $response_array;
		}
	}
}
