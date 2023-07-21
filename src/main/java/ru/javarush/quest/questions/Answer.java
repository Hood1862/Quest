package ru.javarush.quest.questions;

import lombok.Getter;

@Getter
public class Answer {

    private final String text;
    private final boolean wrongAnswer;
    private String wrongAnswerText;

    public Answer(String text, String wrongAnswerEndText) {
        this.text = text;
        this.wrongAnswerText = wrongAnswerEndText;
        this.wrongAnswer = true;
    }

    public Answer(String text) {
        this.text = text;
        this.wrongAnswer = false;
    }

}
