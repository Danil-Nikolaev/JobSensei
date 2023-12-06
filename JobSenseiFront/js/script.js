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

$(document).ready(function () {

    $(".btn").on("click", function () {

        changeFirstStyles();

        const ctx1 = document.getElementsByClassName('myChart')[0];

        new Chart(ctx1, {
            type: 'bar',
            data: {
                labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
                datasets: [{
                    label: '# of Votes',
                    data: [12, 19, 3, 5, 2, 3],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    });

});
