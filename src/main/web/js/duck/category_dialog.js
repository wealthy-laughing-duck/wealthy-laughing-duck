// see more about jstree at http://luban.danse.us/jazzclub/javascripts/jquery/jsTree/reference/

function CategoryDialogTab(categoryControl, selector) {
    this.categoryControl = categoryControl;
    this.selector = selector;
}

CategoryDialogTab.prototype.getTree = function() {
    return MainControl.parseListIntoTree(
        this.categoryControl.getData());
};

CategoryDialogTab.prototype.render = function() {
    var tree = this.getTree();
    var _self = this;

    if (tree == null) {
        $(this.selector).html(TemplateManager.getRenderedError('AJAX'));
    } else {
        $(this.selector).jstree({
            "json_data" : {
                "data" : tree,
                "progressive_render" : true
            },
            "plugins" : [ "themes", "json_data", "dnd", "crrm", "ui" ]
        })
        .bind("open_node.jstree close_node.jstree", function (event, data) {
            var state = event.type == "open_node" ? "open" : "closed";
            _self.categoryControl.setState(data.rslt.obj.attr("id"), state);
        })
        .bind("move_node.jstree", function (event, data) {
            var id = data.rslt.o.attr("id");
            var parent_id = data.rslt.np.attr("id");
            // data.rslt.op.attr("id") - old parent
            // data.rslt.np.attr("id") - new parent
            // data.rslt.cp - current posision
            console.log(id, parent_id, event, data);
        })
        .bind("rename_node.jstree", function (event, data) {
            var id = data.rslt.obj.attr("id");
            var new_name = data.rslt.name;
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "../php/client/json.php",
                data: {
                    action: "renameNode",
                    id: id,
                    new_name: new_name
                }
            }).done(function(response) {
                _self.categoryControl.renameNode(id, new_name);
            }).fail(function(response) {
                bootbox.alert("rename node failed.");
            });
        })
        .bind("create_node.jstree", function (event, data) {
            var parent_id = data.rslt.parent;
            if (parent_id == -1) { parent_id = null }
            var name = "some-name";
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "../php/client/json.php",
                data: {
                    action: "createNode",
                    parent_id: parent_id,
                    name: name,
                    type: _self.categoryControl.getType()
                }
            }).done(function(response) {
                new_id = response;
                // update tree component
                data.rslt.obj.attr("id", new_id);
                _self.categoryControl.addNode(new_id, "some name", parent_id);
            }).fail(function(response) {
                bootbox.alert("create node failed.");
            });
        })
        .bind("delete_node.jstree", function (event, data) {
            console.log(event, data)
        });
    }
};

var CategoryDialog = {
    incomeTab: new CategoryDialogTab(IncomeCategoryControl, "#incomeCategoryTree"),
    outcomeTab: new CategoryDialogTab(OutcomeCategoryControl, "#outcomeCategoryTree"),
    selector: "#chooseCategoriesDialog",
    getActiveTab: function() {
        var tab = $(this.selector + " .tab-pane.active").attr("id");
        return tab.substring(0, tab.length - 3);
    },
    init: function() {
        $(this.selector).html(ich.chooseCategoriesTemplate());

        var _self = this;

        $(this.selector).on('show', function () {
            _self.incomeTab.render();
            _self.outcomeTab.render();
        });

        $(this.selector + " .action-create").bind('click', function() {
            $("#" + _self.getActiveTab() + "CategoryTree").jstree("create", null, "last", {
                data: "category-name"
            });
        });

        $(this.selector + " .action-rename").bind('click', function() {
            $("#" + _self.getActiveTab() + "CategoryTree").jstree("rename");
        });

        $(this.selector + " .action-delete").bind('click', function() {
            $("#" + _self.getActiveTab() + "CategoryTree").jstree("remove");
        });

        $(this.selector + " a").live("dblclick", function(evt) {
            $.jstree._reference(_self.getActiveTab() + 'CategoryTree').rename(evt.currentTarget);
        });
    }
}
