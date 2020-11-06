package course.springdata.intro.service;

import course.springdata.intro.entity.Book;
import course.springdata.intro.entity.enums.Edition;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    Book save(Book book);

    Book get(long id);

    Book update(Book book);

    List<Book> getAllByYearAfter(LocalDate localDate);

    List<Book> getAllByYearBefore(LocalDate localDate);

    List<Book> getAllByAuthorNamesOrderByReleaseDateDescThenTitleAsc(String firstName, String lastName);
}
