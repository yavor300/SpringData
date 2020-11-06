package course.springdata.intro.service.impl;

import course.springdata.intro.dao.AuthorRepository;
import course.springdata.intro.entity.Author;
import course.springdata.intro.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {
        return this.authorRepository.saveAndFlush(author);
    }

    @Override
    public Author get(long index) {
        return authorRepository.getById(index);
    }

    @Override
    public long size() {
        return authorRepository.count();
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
