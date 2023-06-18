package fpmoz.sum.ba.school_library.school_library.model;

public class Books extends Table {

    @Entity(type = "INTEGER", size = 8, primary = true)
    private Long id;

    @Entity(type = "VARCHAR", size = 255)
    private String title;

    @Entity(type = "VARCHAR", size = 255)
    private String author;

    @Entity(type = "VARCHAR", size = 255)
    private String isbn;

    @Entity(type = "VARCHAR", size = 255)
    private String publisher;

    @Entity(type = "INTEGER", size = 8)
    private int edition;

    @Entity(type = "INTEGER", size = 8)
    private int stock_number;

    @ForeignKey(table = "Category", attribute = "id")
    @Entity(type = "INTEGER", size = 8)
    private Long category_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public int getStock_number() {
        return stock_number;
    }

    public void setStock_number(int stock_number) {
        this.stock_number = stock_number;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

}
