package fpmoz.sum.ba.school_library.school_library.model;

import java.time.LocalDate;

public class IssuedBooksTableModel {

    private String bookTitle;
    private String bookIssuer;
    private LocalDate issueDate;
    private String bookCategory;
    private String userType;

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookIssuer() {
        return bookIssuer;
    }

    public void setBookIssuer(String bookIssuer) {
        this.bookIssuer = bookIssuer;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
