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
