package com.emarkova.koreanwonkwang.data.model;

public class DataExercise {
    private String lessonNumber;
    private String type;
    private String word;
    private String description;
    private String question;
    private String answer;
    private String test;
    private String audio;

    public DataExercise(){}

    public DataExercise (String num, String type, String word, String description, String question, String answer, String test, String audio) {
        this.lessonNumber = num;
        this.type = type;
        this.word = word;
        this.description = description;
        this.question = question;
        this.answer = answer;
        this.test = test;
        this.audio = audio;
    }

    public String getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(String lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
