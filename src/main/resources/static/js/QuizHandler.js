$(document).ready(() => {
    $('#quiz-form').on('submit', (e) => {
        console.log('Submit button clicked');
        e.preventDefault();
        const currentUrl = window.location.href;
        const hash = currentUrl.match(/\/([^\/]+)\/?$/);
        const request = {
            quizHash: hash[1],
            questions: []
        }
        $('.question-container').each(function() {

            const name = $(this).find('.quiz-name').text();
            const answers = [];

            $(this).find('input[type="checkbox"]:checked').each(function() {
                answers.push({name:$(this).val()});
            });
            const question = {
                name: name,
                answers:answers
            }
            request.questions.push(question)
        });
        console.log(JSON.stringify(request));
        const csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: 'POST',
            url: '/api/saveQuizResult',
            contentType: 'application/json;charset=UTF-8',
            headers:{
                'X-CSRF-TOKEN':csrfToken
            },
            data: JSON.stringify(request),
            success:function (response){
                console.log(response)
            },
            error:function (error){
                console.log(error)
            }
        })
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
