package course.springdata.intro.service.impl;

import course.springdata.intro.dao.CategoryRepository;
import course.springdata.intro.entity.Category;
import course.springdata.intro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }
}
