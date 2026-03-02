package com.example.solve_bridge;

public class Post {
    public String user, title, desc;

    public Post() {}

    public Post(String user, String title, String desc) {
        this.user = user;
        this.title = title;
        this.desc = desc;
    }

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
