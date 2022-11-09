package ru.dinerik.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.dinerik.models.Person;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class PersonDAO {

    private final EntityManager entityManager;  //  Взаимодействовать с постоянным контекстом

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("select p from Person p", Person.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        Session session = entityManager.unwrap(Session.class);
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = entityManager.unwrap(Session.class);
        Person personToBeUpdate = session.get(Person.class, id);

        personToBeUpdate.setFullName(updatedPerson.getFullName());
        personToBeUpdate.setYearOfBirth(updatedPerson.getYearOfBirth());
    }

    @Transactional
    public void delete(int id) {
        Session session = entityManager.unwrap(Session.class);
        session.delete(session.get(Person.class, id));
    }
}