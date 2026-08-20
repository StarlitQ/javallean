package com.example.springbootdemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("/reader/{id}")
    public Result getReader(@PathVariable Integer id){return Result.success(readerService.findById(id));}

    @GetMapping("/readers")
    public Result getLists(){return Result.success(readerService.findAll());}

    @PostMapping("/reader")
    public Result addReader(@RequestBody Reader reader){
        readerService.insert(reader);
        return  Result.success();
    }

    @PutMapping("/reader/{id}")
    public Result updateReader(@PathVariable Integer id, @RequestBody Reader reader){
        reader.setId(id);
        int count = readerService.update(reader);
        if (count > 0) return Result.success();
        else return Result.error("failed to update");
    }

    @DeleteMapping("/reader/{id}")
    public Result deleteReader(@PathVariable Integer id){
        int count = readerService.delete(id);
        if (count > 0) return Result.success();
        else return Result.error("failed to delete");
    }

}
