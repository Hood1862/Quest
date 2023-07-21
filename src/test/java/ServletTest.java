import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.javarush.quest.GameServlet;
import ru.javarush.quest.questions.Answer;
import ru.javarush.quest.questions.Manager;
import ru.javarush.quest.questions.Questions;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

public class ServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);

    HttpServletResponse response = mock(HttpServletResponse.class);

    HttpSession session = mock(HttpSession.class);

    Questions question1 = new Questions(1, "Ты потерял память.\nПринять вызов НЛО?", List.of(
            new Answer("Принять вызов"),
            new Answer("Отклонить вызов", "Ты отклонил вызов. Поражение")));

    Questions question2 = new Questions(2, "Ты принял вызов. Поднимаешься на мостик к капитану?", List.of(
            new Answer("Подняться на мостик"),
            new Answer("Откзаться подниматься на мостик", "Ты не пошел на переговоры. Поражение.")));

    @Test
    @DisplayName("Проверка неправильного ответа и завершение игры")
    public void doGetGameServletTestWrongAnswer() throws IOException {
        GameServlet gameServlet = new GameServlet();
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("answerId")).thenReturn("1");
        doNothing().when(session).setAttribute(anyString(), any());
        when(session.getAttribute("question")).thenReturn(question1);
        Manager questionManager = spy(new Manager());
        when(questionManager.getById(2)).thenReturn(question2);
        gameServlet.doGet(request, response);
        verify(session).setAttribute(eq("wrongAnswer"), any(Answer.class));
    }

    @Test
    @DisplayName("Проверка 'Хорошего ответа' и продолжение игры")
    public void doGetGameServletTestRightAnswer() throws IOException {
        GameServlet gameServlet = new GameServlet();
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("answerId")).thenReturn("0");
        doNothing().when(session).setAttribute(anyString(), any());
        when(session.getAttribute("question")).thenReturn(question1);
        Manager questionManager = spy(new Manager());
        when(questionManager.getById(2)).thenReturn(question2);
        gameServlet.doGet(request, response);
        verify(session).setAttribute(eq("question"), any(Questions.class));
        verify(response).sendRedirect("/game.jsp");
    }
}