package course.springdata.intro.dao;

import course.springdata.intro.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getById(long id);
}
