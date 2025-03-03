package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
     * @param bookId 書籍ID
     */
    public void rentBook(int bookId) {
        String sql = "INSERT INTO rent_books (book_id) VALUES (" + bookId + ")";
        jdbcTemplate.update(sql);
    }

    /**
     * 貸出リスト内の貸出書籍を削除
     * 
     * @param bookId 書籍ID
     */
    public void returnBook(int bookId) {
        String sql = "delete from rent_books where book_id =" + bookId;
        jdbcTemplate.update(sql);
    }
}