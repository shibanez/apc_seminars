package apc.edu.ph.apcseminarmi151;

/**
 * Created by Mathew on 11/21/2017.
 */

public class Question {
    private String id;
    private String question;
    private String time;
    private String questioner;
    private String seminarId;
    private String answer;

    public Question (String id, String question, String time, String questioner, String seminarId){
        this.id = id;
        this.question = question;
        this.time = time;
        this.questioner = questioner;
        this.seminarId = seminarId;
    }

    public Question (String question, String time){
        this.question = question;
        this.time = time;
    }

    public Question (String question){
        this.question = question;
    }

    public Question (){
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuestioner() {
        return questioner;
    }

    public void setQuestioner(String questioner) {
        this.questioner = questioner;
    }

    public String getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(String seminarId) {
        this.seminarId = seminarId;
    }

}
