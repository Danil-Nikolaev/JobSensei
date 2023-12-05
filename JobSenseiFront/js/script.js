$(document).ready(function () {

    $(".btn").on("click", function () {

        $(".form__input").css({
            'width': '60%',
            'padding': '1rem ',
            'transition': 'width 0.5s'
        });

        $("body").css({
            'align-items': 'flex-start',
            'animation': 'alignItemsAnimation 2s'
        });

        $(".form__group").css({
            'margin-top': '20px',
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

        const ctx2 = document.getElementsByClassName('myChart')[1];

        new Chart(ctx2, {
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
