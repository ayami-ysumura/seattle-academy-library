package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.BookDetailsInfo;

@Configuration
public class BookDetailsInfoRowMapper implements RowMapper<BookDetailsInfo> {

    @Override
    public BookDetailsInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Query結果（ResultSet rs）を、オブジェクトに格納する実装
        BookDetailsInfo bookDetailsInfo = new BookDetailsInfo();
        //青はSQLから渡ってくるやつ
        bookDetailsInfo.setBookId(rs.getInt("book_id"));
        bookDetailsInfo.setTitle(rs.getString("title"));
        bookDetailsInfo.setAuthor(rs.getString("author"));
        bookDetailsInfo.setPublisher(rs.getString("publisher"));
        bookDetailsInfo.setThumbnailUrl(rs.getString("thumbnail_url"));
        bookDetailsInfo.setThumbnailName(rs.getString("thumbnail_name"));
        bookDetailsInfo.setPublishDate(rs.getString("publish_date"));
        bookDetailsInfo.setIsbn(rs.getString("isbn"));
        bookDetailsInfo.setDescription(rs.getString("description"));
        bookDetailsInfo.setFavoCount(rs.getInt("favo_count"));
        return bookDetailsInfo;
    }

}