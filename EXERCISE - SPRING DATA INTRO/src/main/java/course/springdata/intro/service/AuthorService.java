package course.springdata.intro.service;

import course.springdata.intro.entity.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);

    Author get(long index);

    long size();

    List<Author> getAll();
}
