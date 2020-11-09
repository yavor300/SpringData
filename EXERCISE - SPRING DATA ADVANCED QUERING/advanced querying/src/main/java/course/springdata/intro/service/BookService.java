package course.springdata.intro.service;

import course.springdata.intro.entity.Book;
import course.springdata.intro.entity.enums.AgeRestriction;
import course.springdata.intro.entity.enums.Edition;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    Book save(Book book);

    Book get(long id);

    Book update(Book book);

    List<Book> getAllByReleaseDateBefore(LocalDate localDate);

    List<Book> getByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> getAllByEditionAndCopiesLessThan(Edition edition, int copies);

    List<Book> getAllByPriceLessThanOrPriceGreaterThan(double lowerThan, double higherThan);

    List<Book> getAll();

    List<Book> getAllByTitleLike(String pattern);

    List<Book> getAllByAuthorLastNameLike(String pattern);

    List<Book> getAllByTitleLengthGreaterThan(int length);

    Integer getTotalBooksCopiesByAuthor(String fullName);

    List<Book> getAllByTitle(String title);
}
