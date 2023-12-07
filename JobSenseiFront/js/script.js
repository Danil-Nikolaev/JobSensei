function changeFirstStyles() {
    $(".form__input").css({
        'width': '60%',
        'padding': '1rem ',
        'transition': 'width 0.5s'
    });

    $(".container").css({
        'align-items': 'flex-start',
        'animation': 'alignItemsAnimation 2s'
    });

    $("#main").css('height', 'auto');

    $('#diagrams').removeClass('hide');;

    $(".form__group").css({
        'margin-top': '50px',
        'justify-content': 'space-between'
    })

    $(".flex").css({
        'flex-direction': 'row'
    });

    $(".btn").css({
        'margin-top': '0px',
        'margin-left': '10px',
        'font-size': '1rem',
    });

    $('.myChart').css({
        'display': 'block'
    });
}

function sortArray(param) {
    var keyValueArray = [];

    $.each(param, function(key, value) {
        keyValueArray.push({ key: key, value: value });
    });
    keyValueArray.sort(function(a, b) {
        return b.value - a.value;
    });
    if (keyValueArray.length > 10)
        return keyValueArray.slice(0,10);
    return keyValueArray;
}

function setDiagram(ctx, labels, data, backgroundColor="blue", name) {
    Chart.defaults.color = '#fff';
    
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: name,
                data: data,
                borderWidth: 1,
                backgroundColor: backgroundColor,
                color: 'blue'
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }, 
            plugins: {
                legend: {
                    labels: {
                        // This more specific font property overrides the global property
                        font: {
                            size: 20,
                            // family: "'Times New Roman'"
                        }
                    }
                }
            }
        }
    });
}

function averageSalary(json) {
    json["averageSalary"];
}

function cities(json) {
    const cities = json['cities'];
    var keyValueArray = sortArray(cities);

    var labels_cities = [];
    var data_cities = [];
    $.each(keyValueArray, function (indexInArray, el) { 
        labels_cities.push(el['key']);
        data_cities.push(el['value']);
    });

    const ctx1 = document.getElementsByClassName('myChart')[0];
    setDiagram(ctx1, labels_cities, data_cities, "#FFF8DC", "Города");
}

function experiencse(json) {
    const experiences = json['experiences'];

    var labels_cities = [];
    var data_cities = [];
    $.each(experiences, function (key, value) { 
         labels_cities.push(key);
         data_cities.push(value);
    });
    
    const ctx2 = document.getElementsByClassName('myChart')[1];
    setDiagram(ctx2, labels_cities, data_cities, "#BA55D3", "Кол-во вакансий по опыту");
}

function skills(json) {
    const skills = json['skills'];
    var keyValueArray = sortArray(skills);

    var labels_cities = [];
    var data_cities = [];
    $.each(keyValueArray, function (indexInArray, el) { 
        labels_cities.push(el['key']);
        data_cities.push(el['value']);
    });

    const ctx = document.getElementsByClassName('myChart')[2];
    setDiagram(ctx, labels_cities, data_cities, "#9ACD32", "Навыки");
}

function skillsByExperienceNoExperience(json) {
    const noExperience = json['skillsByExperience']['noExperience'];
    var keyValueArray = sortArray(noExperience);

    var labels_cities = [];
    var data_cities = [];
    $.each(keyValueArray, function (indexInArray, el) { 
        labels_cities.push(el['key']);
        data_cities.push(el['value']);
    });

    const ctx = document.getElementsByClassName('myChart')[3];
    setDiagram(ctx, labels_cities, data_cities, "#FFA500", "Навыки без опыта работы");
}

function skillsByExperienceBetweenOneAndThree(json) {
    const between1And3 = json['skillsByExperience']['between1And3'];
    var keyValueArray = sortArray(between1And3);

    var labels_cities = [];
    var data_cities = [];
    $.each(keyValueArray, function (indexInArray, el) { 
        labels_cities.push(el['key']);
        data_cities.push(el['value']);
    });

    const ctx = document.getElementsByClassName('myChart')[4];
    setDiagram(ctx, labels_cities, data_cities, "#FFA07A", "Навыки с опытом от 1-3 лет");
}

function skillsByExperienceBetweenThreeAndSix(json) {
    const between3And6 = json['skillsByExperience']['between3And6'];
    var keyValueArray = sortArray(between3And6);

    var labels_cities = [];
    var data_cities = [];
    $.each(keyValueArray, function (indexInArray, el) { 
        labels_cities.push(el['key']);
        data_cities.push(el['value']);
    });

    const ctx = document.getElementsByClassName('myChart')[5];
    setDiagram(ctx, labels_cities, data_cities, "#87CEEB", "Навыки с опытом от 3-6 лет");
}

function skillsByExperienceMoreThanSix(json) { 
    const moreThan6 = json['skillsByExperience']['moreThan6'];
    var keyValueArray = sortArray(moreThan6);

    var labels_cities = [];
    var data_cities = [];
    $.each(keyValueArray, function (indexInArray, el) { 
        labels_cities.push(el['key']);
        data_cities.push(el['value']);
    });

    const ctx = document.getElementsByClassName('myChart')[6];
    setDiagram(ctx, labels_cities, data_cities, "#F0E68C", "Навыки с более 6 лет опыта");
}

$(document).ready(function () {

    $(".btn").on("click", function () {
        const url = "http://localhost:8080/analyze?profession="
        changeFirstStyles();

        var valueInput = $('#profession').val();
        console.log(valueInput)

        $.getJSON(url + valueInput, function (data) {
            averageSalary(data);
            cities(data);
            experiencse(data);
            skills(data);
            skillsByExperienceNoExperience(data);
            skillsByExperienceBetweenOneAndThree(data);
            skillsByExperienceBetweenThreeAndSix(data);
            skillsByExperienceMoreThanSix(data);
        });

    });

});
