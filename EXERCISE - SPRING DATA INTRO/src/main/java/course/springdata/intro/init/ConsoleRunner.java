package course.springdata.intro.init;

import course.springdata.intro.entity.Author;
import course.springdata.intro.entity.Book;
import course.springdata.intro.entity.Category;
import course.springdata.intro.entity.enums.AgeRestriction;
import course.springdata.intro.entity.enums.Edition;
import course.springdata.intro.service.AuthorService;
import course.springdata.intro.service.BookService;
import course.springdata.intro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final CategoryService categoryService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public ConsoleRunner(CategoryService categoryService, BookService bookService, AuthorService authorService) {
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed Data Into The Database
        //seedDatabase();

        // 1.Get all books after the year 2000. Print only their titles.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
//        LocalDate localDate = LocalDate.parse("12/12/1999", formatter);
//
//        List<Book> allByYearAfter = bookService.getAllByYearAfter(localDate);
//        for (Book book : allByYearAfter) {
//            System.out.println(book.getTitle());
//        }

        // 2. Get all authors with at least one book with release date before 1990. Print their first name and last name.
//        LocalDate localDateBefore = LocalDate.parse("1/1/1990", formatter);
//        List<Book> allByYearBefore = bookService.getAllByYearBefore(localDateBefore);
//        for (Book book : allByYearBefore) {
//            System.out.println(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName());
//        }

        // 3.Get all authors, ordered by the number of their books (descending). Print their first name, last name and book count
//        List<Author> authors = authorService.getAll();
//        authors.stream()
//                .sorted((f, s) -> Integer.compare(s.getBooks().size(), f.getBooks().size()))
//                .forEach(a -> System.out.printf("%s %s %d%n", a.getFirstName(), a.getLastName(), a.getBooks().size()));

        // 4.Get all books from author George Powell, ordered by their release date (descending), then by book title (ascending). Print the book's title, release date and copies
        List<Book> foundBooks = bookService.getAllByAuthorNamesOrderByReleaseDateDescThenTitleAsc("George", "Powell");
        for (Book book : foundBooks) {
            System.out.printf("%s %s %s%n", book.getTitle(), book.getReleaseDate(), book.getCopies());
        }

    }

    private void seedAuthors() throws IOException {
        BufferedReader authorsReader = new BufferedReader(new FileReader("src/main/java/course/springdata/intro/files/authors.txt"));
        String line = authorsReader.readLine();
        while((line = authorsReader.readLine()) != null){
            String[] data = line.split("\\s+");

            String firstName = data[0];
            String lastName = data[0];

            Author author = new Author(firstName, lastName);

            authorService.save(author);
        }
    }

    private void seedCategories() throws IOException {
        BufferedReader categoriesReader = new BufferedReader(new FileReader("src/main/java/course/springdata/intro/files/categories.txt"));
        String line = categoriesReader.readLine();
        while((line = categoriesReader.readLine()) != null){
            if (line.length() > 0) {
                Category category = new Category(line);
                categoryService.save(category);
            }
        }
    }

    private void seedBooks() throws IOException {
        BufferedReader booksReader = new BufferedReader(new FileReader("src/main/java/course/springdata/intro/files/books.txt"));
        String line = booksReader.readLine();
        while((line = booksReader.readLine()) != null){
            String[] data = line.split("\\s+");

            long size = authorService.size();
            System.out.println();

            Random random = new Random();
            int authorIndex = random.nextInt((int) authorService.size())+1;

            Author author = authorService.get(authorIndex);
            Edition editionType = Edition.values()[Integer.parseInt(data[0])];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate localDate = LocalDate.parse(data[1], formatter);
            int copies = Integer.parseInt(data[2]);
            Double price = Double.parseDouble(data[3]);
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];
            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 5; i < data.length; i++) {
                titleBuilder.append(data[i]).append(" ");
            }

            String title = titleBuilder.toString().trim();

            Book book = new Book();
            book.setAuthor(author);
            book.setEdition(editionType);
            book.setReleaseDate(localDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);
            bookService.save(book);

            int categoryIndex = random.nextInt((int) categoryService.size()) + 1;
            Category category = categoryService.get(categoryIndex);

            Long id = bookService.save(book).getId();

            Book savedBook = bookService.get(id);

            savedBook.getCategories().add(category);
            category.getBooks().add(book);

            bookService.update(savedBook);
            categoryService.update(category);

        }

    }

    public void seedDatabase() throws IOException {
        seedAuthors();
        seedCategories();
        seedBooks();
    }

}
