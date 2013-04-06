jQuery.validator.addMethod("money", function(value, element) {
    return this.optional(element) || /^(\d{1,3})(\.\d{1,2})?$/.test(value);
}, "Must be proper currency format 0.99");

var TemplateManager = {
    initAllTemplates: function() {
        this.initOtherTemplates();
        this.initChooseUsersDialog();
        this.initChooseCategoriesDialog();
        this.initIncomeFormDialog();
        this.initOutcomeFormDialog();
        this.bindMenuOptions();
    },
    getMainContainerSelector: function () {
        return '.container#main';
    },
    initChooseUsersDialog: function() {
        $('#chooseUsersDialog').on('show', function () {
            $('#chooseUsersDialog .modal-body').html(ich.UserCheckboxTemplate({
                'users': UsersControl.getData()
            }));
        });
        $('#chooseUsersDialog .btn-primary').bind('click', function() {
            var chosen = $("input[name=user]:checked").map(function() {
                return this.value;
            });
            UsersControl.setChosen(chosen);
            $('#chooseUsersDialog').modal('hide');
        });
    },
    initChooseCategoriesDialog: function() {
        $('#chooseCategoriesDialog').on('show', function () {
            income = WealthyLaughingDuckControl.parseListIntoTree(
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
                })
                .bind("open_node.jstree close_node.jstree", function (event, data) {
                    var state = event.type == "open_node" ? "open" : "closed";
                    IncomeCategoryControl.setState(data.rslt.obj.attr("id"), state)
                });
            }

            outcome = WealthyLaughingDuckControl.parseListIntoTree(
                OutcomeCategoryControl.getData());

            if (outcome == null) {
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
                })
                .bind("open_node.jstree close_node.jstree", function (event, data) {
                    var state = event.type == "open_node" ? "open" : "closed";
                    OutcomeCategoryControl.setState(data.rslt.obj.attr("id"), state)
                });
            }
        });
    },
    initIncomeFormDialog: function() {
        $('#incomeFormDialog form').validate(
        {
            rules: {
                amount: {
                    money: true
                },
                comment: {
                    required: false
                }
            },
            highlight: function(element) {
                $(element).closest('.control-group')
                .removeClass('success').addClass('error');
            },
            success: function(element) {
                element
                .addClass('valid').closest('.control-group')
                .removeClass('error').addClass('success');
            }
        });

        $('#incomeFormDialog form').on( "submit", function( event ) {
            var form = $(this);
            // form validates
            if (form.validate().checkForm()) {
                $.ajax({
                    type: form.attr('method'),
                    url: "../php/client/json.php",
                    data: form.serialize(),
                    success: function(data, status) {
                        $('#incomeFormDialog').modal('hide');
                        bootbox.alert("Income has been successfully added.");
                    }
                });
            }
            event.preventDefault();
        });
    },
    initOutcomeFormDialog: function() {
        $('#outcomeFormDialog form').validate({
            rules: {
                amount: {
                    money: true
                },
                comment: {
                    required: false
                }
            },
            highlight: function(element) {
                $(element).closest('.control-group')
                .removeClass('success').addClass('error');
            },
            success: function(element) {
                element
                .addClass('valid').closest('.control-group')
                .removeClass('error').addClass('success');
            }
        });

        $('#outcomeFormDialog form').on( "submit", function( event ) {
            var form = $(this);
            // form validates
            if (form.validate().checkForm()) {
                $.ajax({
                    type: form.attr('method'),
                    url: "../php/client/json.php",
                    data: form.serialize(),
                    success: function(data, status) {
                        $('#outcomeFormDialog').modal('hide');
                        bootbox.alert("Outcome has been successfully added.");
                    }
                });
            }
            event.preventDefault();
        });
    },
    initOtherTemplates: function() {
        // init main container body
        $(this.getMainContainerSelector()).html(ich.homepageTemplate());

        // append modal containers
        $('body').append(ich.modalsContainerTemplate());

        // add outcome form: render
        $("#outcomeFormDialog").html(ich.outcomeFormTemplate({
            'currency': WealthyLaughingDuckControl.getCurrency(),
            'users': UsersControl.getData(),
            'categories': OutcomeCategoryControl.getData()
        }));

        // add income form: render
        $("#incomeFormDialog").html(ich.incomeFormTemplate({
            'currency': WealthyLaughingDuckControl.getCurrency(),
            'users': UsersControl.getData(),
            'categories': IncomeCategoryControl.getData()
        }));

        // choose users: render
        $("#chooseUsersDialog").html(ich.chooseUsersTemplate());

        // choose categories: render
        $("#chooseCategoriesDialog").html(ich.chooseCategoriesTemplate());

        // monthly balance: render
        $("#monthlyBalanceDialog").html(ich.monthlyBalanceTemplate());

        // category total: render
        $("#categoryTotalDialog").html(ich.categoryTotalTemplate());

        // bootstrap menu: dropdown
        $('.dropdown-toggle').dropdown();

        // popover-ize all info buttons
        $('a.btn-info').popover({
            'placement': 'bottom'
        });
    },
    bindMenuOptions: function () {
        $('#menu_homepage').bind('click', $.proxy(function(){
            this.renderMainContainerTemplate('homepageTemplate');
        }, TemplateManager));

        $('#menu_outcome_list').bind('click', $.proxy(function(){
            this.renderMainContainerTemplate('outcomeListTemplate');
            $('#outcomes').dataTable({
                "bServerSide": true,
                'sPaginationType': 'bootstrap',
                "sAjaxSource": '../php/client/json.php?type=outcomes'
            });
        }, TemplateManager));

        $('#menu_income_list').bind('click', $.proxy(function(){
            this.renderMainContainerTemplate('incomeListTemplate');
        }, TemplateManager));

        $('#menu_monthly_balance').bind('click', $.proxy(function(){
            this.renderMainContainerTemplate('monthlyBalanceTemplate');
        }, TemplateManager));

        $('#menu_category_total').bind('click', $.proxy(function(){
            this.renderMainContainerTemplate('categoryTotalTemplate');
        }, TemplateManager));
    },
    renderMainContainerTemplate: function (template, options) {
        if(typeof(options)==='undefined') options = {};
        var code = "var html = ich." + template + "(options)";
        eval(code);
        $(this.getMainContainerSelector()).html(html);
    }
}

