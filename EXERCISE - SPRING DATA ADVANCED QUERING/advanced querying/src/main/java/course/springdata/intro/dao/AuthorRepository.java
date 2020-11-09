package course.springdata.intro.dao;

import course.springdata.intro.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

   Author getById(long id);

   List<Author> getAllByFirstNameIsLike(String pattern);
}
