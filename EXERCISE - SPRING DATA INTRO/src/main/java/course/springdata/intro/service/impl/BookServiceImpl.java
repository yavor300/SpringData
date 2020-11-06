package course.springdata.intro.service.impl;

import course.springdata.intro.dao.BookRepository;
import course.springdata.intro.entity.Book;
import course.springdata.intro.entity.enums.Edition;
import course.springdata.intro.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return this.bookRepository.saveAndFlush(book);
    }

    @Override
    public Book get(long id) {
        return bookRepository.getById(id);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllByYearAfter(LocalDate localDate) {
        return bookRepository.getAllByReleaseDateAfter(localDate);
    }

    @Override
    public List<Book> getAllByYearBefore(LocalDate localDate) {
        return bookRepository.getAllByReleaseDateBefore(localDate);
    }

    @Override
    public List<Book> getAllByAuthorNamesOrderByReleaseDateDescThenTitleAsc(String firstName, String lastName) {
        return bookRepository.getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName, lastName);
    }
}