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

@Controller
public class ReturnController {
    final static Logger logger = LoggerFactory.getLogger(RentBookController.class);
    @Autowired
    private BooksService booksService;
    @Autowired
    private RentBookService rentBookService;
    
    /**
     * 書籍を返す
     * @param locale
     * @param bookId 書籍ID
     * @param userId ユーザーID
     * @param model
     * @return details
     */
    @RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public String returnBook(Locale locale,
            @RequestParam("bookId") int bookId,
            @RequestParam("userId") int userId,
            Model model) {
        logger.info("Welcome rentBooks.java! The client locale is {}.", locale);

        rentBookService.returnBook(bookId);
        BookDetailsInfo bookDetailsInfo = booksService.getBookInfo(bookId, userId);
        model.addAttribute("favoStatus", bookDetailsInfo.getFavoCount());
        model.addAttribute("bookDetailsInfo", bookDetailsInfo);
        model.addAttribute("rentalStatus", "貸し出し可");
        return "details";
    }
}