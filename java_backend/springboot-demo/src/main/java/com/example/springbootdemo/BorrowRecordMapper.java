package com.example.springbootdemo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BorrowRecordMapper {
    @Select("select br.id, br.book_id, br.reader_id, br.borrow_date, br.return_date, " +
            "b.title as book_title, r.name as reader_name " +
            "from borrow_record br " +
            "left join book b on br.book_id = b.id " +
            "left join reader r on br.reader_id = r.id " +
            "where b.title like concat('%' , #{keyword}, '%') " +
            "order by br.id desc " +
            "limit #{offset}, #{pageSize}"
    )
    List<BorrowRecordVO> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("pageSize") int pageSize);


    @Insert("insert into borrow_record(book_id, reader_id, borrow_date) values(#{bookId}, #{readerId}, #{borrowDate})")
    void insert(@Param("bookId") Integer bookId,
                @Param("readerId") Integer readerId,
                @Param("borrowDate") LocalDate borrowDate);

    @Update("update borrow_record set return_date = #{returnDate} where book_id = #{bookId} and return_date is null")
    int updateReturnDate(@Param("bookId") Integer bookId,
                         @Param("returnDate") LocalDate returnDate);

}
