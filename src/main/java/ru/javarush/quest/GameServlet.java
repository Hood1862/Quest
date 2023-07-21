package ru.javarush.quest;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.javarush.quest.questions.Answer;
import ru.javarush.quest.questions.Manager;
import ru.javarush.quest.questions.Questions;

import java.io.IOException;

@WebServlet(name = "GameServlet", value = "/game")
public class GameServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession currentSession = req.getSession();
        Manager questionManager = new Manager();
        Questions currentQuestion = getCurrentQuestion(req);
        Questions nextQuestion = null;
        if (currentQuestion == null) {
            nextQuestion = questionManager.getById(1);
        } else {
            Answer currentAnswer = getCurrentAnswer(currentQuestion, req);
            if (currentAnswer != null && !currentAnswer.isWrongAnswer()) {
                nextQuestion = questionManager.getById(currentQuestion.getId() + 1);
            } else {
                currentSession.setAttribute("wrongAnswer", currentAnswer);
            }
        }
        currentSession.setAttribute("question", nextQuestion);
        resp.sendRedirect("/game.jsp");
    }

    private Questions getCurrentQuestion(HttpServletRequest req) {
        try {
            return (Questions) req.getSession().getAttribute("question");
        } catch (Exception e) {
            return null;
        }
    }

    private Answer getCurrentAnswer(Questions currentQuestion, HttpServletRequest req) {
        try {
            int answerId = Integer.parseInt(req.getParameter("answerId"));
            return currentQuestion.getAnswerList().get(answerId);
        } catch (Exception e) {
            return null;
        }
    }
}
