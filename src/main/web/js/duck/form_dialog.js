function FormDialog(categoryControl, userControl, type) {
    this.categoryControl = categoryControl;
    this.userControl = userControl;
    this.type = type;
}

FormDialog.prototype.getSelector = function() {
    return '#' + this.type + 'FormDialog';
}

FormDialog.prototype.getFormInput = function(input) {
    return this.getSelector() + " #" + input;
}

FormDialog.prototype.getTemplate = function() {
    return 'formTemplate';
}

FormDialog.prototype.clearFormInputs = function() {
    $(this.getFormInput("amount")).val("");
    $(this.getFormInput("username")).val("");
    $(this.getFormInput("category_id")).val("");
    $(this.getFormInput("comment")).val("");    
}

FormDialog.prototype.clearFormLayout = function() {
    $(this.getSelector() + " label.error").hide();
    $(this.getSelector() + " .error").removeClass("error");
    $(this.getSelector() + " .success").removeClass("success");
}

FormDialog.prototype.getCapitalisedType = function() {
    return this.type.charAt(0).toUpperCase() + this.type.slice(1);
}

FormDialog.prototype.init = function() {
    var _self = this;

    // render templates
    $(this.getSelector()).html(ich[this.getTemplate()]({
        'currency': MainControl.getCurrency(),
        'users': this.userControl.getData(),
        'categories': this.categoryControl.getData(),
        'type': this.type
    }));

    // validate form
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

    // clear input data each time the dialog is shown
    $(this.getSelector()).on('show', function () {
        _self.clearFormInputs();
        _self.clearFormLayout();
    });

    // make ajax call after form is validated
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
