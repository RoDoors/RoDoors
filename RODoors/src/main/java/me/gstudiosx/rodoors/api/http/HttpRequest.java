package me.gstudiosx.rodoors.api.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public record HttpRequest(String url) {
    public static final String USER_AGENT = "RODoors/1.0";

    private String getContent(HttpURLConnection con) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder ret = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null)
            ret.append(line).append("\n");
        in.close();
        return ret.toString();
    }

    public String get() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            return getContent(con);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
