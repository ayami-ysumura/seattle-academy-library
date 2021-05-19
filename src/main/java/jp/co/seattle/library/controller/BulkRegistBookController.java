package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * @author user
 *home画面から一括登録画面に遷移
 */
@Controller //APIの入り口
public class BulkRegistBookController {

    final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

    //@Autowiredがいるとnewを使わずしてインスタンス化できる
    @Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;

    //①home.jspの一括登録ボタンからここにつながる
    @RequestMapping(value = "/registBook", method = RequestMethod.GET) //value＝actionで指定したパラメータ

    public String bulkBook(Locale locale,
            Model model) {
        logger.info("Welcome registBook.java! The client locale is {}.", locale);

        return "registBook";
    }

    /**
     * 複数の書籍情報が記載されたcsvファイルを一括で登録
     * @param locale
     * @param uploadFile
     * @param model
     * @return 一括登録画面に遷移
     */
    @RequestMapping(value = "/bulkRegistBook", method = RequestMethod.POST)
    public String bulk(Locale locale,
            @RequestParam("csvFile") MultipartFile uploadFile, //MultipartFileは型
            Model model) {
        logger.info("Welcome bulkRegistBook.java! The client locale is {}.", locale);

        List<String[]> list = new ArrayList<String[]>();
        //csvファイルの要素を格納するための配列を生成
        String[] splitLine = new String[6];
        //エラー用のリストを生成
        List<String> erorrList = new ArrayList<String>();

            try {
                InputStream input = uploadFile.getInputStream();
                Reader stream = new InputStreamReader(input);
                BufferedReader buffer = new BufferedReader(stream);//文字ストリームからテキストを効率よく読み込む

                //読み込んだ1行目を格納するための変数を定義
                String line;
                //1行が空になるまで読み込む
                while ((line = buffer.readLine()) != null) {//readlineメソッドは1行まとめて読み込む時に使う
                    splitLine = line.split(",", -1);
                    list.add(splitLine);

                //ここからバリデーションチェック
                    //titleまたはauthorまたはpublisherどれかが空だった時
                    if (splitLine[0].isEmpty() || splitLine[1].isEmpty() || splitLine[2].isEmpty()) {
                        erorrList.add(list.size() + "冊目の書籍情報に不備があります");
                    }
                    //ISBNが10または13桁の数字
                    boolean isValidIsbn = splitLine[4].matches("[0-9]{10}||[0-9]{13}");
                    if (!isValidIsbn) {
                        erorrList.add(list.size() + "冊目のISBNは10または13桁の数字を入れてください");
                    }
                    //日付チェック8桁＆日付の数字が日付として成り立たないものをはじく
                    try {
                        DateFormat df = new SimpleDateFormat("yyyymmdd");
                        df.setLenient(false);
                        df.format(df.parse(splitLine[3]));
                    } catch (ParseException e) {
                        erorrList.add(list.size() + "冊目の日付に不備があります");
                    }
                }
                input.close();
                stream.close();
                buffer.close();
            } catch (FileNotFoundException e) {
                model.addAttribute("erorrMsg", "ファイルが見つかりませんでした");
            } catch (IOException e) {
                model.addAttribute("erorrMsg", "ファイルの読み込みに失敗しました");
            }


        //erorrListが空かどうか判定。
        //空じゃなかった場合はエラー文言を出す。
        if (erorrList.size() != 0) {
            //erorrListの中身を取り出す
            model.addAttribute("erorrMsg", erorrList);
            return "registBook";
        }

        //空だった場合は、DBに追加を行う。※リストの中身を取り出す作業が必要
        for (int i = 0; i < list.size(); i++) {
            BookDetailsInfo bookInfo = new BookDetailsInfo();
            bookInfo.setTitle(list.get(i)[0]);
            bookInfo.setAuthor(list.get(i)[1]);
            bookInfo.setPublisher(list.get(i)[2]);
            bookInfo.setPublishDate(list.get(i)[3]);
            bookInfo.setIsbn(list.get(i)[4]);
            bookInfo.setDescription(list.get(i)[5]);
            //書籍情報を登録
            booksService.registBook(bookInfo);
            }

        //登録完了メッセージ
        model.addAttribute("done", "登録完了");
        return "registBook";
    }
}
