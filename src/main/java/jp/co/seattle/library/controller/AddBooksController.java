package jp.co.seattle.library.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class AddBooksController {
    final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);
    //@Autowiredがいるとnewを使わずしてインスタンス化できる
    @Autowired
    private BooksService booksService;
    @Autowired
    private ThumbnailService thumbnailService;

    @RequestMapping(value = "/addBook", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String login(Model model) {
        return "addBook";
    }
    
    /**
     * 書籍情報を登録する
     * @param locale ロケール情報
     * @param title 書籍名
     * @param author 著者名
     * @param publisher 出版社
     * @param publish_Data 出版日
     * @param isbn ISBN
     * @param file サムネイルファイル
     * @param description 書籍説明
     * @param userId ユーザーID
     * @param model モデル
     * @return 遷移先画面
     */
    @Transactional
    //JSPでユーザーが入力した書籍情報（青）を変数（赤）に入れてJavaで使えるようにする
    @RequestMapping(value = "/insertBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String insertBook(Locale locale,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publish_date") String publishDate,
            @RequestParam("thumbnail") MultipartFile file,
            @RequestParam("isbn") String isbn,
            @RequestParam("description") String description,
            @RequestParam("userId") int userId,
            Model model) {
        logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

        // パラメータで受け取った書籍情報をDtoに格納する。
        //bookInfoのフィールドがpivateだからsetする
        //インスタンス化。ピンクはインスタンス名。（クラス名　インスタンス名　＝new　コンストラクタ）
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setTitle(title);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setPublishDate(publishDate);
        bookInfo.setIsbn(isbn);
        bookInfo.setDescription(description);
        
        
        // クライアントのファイルシステムにある元のファイル名を設定する
        String thumbnail = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                // サムネイル画像をアップロード
                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
                // URLを取得
                String thumbnailUrl = thumbnailService.getURL(fileName);
                bookInfo.setThumbnailName(fileName);
                bookInfo.setThumbnailUrl(thumbnailUrl);
            } catch (Exception e) {
                // 異常終了時の処理
                logger.error("サムネイルアップロードでエラー発生", e);
                model.addAttribute("bookDetailsInfo", bookInfo);
                return "addBook";
            }
        }
        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || publishDate.isEmpty()) {
            model.addAttribute("errorMessage", "必須項目が未入力のため登録できません");
            return "addBook";
        }
        //バリデーションチェック
        //ISBNが10または13桁の数字
        boolean isValidIsbn = isbn.matches("[0-9]{10}||[0-9]{13}");
        if (!isValidIsbn) {
            model.addAttribute("errorMessage", "ISBNの桁数または半角数字が正しくありません");
            return "addBook";
        }
        //日付チェック8桁＆日付の数字が日付として成り立たないものをはじく
        try {
            DateFormat df = new SimpleDateFormat("yyyymmdd");
            df.setLenient(false);
            df.format(df.parse(publishDate));
        } catch (ParseException e) {
            model.addAttribute("errorMessage", "出版日は半角数字のYYYYMMDD形式で入力してください");
            return "addBook";
        }

        // 書籍情報を新規登録する
        booksService.registBook(bookInfo);
        model.addAttribute("resultMessage", "登録完了");
        // TODO 登録した書籍の詳細情報を表示するように実装
        //bookdetailsinfo型の新しい変数作る
        int MaxId = booksService.newRegistBook();
        BookDetailsInfo bookDetailsInfo = booksService.getBookInfo(MaxId, userId);
        model.addAttribute("bookDetailsInfo", bookDetailsInfo);
        //貸出ステータス表示
        model.addAttribute("rentalStatus", "貸し出し可");
        model.addAttribute("favoStatus", bookDetailsInfo.getFavoCount());
        //  詳細画面に遷移する
        return "details";
    }
}