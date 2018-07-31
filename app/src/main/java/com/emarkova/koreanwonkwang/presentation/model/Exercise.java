package com.emarkova.koreanwonkwang.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    //id integer PRIMARY KEY, les_num text NOT NULL, type text, ques text, ans text, word text, descr text
    private String lessonNumber;
    private String type;
    private String word;
    private String description;
    private String question;
    private String answer;
    private String test;
    private String audio;

    public Exercise(){}

    public Exercise (String num, String type, String word, String description, String question, String answer, String test, String audio) {
        this.lessonNumber = num;
        this.type = type;
        this.word = word;
        this.description = description;
        this.question = question;
        this.answer = answer;
        this.test = test;
        this.audio = audio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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

    public String getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(String lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
