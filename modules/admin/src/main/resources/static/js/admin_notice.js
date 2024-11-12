window.addEventListener('load', () => {
    
    let notice = document.querySelector('#notice');
    notice.addEventListener('submit', (e) => {
        e.preventDefault();

        if (confirm('해당 공지사항을 삭제하시겠습니까?')) {
            notice.submit();
        }

    })
})