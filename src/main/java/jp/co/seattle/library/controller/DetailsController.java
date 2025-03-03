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
 * 詳細表示コントローラー
 */
@Controller
public class DetailsController {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private BooksService booksService;
    @Autowired
    private RentBookService rentBookService;
    
    /**
     * 詳細画面に遷移する
     * @param locale
     * @param bookId 書籍ID
     * @param userId ユーザーID
     * @param model
     * @return details
     */
    //@Transactional
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public String detailsBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            @RequestParam("userId") int userId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome detailsControler.java! The client locale is {}.", locale);
        
        //詳細情報表示のためのコード
        BookDetailsInfo bookDetailsInfo = booksService.getBookInfo(bookId, userId);
        model.addAttribute("bookDetailsInfo", bookDetailsInfo);
        rentBookService.getRentNum(bookId);
        //取得した書籍IDの数によって貸出ステータスを変える
        int rent = rentBookService.getRentNum(bookId);
        if (rent == 0) {
            model.addAttribute("rentalStatus", "貸し出し可");
        } else {
            model.addAttribute("rentalStatus", "貸し出し中");
        }
        model.addAttribute("favoStatus", bookDetailsInfo.getFavoCount());
        return "details";
    }
}