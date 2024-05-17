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



