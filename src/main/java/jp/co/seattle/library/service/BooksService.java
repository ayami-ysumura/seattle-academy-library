package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 */
/**
 * @author user
 *
 */
@Service
public class BooksService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍リストを取得する
     *
     * @return 書籍リスト
     */
    public List<BookInfo> getBookList() {
        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> getedBookList = jdbcTemplate.query(
                "select book_id,title,author,publisher,publish_date,thumbnail_url, isbn, description"
                        + " from books order by title asc",
                new BookInfoRowMapper());
        return getedBookList;
    }

    /**
     * 書籍IDに紐づく書籍詳細情報と、書籍IDとユーザーIDに紐づくお気に入り登録の数を取得する
     * @param bookId 書籍ID
     * @param userId ユーザーID
     * @return 書籍情報
     */
    //BookDetailsInfoは型
    public BookDetailsInfo getBookInfo(int bookId, int userId) {
        // JSPに渡すデータを設定する
        String sql = "select book_id,title,author,publisher,publish_date,thumbnail_url,thumbnail_name,isbn,description,(select count(*) from favorites where book_id="
                + bookId + " and user_id =" + userId + ") as favo_count FROM books where book_id =" + bookId;
        BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());
        return bookDetailsInfo;
    }

    /**
     * 書籍の編集情報を更新する
     * @param editBookInfo
     */
    public void editBook(BookDetailsInfo editBookInfo) {
        String sql = "UPDATE books SET title='"
                + editBookInfo.getTitle() + "',author='" + editBookInfo.getAuthor()
                + "',publisher='" + editBookInfo.getPublisher()
                + "',thumbnail_name='" + editBookInfo.getThumbnailName()
                + "',thumbnail_url='" + editBookInfo.getThumbnailUrl()
                + "',publish_date='" + editBookInfo.getPublishDate()
                + "',upd_date=" + "sysdate()"
                + ",isbn='" + editBookInfo.getIsbn()
                + "',description='" + editBookInfo.getDescription()
                + "'  WHERE book_id=" + editBookInfo.getBookId() + ";";
        jdbcTemplate.update(sql);
    }

    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void registBook(BookDetailsInfo bookInfo) {
        //SQLと繋がってるところ（括弧内はSQLのカラム名と同じじゃなきゃ追加できない）
        String sql = "INSERT INTO books (title, author,publisher,thumbnail_url,thumbnail_name,publish_date,isbn,description,reg_date,upd_date) VALUES ('"
                + bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
                + bookInfo.getThumbnailUrl() + "','"
                + bookInfo.getThumbnailName() + "','"
                + bookInfo.getPublishDate() + "','"
                + bookInfo.getIsbn() + "','"
                + bookInfo.getDescription() + "',"
                + "sysdate(),"
                + "sysdate())";
        //SQLの内容をデータベースに投げる
        jdbcTemplate.update(sql);
    }

    /**
     * 追加した書籍情報を取得
     * @return 書籍情報
     */
    public int newRegistBook() {
        String sql = "select max(book_id) from books";
        int bookId = jdbcTemplate.queryForObject(sql, Integer.class);
        return bookId;
    }

    /**
     * 書籍を削除する
     * @param bookId　書籍情報
     */
    public void deletingBook(int bookId) {
        String sql = "delete from books where book_id =" + bookId;
        jdbcTemplate.update(sql);
    }
}