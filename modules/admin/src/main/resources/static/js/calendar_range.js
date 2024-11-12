export class CalendarRange {
    constructor(startDate, endDate, beforeLimitDate, afterLimitDate) {
        this.date = new Date();

        this.year = this.date.getFullYear();
        this.month = this.date.getMonth() + 1;

        this.flag = true; // true = 왼쪽 startDate 변경됨, false = 오른쪽 endDate 변경됨
        this.beforeLimitDate = beforeLimitDate ?? new Date(2024, 1, 1); // 기본값으로 2024년 1월 1일까지
        this.afterLimitDate = afterLimitDate ?? new Date(this.date.getFullYear(), this.date.getMonth() + 2, 0); // 기본값으로 2달 후까지

        // 페이지가 모두 로드되고 초기화
        if (document.readyState === 'loading') {
            window.addEventListener('DOMContentLoaded', () => this.initialize(startDate, endDate));
        } else {
            this.initialize(startDate, endDate);
        }
    }

    initialize(startDate, endDate) {
        window.addEventListener('load', () => {
            this.startDate = document.querySelector('input[name="startDate"]');
            this.endDate = document.querySelector('input[name="endDate"]');
            this.calendar = document.querySelector('#date_range_calendar');
            this.preBtn = document.querySelector('#preButton');
            this.nextBtn = document.querySelector('#nextButton');

            this.leftBody = document.querySelector('#calendar_body > #leftBody');
            this.leftMonth = document.querySelector('#calendar_header #leftMonth');
            this.rightBody = document.querySelector('#calendar_body > #rightBody');
            this.rightMonth = document.querySelector('#calendar_header #rightMonth');

            // 초기 날짜 설정
            if (startDate && endDate) {
                // beforeLimitDate 체크 및 조정
                if (startDate.getTime() < this.beforeLimitDate.getTime()) {
                    startDate = new Date(this.beforeLimitDate);
                    startDate.setDate(startDate.getDate() + 1);
                }

                // afterLimitDate 체크 및 조정
                if (endDate.getTime() > this.afterLimitDate.getTime()) {
                    endDate = new Date(this.afterLimitDate);
                    endDate.setDate(endDate.getDate() - 1);
                }

                this.startDate.value = this.dateForm(startDate.getFullYear(), startDate.getMonth() + 1, startDate.getDate());
                this.endDate.value = this.dateForm(endDate.getFullYear(), endDate.getMonth() + 1, endDate.getDate());
            }

            this.setupEventListeners();
            this.renderCalendar();

            if (this.calButtonClick) {
                this.calButtonClick(this.startDate.value, this.endDate.value);
            }
        });
    }

    setupEventListeners() {
        const confirmBtn = document.querySelector('#calendar_confirm');
        confirmBtn.addEventListener('click', () => {
            this.calendar.classList.add('disabled');
            if (this.calButtonClick) {
                this.calButtonClick(this.startDate.value, this.endDate.value);
            }
        });

        this.startDate.addEventListener('click', () => {
            this.calendar.classList.toggle('disabled');
            this.flag = true;
            this.focusInputDate();
        });

        this.endDate.addEventListener('click', () => {
            this.calendar.classList.toggle('disabled');
            this.flag = false;
            this.focusInputDate();
        });

        this.preBtn.addEventListener('click', () => {
            if (this.month === 1) {
                this.month = 12;
                this.year--;
            } else {
                this.month--;
            }
            this.renderCalendar();
        });

        this.nextBtn.addEventListener('click', () => {
            if (this.month - 1 === 12) {
                this.month = 1;
                this.year++;
            } else {
                this.month++;
            }
            this.renderCalendar();
        });

        this.leftBody.addEventListener('click', (e) => {
            if (this.isAvailableButton(e)) this.handleDateClick(this.leftMonth, e.target);
        })
        this.rightBody.addEventListener('click', (e) => {
            if (this.isAvailableButton(e)) this.handleDateClick(this.rightMonth, e.target);
        })
    }

    isAvailableButton(e) {
        return e.target.hasAttribute('aria-pressed') &&
            e.target.hasAttribute('data-is-today') &&
            !e.target.hasAttribute('data-is-disabled');
    }

    focusInputDate() {
        this.flag ? this.startDate.focus() : this.endDate.focus();
    }

    selectDateForm(innerDate, clickDate, startDateForm, endDateForm) {
        if (clickDate === startDateForm) {
            this.endDate.value = innerDate;
            this.flag = true;
            return;
        } else if (clickDate === endDateForm) {
            this.startDate.value = innerDate;
            this.flag = true;
            return;
        }

        if (clickDate < startDateForm) {
            this.startDate.value = innerDate;
            this.flag = false;
            return;
        }
        if (clickDate > startDateForm && clickDate < endDateForm) {
            if (this.flag) {
                this.startDate.value = innerDate;
            } else {
                this.endDate.value = innerDate;
            }
            this.flag = !this.flag;
            return;
        }
        if (clickDate > endDateForm) {
            if (this.flag) {
                this.startDate.value = innerDate;
            }
            this.endDate.value = innerDate;
            this.flag = !this.flag;
        }
    }

    clickAndGetDate(month, target) {
        const withinDay = Number(target.textContent);

        const lMonth = month.textContent; // '2024. 10'
        const lArray = lMonth.split(". ");
        return new Date(Number(lArray[0]), Number(lArray[1])-1, withinDay);
    }

    drawRange() {
        this.clearRange();
        const start = this.getDate(this.startDate.value);
        const end = this.getDate(this.endDate.value);

        if (start == null || end == null) return;

        const dateStart = new Date(Number(start[0]), Number(start[1])-1, Number(start[2]));
        const dateEnd = new Date(Number(end[0]), Number(end[1])-1, Number(end[2]));

        this.drawCalendarRange(dateStart, dateEnd, this.leftBody, this.leftMonth);
        this.drawCalendarRange(dateStart, dateEnd, this.rightBody, this.rightMonth);

        const within = document.querySelectorAll(".cell[data-is-within-range='true']");
        if (within.length !== 0) {
            this.checkStartOrEndDay(dateStart, within[0]);
            this.checkStartOrEndDay(dateEnd, within[within.length-1]);

            within[0].style.backgroundColor = '#ffffff00';

        }
    }

    drawCalendarRange(start, end, body, month) {
        const left = month.textContent.split(". ");
        const cell = body.querySelectorAll('.cell[data-is-within-range="false"], .cell[data-is-within-range="true"]');
        for (let i = 0; i < cell.length; i++) {
            const temp = new Date(Number(left[0]), Number(left[1]) - 1, i + 1);
            if (start <= temp && end >= temp) {
                cell[i].setAttribute('data-is-within-range', 'true');
            }
        }
    }

    clearRange() {
        const all = document.querySelectorAll('.cell[data-is-within-range="true"]');
        all.forEach(el => {
            el.setAttribute('data-is-within-range', 'false');
            el.style = 'initial';
        });
        const allBtn = document.querySelectorAll('button[aria-pressed="true"]');
        allBtn.forEach(el => {
            el.setAttribute('aria-pressed', 'false');
        });
    }

    checkStartOrEndDay(date, within) {
        const parentId = within.parentElement.parentElement.id;
        const withinDay = Number(within.children.item(0).textContent);
        if (parentId === 'leftBody') {
            const lMonth = this.leftMonth.textContent;
            const lArray = lMonth.split(". ");
            const ldate = new Date(Number(lArray[0]), Number(lArray[1])-1, withinDay);

            if (date.getTime() !== ldate.getTime()) {
                return false;
            }
        }
        else if (parentId === 'rightBody') {
            const rMonth = this.rightMonth.textContent;
            const rArray = rMonth.split(". ");
            const rdate = new Date(Number(rArray[0]), Number(rArray[1])-1, withinDay);
            if (date.getTime() !== rdate.getTime()) {
                return false;
            }
        }
        within.children.item(0).setAttribute('aria-pressed', 'true');
        return true;
    }

    checkToday() {
        const left = this.leftMonth.textContent.split(". ");
        const right = this.rightMonth.textContent.split(". ");

        const today = new Date();
        if (today.getFullYear() === Number(left[0]) && today.getMonth() + 1 === Number(left[1])) {
            this.drawToday(this.leftBody, today.getDate());
        }
        if (today.getFullYear() === Number(right[0]) && today.getMonth() + 1 === Number(right[1])) {
            this.drawToday(this.rightBody, today.getDate());
        }
    }

    drawToday(body, todayDateValue) {
        const selector = '#' + body.id + ' button[data-is-today="false"]';
        const cellList = document.querySelectorAll(selector);
        cellList[todayDateValue-1].setAttribute('data-is-today', 'true');
    }

    lender(header, body, year, month) {
        if (month === 0) {
            year--;
            month = 12;
        }
        if (month === 13) {
            year++;
            month = 1;
        }
        header.innerHTML = year + ". " + String(month).padStart(2, '0');

        const lastDateMonth = new Date(year, month, 0).getDate();
        let startDateMonth = new Date(year, month-1, 1).getDay();

        let temp = '<div class="week"><div class="cell"><div>일</div></div><div class="cell"><div>월</div></div><div class="cell"><div>화</div></div><div class="cell"><div>수</div></div><div class="cell"><div>목</div></div><div class="cell"><div>금</div></div><div class="cell"><div>토</div></div></div></div>';
        let now = 1;

        while (now <= lastDateMonth) {
            temp += '<div class="week">' + this.createWeek(now, lastDateMonth, startDateMonth) + '</div>';
            now += 7 - startDateMonth;
            startDateMonth = 0;
        }

        body.innerHTML = temp;
    }

    createWeek(now, lastDateMonth, startDateMonth) {
        let temp = '';
        for (let i = startDateMonth; i < 7; i++) {
            if (now <= lastDateMonth) {
                temp += '<div class="cell" data-is-within-range="false"><button type="button" aria-pressed="false" data-is-today="false">' + now + '</button></div>';
                now++;
            }
        }
        return temp;
    }

    getDate(value) {
        if (value === '') return null;

        const split = value.split("/");
        return [split[0], split[1], split[2]];
    }

    dateForm(year, month, day) {
        return `${year}/${String(month).padStart(2, '0')}/${String(day).padStart(2, '0')}`;
    }

    getDateForm(date) {
        const split = date.value.split('/');
        return new Date(Number(split[0]), Number(split[1]) - 1, Number(split[2]));
    }

    setOnConfirm(callback) {
        this.calButtonClick = callback;
    }

    renderCalendar() {
        this.lender(this.leftMonth, this.leftBody, this.year, this.month - 1);
        this.lender(this.rightMonth, this.rightBody, this.year, this.month);

        const leftDate = new Date(this.year, this.month - 1, 0);
        const rightDate = new Date(this.year, this.month, 0);

        this.preBtn.disabled = leftDate.getTime() <= this.beforeLimitDate.getTime();
        this.nextBtn.disabled = rightDate.getTime() >= this.afterLimitDate.getTime();

        this.checkToday();

        // preBtn, nextBtn disabled 처리가 반드시 선행되어야 함
        this.checkLeftLimitDate(this.leftBody);
        this.checkRightLimitDate(this.rightBody);

        this.drawRange();
    }

    checkLeftLimitDate(leftBody) {
        if (!this.preBtn.disabled) return;
        let beforeDay = this.beforeLimitDate.getDate();

        let buttons = leftBody.querySelectorAll('.cell > button');
        for (let i = 0; i < Math.min(buttons.length, beforeDay); i++) {
            buttons[i].setAttribute('data-is-disabled', 'true');
        }

    }
    checkRightLimitDate(rightBody) {
        if (!this.nextBtn.disabled) return;
        let afterDay = this.afterLimitDate.getDate();

        let buttons = rightBody.querySelectorAll('.cell > button');
        for (let i = Math.max(afterDay - 1, 0); i < buttons.length; i++) {
            buttons[i].setAttribute('data-is-disabled', 'true');
        }
    }

    handleDateClick(month, target) {
        const clickDate = this.clickAndGetDate(month, target);
        const startDateForm = this.getDateForm(this.startDate);
        const endDateForm = this.getDateForm(this.endDate);
        const innerDate = this.dateForm(
            clickDate.getFullYear(),
            clickDate.getMonth() + 1,
            clickDate.getDate()
        );

        this.selectDateForm(
            innerDate,
            clickDate.getTime(),
            startDateForm.getTime(),
            endDateForm.getTime()
        );
        this.focusInputDate();
        this.drawRange();
    }
}