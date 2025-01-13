
function getCookie(name) {
    const cookies = document.cookie.split('; ');
    for (const cookie of cookies) {
        const [key, value] = cookie.split('=');
        if (key === name) {
            return decodeURIComponent(value);
        }
    }
    return null; // 해당 이름의 쿠키가 없으면 null 반환
}

const myCookie = getCookie('demkq');
if (myCookie === 'ekwer1') {
    document.querySelector(".login").classList.add("d-none");
    document.querySelector(".signup").classList.add("d-none");
    document.querySelector(".logout").classList.remove("d-none");
    document.querySelector(".mypage").classList.remove("d-none");
    document.querySelector(".mypagedrop").classList.remove("d-none");
    document.querySelector(".mybag").classList.remove("d-none");
    document.querySelector(".write-button").classList.remove("d-none");
} else {
    document.querySelector(".login").classList.remove("d-none");
    document.querySelector(".signup").classList.remove("d-none");
    document.querySelector(".logout").classList.add("d-none");
    document.querySelector(".mypage").classList.add("d-none");
    document.querySelector(".mypagedrop").classList.add("d-none");
    document.querySelector(".mybag").classList.add("d-none");
    document.querySelector(".write-button").classList.add("d-none");
}
