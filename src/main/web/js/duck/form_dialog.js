function FormDialog(categoryControl, userControl, selector, template) {
    this.categoryControl = categoryControl;
    this.userControl = userControl;
    this.selector = selector;
    this.template = template;
}

FormDialog.prototype.init = function() {
    var _self = this;

    $(this.selector).html(ich[this.template]({
        'currency': MainControl.getCurrency(),
        'users': this.userControl.getData(),
        'categories': this.categoryControl.getData()
    }));

    $(this.selector + ' form').validate({
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

    $(this.selector + ' form').on( "submit", function( event ) {
        var form = $(this);
        // form validates
        if (form.validate().checkForm()) {
            $.ajax({
                type: form.attr('method'),
                url: "../php/client/json.php",
                data: form.serialize(),
                success: function(data, status) {
                    $(_self.selector).modal('hide');
                    bootbox.alert("Income has been successfully added.");
                }
            });
        }
        event.preventDefault();
    });
};

var IncomeFormDialog = new FormDialog(IncomeCategoryControl, UsersControl, "#incomeFormDialog", "incomeFormTemplate");
var OutcomeFormDialog = new FormDialog(OutcomeCategoryControl, UsersControl, "#outcomeFormDialog", "outcomeFormTemplate");
