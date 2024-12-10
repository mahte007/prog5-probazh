package hu.pte.mik.probazh.repository;

import hu.pte.mik.probazh.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
