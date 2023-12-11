$(document).ready(() => {
    let questionCounter = 0;
    const container = $('#questionsContainer');

    function addQuestion() {
        questionCounter++;
        const questionDiv = $(`
            <div class="form-group" data-question="${questionCounter}">
                <label for="question${questionCounter}">Question ${questionCounter}:</label>
                <input type="text" class="form-control" data-name-question required>
                <label for="question${questionCounter}">Clarifying information to the question (optional):</label>
                <textarea class="form-control" data-description-question cols="30" rows="10"></textarea>
                <div class="form-group">
                    <label for="answers${questionCounter}">Answers:</label>
                    <div class="answerContainer" data-answer-for-question="${questionCounter}">
                        <div data-answer="0">
                            <div class="form-group">
                                <label for="">Answer 1:</label>
                                <input type="text" class="form-control" data-answer-name required>
                            </div>
                            <div class="form-group">
                                <label for="">Is correct?</label>
                                <select class="form-control" name="isCorrect[]">
                                    <option value="false">No</option>
                                    <option value="true">Yes</option>
                                </select>
                            </div>
                        </div>
                        <div data-answer="1">
                            <div class="form-group">
                                <label for="">Answer 2:</label>
                                <input type="text" class="form-control" data-answer-name required>
                            </div>
                            <div class="form-group">
                                <label for="">Is correct?</label>
                                <select class="form-control" name="isCorrect[]">
                                    <option value="false">No</option>
                                    <option value="true">Yes</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <button type="button" class="btn btn-success addAnswerBtn">Add Answer</button>
            </div>
        `);

        container.append(questionDiv);
    }

    function addAnswer(questionNumber) {
        const answersContainer = $(`.answerContainer[data-answer-for-question="${questionNumber}"]`);
        const answerCounter = answersContainer.children().length;

        if (answerCounter < 6) {
            const answerInput = generateAnswerHTML(answerCounter + 1);
            answersContainer.append(answerInput);
        }
    }

    function generateAnswerHTML(answerNumber) {
        return $(`
            <div class="form-group" data-answer="${answerNumber}">
                <label for="">Answer ${answerNumber}:</label>
                <input type="text" class="form-control" data-answer-name required>
                <label for="">Is correct?</label>
                <select class="form-control" name="isCorrect[]">
                    <option value="false">No</option>
                    <option value="true">Yes</option>
                </select>
            </div>
        `);
    }

    $('#addQuestionBtn').click(addQuestion);

    container.on('click', '.addAnswerBtn', function () {
        const questionNumber = $(this).closest('[data-question]').data('question');
        addAnswer(questionNumber);
    });

    $('#quizForm').submit(function (e) {
        e.preventDefault();

        const request = {
            name: $('[data-name="quizName"]').val(),
            introduction: $('[data-introduction="quizIntroduction"]').val(),
            questions: [],
        };

        $('[data-question]').each(function () {
            const name = $(this).find('[data-name-question]').val();
            const description = $(this).find('[data-description-question]').val();
            const questionMap = {
                name,
                description,
                answers: [],
            };

            $(this).find('.answerContainer [data-answer]').each(function () {
                const name = $(this).find('[data-answer-name]').val();
                const isCorrect = $(this).find('select[name="isCorrect[]"]').val();
                const answerMap = {
                    name,
                    isCorrect,
                };
                questionMap.answers.push(answerMap);
            });

            request.questions.push(questionMap);
        });
        const csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
          type: 'POST',
          url: '/api/createQuiz',
          contentType: 'application/json;charset=UTF-8',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            },
          data: JSON.stringify(request),
          success: function(response) {
            // Обработка успешного ответа от бэкенда
            console.log(response);
          },
          error: function(error) {
            // Обработка ошибки
            console.error(error);
          }
        });
        console.log(JSON.stringify(request));
    });
});
