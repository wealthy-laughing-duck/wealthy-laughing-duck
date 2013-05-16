The wealthy laughing duck documentation
=======================================

![wealthy laughing duck logo](wealthy-laughing-duck-logo.png "wealthy laughing duck logo")

Main project structure
----------------------

There are two main components in the system: frontend and backend.

![main diagram](diagram_main.png "main diagram")

Backend
-------

Backend is just a Java console application. On one side it uses hibernate to
fetch data from MySQL database and on the other it uses thrift to provide a
friendly service that other applications can communicate with.

Frontend
--------

Frontend consists of the server-side [API](api.md) (the inbetween layer, as
small as possible) and the client-side part. Server-side can be based on any
scripting language (PHP, python, ruby, NodeJS - in this case it's PHP, but
thrift supports all of them) and it is responsible only for receiving AJAX
requests, translating them into thrift service calls and passing them to the
backend. Client-side is a rich interface application that provides
user-friendly widgets to access the real data.

Configuration
=============

You may find these files important:

 * [hibernate configuration](../src/main/resources/hibernate.cfg.xml)
 * [thrift definitions](../src/main/thrift/service.thrift)
