package jp.co.seattle.library.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.FavoriteService;

@Controller
public class FavoriteListController {
    final static Logger logger = LoggerFactory.getLogger(FavoriteListController.class);

    @Autowired
    private FavoriteService favoriteServise;

    /**
     * ★ボタンからお気に入り一覧に遷移
     * @param model
     * @param userId ユーザーID
     * @return favorite
     */
    @RequestMapping(value = "/favoriteList", method = RequestMethod.POST)
    public String favoList(Locale locale,
            @RequestParam("userId") int userId,
            Model model) {
        logger.info("Welcome FavoriteList.java! The client locale is {}.", locale);

        model.addAttribute("favoList", favoriteServise.getFavoList(userId));
        List<BookInfo> favoList = favoriteServise.getFavoList(userId);
        if (favoList.isEmpty()) {
            model.addAttribute("noFavo", "お気に入り書籍はありません");
        }
        return "favorite";
    }
}
