package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @GetMapping("/borrows")
    public Result listRecords(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int pageSize) {
        int offset = (page - 1) * pageSize;
        return Result.success(borrowService.search(keyword, offset, pageSize));
    }

    @PostMapping("borrow/{bookId}/{readerId}")
    public Result borrow(@PathVariable Integer bookId, @PathVariable Integer readerId){
        try{
            borrowService.borrow(bookId, readerId);
            return Result.success();
        }
        catch(RuntimeException e){
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/return/{bookId}")
    public Result returnBook(@PathVariable Integer bookId){
        try{
            borrowService.returnBook(bookId);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }


}
