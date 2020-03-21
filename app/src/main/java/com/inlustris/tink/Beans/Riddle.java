package com.inlustris.tink.Beans;

public class Riddle {
    private String riddle,answer;

    public Riddle() {
    }

    public String getRiddle() {
        return riddle;
    }

    public void setRiddle(String riddle) {
        this.riddle = riddle;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Riddle(String riddle, String answer) {
        this.riddle = riddle;
        this.answer = answer;
    }
}
