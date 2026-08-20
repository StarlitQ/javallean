package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Transactional
    public void borrow(Integer bookId, Integer readerId) {
        Book book = bookMapper.findById(bookId);
        if (book == null) {
            throw new RuntimeException("这本书不存在：id=" + bookId);
        }
        if ("1".equals(book.getStatus())) {
            throw new RuntimeException("这本书已经被借出去了");
        }

        int count = bookMapper.updateStatus(bookId, "1");

        borrowRecordMapper.insert(bookId, readerId, LocalDate.now());
    }

    @Transactional
    public void returnBook(Integer bookId){
        int count = borrowRecordMapper.updateReturnDate(bookId, LocalDate.now());

        if (count == 0) {
            throw new RuntimeException("这本书没有未归还的借阅记录");
        }

        bookMapper.updateStatus(bookId, "0");
    }

    public List<BorrowRecordVO> search(String keyword, int offset, int pageSize) {
        return borrowRecordMapper.search(keyword, offset, pageSize);
    }

}
