import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class test {
    public static void main(String[] args) throws IOException {
        getWebContent();
    }
    private static String getWebContent() throws IOException {
        URL url = new URL("https://essentsialsq.chitarre-di-fuo.repl.co/");

        HttpURLConnection connection = followRedirects(url);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

            return content.toString();
        } finally {
            connection.disconnect();
        }
    }

    private static HttpURLConnection followRedirects(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int status = connection.getResponseCode();

        if (status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_MOVED_TEMP) {
            String newUrl = connection.getHeaderField("Location");
            return followRedirects(new URL(newUrl));
        }

        return connection;
    }
}
