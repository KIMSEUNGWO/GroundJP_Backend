import { CalendarRange } from "./calendar_range.js";

let regionData = null;
let regionChart = null;
let regionSort = 'COMPLETE_ASC';

const regionSortEnum = {
    'COMPLETE_ASC': (o1, o2) => o1.completeCnt - o2.completeCnt,
    'COMPLETE_DESC': (o1, o2) => o2.completeCnt - o1.completeCnt,
    'CANCEL_ASC': (o1, o2) => o1.cancelCnt - o2.cancelCnt,
    'CANCEL_DESC': (o1, o2) => o2.cancelCnt - o1.cancelCnt
};

const calendar = new CalendarRange(
    new Date(2024, 10, 2),  // 시작 날짜
    new Date(2024, 12, 31),  // 종료 날짜,
    new Date(2024, 9, 20),
    new Date() // 마지막날로 제한
);

// 확인 버튼 클릭 시 콜백 설정 (선택적)
calendar.setOnConfirm((startDate, endDate) => {
    console.log('선택된 날짜 범위:', startDate, endDate);
    fetch(`/admin/statistics/region?startDate=${startDate}&endDate=${endDate}`)
        .then(res => res.json())
        .then(json => {
            if (json.result !== 'OK') return;

            regionData = new ChartBarData(json.data);
            barChartDraw();

        });
});

window.addEventListener('load', () => {

    const regionSorts = document.querySelector('select[name="region-sort"]');

    regionSorts.addEventListener('change', (e) => {
        regionSort = e.target.value;
        barChartDraw();
    })

    const statCards = document.querySelectorAll('.stat-card');
    statCards.forEach(statCard => {
        let color = statCard.getAttribute('aria-border-color');
        statCard.style.borderLeft = `${color} solid 10px`;

        let trend = statCard.getAttribute('aria-trend');

        if (trend !== null) {
            trend = Number(trend);
            let trendElement = statCard.querySelector('.trend');
            if (trend > 0) {
                trendElement.style.color = 'var(--font-color-1)';
                trendElement.innerHTML = '- 0';
            } else if (trend < 0) {
                trendElement.style.color = '#2F69DD';
                trendElement.innerHTML = `▼ ${trend}`;
            } else {
                trendElement.style.color = '#ff4444';
                trendElement.innerHTML = `▲ ${trend}`;

            }
        }

    })

    const chartCards = document.querySelectorAll('.chart-card');
    chartCards.forEach(chartCard => {
        let label = chartCard.getAttribute('aria-label');
        let datas = chartCard.querySelectorAll('.data');
        let chartData = new ChartData(datas);

        let labelElement = createLabelElement(label);
        chartCard.appendChild(labelElement);

        const canva = chartCard.querySelector('canvas');

        new Chart(canva, {
            type: 'doughnut',
            data: {
                labels: chartData.labels,
                datasets: [{
                    data: chartData.dataSets,
                    backgroundColor: chartData.colors
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    },
                    title: {
                        display: false,
                    }
                }
            }
        });

        datas.forEach(data => data.remove());
    })

})

function createLabelElement(label) {
    let div = document.createElement('div');
    div.classList.add('chart-title');
    div.innerHTML = label;
    return div;
}

function barChartDraw() {
    // 기존 차트가 있다면 제거
    if (regionChart !== null) {
        regionChart.destroy();
    }

    let sortedData = regionData.datas.sort(regionSortEnum[regionSort]);
    console.log(sortedData);
    regionChart = new Chart(document.querySelector('#regionChart'), {
        type: 'bar',
        data: {
            labels: sortedData.map(data => data.region),
            datasets: [
                {
                    label: '완료',
                    data: sortedData.map(data => data.completeCnt),
                    backgroundColor: '#3F9DE0',
                    borderRadius: sortedData.map((data, index) =>
                        setBorderRadius(data.completeCnt, data.cancelCnt).completeRadius
                    ),
                    borderSkipped: false,
                    barPercentage: 0.8,
                    maxBarThickness: 60
                },
                {
                    label: '취소',
                    data: sortedData.map(data => data.cancelCnt),
                    backgroundColor: '#e3e6f0',
                    borderRadius: sortedData.map((data, index) =>
                        setBorderRadius(data.completeCnt, data.cancelCnt).cancelRadius
                    ),
                    borderSkipped: false,
                    barPercentage: 0.8,
                    maxBarThickness: 60
                }
            ]
                .sort((o1, o2) => {
                    if (regionSort === 'CANCEL_ASC' || regionSort === 'CANCEL_DESC') {
                        o2.borderRadius = o2.data.map((value, index) =>
                            setBorderRadius(sortedData[index].completeCnt, value).cancelRadius
                        );
                        o1.borderRadius = o1.data.map((value, index) =>
                            setBorderRadius(value, sortedData[index].cancelCnt).completeRadius
                        );
                        return -1;
                    }
                })
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    stacked: true,
                    grid: {
                        display: false
                    }
                },
                y: {
                    stacked: true,
                    beginAtZero: true,
                    grid: {
                        color: '#e3e6f0'
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });
}

function setBorderRadius(completeData, cancelData) {
    const completeRadius = {
        topLeft: cancelData === 0 ? 3 : 0,
        topRight: cancelData === 0 ? 3 : 0,
        bottomLeft: 3,
        bottomRight: 3
    };

    const cancelRadius = {
        topLeft: 3,
        topRight: 3,
        bottomLeft: completeData === 0 ? 3 : 0,
        bottomRight: completeData === 0 ? 3 : 0
    };

    return { completeRadius, cancelRadius };
}

class ChartData {

    constructor(datas) {
        let labels = [];
        let dataSets = [];
        let colors = [];
        datas.forEach(data => {
            labels.push(data.getAttribute('data-is-label'));
            dataSets.push(Number(data.getAttribute('data-is')));
            colors.push(data.getAttribute('data-is-color'));
        })

        this.labels = labels;
        this.dataSets = dataSets;
        this.colors = colors;
    }
}

class ChartBarData {

    constructor(datas) {
        console.log('ChartBarData : ', datas);
        let regionDatas = [];
        datas.forEach(data => regionDatas.push(new ChartRegionData(data)));
        this.datas = regionDatas;
    }
}

class ChartRegionData {

    constructor(data) {
        this.region = data.region;
        this.completeCnt = data.completeCnt;
        this.cancelCnt = data.cancelCnt;
    }
}
