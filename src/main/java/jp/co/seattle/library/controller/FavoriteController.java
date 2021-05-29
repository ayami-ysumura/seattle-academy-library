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

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.FavoriteService;
import jp.co.seattle.library.service.RentBookService;

@Controller
public class FavoriteController {
    final static Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private BooksService booksService;
    @Autowired
    private RentBookService rentBookService;
    
    /**
     * お気に入り追加
     * @param locale
     * @param bookId 書籍ID
     * @param userId ユーザーID
     * @param model
     * @return
     */
    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    public String favorite(Locale locale,
            @RequestParam("bookId") Integer bookId,
            @RequestParam("userId") int userId,
            Model model) {
        logger.info("Welcome FavoriteControler.java! The client locale is {}.", locale);
        
        favoriteService.favoRegist(bookId, userId);
        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId, userId));
        model.addAttribute("userId", userId);
        model.addAttribute("favoStatus", "1");
        int rent = rentBookService.getRentNum(bookId);
        if (rent == 0) {
            model.addAttribute("rentalStatus", "貸し出し可");
        } else {
            model.addAttribute("rentalStatus", "貸し出し中");
        }
        return "details";
    }

    /**
     * お気に入り解除
     * @param locale
     * @param bookId 書籍ID
     * @param userId ユーザーID
     * @param model
     * @return
     */
    @RequestMapping(value = "/noFavorite", method = RequestMethod.POST)
    public String noFavorite(Locale locale,
            @RequestParam("bookId") Integer bookId,
            @RequestParam("userId") int userId,
            Model model) {
        logger.info("Welcome FavoriteControler.java! The client locale is {}.", locale);

        favoriteService.favoDelete(bookId, userId);
        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId, userId));
        model.addAttribute("userId", userId);
        model.addAttribute("favoStatus", "0");
        int rent = rentBookService.getRentNum(bookId);
        if (rent == 0) {
            model.addAttribute("rentalStatus", "貸し出し可");
        } else {
            model.addAttribute("rentalStatus", "貸し出し中");
        }
        return "details";
    }
}
