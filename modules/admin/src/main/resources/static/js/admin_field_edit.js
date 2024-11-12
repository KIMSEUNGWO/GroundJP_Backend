
window.addEventListener('load', () => {
    let size = document.querySelectorAll('.size');
    size.forEach(el => {
        el.addEventListener('keyup', e => {
            if (!isNumber(el.value)) {
               el.value = '';
               return;
            }
            // 세자리수까지 허용
            if (el.value > 3) {
                el.value = el.value.slice(0, 3);
            }
        })
    })

    let form = document.querySelector('form');
    form.addEventListener('submit', (e) => {

        e.preventDefault();

        let isImages = isImagesAndDeleteImages();
        let isTitle = isFieldTitle();
        let isRegion = isFieldRegion();
        let isAddress = isFieldAddress();
        let isSize = isFieldSize();
        let isOption = isFieldOption();
        let isDetails = isFieldDetails();

        if (isImages && isTitle && isRegion && isAddress && isSize && isOption && isDetails) {
            form.submit();
        }
    })

})

function isNumber(value) {
    return !isNaN(value) && value != '';
}

function isImagesAndDeleteImages() {
    let imgBox = document.querySelectorAll('div.imgBox');

    let imgFiles = document.querySelector('input[type="file"]');
    let files = imgFiles.files;
    if (files.length != 0) {
        return true;
    }
    let errorTag = document.querySelector('.errorImages');
    if (imgBox.length == 0) {
        errorTag.innerHTML = '사진을 1장이상 채워주세요';
        return false;
    }
    return true;
}



function isFieldTitle() {
    let title = document.querySelector('input[name="fieldName"]');
    let text = title.value.replaceAll(' ', '');
    let errorTag = document.querySelector('#errorFileName');
    if (text == null || text.length < 5) {
        errorTag.innerHTML = '구장 이름을 정확히 적어주세요';
        return false;
    }
    errorTag.innerHTML = '';
    return true;
}

function isFieldRegion() {
    let region = document.querySelector('input[name="region"]:checked');
    let errorTag = document.querySelector('#errorRegion');
    if (region == null) {
        errorTag.innerHTML = '지역을 설정해주세요';
        return false;
    }
    errorTag.innerHTML = '';
    return true;
}

function isFieldAddress() {
    let address = document.querySelector('input[name="fieldAddress"]');
    let text = address.value.replaceAll(' ', '');
    let errorTag = document.querySelector('#errorFieldAddress');
    if (text == null || text.length < 10) {
        errorTag.innerHTML = '주소를 정확하게 작성해주세요';
        return false;
    }
    errorTag.innerHTML = '';
    return true;
}

function isFieldSize() {
    let xSize = document.querySelector('input[name="xSize"]');
    let ySize = document.querySelector('input[name="ySize"]');
    let errorTag = document.querySelector('#errorSize');

    if (!isNumber(xSize.value) || !isNumber(ySize.value) || Number(xSize.value) < 10 || Number(xSize.value) > 200 || Number(ySize.value) < 10 || Number(ySize.value) > 200) {
        errorTag.innerHTML = '200 이하의 숫자를 입력해주세요'
        return false;
    }
    errorTag.innerHTML = '';
    return true;
}

function isFieldOption() {
    let parking = isParking();
    let toilet = isToilet();
    let shower = isShower();
    return parking && toilet && shower;
}

function isFieldDetails() {
    let details = document.querySelector('textarea[name="fieldDetails"]');
    let text = details.value.replaceAll(' ', '');
    let errorTag = document.querySelector('#errorDetails');

    if (text.length == 0) {
        errorTag.innerHTML = '구장 특이사항을 작성해주세요';
        return false;
    }
    errorTag.innerHTML = '';
    return true;
}

function isParking() {
    let parking = document.querySelector('input[name="parking"]:checked');
    let errorTag = document.querySelector('#errorParking');
    if (parking == null) {
        errorTag.innerHTML = '주차장 여부를 설정해주세요';
        return false;
    }
    errorTag.innerHTML = '';
    return true;
}

function isToilet() {
    let toilet = document.querySelector('input[name="toilet"]:checked');
    let errorTag = document.querySelector('#errorToilet');
    if (toilet == null) {
        errorTag.innerHTML = '화장실 여부를 설정해주세요';
        return false;
    }
    errorTag.innerHTML = '';
    return true;
}

function isShower() {
    let shower = document.querySelector('input[name="shower"]:checked');
    let errorTag = document.querySelector('#errorShower');
    if (shower == null) {
        errorTag.innerHTML = '샤워장 여부를 설정해주세요';
        return false;
    }
    errorTag.innerHTML = '';
    return true;
}

// 이미지 Preview 관리
function printPreview(imageFiles) {
    let preview = document.querySelector('.preview');

    let temp = '';
    for (let i=0;i<imageFiles.length;i++){
        temp += createImgBox(imageFiles[i]);
    }
    preview.innerHTML += temp;
}