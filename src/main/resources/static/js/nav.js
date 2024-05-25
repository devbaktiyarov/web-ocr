$(document).ready(function() {
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption != ''){
            window.location.replace(window.location.href.split('?')[0] + '?lang=' + selectedOption);
            localStorage.setItem("l", selectedOption);
        }
    });
});

$(document).ready(function() {
    $("#locales").val(localStorage.getItem("l"));
});




console.log(localStorage.getItem("l"));

