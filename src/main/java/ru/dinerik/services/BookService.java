package ru.dinerik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dinerik.models.Book;
import ru.dinerik.models.Person;
import ru.dinerik.repositories.BooksRepository;
import ru.dinerik.repositories.PeopleRepository;

import java.util.*;

// Бизнес логика
@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }
    @Transactional
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Transactional
    public List<Book> findAll(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    @Transactional
    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }
    @Transactional
    public List<Book> findBooksByOwnerId(int id) {
       return booksRepository.findAll().stream().filter(book -> Optional.ofNullable(book.getOwner()).equals(peopleRepository.findById(id))).toList();

    }
    @Transactional
    public Optional<Person> findOwnerById(int id) {
        return Optional.ofNullable(booksRepository.findById(id).get().getOwner());
        //return Optional.ofNullable(Objects.requireNonNull(booksRepository.findById(id).orElse(null)).getOwner());
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setId(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    // Назначает книгу человеку (этот метод вызывается, когда человек забирает книгу из библиотеки)
    @Transactional
    public void assign(int id, Person selectedPerson) {
        booksRepository.findById(id).get().setOwner(selectedPerson);
    }

    // Освобождаем книгу (этот метод вызывается, когда человек оставляет книгу в библиотеке)
    // Нужно в поле person_id таблицы book сделать null
    @Transactional
    public void release(int id) {
        booksRepository.findById(id).get().setOwner(null);
    }
}