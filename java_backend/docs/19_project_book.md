# 19 · 图书模块（Book）—— CRUD 套路再来一遍

> 目标：给 book 表做增删改查。**套路跟你 user 模块一模一样**，只是表和字段换了。
> 前置：跑通了 18 的建库建表，数据库切到了 library。

---

## 零、这一步要干嘛

图书模块 = book 表的增删改查。好消息：你在 14 学的"实体类 + Mapper + Service + Controller"**四件套套路，现在原样再用一遍**，只是把 User 换成 Book、user 表换成 book 表。

**建议：先自己试着写**（把 User 那套照着改成 Book），写不出来再往下看完整代码。这能帮你真正内化"CRUD 是通用套路"，而不是每次都靠抄。

> 这正是真实开发的日常：一个项目里的图书、读者、订单……每个模块都在重复同一套 CRUD 套路。所以这一步越熟练，后面越轻松。

### 📋 这一步要建 4 个文件（全在 `com.example.springbootdemo` 包下）

| 文件 | 干什么 |
|---|---|
| `Book.java` | 实体类（对应 book 表） |
| `BookMapper.java` | 写 SQL |
| `BookService.java` | 业务层 |
| `BookController.java` | 接口 |

---

## 一、Book 实体类（对应 book 表）

**📁 写在：`src/main/java/com/example/springbootdemo/Book.java`**

book 表有 4 列：id、title、author、status，对应 4 个字段：

```java
package com.example.springbootdemo;

public class Book {
    private Integer id;
    private String title;    // 书名
    private String author;   // 作者
    private String status;   // '0'=在馆，'1'=已借出

    // getter / setter 用 IDEA 的 Alt+Insert 自动生成
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
```

> 注意 `status` 这个字段：它代表"这本书在不在馆"，是后面**借书 / 还书**的核心（借书把它改成 '1'，还书改回 '0'）。现在 CRUD 阶段先手动填，后面会自动改。

---

## 二、BookMapper（写 SQL）

**📁 写在：`src/main/java/com/example/springbootdemo/BookMapper.java`**

跟 UserMapper 一个样，四句 SQL 对应增删改查：

```java
package com.example.springbootdemo;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select * from book where id = #{id}")
    Book findById(Integer id);

    @Select("select * from book")
    List<Book> findAll();

    @Insert("insert into book(title, author, status) values(#{title}, #{author}, #{status})")
    void insert(Book book);

    @Update("update book set title=#{title}, author=#{author}, status=#{status} where id=#{id}")
    int update(Book book);

    @Delete("delete from book where id=#{id}")
    int delete(Integer id);
}
```

---

## 三、BookService（业务层）

**📁 写在：`src/main/java/com/example/springbootdemo/BookService.java`**

```java
package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    public Book findById(Integer id) {
        return bookMapper.findById(id);
    }

    public List<Book> findAll() {
        return bookMapper.findAll();
    }

    public void insert(Book book) {
        bookMapper.insert(book);
    }

    public int update(Book book) {
        return bookMapper.update(book);
    }

    public int delete(Integer id) {
        return bookMapper.delete(id);
    }
}
```

---

## 四、BookController（接口）

**📁 写在：`src/main/java/com/example/springbootdemo/BookController.java`**

```java
package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    // 查一个
    @GetMapping("/book/{id}")
    public Result getBook(@PathVariable Integer id) {
        return Result.success(bookService.findById(id));
    }

    // 查所有
    @GetMapping("/books")
    public Result listBooks() {
        return Result.success(bookService.findAll());
    }

    // 增
    @PostMapping("/book")
    public Result addBook(@RequestBody Book book) {
        bookService.insert(book);
        return Result.success();
    }

    // 改
    @PutMapping("/book/{id}")
    public Result updateBook(@PathVariable Integer id, @RequestBody Book book) {
        book.setId(id);
        int count = bookService.update(book);
        if (count > 0) {
            return Result.success();
        } else {
            return Result.error("修改失败：没找到 id=" + id + " 的图书");
        }
    }

    // 删
    @DeleteMapping("/book/{id}")
    public Result deleteBook(@PathVariable Integer id) {
        int count = bookService.delete(id);
        if (count > 0) {
            return Result.success();
        } else {
            return Result.error("删除失败：没找到 id=" + id + " 的图书");
        }
    }
}
```

---

## 五、动手练习

1. 建好 4 个文件；
2. 重启服务；
3. 用 REST Client 测（在 test.http 里加）：

```http
### 查所有图书
GET http://localhost:8080/books

### 新增一本图书
POST http://localhost:8080/book
Content-Type: application/json

{
  "title": "百年孤独",
  "author": "马尔克斯",
  "status": "0"
}

### 修改 id=1 的图书
PUT http://localhost:8080/book/1
Content-Type: application/json

{
  "title": "Java核心技术（第12版）",
  "author": "霍斯特曼",
  "status": "0"
}

### 删除 id=4 的图书
DELETE http://localhost:8080/book/4
```

4. 逐个测，确认增删改查都通。

> 做完你会发现：**图书模块跟你 user 模块长得几乎一模一样**——这就是"套路"。后面读者模块也是这套，你会越写越快。

---

## 怎么用（一句话）

一个模块的 CRUD = 四件套（实体类 → Mapper → Service → Controller）+ 四句 SQL（SELECT/INSERT/UPDATE/DELETE）+ 四个 HTTP 方法（GET/POST/PUT/DELETE）。学会了，套到任何表上都一样。
