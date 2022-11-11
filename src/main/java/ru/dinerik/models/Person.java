package ru.dinerik.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity // Указывает, что данный бин (класс) является сущностью.
@Table(name = "Person") // Указывает, что данный бин (класс) является сущностью.
public class Person {

    @Id //  id колонки
    @Column(name = "id")    // указывает на имя колонки, которая отображается в свойство сущности.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Указывает, что данное свойство будет создаваться
    // согласно указанной стратегии. IDENTITY автоматически присваивает значение первичному ключу.
    // (AUTO, SEQUENCE, TABLE)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message="Имя должно быть от 2 до 100 символов длинной")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "Год рождения должен быть не меньше 1900")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    // Конструктор по умолчанию нужен для Spring
    public Person() {}

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (yearOfBirth != person.yearOfBirth) return false;
        return Objects.equals(fullName, person.fullName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + yearOfBirth;
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}