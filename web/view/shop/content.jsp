<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 내용</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/style.css">
</head>
<body>

<!-- 헤더 -->
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

<!-- 타이틀-->
<div class="title-container">
    <h1 class="title">상세페이지</h1>
<c:if test="${empty bag}">
    <button class="write-button btn btn-outline-dark" onclick="location.href='/shop/delete?idx=${product.idx}';">상품삭제</button>
</c:if>
</div>
<!-- 글 내용 표시 -->
<c:if test="${empty bag}">
<div class="content-container">
    <div class="content-header">
        <div class="content-img">
            <img src="${product.path}" alt="Uploaded Image" />
        </div>

        <div class="product-info">

            <h2> ${product.name}</h2> <!-- 제목 -->
            <h2>${product.original_price}</h2>
            <h2>${product.price}</h2>
            <h2>${product.description}</h2>
            <h2>${product.quantity}</h2>
            <form action="/shop/addbag" method="get" class="d-flex">
                <input type="hidden" name="idx" value="${product.idx}">
                <button type="submit" class="btn btn-outline-dark" style="margin-left: 20px">장바구니 담기</button>
                <button type="button" class="btn btn-outline-dark" onclick="pay()" style="margin-left: 20px">결제하기</button>
            </form>
        </div>
    </div>
</div>
</c:if>
<c:if test="${not empty bag}">
    <div class="content-container">
        <div class="content-header">
            <div class="content-img">
                <img src="${product.path}" alt="Uploaded Image" />
            </div>

            <div class="product-info">

                <h2> ${product.name}</h2> <!-- 제목 -->
                <h2>${product.original_price}</h2>
                <h2>${product.price}</h2>
                <h2>${product.description}</h2>
                <h2>${product.quantity}</h2>
                <form action="/shop/deletebag" method="get" class="d-flex">
                    <input type="hidden" name="idx" value="${product.idx}">
                    <button type="submit" class="btn btn-outline-dark" style="margin-left: 20px">장바구니에서 빼기</button>
                </form>
            </div>
        </div>
    </div>
</c:if>
<!-- 리뷰 목록 -->

<script src="https://cdn.portone.io/v2/browser-sdk.js"></script>
<script>
    const pay = async () => {
            const response = await PortOne.requestPayment({
                // Store ID 설정
                storeId: "store-b11387a5-26aa-4e6b-b7bf-dd705e2ccc62",
                // 채널 키 설정
                channelKey: "channel-key-41ab8efe-9eb9-455a-8469-98f988aaff52",
                paymentId: 'payment-' + generateUUID(),
                orderName: "${product.name}",
                totalAmount: ${product.price},
                currency: "CURRENCY_KRW",
                payMethod: "EASY_PAY",
            });

        if (response.code !== undefined) {
            // 오류 발생
            return alert(response.message);
        }
        console.log(response);

        // /payment/complete 엔드포인트를 구현해야 합니다. 다음 목차에서 설명합니다.
        const notified = await fetch(`http://222.112.156.89:107/board/payment`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            // paymentId와 주문 정보를 서버에 전달합니다
            body: JSON.stringify({
                paymentId: response.paymentId,
                // 주문 정보...
            }),
        });
    }
    function generateUUID() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            const r = Math.random() * 16 | 0;
            const v = c === 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }
</script>
<script src="${pageContext.request.contextPath}/view/js/default.js"></script>
</body>
</html>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
