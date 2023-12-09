$(document).ready(() => {
    $('#quiz-form').on('submit', (e) => {
        console.log('Submit button clicked');
        e.preventDefault();
        const currentUrl = window.location.href;
        const hash = currentUrl.match(/\/([^\/]+)\/?$/);
        const response = {
            quizHash: hash[1],
            questions: []
        }
        $('.question-container').each(function() {

            const name = $(this).find('.quiz-name').text();
            const answers = [];
            $(this).find('input[type="checkbox"]:checked').each(function() {
                answers.push($(this).val());
            });
            const question = {
                name: name,
                answers:answers
            }
            response.questions.push(question)
        });
        console.log(JSON.stringify(response));

    });
});
const nextQuestion = (currentQuestion) => {
    $(`.question-container[data-question=${currentQuestion}]`).removeClass('active');
    $(`.question-container[data-question=${currentQuestion + 1}]`).addClass('active');
};

const prevQuestion = (currentQuestion) => {
    $(`.question-container[data-question=${currentQuestion}]`).removeClass('active');
    $(`.question-container[data-question=${currentQuestion - 1}]`).addClass('active');
};
