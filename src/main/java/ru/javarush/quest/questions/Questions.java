package ru.javarush.quest.questions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class Questions {

    private int id;
    private String text;
    public List<Answer> answerList;

}

