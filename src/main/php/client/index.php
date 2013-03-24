<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/tr/html4/transitional.dtd">
<html>
  <head>
    <title>Wealthy Laughing Duck</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="http://twitter.github.com/bootstrap/1.4.0/bootstrap.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" ></script>
    <script src="http://datatables.net/download/build/jquery.dataTables.nightly.js"></script>
    <script>
			/* Default class modification */
			$.extend( $.fn.dataTableExt.oStdClasses, {
				"sSortAsc": "header headerSortDown",
				"sSortDesc": "header headerSortUp",
				"sSortable": "header"
			} );

			/* API method to get paging information */
			$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
			{
				return {
					"iStart":         oSettings._iDisplayStart,
					"iEnd":           oSettings.fnDisplayEnd(),
					"iLength":        oSettings._iDisplayLength,
					"iTotal":         oSettings.fnRecordsTotal(),
					"iFilteredTotal": oSettings.fnRecordsDisplay(),
					"iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
					"iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
				};
			}

			/* Bootstrap style pagination control */
			$.extend( $.fn.dataTableExt.oPagination, {
				"bootstrap": {
					"fnInit": function( oSettings, nPaging, fnDraw ) {
						var oLang = oSettings.oLanguage.oPaginate;
						var fnClickHandler = function ( e ) {
							e.preventDefault();
							if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
								fnDraw( oSettings );
							}
						};

						$(nPaging).addClass('pagination').append(
							'<ul>'+
								'<li class="prev disabled"><a href="#">&larr; '+oLang.sPrevious+'</a></li>'+
								'<li class="next disabled"><a href="#">'+oLang.sNext+' &rarr; </a></li>'+
							'</ul>'
						);
						var els = $('a', nPaging);
						$(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
						$(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
					},

					"fnUpdate": function ( oSettings, fnDraw ) {
						var iListLength = 5;
						var oPaging = oSettings.oInstance.fnPagingInfo();
						var an = oSettings.aanFeatures.p;
						var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

						if ( oPaging.iTotalPages < iListLength) {
							iStart = 1;
							iEnd = oPaging.iTotalPages;
						}
						else if ( oPaging.iPage <= iHalf ) {
							iStart = 1;
							iEnd = iListLength;
						} else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
							iStart = oPaging.iTotalPages - iListLength + 1;
							iEnd = oPaging.iTotalPages;
						} else {
							iStart = oPaging.iPage - iHalf + 1;
							iEnd = iStart + iListLength - 1;
						}

						for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
							// Remove the middle elements
							$('li:gt(0)', an[i]).filter(':not(:last)').remove();

							// Add the new list items and their event handlers
							for ( j=iStart ; j<=iEnd ; j++ ) {
								sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
								$('<li '+sClass+'><a href="#">'+j+'</a></li>')
									.insertBefore( $('li:last', an[i])[0] )
									.bind('click', function (e) {
										e.preventDefault();
										oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
										fnDraw( oSettings );
									} );
							}

							// Add / remove disabled classes from the static elements
							if ( oPaging.iPage === 0 ) {
								$('li:first', an[i]).addClass('disabled');
							} else {
								$('li:first', an[i]).removeClass('disabled');
							}

							if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
								$('li:last', an[i]).addClass('disabled');
							} else {
								$('li:last', an[i]).removeClass('disabled');
							}
						}
					}
				}
			} );
      
      $(document).ready( function() {
        $('#outcomes').dataTable({
          "bServerSide": true,
          'sPaginationType': 'bootstrap',
          "sAjaxSource": 'json.php?type=outcomes'
        });
        
        $("a#users").click(function() {
          $.ajax({
            type: "GET",
            url: "json.php",
            data: { type: "users" }
          }).done(function( response ) {
            alert( response );
          });
        });

      });
    </script>
    <style>
div.dataTables_length label {
	width: 460px;
	float: left;
	text-align: left;
}

div.dataTables_length select {
	width: 75px;
}

div.dataTables_filter label {
	float: right;
	width: 460px;
}

div.dataTables_info {
	padding-top: 8px;
}

div.dataTables_paginate {
	float: right;
	margin: 0;
}

table {
	margin: 1em 0;
	clear: both;
}

table.dataTable th:active {
	outline: none;
}

    </style>
  </head>
<body id="dt_example">
    <a id="users" class="btn">show users</a>
  
    <div class="container">
      <h1>Outcomes</h1>

      <table class="table table-striped" id="outcomes">
        <thead>
          <tr>
            <th>Amount</th>
            <th>User</th>
            <th>Category</th>
            <th>Comment</th>
          </tr>
        </thead>
        <tbody>
        </tbody>
      </table>

    </div>
  </body>
</html>