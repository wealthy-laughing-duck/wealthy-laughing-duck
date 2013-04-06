var WealthyLaughingDuckControl = {
    currency: 'zł',
    getCurrency: function() {
        return this.currency;
    },
    parseListIntoTree: function(categories) {
        if (categories == null)
            return null;

        var itemsByID = [];

        categories.forEach(function(item) {
            itemsByID[item.id] = {
                data: {title: item.name},
                attr: {id: item.id},
                parentID: item.parent_id,
                state: item.state
            };
        });

        itemsByID.forEach(function(item) {
            if(item.parentID !== null) {
                if (typeof itemsByID[item.parentID].children === "undefined") {
                    itemsByID[item.parentID].children = [];
                }
                itemsByID[item.parentID].children.push(item);
            }
        });

        var roots = itemsByID.filter(function(item) {
            return item.parentID === null;
        });

        itemsByID.forEach(function(item) {
            delete item.parentID;
        });

        return roots;
    }
};
