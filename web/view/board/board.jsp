<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>게시판</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/style.css">
</head>
<body>

<!-- 게시판 헤더 -->
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <img src="https://valorantinfo.com/images/kr/tactibear-spray_valorant_gif_3946.gif"
         height="80px"
         width="80px"
         alt="logo"
         onclick="window.location.href='/'"
         style="cursor: pointer;" />
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="https://github.com/chan0o0seo">만든사람</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            메뉴
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="/board/list">게시판</a></li>
            <li><a class="dropdown-item mypagedrop" href="/user/mypage">마이페이지</a></li>
          </ul>
        </li>
      </ul>
      <form action="/user/login" method="get" class="d-flex">
        <button type="submit" class="btn btn-outline-dark login" style="margin-left: 20px">로그인</button>
      </form>
      <form action="/user/logout" method="get" class="d-flex">
        <button type="submit" class="btn btn-outline-dark logout" style="margin-left: 20px">로그아웃</button>
      </form>
      <form action="/user/signup" method="get" class="d-flex">
        <button type="submit" class="btn btn-outline-dark signup" style="margin-left: 20px">회원가입</button>
      </form>
      <form action="/user/mypage" method="get" class="d-flex">
        <button type="submit" class="btn btn-outline-dark mypage" style="margin-left: 20px">마이페이지</button>
      </form>
      <form action="/shop/mybag" method="get" class="d-flex">
        <button type="submit" class="btn btn-outline-dark mybag" style="margin-left: 20px">장바구니</button>
      </form>
    </div>
  </div>
</nav>
<div class="title-container">
  <h1 class="title">게시판</h1>
  <div class="write-button-container">
    <button class="write-button btn btn-outline-dark" onclick="location.href='/view/board/write.jsp';">글쓰기</button>
  </div>
</div>


<div class="board-container">
<!-- 게시글 목록 -->
    <!-- 게시글 목록 출력 (동적 데이터) -->
    <c:forEach var="post" items="${postList}">

<div class="card">
  <a href="/board/content?idx=${post.idx}" class="card-link">
    <div class="card-body">
      <h5 class="card-title">${post.idx}. ${post.title}</h5>
        <h6 class="card-subtitle mb-2 text-body-secondary">${post.writer}</h6>
    </div>
    </a>

</div>

    </c:forEach>
</div>
<!-- 글쓰기 버튼 -->

<script src="${pageContext.request.contextPath}/view/js/default.js"></script>
</body>
</html>
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
