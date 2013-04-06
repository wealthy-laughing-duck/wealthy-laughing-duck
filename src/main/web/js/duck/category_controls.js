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
