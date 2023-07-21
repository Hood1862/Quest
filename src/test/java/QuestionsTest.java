import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.javarush.quest.questions.Answer;
import ru.javarush.quest.questions.Manager;
import ru.javarush.quest.questions.Questions;

public class QuestionsTest {

    Manager questionManager = new Manager();

    @Test
    @DisplayName("Проверяем заполнены ли вопросы")
    public void getByIdTest() {
        int questionId = 1;
        Questions question = questionManager.getById(questionId);
        Assertions.assertEquals(questionId, question.getId());
    }

    @Test
    @DisplayName("Проверяем все ли вопросы содержат ответы среди которых 1 верный. А так же неверный ответы содержат текст")
    public void allTestHaveAnswersAndOnlyOneIsRight() {
        boolean result = true;
        for (Questions question : Manager.questionList.values()) {
            if (question != null && question.getAnswerList().size() > 0) {
                int rightAnswerCount = 0;
                for(Answer answer : question.getAnswerList()) {
                    if (!answer.isWrongAnswer()) {
                        rightAnswerCount++;
                    } else {
                        if (answer.getWrongAnswerText() == null) {
                            result = false;
                            break;
                        }
                    }
                }
                if (rightAnswerCount > 1) {
                    result = false;
                    break;
                }
            } else {
                result = false;
                break;
            }
        }
        Assertions.assertTrue(result);
    }

}
