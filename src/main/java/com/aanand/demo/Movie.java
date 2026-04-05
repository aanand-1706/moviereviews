package com.aanand.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
//this annotation maps it to database table
//springboot creates the table since we have configured it in application.properties using create-drop
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //to specify primary key
    private UUID id;
    private String title;
    private String director;

    public Movie(){

    }

    public Movie(UUID id, String title, String director) {
        this.id = id;
        this.title = title;
        this.director = director;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
