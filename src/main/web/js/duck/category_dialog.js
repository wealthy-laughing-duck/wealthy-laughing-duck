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
        $(this.selector).html(MainControl.getAjaxError());
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
            console.log(id, parent_id, event, data);
        })
        .bind("rename_node.jstree", function (event, data) {
            var id = data.rslt.obj.attr("id");
            var new_name = data.rslt.name;
            _self.categoryControl.renameNode(id, new_name);
            console.log(id, new_name, event, data);
        })
        .bind("create_node.jstree", function (event, data) {
            var parent_id = data.rslt.parent;
            var new_id = 10;
            // update tree component
            data.rslt.obj.attr("id", new_id);
            // update data holder
            _self.categoryControl.addNode(new_id, "some name", parent_id);
            console.log(parent_id, event, data);
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
    initChooseCategoriesDialog: function() {
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

        $(this.selector + " a").live("dblclick", function(evt) {
            $.jstree._reference(_self.getActiveTab() + 'CategoryTree').rename(evt.currentTarget);
        });
    }
}
