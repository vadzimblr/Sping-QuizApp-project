$(document).ready(() => {
    let questionCounter = 0;
    const container = $('#questionsContainer');

    function addQuestion() {
        questionCounter++;
        const questionDiv = $(document.createElement('div')).attr("class","form-group").html(`
            
            <div class="form-group">
                <label  for="question${questionCounter}">Question ${questionCounter}:</label>
                <input type="text" class="form-control" data-question="${questionCounter}" required>
            </div>
            <div class="form-group">
                <label for="question${questionCounter}">Clarifying information to the question (optional):</label>
                <textarea class="form-control" data-question="${questionCounter}" cols="30" rows="10"></textarea>
            </div>
            <div class="form-group">
                <label for="answers${questionCounter}">Answers:</label>
                <div class="answerContainer" data-answer-for-question="${questionCounter}">
                    <div data-answer="0">
                        <div class="form-group">
                            <label for="">Answer 1:</label>
                            <input type="text" class="form-control" required>
                        </div>
                        <div class="form-group">
                         <label for="">Is correct?</label>
                          <select class="form-control" name="isCorrect[]" >
                            <option value="false">No</option>
                            <option value="true">Yes</option>
                        </select> 
                        </div>
                    </div>
                    <div data-answer="1">
                        <div class="form-group">
                            <label for="">Anser 2:</label>
                            <input type="text" class="form-control" required>
                        </div>
                        <label for="">Is correct?</label>
                          <select class="form-control" name="isCorrect[]" >
                            <option value="false">No</option>
                            <option value="true">Yes</option>
                        </select>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-success addAnswerBtn">Add Answer</button>
        `);

        container.append(questionDiv);
    }
    function addAnswer(questionNumber,answer) {
        const answersContainer = $(`.answerContainer[data-answer-for-question="${questionNumber}"]`);
        if(answersContainer.children().length < 6){
            const answerInput = $(document.createElement('div')).attr({
                class: "form-group",
                'data-answer': answersContainer.children().length,

            }).html(`
               <div class="form-group">
                   <label for="">Answer ${answersContainer.children().length+1}</label>
                   <input type="text" class="form-control" required>
               </div>
               <div class="form-group">
                   <label for="">Is correct?</label>
                    <select class="form-control" name="isCorrect[]" >
                        <option value="false">No</option>
                        <option value="true">Yes</option>
                    </select> 
                </div>
            `)
            answersContainer.append(answerInput);
        }
    }

    document.getElementById("addQuestionBtn").addEventListener('click', addQuestion);

    container.on('click', '.addAnswerBtn', function() {
        const questionNumber = $(this).closest('div').find('input[data-question]').data('question');
        addAnswer(questionNumber,2);
    });

    $('#quizForm').submit(function(e) {
        e.preventDefault();

        const formData = $(this).serializeArray();
        const jsonData = {};

        formData.forEach(function(field) {
            jsonData[field.name] = field.value;
        });
        console.log(jsonData);
        // $.ajax({
        //   type: 'POST',
        //   url: '/createQuiz',
        //   contentType: 'application/json;charset=UTF-8',
        //   data: JSON.stringify(jsonData),
        //   success: function(response) {
        //     // Обработка успешного ответа от бэкенда
        //     console.log(response);
        //   },
        //   error: function(error) {
        //     // Обработка ошибки
        //     console.error(error);
        //   }
        // });
    });
});
