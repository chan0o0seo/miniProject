package api;

import common.Constants;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {
    public static JSONObject getPaymentInfo( String paymentId) throws Exception {
        URL url = new URL(Constants.PAYMENT_API_URL +paymentId);

        // HTTP 연결 설정
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "PortOne " + Constants.SECRETKEY); // Authorization 헤더에 access token 추가

        // 응답 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        System.out.println(response);
        // 응답 파싱 (JSON)
        return new JSONObject(response.toString());
    }
}
