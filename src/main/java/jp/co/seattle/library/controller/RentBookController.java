package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBookService;

/**
 * 借りるボタン実装コントローラー
 */
@Controller
public class RentBookController {
    final static Logger logger = LoggerFactory.getLogger(RentBookController.class);
    @Autowired
    private BooksService booksService;
    @Autowired
    private RentBookService rentBookService;
    @Autowired
    private BookDetailsInfo bookDetailsInfo;
    
    /**
     * 書籍を借りる
     * @param locale
     * @param bookId 書籍ID
     * @param userId ユーザーID
     * @param model
     * @return details
     */
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String rentBook(Locale locale,
            @RequestParam("bookId") int bookId,
            @RequestParam("userId") int userId,
            Model model) {
        logger.info("Welcome rentBooks.java! The client locale is {}.", locale);
        //bookIdをセット
        BookDetailsInfo rentBookInfo = new BookDetailsInfo();
        rentBookInfo.setBookId(bookId);
        //bookIdを貸出リストに追加
        int rent = rentBookService.getRentNum(bookId);
        if (rent == 0) {
            rentBookService.rentBook(rentBookInfo);
        }
        int favo = bookDetailsInfo.getFavoCount();
        if (favo == 1) {
            model.addAttribute("favo", "disabled");
        } else {
            model.addAttribute("noFavo", "disabled");
        }
        BookDetailsInfo bookDetailsInfo = booksService.getBookInfo(bookId, userId);
        model.addAttribute("bookDetailsInfo", bookDetailsInfo);
        model.addAttribute("rentalStatus", "貸し出し中");
        //model.addAttribute("userId", userId);
        return "details";
    }
}