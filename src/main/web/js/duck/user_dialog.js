var UserDialog = {
    selector: '#chooseUsersDialog',
    initChooseUsersDialog: function() {
        var _self = this;
        var users = UsersControl.getData();

        $(this.selector).on('show', function () {
            if (users == null) {
                $(_self.selector + ' .modal-body').html(TemplateManager.getRenderedError('AJAX'));
            } else {
                $(_self.selector + ' .modal-body').html(ich.UserCheckboxTemplate({
                    'users': users
                }));
            }
        });

        $(this.selector + ' .btn-primary').bind('click', function() {
            var chosen = $("input[name=user]:checked").map(function() {
                return this.value;
            });
            UsersControl.setChosen(chosen);
            $(_self.selector).modal('hide');
        });
    }
}
