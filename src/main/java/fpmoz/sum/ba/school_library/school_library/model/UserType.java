package fpmoz.sum.ba.school_library.school_library.model;

public class UserType extends Table {

    @Entity(type = "INTEGER", size = 8, primary = true)
    private Long id;

    @Entity(type = "VARCHAR", size = 255)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
