package hu.pte.mik.probazh.service;

import hu.pte.mik.probazh.bean.BookDTO;
import hu.pte.mik.probazh.bean.BookSaveDTO;
import hu.pte.mik.probazh.entity.Author;
import hu.pte.mik.probazh.entity.Book;
import hu.pte.mik.probazh.mapper.BookMapper;
import hu.pte.mik.probazh.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @PersistenceContext
    private EntityManager entityManager;


    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> listAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.booksToBookDTOs(books);
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return bookMapper.bookToBookDTO(book);
    }

    public BookDTO createBook(BookSaveDTO dto) {
        List<Author> authors = dto.authorIds() != null
                ? dto.authorIds().stream()
                .map(authorId -> {
                    Author author = entityManager.find(Author.class, authorId);
                    if (author == null) {
                        throw new RuntimeException("Author not found");
                    }
                    return author;
                }).toList()
                : List.of();


        Book book = bookMapper.bookSaveDTOToBook(dto);
        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setAuthors(authors);

        Book savedBook = bookRepository.save(book);
        return bookMapper.bookToBookDTO(savedBook);
    }

    public BookDTO updateBook(Long id, BookSaveDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        List<Author> authors = dto.authorIds() != null
                ? dto.authorIds().stream()
                .map(authorId -> {
                    Author author = entityManager.find(Author.class, authorId);
                    if (author == null) {
                        throw new RuntimeException("Author not found");
                    }
                    return author;
                }).toList()
                : new ArrayList<>();

        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setAuthors(new ArrayList<>(authors));

        Book updatedBook = bookRepository.save(book);

        return bookMapper.bookToBookDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}
