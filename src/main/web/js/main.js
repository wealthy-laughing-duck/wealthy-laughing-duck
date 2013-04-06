jQuery.validator.addMethod("money", function(value, element) {
    return this.optional(element) || /^(\d{1,3})(\.\d{1,2})?$/.test(value);
}, "Must be proper currency format 0.99");

$(document).ready( function() {
    TemplateEngine.fetchConcatenatedTemplates();
    TemplateManager.initAllTemplates();
});
