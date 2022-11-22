package ru.dinerik.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dinerik.models.Book;
import ru.dinerik.models.Person;
import ru.dinerik.services.BookService;
import ru.dinerik.services.PeopleService;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    // http://localhost:8080/books?page=1&books_per_page=3
    // http://localhost:8080/books?sort_by_year=true
    @GetMapping()
    public String index(@RequestParam("page") Optional<Integer> page, @RequestParam("books_per_page") Optional<Integer> booksPerPage, @RequestParam("sort_by_year") Optional<Boolean> sortByYear, Model model) {
        model.addAttribute("books", bookService.findAll(page, booksPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Optional<Person> bookOwner = bookService.findOwnerById(id);

        // Если книга принадлежит человеку, то показать этого человека, если нет, то показать список людей
        if(bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        }
        else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    // освобождение книги
    @PatchMapping("{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    // назначение книги
    @PatchMapping("{id}/assign")
    public String assign( @PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        // У selectedPerson назначено только поле id, остальные поля - null
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search_book") Optional<String> searchBook, Model model) {
        if(searchBook.isPresent() && !searchBook.get().equals("")) {
            model.addAttribute("books", bookService.search(searchBook.get()));
            return "books/search";
        }
        else {
            model.addAttribute("books", "");
        }
        return "books/search";
    }
}