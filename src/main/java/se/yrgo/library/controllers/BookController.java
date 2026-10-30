package se.yrgo.library.controllers;

import java.time.*;
import java.time.format.*;
import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import se.yrgo.library.domain.*;

@Controller
public class BookController {

    private static List<Book> books = List.of(
            new Book("Ico", "Erik Berik", "Fantasy"),
            new Book("Bordsbensmordet", "Babilla Bäckberg", "Deckare"),
            new Book("Mystiska Marmordjur", "Jordansk Man", "Fantasy"),
            new Book("Mänsklighetens Dumma Historia", "Vetaren", "Historia"),
            new Book("Hur man skapar en kakelplatta", "Muraren", "Fakta"),
            new Book("Ät som en jordbagge", "Hans In Sekt", "Mat"),
            new Book("Jag ville göra det gott", "Torsten Smakbrist", "Mat"),
            new Book("När du verkligen vill ha det där huset hugget i granit", "Ernst-Hugo", "Fakta"));

    @GetMapping("/home")
    public String homePage(Model model) {
        DateTimeFormatter formatting = DateTimeFormatter.ofPattern("yyyy-MM/dd HH:mm");
        LocalDateTime date = LocalDateTime.now(ZoneId.systemDefault());
        String formattedDate = date.format(formatting);
        model.addAttribute("date", formattedDate);
        return "home";
    }

    @GetMapping("/books")
    public String bookList(Model model) {
        model.addAttribute("books", books);
        return "booklist";
    }

    @GetMapping("/genre")
    public String filterByGenre(Model model, @RequestParam(required = false) List<String> type) {
        List<Book> filteredBooks;
        if (type == null || type.isEmpty()) {
            filteredBooks = books;
        } else {
            filteredBooks = books.stream()
                    .filter(book -> type.stream()
                            .anyMatch(t -> t.equalsIgnoreCase(book.getGenre())))
                    .toList();
        }
        model.addAttribute("allGenres", books.stream()
                .map(Book::getGenre).distinct().sorted().toList());
        model.addAttribute("books", filteredBooks);
        return "genre";
    }
}
