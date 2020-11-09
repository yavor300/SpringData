package course.springdata.intro.dao;

import course.springdata.intro.entity.Book;
import course.springdata.intro.entity.enums.AgeRestriction;
import course.springdata.intro.entity.enums.Edition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book getById(long id);

    List<Book> getAllByReleaseDateAfter(LocalDate localDate);

    List<Book> getAllByReleaseDateBefore(LocalDate localDate);

    List<Book> getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    List<Book> getAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> getAllByEditionAndCopiesLessThan(Edition edition, int copies);

    List<Book> getAllByPriceLessThanOrPriceGreaterThan(double lowerThan, double higherThan);

    @Query("select b from Book b where upper(b.title) like :pattern")
    List<Book> getAllByTitleLike(@Param("pattern") String pattern);

    @Query("select b from Book b where upper(b.author.lastName) like :pattern")
    List<Book> getAllByAuthorLastNameLike(@Param("pattern") String pattern);

    @Query("select b from Book b where length(b.title) > :parameter")
    List<Book> getAllByTitleLengthGreaterThan(@Param("parameter") int length);

    @Query("select sum(b.copies) from Book b where concat(b.author.firstName, ' ', b.author.lastName) = :fullAuthorName")
    Integer getTotalBooksCopiesByAuthor(@Param("fullAuthorName") String fullAuthorName);

    List<Book> getAllByTitle(String title);
}
