package kz.netcracker.libra.repository;

import kz.netcracker.libra.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(Long authorId);
    boolean existsByIsbnIgnoreCase(String isbn);
    boolean existsByTitleIgnoreCaseAndAuthorId(String title, Long authorId);
}