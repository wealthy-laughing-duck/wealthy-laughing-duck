jQuery.validator.addMethod("money", function(value, element) {
    return this.optional(element) || /^(\d{1,6})(\.\d{1,2})?$/.test(value);
}, "Must be proper currency format 'dddddd.dd' (d - digit)");

$(document).ready( function() {
    TemplateEngine.fetchConcatenatedTemplates();
    TemplateManager.initAllTemplates();
});
