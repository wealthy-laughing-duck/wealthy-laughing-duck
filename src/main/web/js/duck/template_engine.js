var TemplateEngine = {
    path: 'templates/',
    templates: ['containers', 'forms', 'filters', 'modals', 'misc'],
    fetchTemplate: function(path) {
        $.ajax({
            type: 'GET',
            dataType: 'text',
            async: false,
            url: path
        }).done(function(response) {
            $('body').append(response);
        });
    },
    fetchAllTemplates: function() {
        var index;
        for (index = 0; index < this.templates.length; ++index) {
            this.fetchTemplate(this.path + this.templates[index] + '.ich');
        }
        ich.grabTemplates();
    },
    concatenated_templates: 'templates.ich',
    fetchConcatenatedTemplates: function() {
        this.fetchTemplate(this.concatenated_templates);
        ich.grabTemplates();
    }
}
