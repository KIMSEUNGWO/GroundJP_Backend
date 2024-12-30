import {Pagination} from "./pagination.js";

const pagination = new Pagination('/user/order', resultForm, []);

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
    return `<a href="/match/${searchForm.matchId}" class="result">
                <span>${searchForm.orderId}</span>
                <span>${searchForm.title}</span>
                <span>${Number(searchForm.price).toLocaleString('ko-KR') + '원'}</span>
                <span>${searchForm.cancelDate != null ? dateFormat(searchForm.cancelDate) + " " + timeFormat(searchForm.cancelDate) : 'X'}</span>
                <span>${dateFormat(searchForm.createDate)} ${timeFormat(searchForm.createDate)}</span>
            </a>`
}

function dateFormat(data) {
    let date = new Date(data);
    return `${date.getFullYear()}/${leftPad(date.getMonth() + 1)}/${leftPad(date.getDate())}`;
}
function timeFormat(data) {
    let date = new Date(data);
    return `${leftPad(date.getHours())}:${leftPad(date.getMinutes())}:${leftPad(date.getSeconds())}`;
}

function leftPad(data) {
    return String(data).padStart(2, '0');
}
