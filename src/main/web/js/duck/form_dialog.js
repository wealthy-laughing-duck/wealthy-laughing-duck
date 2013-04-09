function FormDialog(categoryControl, userControl, type) {
    this.categoryControl = categoryControl;
    this.userControl = userControl;
    this.type = type;
}

FormDialog.prototype.getSelector = function() {
    return '#' + this.type + 'FormDialog';
}

FormDialog.prototype.getTemplate = function() {
    return this.type + 'FormTemplate';
}

FormDialog.prototype.getCapitalisedType = function()
{
    return this.type.charAt(0).toUpperCase() + this.type.slice(1);
}

FormDialog.prototype.init = function() {
    var _self = this;

    $(this.getSelector()).html(ich[this.getTemplate()]({
        'currency': MainControl.getCurrency(),
        'users': this.userControl.getData(),
        'categories': this.categoryControl.getData()
    }));

    $(this.getSelector() + ' form').validate({
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

    $(this.getSelector() + ' form').on( "submit", function( event ) {
        var form = $(this);
        // form validates
        if (form.validate().checkForm()) {
            $.ajax({
                type: form.attr('method'),
                url: "../php/client/json.php",
                data: form.serialize(),
                success: function(data, status) {
                    $(_self.getSelector()).modal('hide');
                    bootbox.alert(_self.getCapitalisedType() + " has been successfully added.");
                }
            });
        }
        event.preventDefault();
    });
};

var IncomeFormDialog = new FormDialog(IncomeCategoryControl, UsersControl, "income");
var OutcomeFormDialog = new FormDialog(OutcomeCategoryControl, UsersControl, "outcome");
