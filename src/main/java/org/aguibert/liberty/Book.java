package org.aguibert.liberty;

import javax.json.bind.annotation.JsonbProperty;

public class Book {
    @JsonbProperty
    private Integer ID;

    @JsonbProperty
    private String BOOKTITLE;

    @JsonbProperty
    private String nameofauthor;

    @JsonbProperty
    private Double PRICE;

    public Book() {
        ID = 999;

    }

    public Book(Integer id, String booktitle, String author, Double price) {
        ID = id;
        BOOKTITLE = booktitle;
        nameofauthor = author;
        PRICE = price;
        System.out.println("New book created: " + "id = " + id + " title = " + booktitle + " author = " + author);
    }

    /* getters */
    public Integer getID() {
        return ID;
    }

    public String getBOOKTITLE() {
        return BOOKTITLE;
    }

    public String getNameofauthor() {
        return nameofauthor;
    }

    public Double getPRICE() {
        return PRICE;
    }

    /* setters */
    public void setID(Integer id) {
        ID = id;
    }

    public void setBOOKTITLE(String booktitle) {
        BOOKTITLE = booktitle;
    }

    public void setNameofauthor(String author) {
        nameofauthor = author;
    }

    public void setPRICE(double price) {
        PRICE = price;
    }
}