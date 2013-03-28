$(document).ready( function() {

    $('#outcomes').dataTable({
        "bServerSide": true,
        'sPaginationType': 'bootstrap',
        "sAjaxSource": '../php/client/json.php?type=outcomes'
    });

    var WealthyLaughingDuckControl = {
        users: [],
        setUsers: function(new_users) {
            this.users = new_users;
        }
    };

    $("#choose-users-dialog").dialog({
        autoOpen: false,
        modal: true,
        buttons: {
            Apply: function() {
                WealthyLaughingDuckControl.setUsers(
                    $("input[name=user]:checked").map(function() {
                        return this.value;
                    })
                );
                console.log(WealthyLaughingDuckControl);
                $( this ).dialog( "close" );
            },
            Cancel: function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            var element = $('#choose-users-dialog');
            element.html("");
        }
    })

    $("#choose-users-btn").click(function() {
        //        console.log($('#choose-users-dialog').is('.ui-dialog-content'));
        //        if ($('#choose-users-dialog').is('.ui-dialog-content')) {
        //            $('#choose-users-dialog').dialog('open');
        //        } else {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "../php/client/json.php",
            data: {
                type: "users"
            }
        }).done(function( response ) {
            var element = $('#choose-users-dialog');
            element.append(ich.choose_users(response));
            $("#choose-users-dialog").dialog("open");
        });
    //        }
    });

});
