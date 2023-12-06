// $(document).ready(function () {
//     var form = $('#quizForm');
//     var quizSteps = $('.quiz-step');
//
//     $('.next-btn, .prev-btn').on('click', function () {
//         var currentStep = $(this).closest('.quiz-step');
//         var nextStep = $(this).hasClass('next-btn') ? currentStep.next('.quiz-step') : currentStep.prev('.quiz-step');
//
//         currentStep.hide();
//         nextStep.show();
//     });
//
//     form.on('submit', function (e) {
//         e.preventDefault();
//         alert('Викторина завершена! Результаты будут обработаны.');
//     });
// });
function nextQuestion(currentQuestion) {
    $(`.question-container[data-question=${currentQuestion}]`).removeClass('active');
    $(`.question-container[data-question=${currentQuestion + 1}]`).addClass('active');

    if (currentQuestion === 3) {
        showResults();
    }
}

function prevQuestion(currentQuestion) {
    $(`.question-container[data-question=${currentQuestion}]`).removeClass('active');
    $(`.question-container[data-question=${currentQuestion - 1}]`).addClass('active');
}

function showResults() {
    $('#results-container').show();
    $('#quiz-results').text('Поздравляем! Викторина завершена.');
}