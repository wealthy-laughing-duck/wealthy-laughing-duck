var WealthyLaughingDuckControl = {
    parseListIntoForest: function (categories) {
        if (categories == null)
            return null;

        var itemsByID = [];

        categories.forEach(function(item) {
            itemsByID[item.id] = {
                data: {title: item.name},
                parentID: item.parent_id
            };
        });

        itemsByID.forEach(function(item) {
            if(item.parentID !== null) {
                if (typeof itemsByID[item.parentID].children === "undefined") {
                    itemsByID[item.parentID].children = [];
                }
                itemsByID[item.parentID].children.push(item);
            }
        });

        var roots = itemsByID.filter(function(item) {
            return item.parentID === null;
        });

        itemsByID.forEach(function(item) {
            delete item.parentID;
        });

        return roots;
    }
};

var UsersControl = {
    users: null,
    fetchData: function() {
        $.ajax({
            type: "GET",
            dataType: "json",
            context: this,
            async: false,
            url: "../php/client/json.php",
            data: {
                type: "users"
            }
        }).done(function(response) {
            this.users = response;
        });
    },
    getData: function() {
        if (this.users == null) {
            this.fetchData();
        }
        return this.users;
    }
};

var IncomeCategoryControl = {
    categories: null,
    fetchData: function() {
        $.ajax({
            type: "GET",
            dataType: "json",
            context: this,
            async: false,
            url: "../php/client/json.php",
            data: {
                type: "incomeCategories"
            }
        }).done(function(response) {
            this.categories = response;
        });
    },
    getData: function() {
        if (this.categories == null) {
            this.fetchData();
        }
        return this.categories;
    }
};

var OutcomeCategoryControl = {
    categories: null,
    fetchData: function() {
        $.ajax({
            type: "GET",
            dataType: "json",
            context: this,
            async: false,
            url: "../php/client/json.php",
            data: {
                type: "outcomeCategories"
            }
        }).done(function(response) {
            this.categories = response;
        });
    },
    getData: function() {
        if (this.categories == null) {
            this.fetchData();
        }
        return this.categories;
    }
};

$(document).ready( function() {

    $.ajax({
        type: "GET",
        dataType: 'text',
        async: false,
        url: "templates.html"
    }).done(function(response) {
        $("body").append(response);
        ich.grabTemplates();
    });

    $('.dropdown-toggle').dropdown();

    $('#outcomes').dataTable({
        "bServerSide": true,
        'sPaginationType': 'bootstrap',
        "sAjaxSource": '../php/client/json.php?type=outcomes'
    });

//    $("#choose-users-dialog").dialog({
//        autoOpen: false,
//        modal: true,
//        buttons: {
//            Apply: function() {
//                WealthyLaughingDuckControl.setUsers(
//                    $("input[name=user]:checked").map(function() {
//                        return this.value;
//                    })
//                );
//                console.log(WealthyLaughingDuckControl);
//                $( this ).dialog( "close" );
//            },
//            Cancel: function() {
//                $( this ).dialog( "close" );
//            }
//        },
//        close: function() {
//            var element = $('#choose-users-dialog');
//            element.html("");
//        }
//    })

    $('#chooseUsersDialog').on('show', function () {
        $('#chooseUsersDialog .modal-body').html(ich.chooseUsersTemplate(
            {'users': UsersControl.getData()}
        ));
    })

    $('#chooseCategoriesDialog').on('show', function () {
        income = WealthyLaughingDuckControl.parseListIntoForest(
            IncomeCategoryControl.getData());

        if (income == null) {
            $("#incomeCategoryTree").html(ich.errorTemplate({
                'type': 'AJAX',
                'message': 'could not load income category data'
            }));
        } else {
            $("#incomeCategoryTree").jstree({ 
                "json_data" : {
                    "data" : income,
                    "progressive_render" : true
                },
                "plugins" : [ "themes", "json_data" ]
            });
        }

        outcome = WealthyLaughingDuckControl.parseListIntoForest(
            OutcomeCategoryControl.getData());

        if (income == null) {
            $("#outcomeCategoryTree").html(ich.errorTemplate({
                'type': 'AJAX',
                'message': 'could not load outcome category data'
            }));
        } else {
            $("#outcomeCategoryTree").jstree({ 
                "json_data" : {
                    "data" : outcome,
                    "progressive_render" : true
                },
                "plugins" : [ "themes", "json_data" ]
            });
        }
    });
    
    $('form[data-async]').live('submit', function(event) {
        console.log('submitted');
//        var $form = $(this);
//        var $target = $($form.attr('data-target'));
// 
//        $.ajax({
//            type: $form.attr('method'),
//            url: "../php/client/json.php",
//            data: $form.serialize(),
// 
//            success: function(data, status) {
//                $target.html(data);
//            }
//        });
 
        event.preventDefault();
    });

});
