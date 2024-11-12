export class DateFormatter {

    constructor() {
    }

    formatDate(jsonDate, split) {
        let date = new Date(jsonDate);
        return `${date.getFullYear()}${split}${this._leftPad(date.getMonth() + 1)}${split}${this._leftPad(date.getDate())}`;
    }
    formatTime(jsonDate, split) {
        let date = new Date(jsonDate);
        return `${this._leftPad(date.getHours())}${split}${this._leftPad(date.getMinutes())}${split}${this._leftPad(date.getSeconds())}`;
    }

    _leftPad(data) {
        return String(data).padStart(2, '0');
    }
}