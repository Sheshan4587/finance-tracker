package com.financetracker.dto;

public class SmartExpenseRequest {

    private  String sentence;

    public SmartExpenseRequest(String sentence) {
        this.sentence = sentence;
    }

    public SmartExpenseRequest() {
    }

    public String getSentence() {
        return sentence;
    }
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

}
