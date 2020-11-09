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
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

//        // Seed Data Into The Database
//        //seedDatabase();
//
//        // 1.Books Titles by Age Restriction
//        String ageRestrictionInput = scanner.nextLine();
//        AgeRestriction ageRestriction = AgeRestriction.valueOf(ageRestrictionInput.toUpperCase());
//        bookService.getByAgeRestriction(ageRestriction)
//                .forEach(b -> System.out.println(b.getTitle()));
//
//
//        // 2.Golden Books
//        bookService.getAllByEditionAndCopiesLessThan(Edition.GOLD, 5000)
//                .forEach(b -> System.out.println(b.getTitle()));
//
//        // 3.BooksByPrice
//        bookService.getAllByPriceLessThanOrPriceGreaterThan(5, 40)
//                .forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));
//
//        // 4.Not Released Books
//        int year = Integer.parseInt(scanner.nextLine());
//        bookService.getAll()
//                .stream()
//                .filter(b -> b.getReleaseDate().getYear() != year)
//                .forEach(b -> System.out.println(b.getTitle()));
//
//        // 5.Books Released Before Date
//        String date = scanner.nextLine();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDate localDate = LocalDate.parse(date, formatter);
//        bookService.getAllByReleaseDateBefore(localDate)
//                .forEach(b -> System.out.printf("%s %s %.2f%n", b.getTitle(), b.getEdition(), b.getPrice()));
//
//        // 6.Authors Search
//        String pattern = "%" + scanner.nextLine();
//        authorService.getAllByFirstNameIsLike(pattern)
//                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
//
//
//        // 7.Books Search
//        String input = scanner.nextLine().toUpperCase();
//        bookService.getAllByTitleLike("%" + input + "%")
//                .forEach(b -> System.out.println(b.getTitle()));
//
//        // 8.Book Titles Search
//        String input = scanner.nextLine().toUpperCase();
//        bookService.getAllByAuthorLastNameLike(input + "%")
//                .forEach(b -> System.out.printf("%s (%s %s)%n", b.getTitle(), b.getAuthor().getFirstName(),
//                        b.getAuthor().getLastName()));
//
//        // 9.Count Books
//        int length = Integer.parseInt(scanner.nextLine());
//        System.out.println(bookService.getAllByTitleLengthGreaterThan(length).size());
//
//        // 10.Total Books Copies
//        authorService.getAll()
//                .forEach(a -> System.out.printf("%s %s - %d%n",
//                        a.getFirstName(), a.getFirstName(),
//                        bookService.getTotalBooksCopiesByAuthor(a.getFirstName() + " " + a.getLastName())));
//
//        // 11.Reduced Book
//        String title = scanner.nextLine();
//        bookService.getAllByTitle(title)
//                .forEach(b -> System.out.printf("%s %s %s %.2f%n", b.getTitle(), b.getEdition(), b.getAgeRestriction(), b.getPrice()));

    }

    private void seedAuthors() throws IOException {
        BufferedReader authorsReader = new BufferedReader(new FileReader("src/main/java/course/springdata/intro/files/authors.txt"));
        String line = authorsReader.readLine();
        while((line = authorsReader.readLine()) != null){
            String[] data = line.split("\\s+");

            String firstName = data[0];
            String lastName = data[1];

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
