package com.example.springbootdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/book/{id}")
    public Result getBook(@PathVariable Integer id){return Result.success(bookService.findById(id));}

    @GetMapping("/books")
    public Result listBooks(){return Result.success(bookService.findAll());}

    @PostMapping("/book")
    public Result addBook(@RequestBody Book book){
        bookService.insert(book);
        return Result.success();
    }

    @PutMapping("/book/{id}")
    public Result updateBook(@PathVariable Integer id, @RequestBody Book book){
        book.setId(id);
        int count = bookService.update(book);
        if (count > 0) return Result.success();
        else return Result.error("failed to update id: " + id);
    }

    @DeleteMapping("/book/{id}")
    public Result deleteBook(@PathVariable Integer id){
        int count = bookService.delete(id);
        if (count > 0) return Result.success();
        else return Result.error("failed to delete id: " + id);
    }

}
