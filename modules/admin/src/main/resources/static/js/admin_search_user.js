import { DateFormatter } from "./component/date_format.js";
import { PageData, Pagination } from "./pagination.js";

const pagination = new Pagination('/user', resultForm, [
    new PageData('word', () => document.querySelector('input[name="searchWord"]')?.value, (result) => {
        let word = (typeof result === 'object' && result != null) ? result.data.word : result;
        let searchWord = document.querySelector('input[name="searchWord"]');
        searchWord.value = word ?? '';
    })
]);
const dateFormatter = new DateFormatter();


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
    return `<a href="/user/${searchForm.userId}" class="result">
                <span>${searchForm.userId}</span>
                <span>${searchForm.nickname}</span>
                <span>${searchForm.social}</span>
                <span>${searchForm.sex}</span>
                <span>${dateFormatter.formatDate(searchForm.birth, '-')}</span>
                <span>${dateFormatter.formatDate(searchForm.createDate, '-')} ${dateFormatter.formatTime(searchForm.createDate, ':')}</span>
                <span>${searchForm.status}</span>
            </a>`
}
