function previewFile() {
    var preview = $('#previewImage'); //selects the query named img
    var file = document.querySelector('input[type=file]').files[0];
    var reader = new FileReader();

    reader.onloadend = function () {
        preview.attr("src", reader.result);
        preview.attr("class", "previewImage");
    }

    if (file) {
        reader.readAsDataURL(file); //reads the data as a URL
    } else {
        preview.attr("src", "");
    }
}