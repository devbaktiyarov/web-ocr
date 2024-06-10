var input = document.querySelector("#file-input");
var output = document.querySelector("#file-output");
var submitButton = document.querySelector("#submit-button");
var progressBar = document.querySelectorAll("#progress-status");

var imageList = document.querySelector("#image-list");

var browseButton = document.querySelector("#browse-button");
var dropArea = document.querySelector("#drop-area");

var pageTitle = document.querySelector("#page-title");

let imagesArray = [];
let dataTransfer = new DataTransfer()

browseButton.onclick = () => {
    input.click();
};


submitButton.addEventListener("click", showProgress);

dropArea.addEventListener("dragover", (e) => {
    e.preventDefault();
    dropArea.classList.add("active");
});

dropArea.addEventListener("dragleave", () => {
    dropArea.classList.remove("active");
});


// !!!!! get rid off duplicating
dropArea.addEventListener("drop", (e) => {
    e.preventDefault();
    dropArea.classList.remove("active");
    // input.files = e.dataTransfer.files;
    console.log("Dropped");
    uploadFiles(e.dataTransfer.files);
    const files = input.files
    for (let i = 0; i < files.length; i++) {
        console.log(files[0]);
        imagesArray.push(files[i])
    }
    dropArea.classList.remove("active");
    displayImages();  

    if(imagesArray.length != 0) {
        submitButton.disabled = false;
    }
    imagesArray = []; 
});

function uploadFiles(files) {
    for (let i = 0; i < files.length; i++) {
        dataTransfer.items.add(files[i])
    }

    input.files = dataTransfer.files
    console.log(input.files)
}


input.addEventListener("change", () => {
    const files = input.files;
    for (let i = 0; i < files.length; i++) {
        console.log(files[0]);
        imagesArray.push(files[i])
    }
    displayImages();  

    if(imagesArray.length != 0) {
        submitButton.disabled = false;
    }
    imagesArray = []; 
});


function displayImages() {
    let images = ""
    imagesArray.forEach((image, index) => {
        images += `<img src="${URL.createObjectURL(image)}" class="img-fluid border" alt="image" style="height: 100px">
                `
    })
    output.innerHTML = images;
    imageList.classList.remove('d-none');
    // dropArea.style= "height:250px"

}

function showProgress() {
    progressBar.classList.remove('d-none');
}



const languageNames = {
    'en': {
        
        'ara': 'Arabic', 'aze': 'Azerbaijani', 'bel': 'Belarusian', 'ben': 'Bengali', 
        'bul': 'Bulgarian', 'ces': 'Czech', 'chi_sim': 'Chinese - Simplified', 
        'chi_tra': 'Chinese - Traditional', 'dan': 'Danish', 'eng': 'English', 
        'equ': 'Math / equation detection module', 'est': 'Estonian', 'fas': 'Persian', 
        'fin': 'Finnish', 'fra': 'French', 'gle': 'Irish', 'grc': 'Greek', 'hin': 'Hindi', 
        'hrv': 'Croatian', 'hun': 'Hungarian', 'hye': 'Armenian', 'ind': 'Indonesian', 
        'ita': 'Italian', 'jpn': 'Japanese', 'kat': 'Georgian', 'kaz': 'Kazakh', 
        'kir': 'Kyrgyz', 'kor': 'Korean', 'lat': 'Latin', 'lav': 'Latvian', 'mon': 'Mongolian', 
        'nld': 'Dutch', 'pol': 'Polish', 'por': 'Portuguese', 'rus': 'Russian', 'spa': 'Spanish', 
        'syr': 'Syriac', 'tat': 'Tatar', 'tgk': 'Tajik', 'ukr': 'Ukrainian', 'uzb': 'Uzbek', 
        'uzb_cyrl': 'Uzbek - Cyrilic'
    },
    'ru': {
       
        'ara': 'арабский', 'aze': 'азербайджанский', 'bel': 'белорусский', 'ben': 'бенгальский', 
        'bul': 'болгарский', 'ces': 'чешский', 'chi_sim': 'китайский (упрощенный)', 
        'chi_tra': 'китайский (традиционный)', 'dan': 'датский', 'eng': 'английский', 
        'equ': 'модуль обнаружения математических/уравнений', 'est': 'эстонский', 'fas': 'персидский', 
        'fin': 'финский', 'fra': 'французский', 'gle': 'ирландский', 'grc': 'греческий', 'hin': 'хинди', 
        'hrv': 'хорватский', 'hun': 'венгерский', 'hye': 'армянский', 'ind': 'индонезийский', 
        'ita': 'итальянский', 'jpn': 'японский', 'kat': 'грузинский', 'kaz': 'казахский', 
        'kir': 'киргизский', 'kor': 'корейский', 'lat': 'латинский', 'lav': 'латышский', 'mon': 'монгольский', 
        'nld': 'голландский', 'pol': 'польский', 'por': 'португальский', 'rus': 'русский', 'spa': 'испанский', 
        'syr': 'сирийский', 'tat': 'татарский', 'tgk': 'таджикский', 'ukr': 'украинский', 'uzb': 'узбекский', 
        'uzb_cyrl': 'узбекский - кириллица'
    },
    'kg': {
        
        'ara': 'арабча', 'aze': 'азербайжан', 'bel': 'беларусча', 'бен': 'бенгалча',
        'bul': 'болгарча', 'ces': 'чех', 'chi_sim': 'кытайча (жөнөкөйлөштүрүлгөн)',
        'chi_tra': 'кытайча (салттуу)', 'dan': 'даниялык', 'англ.': 'англисче',
        'equ': 'математика/теңдемелерди аныктоо модулу', 'est': 'Эстония', 'fas': 'Перси',
        'fin': 'Фин', 'fra': 'французча', 'gle': 'ирландча', 'grc': 'грекче', 'hin': 'хиндиче',
        'hrv': 'Хорват', 'hun': 'Венгрия', 'hye': 'Армян', 'ind': 'Индонезия',
        'ita': 'итальянча', 'jpn': 'жапонча', 'kat': 'грузинче', 'kaz': 'казакча',
        'kir': 'кыргыз', 'kor': 'корей', 'лат': 'латын', 'lav': 'латвия', 'mon': 'монгол',
        'nld': 'голландча', 'pol': 'полякча', 'por': 'португалча', 'rus': 'орусча', 'spa': 'испанча',
        'syr': 'сириялык', 'тат': 'татар', 'tgk': 'тажик', 'ukr': 'украин', 'uzb': 'өзбек',
        'uzb_cyrl': 'өзбек - кириллица'
        }
};


function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

const lang = getQueryParam('lang') || 'ru';

const localesSelect = document.getElementById('language');
function populateLocales(language) {
    // localesSelect.innerHTML = '';
    const names = languageNames[language];
    for (const [code, name] of Object.entries(names)) {
        const option = document.createElement('option');
        option.value = code;
        option.textContent = name;
        localesSelect.appendChild(option);
    }
}

populateLocales(lang);

const form = document.getElementById('uploadForm');

const loading = document.getElementById('loading');
            
form.addEventListener('submit', function() {
    if(localStorage.getItem('l') == 'ru') {
        alert('Не обновялйте страницу')
    } else if(localStorage.getItem('l') == 'kg') {
        alert('Баракты жаңыртпаңыз')
    } else {
        alert("Don't refresh the page")
    }  
});







