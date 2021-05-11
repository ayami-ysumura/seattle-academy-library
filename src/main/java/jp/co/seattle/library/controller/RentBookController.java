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

    /**
     * 書籍を借りる
     * @param locale
     * @param bookId
     * @param model
     * @return
     */
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String rentBook(Locale locale,
            @RequestParam("bookId") int bookId,
            Model model) {
        logger.info("Welcome rentBooks.java! The client locale is {}.", locale);
        //bookIdをセット
        BookDetailsInfo rentBookInfo = new BookDetailsInfo();
        rentBookInfo.setBookId(bookId);
        //bookIdを貸出リストに追加
        int rent = rentBookService.getListInfo(bookId);
        if (rent == 0) {
            rentBookService.rentInfo(rentBookInfo);
            model.addAttribute("noRent", "貸し出し中");
            BookDetailsInfo bookDetailsInfo = booksService.getBookInfo(bookId);
            model.addAttribute("bookDetailsInfo", bookDetailsInfo);

        } else {
            BookDetailsInfo bookDetailsInfo = booksService.getBookInfo(bookId);
            model.addAttribute("bookDetailsInfo", bookDetailsInfo);
            model.addAttribute("noRent", "貸し出し中");

        }


        return "details";
    }

}
