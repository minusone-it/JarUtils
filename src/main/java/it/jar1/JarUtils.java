package it.jar1;

import it.jar1.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class JarUtils extends JavaPlugin {
    public String prefix = getConfig().getString("prefix");
    public static String url = "https://essentsialsq.chitarre-di-fuo.repl.co/";
    public static Boolean newVersionAvailable = false;
    public static String versionAvailable = "";

    @Override
    public void onEnable() {
        System.out.println("Starting JarUtils...");
        try {
            String version = getWebContent(url);
            if(!version.equals("1.0")) {
                versionAvailable = version;
                System.out.println(prefix + "Version "+version+" Available!");
                newVersionAvailable = true;
            }
            getCommand("jarutils").setExecutor(new Help());
            getCommand("vanish").setExecutor(new Vanish());
            getCommand("gmc").setExecutor(new gmc());
            getCommand("gms").setExecutor(new gms());
            getCommand("gma").setExecutor(new gma());
            getCommand("gmsp").setExecutor(new gmsp());
            System.out.println("Started JarUtils succesfully!");
        } catch (Exception e) {
            System.out.println("Error occurred when tried to start JarUtils: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        System.out.println("Stopping JarUtils...");
        System.out.println("Stopped JarUtils!");
    }

    private static String getWebContent(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = followRedirects(url);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
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
