package kz.netcracker.libra.repository;

import kz.netcracker.libra.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(Long authorId);

    boolean existsByIsbnIgnoreCase(String isbn);

    boolean existsByTitleIgnoreCaseAndAuthorId(String title, Long authorId);

    Optional<Book> findByQrCode(String qrCode);
}