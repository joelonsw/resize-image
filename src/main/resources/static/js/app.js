let resizeBtn = document.getElementById("resizeBtn");
let resizeForm = document.getElementById("resizeForm");
let progressBar = document.getElementById("progressBar");
let afterResize = document.getElementById("afterResize");
let downloadBtn = document.getElementById("downloadBtn");

resizeBtn.onclick = () => {
    resizeForm.style.display = "none";
    afterResize.style.display = "flex";

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
            }, 425);
        })
    }
}

downloadBtn.onclick = () => {
    console.log("download img")
}