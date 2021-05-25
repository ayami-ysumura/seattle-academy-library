package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

@Service
public class FavoriteService {
    final static Logger logger = LoggerFactory.getLogger(FavoriteService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * お気に入り登録
     * @param bookId
     * @param userId
     */
    public void favoRegist(int bookId, int userId) {
        String sql = "insert into favorites(book_id,user_id,reg_date)values("
                + bookId + "," + userId + ","
                + "sysdate()"
                + ")";
        jdbcTemplate.update(sql);
    }

    /**
     * お気に入り解除（削除）
     * @param bookId
     * @param userId
     */
    public void favoDelete(int bookId, int userId) {
        String sql = "delete from favorites where book_id="
                + bookId + " and user_id=" + userId;
        jdbcTemplate.update(sql);
    }

    /**
     * お気に入り書籍と書籍情報を取得
     * @param userId
     * @return getedFavoList
     */
    public List<BookInfo> getFavoList(int userId) {
        List<BookInfo> getedFavoList = jdbcTemplate.query(
                "select book_id,title,author,publisher,publish_date,thumbnail_url,thumbnail_name,isbn,description"
                + " from books where book_id in(select book_id from favorites where user_id="
                        + userId + " order by reg_date asc)",
                new BookInfoRowMapper());
        return getedFavoList;
    }

}
