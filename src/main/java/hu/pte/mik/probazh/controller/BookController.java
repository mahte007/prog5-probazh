package hu.pte.mik.probazh.controller;

import hu.pte.mik.probazh.bean.BookDTO;
import hu.pte.mik.probazh.bean.BookSaveDTO;
import hu.pte.mik.probazh.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "List all books", description = "Returns a list of all books in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all books")
    @GetMapping
    public List<BookDTO> listAllBooks() {
        return bookService.listAllBooks();
    }

    @Operation(summary = "Get book by ID", description = "Returns a book by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the book")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @GetMapping("/{id}")
    public BookDTO loadBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @Operation(summary = "Create a new book", description = "Creates a new book and returns the created book")
    @ApiResponse(responseCode = "201", description = "Successfully created the book")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookSaveDTO dto) {
        return bookService.createBook(dto);
    }

    @Operation(summary = "Edit an existing book", description = "Edits an existing book by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully edited the book")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @PutMapping("/{id}")
    public BookDTO edit(@PathVariable Long id, @RequestBody BookSaveDTO dto) {
        return bookService.updateBook(id, dto);
    }

    @Operation(summary = "Delete a book by ID", description = "Deletes a book by its ID")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the book")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
