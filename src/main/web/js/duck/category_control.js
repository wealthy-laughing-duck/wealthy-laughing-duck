function CategoryControl(type) {
    this.type = type;
    this.data = null;
}

CategoryControl.prototype.fetchData = function() {
    $.ajax({
        type: "GET",
        dataType: "json",
        context: this,
        async: false,
        url: "../php/client/json.php",
        data: {
            action: this.type + "Categories"
        }
    }).done(function(response) {
        this.data = response;
    });
};

CategoryControl.prototype.getType = function() {
    return this.type;
}

CategoryControl.prototype.getData = function() {
    if (this.data == null) {
        this.fetchData();
    }
    return this.data;
};

CategoryControl.prototype.addNode = function(id, name, parent_id) {
    if (parent_id == -1) parent_id = null;
    var node = {
        "id": id,
        "name": name,
        "parent_id": parent_id
    };
    this.data.push(node);
};

CategoryControl.prototype.getNode = function(id) {
    var found = $(this.getData()).map(function() {
        return (this.id == id) ? this : null;
    });
    return found.length ? found[0] : null;
};

CategoryControl.prototype.renameNode = function(id, new_name) {
    var node = this.getNode(id);
    node.name = new_name;
};

CategoryControl.prototype.setState = function(id, value) {
    var node = this.getNode(id);
    if (node != null) {
        node.state = value;
        return true;
    } else {
        return false;
    }
};

var IncomeCategoryControl = new CategoryControl("income");
var OutcomeCategoryControl = new CategoryControl("outcome");
