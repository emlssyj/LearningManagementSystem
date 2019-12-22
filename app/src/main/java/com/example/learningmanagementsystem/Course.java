package com.example.learningmanagementsystem;

import java.util.Date;

public class Course {

    private String name,lecturer,avatar;
    private String description,lecturerPic;

    public String getLecturer() {
        return lecturer;
    }
    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getCourseName() {
        return name;
    }
    public void setCourseName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getLecturerPic() {
        return lecturerPic;
    }
    public void setLecturerPic(String lecturerPic) {
        this.lecturerPic = lecturerPic;
    }
    public Course(String lecturer, String name, String description,String avatar,String lecturerPic) {
        super();
        this.lecturer = lecturer;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.lecturerPic = lecturerPic;
    }
    public Course() {
        super();
    }
    @Override
    public String toString() {
        return "News [lecturer=" + lecturer + ", name=" + name + ", description=" + description +", avatar=" + avatar +", lecturePic=" + lecturerPic + "]";
    }
}