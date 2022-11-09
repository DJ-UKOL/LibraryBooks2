package ru.dinerik.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.dinerik.models.Book;

import javax.persistence.EntityManager;
import java.util.List;

public class BookDAO {

    private final EntityManager entityManager;  //  Взаимодействовать с постоянным контекстом

    @Autowired
    public BookDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Book show(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Book.class, id);
    }

    @Transactional
    public void save(Book book) {
        Session session = entityManager.unwrap(Session.class);
        session.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Session session = entityManager.unwrap(Session.class);
        Book bookToBeUpdate = session.get(Book.class, id);

        bookToBeUpdate.setTitle(updatedBook.getTitle());
        bookToBeUpdate.setYear(updatedBook.getYear());
    }

    @Transactional
    public void delete(int id) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(session.get(Book.class, id));
    }
}
