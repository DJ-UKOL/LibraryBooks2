package ru.dinerik.repositories;


import ru.dinerik.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Репозиторий для работы с сущностями, людьми.
// Компонент, который предназначен для хранения, извлечения и поиска.
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
