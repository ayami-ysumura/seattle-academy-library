package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;

/**
 * 貸出サービス
 * @author user
 *
 */
@Service
public class RentBookService {
    final static Logger logger = LoggerFactory.getLogger(RentBookService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 貸出リスト内の書籍IDの数を取得
     * 
     * @param bookId
     * @return 貸出情報
     */
    public int getRentNum(int bookId) {
        String sql = "SELECT COUNT(*) FROM rent_books where book_id=" + bookId;
        int getListedInfo = jdbcTemplate.queryForObject(sql, Integer.class);
        return getListedInfo;
    }

    /**
     * 書籍IDを貸出リストに追加
     * 
     * @param rentBookInfo
     */
    public void rentBook(BookDetailsInfo rentBookInfo) {
        String sql = "INSERT INTO rent_books (book_id) VALUES (" + rentBookInfo.getBookId() + ")";
        jdbcTemplate.update(sql);
    }

    /**
     * 貸出リスト内の貸出書籍を削除
     * 
     * @param returnBookInfo
     */
    public void returnBook(BookDetailsInfo returnBookInfo) {
        String sql = "delete from rent_books where book_id =" + returnBookInfo.getBookId();
        jdbcTemplate.update(sql);
    }
}