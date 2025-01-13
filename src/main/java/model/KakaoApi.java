package model;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class KakaoApi {
    public static String getAccessToken(String code) throws IOException {
        String accessToken = null;
        String apiUrl = "https://kauth.kakao.com/oauth/token"; // Kakao Token API URL
        String clientId = "90c0a4fc42fe93071274c7f5669f12e4"; // Kakao REST API Key
        String redirectUri = "http://222.112.156.89:107/user/kakao"; // Kakao Redirect URI

        // URL 파라미터 준비
        StringBuilder sb = new StringBuilder(apiUrl);
        sb.append("?grant_type=authorization_code");
        sb.append("&client_id=" + clientId);
        sb.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8"));
        sb.append("&code=" + code); // Kakao에서 전달된 Authorization Code

        // HTTP POST 요청 보내기
        URL url = new URL(sb.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // 서버 응답 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        // 응답에서 Access Token 추출 (JSON 파싱)
        String responseBody = response.toString();
        accessToken = extractAccessToken(responseBody); // 실제로 JSON에서 access_token 추출하는 메서드 필요

        return accessToken;
    }
    public static String extractAccessToken(String responseBody) {
        // org.json을 사용하여 JSON 파싱
        JSONObject jsonResponse = new JSONObject(responseBody);
        return jsonResponse.getString("access_token");
    }
    public static JSONObject getUserInfo(String accessToken) throws Exception {
        String apiUrl = "https://kapi.kakao.com/v2/user/me"; // Kakao 사용자 정보 API URL
        URL url = new URL(apiUrl);

        // HTTP 연결 설정
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken); // Authorization 헤더에 access token 추가

        // 응답 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        // 응답 파싱 (JSON)
        return new JSONObject(response.toString());
    }
    public static Map<String,String> printUserInfo(JSONObject userInfo) {
        // 예시로 사용자 이름과 이메일을 출력
        Map<String,String> map = new HashMap<>();
        String userName = userInfo.getJSONObject("properties").getString("nickname");
        // 이메일 정보를 받았는지 확인하고 이메일 가져오기
        String email = null;
        if (userInfo.getJSONObject("kakao_account").has("email")) {
            email = userInfo.getJSONObject("kakao_account").getString("email");
        }

        // 이메일이 없을 경우 처리 (예: 이메일 동의를 하지 않은 경우)
        if (email == null || email.isEmpty()) {
            System.out.println("Email not provided or consent not given.");
        } else {
            System.out.println("User Email: " + email);
        }
        System.out.println("User Name: " + userName);
        map.put("userName", userName);
        map.put("email", email);
        return  map;
    }

}
