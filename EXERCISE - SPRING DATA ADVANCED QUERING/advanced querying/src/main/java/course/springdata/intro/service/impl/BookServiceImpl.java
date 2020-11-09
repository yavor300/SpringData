package course.springdata.intro.service.impl;

import course.springdata.intro.dao.BookRepository;
import course.springdata.intro.entity.Book;
import course.springdata.intro.entity.enums.AgeRestriction;
import course.springdata.intro.entity.enums.Edition;
import course.springdata.intro.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService  {
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
    public List<Book> getAllByReleaseDateBefore(LocalDate localDate) {
        return bookRepository.getAllByReleaseDateBefore(localDate);
    }

    //8.11

    @Override
    public List<Book> getByAgeRestriction(AgeRestriction ageRestriction) {
        return bookRepository.getAllByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> getAllByEditionAndCopiesLessThan(Edition edition, int copies) {
        return bookRepository.getAllByEditionAndCopiesLessThan(edition, copies);
    }

    @Override
    public List<Book> getAllByPriceLessThanOrPriceGreaterThan(double lowerThan, double higherThan) {
        return bookRepository.getAllByPriceLessThanOrPriceGreaterThan(lowerThan, higherThan);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllByTitleLike(String pattern) {
        return bookRepository.getAllByTitleLike(pattern);
    }

    @Override
    public List<Book> getAllByAuthorLastNameLike(String pattern) {
        return bookRepository.getAllByAuthorLastNameLike(pattern);
    }

    @Override
    public List<Book> getAllByTitleLengthGreaterThan(int length) {
        return bookRepository.getAllByTitleLengthGreaterThan(length);
    }

    @Override
    public Integer getTotalBooksCopiesByAuthor(String fullName) {
        return bookRepository.getTotalBooksCopiesByAuthor(fullName);
    }

    @Override
    public List<Book> getAllByTitle(String title) {
        return bookRepository.getAllByTitle(title);
    }
}