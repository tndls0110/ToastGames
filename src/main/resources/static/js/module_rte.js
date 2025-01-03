// setting > write documents
var configDocument = {
    toolbar: "document",
    allowScriptCode: true
};

// setting > no toolbar
var configNone = {
    toolbar: "none",
    showPlusButton: false,
    maxTextLength: 1000,
    editorResizeMode: "none"
};

// run editor
var editor1 = new RichTextEditor("#div_editor1", configNone);
var editor2 = new RichTextEditor("#div_editor2", configDocument);