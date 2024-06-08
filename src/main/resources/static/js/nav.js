document.addEventListener('DOMContentLoaded', function() {
    var locales = document.getElementById('locales');

    locales.addEventListener('change', function() {
        var selectedOption = locales.value;
        if (selectedOption !== '') {
            window.location.replace(window.location.href.split('?')[0] + '?lang=' + selectedOption);
            localStorage.setItem('l', selectedOption);
        }
    });

    // Устанавливаем значение select элемента из localStorage при загрузке страницы
    locales.value = localStorage.getItem('l');
});

console.log(localStorage.getItem('l'));