var WealthyLaughingDuckControl = {
    currency: 'zł',
    getCurrency: function() {
        return this.currency;
    },
    parseListIntoTree: function(categories) {
        if (categories == null)
            return null;

        var itemsByID = [];

        categories.forEach(function(item) {
            itemsByID[item.id] = {
                data: {title: item.name},
                attr: {id: item.id},
                parentID: item.parent_id,
                state: item.state
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
    data: null,
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
            this.data = response;
            this.setAll(true);
        });
    },
    getData: function() {
        if (this.data == null) {
            this.fetchData();
        }
        return this.data;
    },
    setAll: function(chosen) {
        var index;
        for (index = 0; index < this.data.length; ++index) {
            this.data[index].chosen = chosen;
        }
    },
    setChosen: function(username_list) {
        var index;
        for (index = 0; index < this.data.length; ++index) {
            this.data[index].chosen = ($.inArray(this.data[index].username, username_list) > -1);
        }
    },
    getChosen: function() {
        return $(this.getData()).map(function() {
            return (this.chosen) ? this.username : null;
        });
    }
};

var IncomeCategoryControl = {
    data: null,
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
            this.data = response;
        });
    },
    getData: function() {
        if (this.data == null) {
            this.fetchData();
        }
        return this.data;
    },
    setState: function(id, value) {
        var found = $(this.getData()).map(function() {
            return (this.id == id) ? this : null;
        });
        if (found.length) {
            found[0].state = value;
            return true;
        } else {
            return false;
        }
    }
};

var OutcomeCategoryControl = {
    data: null,
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
            this.data = response;
        });
    },
    getData: function() {
        if (this.data == null) {
            this.fetchData();
        }
        return this.data;
    },
    setState: function(id, value) {
        var found = $(this.getData()).map(function() {
            return (this.id == id) ? this : null;
        });
        if (found.length) {
            found[0].state = value;
            return true;
        } else {
            return false;
        }
    }
};

$(document).ready( function() {
    TemplateEngine.fetchConcatenatedTemplates();
    TemplateManager.initAllTemplates();
});
