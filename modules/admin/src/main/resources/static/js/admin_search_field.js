import { PageData, Pagination } from "./pagination.js";

const pagination = new Pagination('/admin/field', resultForm, [
    new PageData('word', () => document.querySelector('input[name="searchWord"]')?.value, (result) => {
        let word = (typeof result === 'object' && result != null) ? result.data.word : result;
        let searchWord = document.querySelector('input[name="searchWord"]');
        searchWord.value = word ?? '';
    }), 
    new PageData('region', () => document.querySelector('input[name="region"]:checked')?.value, (result) => {
        let region = (typeof result === 'object' && result != null) ? result.data.region : result;
        let regionRadio = document.querySelector(`input[name="region"][value="${region ?? ''}"]`);
        regionRadio.checked = true;
        let label = document.querySelector(`label[for="${regionRadio.id}"]`);
        let text = document.querySelector('span[aria-label="region"]');
        text.innerHTML = label.textContent;
    })
]);

window.addEventListener('popstate', (e) => {
    if (e.state && e.state.data) {
        pagination.onPopState(e.state.data);
    }
});

window.addEventListener('load', ()=>{
    //   최초검색
    pagination.search();

})

function resultForm(searchForm) {
    return '<a href="/admin/field/' + searchForm.fieldId + '" class="result">' +
                '<span>' + searchForm.fieldId + '</span>' +
                '<span>' + searchForm.region + '</span>' +
                '<span>' + searchForm.title +'</span>' +
                '<span>' + searchForm.address + '</span>' +
            '</a>'
}
