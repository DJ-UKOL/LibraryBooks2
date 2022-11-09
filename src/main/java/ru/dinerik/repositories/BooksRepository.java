package ru.dinerik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dinerik.models.Book;

// Репозиторий для работы с сущностями, книгами.
// Компонент, который предназначен для хранения, извлечения и поиска.
@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

}
