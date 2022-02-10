let resizeBtn = document.getElementById("resizeBtn");
let resizeForm = document.getElementById("resizeForm");
let progressBar = document.getElementById("progressBar");
let afterResize = document.getElementById("afterResize");
let downloadBtn = document.getElementById("downloadBtn");
let imageFileInput = document.getElementById("formFile image");
let imageFile = null;
let allowedImageFormat = ["image/jpeg", "image/jpg", "image/png", "image/gif"];
let sizeOption = document.getElementById("sizeOption");
let selectedSize = "800";
let resizedFileName = "";

imageFileInput.addEventListener("change", function() {
    if (allowedImageFormat.includes(this.files[0].type)) {
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

    var requestOptions = {
        method: 'POST',
        body: formData
    };

    fetch("/resize", requestOptions)
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
