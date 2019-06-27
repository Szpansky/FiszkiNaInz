package com.apps.szpansky.fiszkinainz.data;

public class Question {
    private Integer id;
    private String title;
    private String question;
    private String answer;
    private String question_image;
    private Integer questions_count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getQuestions_count() {
        return questions_count;
    }

    public void setQuestions_count(Integer questions_count) {
        this.questions_count = questions_count;
    }

    public String getQuestion_image() {
        return question_image;
    }

    public void setQuestion_image(String question_image) {
        this.question_image = question_image;
    }
}
