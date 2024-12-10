package hu.pte.mik.probazh.bean;

import java.util.List;

public record BookSaveDTO(
        String title,
        String isbn,
        List<Long> authorIds
) {
}
