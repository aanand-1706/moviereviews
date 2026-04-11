package com.aanand.demo;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
//this annotation maps it to database table
//springboot creates the table since we have configured it in application.properties using create-drop
public class Movie extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //to specify primary key
    private Integer id;
    private String title;
    private String director;

    public Movie(){

    }

    public Movie(String title, String director) {
        this.title = title;
        this.director = director;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
