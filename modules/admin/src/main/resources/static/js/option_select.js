window.addEventListener('load', function(){
    const optionButtons = document.querySelectorAll('button.option');
    const optionList = document.querySelectorAll('.subOption');

    optionButtons.forEach(optionButton => {
        optionButton.addEventListener('click', () => {
            let subOption = optionButton.parentElement.querySelector('.subOption');
            addDisabled(optionList, subOption);
            subOption?.classList.toggle('disabled');
        });
    });

    connectionLabel('region');
    connectionLabel('toilet');
    connectionLabel('shower');
    connectionLabel('parking');
    connectionLabel('sex');
    connectionLabel('state');

})

function addDisabled(optionList, e) {
    for (let i=0;i<optionList.length;i++){
        if (!optionList[i].isEqualNode(e)) {
            optionList[i].classList.add('disabled');
        }
    }
}

function allDisabled() {
    const options = document.querySelectorAll('.subOption');
    options.forEach(option => {
        option.classList.add('disabled');
    })
}

function connectionLabel(name) {
    const inputs = document.querySelectorAll('input[name="' + name +'"]');
    const label = document.querySelector('span[aria-label="' + name +'"]');

    let initial = document.querySelector('input[name="' + name + '"]:checked')?.id;
    if (label != null) {
        label.innerHTML =  document.querySelector('label[for="' + initial + '"]')?.textContent ?? '';
    }
    inputs?.forEach(input => {
        input.addEventListener('change', (e) => {
            label.innerHTML = document.querySelector('label[for="' + input.id + '"]').textContent;
            allDisabled();
        })
    })
}