package course.springdata.intro.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<Book> books;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}