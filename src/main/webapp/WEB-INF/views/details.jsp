<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>書籍の詳細｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/lightbox.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/lightbox.js" /></script>
<script src="resources/js/getUserId.js" /></script>
<script src="resources/js/removeUserId.js"></script>
<script src="resources/js/rentStatus.js"></script>
<script src="resources/js/favoriteStatus.js"></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/" id="remove">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>書籍の詳細</h1>
        <div class="content_body detail_book_content">
            <div class="content_left">
                <span>書籍の画像</span>
                <div class="book_thumnail">
                    <a href="${bookDetailsInfo.thumbnailUrl}" data-lightbox="image-1"> <c:if test="${empty bookDetailsInfo.thumbnailUrl}">
                            <img class="book_noimg" src="resources/img/noImg.png">
                        </c:if> <c:if test="${!empty bookDetailsInfo.thumbnailUrl}">
                            <img class="book_img" src="${bookDetailsInfo.thumbnailUrl}">
                        </c:if> <input type="hidden" name="bookId" value="${bookDetailsInfo.bookId}">
                    </a>
                </div>
                <div>${rentalStatus}</div>
            </div>
            <div class="content_right">
                <div>
                    <span>書籍名</span>
                    <p>${bookDetailsInfo.title}</p>
                </div>
                <div>
                    <span>著者名</span>
                    <p>${bookDetailsInfo.author}</p>
                </div>
                <div>
                    <span>出版社</span>
                    <p>${bookDetailsInfo.publisher}</p>
                </div>
                <div>
                    <span>出版日</span>
                    <p>${bookDetailsInfo.publishDate}</p>
                </div>
                <div>
                    <span>ISBN</span>
                    <p>${bookDetailsInfo.isbn}</p>
                </div>
                <div>
                    <span>書籍説明</span>
                    <p>${bookDetailsInfo.description}</p>
                </div>
            </div>
        </div>
        <div class="edtDelBookBtn_box">
            <input type="hidden" class="rentalStatus" value="${rentalStatus}">
            <form method="post" action="rentBook">
                <button type="submit" value="${bookDetailsInfo.bookId}" name="bookId" class="btn_rentBook">借りる</button>
                <input type="hidden" name="userId" class="get_userId" value="${userId}">
            </form>
            <form method="post" action="returnBook">
                <button type="submit" value="${bookDetailsInfo.bookId}" name="bookId" class="btn_returnBook">返す</button>
                <input type="hidden" name="userId" class="get_userId" value="${userId}">
            </form>
            <form method="post" action="editBook">
                <button type="submit" value="${bookDetailsInfo.bookId}" name="bookId" class="btn_editBook">編集</button>
                <input type="hidden" name="userId" class="get_userId" value="${userId}">
            </form>
            <form method="post" action="deleteBook">
                <button type="submit" value="${bookDetailsInfo.bookId}" name="bookId" class="btn_deleteBook">削除</button>
                <input type="hidden" name="userId" class="get_userId" value="${userId}">
            </form>
        </div>
        <div class="edtDelBookBtn_box">
            <input type="hidden" class="favoStatus" value="${favoStatus}">
            <p>お気に入り</p>
            <form method="post" action="favorite">
                <button class="favo" type="submit" value="${bookDetailsInfo.bookId}" name="bookId">登録</button>
                <input type="hidden" name="userId" value="${userId}" class="get_userId">
            </form>
            <form method="post" action="noFavorite">
                <button class="noFavo" type="submit" value="${bookDetailsInfo.bookId}" name="bookId">解除</button>
                <input type="hidden" name="userId" value="${userId}" class="get_userId">
            </form>
        </div>
    </main>
</body>
</html>
