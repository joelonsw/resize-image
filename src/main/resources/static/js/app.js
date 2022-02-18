let resizeBtn = document.getElementById("resizeBtn");
let resizeForm = document.getElementById("resizeForm");
let progressBar = document.getElementById("progressBar");
let afterResize = document.getElementById("afterResize");
let downloadBtn = document.getElementById("downloadBtn");
let imageFileInput = document.getElementById("formFile image");
let imageFile = null;
let allowedImageFormat = ["jpg", "jpeg", "png"];
let sizeOption = document.getElementById("sizeOption");
let selectedSize = "800";
let qualityOption = document.getElementById("qualityOption");
let selectedQuality = "automatic";
let resizedFileName = "";

imageFileInput.addEventListener("change", function() {
    if (allowedImageFormat.includes(this.files[0].name.split('.').pop().toLowerCase())) {
        imageFile = this.files[0];
    } else {
        alert("File Type Not Supported");
        imageFile = null;
        imageFileInput.value = "";
    }
})

function selectSize() {
    selectedSize = sizeOption.options[sizeOption.selectedIndex].value;
}

function selectQuality() {
    selectedQuality = qualityOption.options[qualityOption.selectedIndex].value;
}

resizeBtn.onclick = () => {
    if (imageFile == null) {
        alert("Please Select A Picture");
    } else {
        resizeForm.style.display = "none";
        afterResize.style.display = "flex";
        sendImageToServer();
        paintProgressBarWhileWaiting();
    }
}

function sendImageToServer() {
    var formData = new FormData();
    formData.append("image", imageFile);
    formData.append("maxLength", selectedSize);
    formData.append("quality", selectedQuality);

    var requestOptions = {
        method: 'POST',
        body: formData
    };

    fetch("/resize", requestOptions)
        .then(response => handleFetchError(response))
        .then(response => response.text())
        .then(fileName => downloadEnable(fileName))
        .catch(() => alert("Error Occurred During Resizing"));
}

function downloadEnable(filename) {
    resizedFileName = filename;
}

function paintProgressBarWhileWaiting() {
    paintProgressBar("12.5%")()
        .then(paintProgressBar("25%"))
        .then(paintProgressBar("37.5%"))
        .then(paintProgressBar("50%"))
        .then(paintProgressBar("67.5%"))
        .then(paintProgressBar("75%"))
        .then(paintProgressBar("87.5%"))
        .then(paintProgressBar("100%"))
}

const paintProgressBar = (percentage) => {
    return () => {
        return new Promise((resolve) => {
            setTimeout(() => {
                progressBar.style.width = percentage;
                if (percentage == "100%") {
                    downloadBtn.style.display = "block";
                }
                resolve();
            }, 400);
        })
    }
}

downloadBtn.onclick = () => {
    let url = "/download/" + resizedFileName;
    fetch(url)
        .then(response => handleFetchError(response))
        .then(response => response.blob())
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
            a.download = resizedFileName;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
        })
        .catch(() => alert("Error Occurred During Download"));
}

function handleFetchError(response) {
    if (!response.ok) {
        throw Error(response.statusText);
    }
    return response;
}