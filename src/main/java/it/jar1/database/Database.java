package it.jar1.database;

import java.net.*;
import java.io.*;

public class Database
{
    private static Socket createConnection() throws Exception {
        return new Socket("node-powerpixel.ddns.net", 2000);
    }
    
    public static void sendValue(final String key, final Object value) {
        try (final Socket socket = createConnection();
             final PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("insert:" + key + ":" + value.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getValue(final String key) {
        String result = null;
        try (final Socket socket = createConnection();
             final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("retrieve:" + key);
            result = in.readLine();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
