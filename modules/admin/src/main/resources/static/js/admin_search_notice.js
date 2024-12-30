import { DateFormatter } from "./component/date_format.js";
import { Pagination } from "./pagination.js";

const dateFormmater = new DateFormatter();
const pagination = new Pagination('/notice', resultForm, []);

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
    return `<a href="/notice/${searchForm.noticeId}" class="result">
                <span>${searchForm.title}</span>
                <span>${dateFormmater.formatDate(searchForm.createDate, '/')}</span>
            </a>`
}
