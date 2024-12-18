package com.example.AttendanceApp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "separates")
public class Separate extends BaseEntity{
    private String title;
    private String description;

    @OneToMany(mappedBy = "separate")
    private List<Employee> employees = new ArrayList<>();

    public Separate() {
    }

    public Separate(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Separate{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
