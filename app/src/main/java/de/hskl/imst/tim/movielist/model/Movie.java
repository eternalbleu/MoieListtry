package de.hskl.imst.tim.movielist.model;

import java.io.Serializable;

/**
 * Created by timap on 01.07.2017.
 */

public class Movie implements Serializable {
    private long id;
    private String title;
    private String desc;

    public Movie(String title) {
        this(title, null);
    }
    public Movie(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
