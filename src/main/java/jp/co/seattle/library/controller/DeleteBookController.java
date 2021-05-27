package jp.co.seattle.library.controller;

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

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * 削除コントローラー
 */
@Controller //APIの入り口
public class DeleteBookController {
    final static Logger logger = LoggerFactory.getLogger(DeleteBookController.class);
    @Autowired
    private BooksService booksService;
    //@Autowired
    //private RentBookService rentBookService;
    @Autowired
    private BookDetailsInfo bookDetailsInfo;
    
    /**
     * 対象書籍を削除する
     *
     * @param locale ロケール情報
     * @param bookId 書籍ID
     * @param userId ユーザーID
     * @param model モデル情報
     * @return 遷移先画面名
     */
    @Transactional
    @RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
    public String deleteBook(
            Locale locale,
            @RequestParam("bookId") Integer bookId,
            @RequestParam("userId") int userId,
            Model model) {
        logger.info("Welcome delete! The client locale is {}.", locale);

        booksService.deletingBook(bookId);
        //サービスクラスを使用するコード
        //int rent = rentBookService.getRentNum(bookId);
        //if (rent == 1) {
        int favo = bookDetailsInfo.getFavoCount();
        if (favo == 1) {
            model.addAttribute("favoStatus", "noFavo");
        } else {
            model.addAttribute("favoStatus", "favo");
        }
            //model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId, userId));
            //model.addAttribute("rentalStatus", "貸出中の本は削除できません");
            //return "details";
            //}
        model.addAttribute("bookList", booksService.getBookList());
        return "home";
    }
}