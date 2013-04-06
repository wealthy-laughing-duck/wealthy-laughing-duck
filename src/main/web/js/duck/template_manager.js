var TemplateManager = {
    initAllTemplates: function() {
        this.renderTemplates();
        this.initDialogs();
        this.initChooseUsersDialog();
        this.initChooseCategoriesDialog();
        this.initIncomeFormDialog();
        this.initOutcomeFormDialog();
        this.bindMenuOptions();
    },
    getMainContainerSelector: function () {
        return '.container#main';
    },
    renderMainContainerTemplate: function (template, options) {
        if(typeof(options)==='undefined') options = {};
        var code = "var html = ich." + template + "(options)";
        eval(code);
        $(this.getMainContainerSelector()).html(html);
    },
    renderTemplates: function() {
        // init main container body
        $(this.getMainContainerSelector()).html(ich.homepageTemplate());

        // append modal containers
        $('body').append(ich.modalsContainerTemplate());

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
    initDialogs: function() {
        // add outcome form: render
        $("#outcomeFormDialog").html(ich.outcomeFormTemplate({
            'currency': MainControl.getCurrency(),
            'users': UsersControl.getData(),
            'categories': OutcomeCategoryControl.getData()
        }));

        // add income form: render
        $("#incomeFormDialog").html(ich.incomeFormTemplate({
            'currency': MainControl.getCurrency(),
            'users': UsersControl.getData(),
            'categories': IncomeCategoryControl.getData()
        }));

        // choose users: render
        $("#chooseUsersDialog").html(ich.chooseUsersTemplate());

        // choose categories: render
        $("#chooseCategoriesDialog").html(ich.chooseCategoriesTemplate());
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
    getActiveCategoryTab: function() {
        var tab = $("#chooseCategoriesDialog .tab-pane.active").attr("id");
        return tab.substring(0, tab.length - 3);
    },
    initChooseCategoriesDialog: function() {
        $('#chooseCategoriesDialog').on('show', function () {
            income = MainControl.parseListIntoTree(
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
                    "plugins" : [ "themes", "json_data", "dnd", "crrm", "ui" ]
                })
                .bind("open_node.jstree close_node.jstree", function (event, data) {
                    var state = event.type == "open_node" ? "open" : "closed";
                    IncomeCategoryControl.setState(data.rslt.obj.attr("id"), state);
                })
                .bind("move_node.jstree", function (event, data) {
                    var id = data.rslt.o.attr("id");
                    var parent_id = data.rslt.np.attr("id");
                    console.log(id, parent_id, event, data);
                })
                .bind("rename_node.jstree", function (event, data) {
                    var id = data.rslt.obj.attr("id");
                    var new_name = data.rslt.name;
                    console.log(id, new_name, event, data);
                })
                .bind("create_node.jstree", function (event, data) {
                    var parent_id = data.rslt.parent;
                    var new_id = 10;
                    data.rslt.obj.attr("id", new_id);
                    console.log(parent_id, event, data);
                });
            }

            outcome = MainControl.parseListIntoTree(
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
                    "plugins" : [ "themes", "json_data", "dnd", "crrm" ]
                })
                .bind("open_node.jstree close_node.jstree", function (event, data) {
                    var state = event.type == "open_node" ? "open" : "closed";
                    OutcomeCategoryControl.setState(data.rslt.obj.attr("id"), state);
                })
                .bind("move_node.jstree", function (event, data) {
                    var id = data.rslt.o.attr("id");
                    var parent_id = data.rslt.np.attr("id");
                    console.log(id, parent_id, event, data);
                })
                .bind("rename_node.jstree", function (event, data) {
                    var id = data.rslt.obj.attr("id");
                    var new_name = data.rslt.name;
                    console.log(id, new_name, event, data);
                })
                .bind("create_node.jstree", function (event, data) {
                    var parent_id = data.rslt.parent;
                    var new_id = 10;
                    data.rslt.obj.attr("id", new_id);
                    console.log(parent_id, event, data);
                });
            }
        });

        var _self = this;
        $("#chooseCategoriesDialog .action-create").bind('click', function() {
            $("#" + _self.getActiveCategoryTab() + "CategoryTree").jstree("create", null, "last", {
                data: "name"
            });
        });

        $("#chooseCategoriesDialog .action-rename").bind('click', function() {
            $("#" + _self.getActiveCategoryTab() + "CategoryTree").jstree("rename");
        });

        $("#chooseCategoriesDialog a").live("dblclick", function(evt) {
            $.jstree._reference(_self.getActiveCategoryTab() + 'CategoryTree').rename(evt.currentTarget);
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
    }
};
