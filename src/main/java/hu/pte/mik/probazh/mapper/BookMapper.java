package hu.pte.mik.probazh.mapper;

import hu.pte.mik.probazh.bean.BookDTO;
import hu.pte.mik.probazh.bean.BookSaveDTO;
import hu.pte.mik.probazh.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO bookToBookDTO(Book book);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "authors", ignore = true),
    })
    Book bookSaveDTOToBook(BookSaveDTO bookSaveDTO);

    List<BookDTO> booksToBookDTOs(List<Book> books);
}
