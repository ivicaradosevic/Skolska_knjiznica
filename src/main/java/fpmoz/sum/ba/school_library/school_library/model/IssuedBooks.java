package fpmoz.sum.ba.school_library.school_library.model;

import java.time.LocalDate;

public class IssuedBooks extends Table {

    @Entity(type = "INTEGER", size = 8, primary = true)
    private Long id;

    @ForeignKey(table = "Books", attribute = "id")
    @Entity(type = "INTEGER", size = 8)
    private Long books_id;

    @ForeignKey(table = "User", attribute = "id")
    @Entity(type = "INTEGER", size = 8)
    private Long user_id;

    @Entity(type = "DATE")
    private LocalDate issue_date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBooks_id() {
        return books_id;
    }

    public void setBooks_id(Long books_id) {
        this.books_id = books_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public LocalDate getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(LocalDate issue_date) {
        this.issue_date = issue_date;
    }
}
