package ru.dinerik.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 и до 100 символов длинной.")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Автор книги не должен быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора книги должно быть от 2 до 100 символов длинной.")
    @Column(name = "author")
    private String author;

    @Range(min = 1500, max = 2022, message = "Год издания книги должен быть больше чем 1500 и в правильном формате (2022")
    @Column(name = "year")
    private int year;

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @ManyToOne
    private Person owner;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (year != book.year) return false;
        if (!Objects.equals(title, book.title)) return false;
        return Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
