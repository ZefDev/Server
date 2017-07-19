package com.example.vadim.dpapp.container;

/**
 * Created by Vadim on 05.05.2017.
 */
public class UserContainer {
    String UID;
    String login;
    String post;
    String lastDate;
    String right;
    String contractor;

    public UserContainer(String UID, String login, String post, String lastDate, String right, String contractor) {
        this.UID = UID;
        this.login = login;
        this.post = post;
        this.lastDate = lastDate;
        this.right = right;
        this.contractor = contractor;
    }

    public String getContractor() {

        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }


    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLogin() {
        return login;
    }

    public String getPost() {
        return post;
    }

    public String getUID() {
        return UID;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
