package course.springdata.intro.service;

import course.springdata.intro.entity.Category;

public interface CategoryService {
    Category save(Category category);

    long size();

    Category get(long id);

    Category update(Category category);
}
