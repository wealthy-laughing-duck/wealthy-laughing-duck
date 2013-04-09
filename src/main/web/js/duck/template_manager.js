var TemplateManager = {
    initAllTemplates: function() {
        this.renderTemplates();
        UserDialog.initChooseUsersDialog();
        CategoryDialog.initChooseCategoriesDialog();
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
    getRenderedError: function(type) {
        return ich.errorTemplate({
            'type': type,
            'message': 'could not load data from server'
        })
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
    initIncomeFormDialog: function() {
        $("#incomeFormDialog").html(ich.incomeFormTemplate({
            'currency': MainControl.getCurrency(),
            'users': UsersControl.getData(),
            'categories': IncomeCategoryControl.getData()
        }));

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
        $("#outcomeFormDialog").html(ich.outcomeFormTemplate({
            'currency': MainControl.getCurrency(),
            'users': UsersControl.getData(),
            'categories': OutcomeCategoryControl.getData()
        }));

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
