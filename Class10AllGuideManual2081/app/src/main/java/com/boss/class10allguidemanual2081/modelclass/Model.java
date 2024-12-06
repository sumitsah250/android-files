package com.boss.class10allguidemanual2081.modelclass;

public class Model {

    String id,chapter,image,bookPdfLink,SolutionPdfLink;

    public Model() {
    }

    public Model(String id, String chapter, String image, String bookPdfLink, String solutionPdfLink) {
        this.id = id;
        this.chapter = chapter;
        this.image = image;
        this.bookPdfLink = bookPdfLink;
        SolutionPdfLink = solutionPdfLink;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBookPdfLink() {
        return bookPdfLink;
    }

    public void setBookPdfLink(String bookPdfLink) {
        this.bookPdfLink = bookPdfLink;
    }

    public String getSolutionPdfLink() {
        return SolutionPdfLink;
    }

    public void setSolutionPdfLink(String solutionPdfLink) {
        SolutionPdfLink = solutionPdfLink;
    }
}
