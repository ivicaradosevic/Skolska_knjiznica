package fpmoz.sum.ba.school_library.school_library.model;

public class User extends Table{

    @Entity(type = "INTEGER", size = 8, primary = true)
    private Long id;

    @Entity(type = "VARCHAR", size = 255)
    private String oib;

    @Entity(type = "VARCHAR", size = 255)
    private String first_name;

    @Entity(type = "VARCHAR", size = 255)
    private String last_name;

    @Entity(type = "VARCHAR", size = 255)
    private String email;

    @Entity(type = "VARCHAR", size = 255)
    private String username;

    @Entity(type = "VARCHAR", size = 255)
    private String password;

    @ForeignKey(table = "UserType", attribute = "id")
    @Entity(type = "INTEGER", size = 8)
    private Long user_type_id;

    @Entity(type = "INTEGER", size = 1)
    private Integer year;

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(Long user_type_id) {
        this.user_type_id = user_type_id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
