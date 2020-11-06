package course.springdata.intro.dao;

import course.springdata.intro.entity.Book;
import course.springdata.intro.entity.enums.Edition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book getById(long id);

    List<Book> getAllByReleaseDateAfter(LocalDate localDate);

    List<Book> getAllByReleaseDateBefore(LocalDate localDate);

    List<Book> getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);
}
