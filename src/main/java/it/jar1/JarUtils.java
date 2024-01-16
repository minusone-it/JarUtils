package it.jar1;

import it.jar1.commands.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class JarUtils extends JavaPlugin {
    public static String prefix;
    public static String lang;
    public static boolean playAnnounceSound;
    public static String announceTitleColor;
    public static String url = "https://4e60d526-f179-470a-b5b3-a421deb6711d-00-3ngeku8kq47c2.worf.replit.dev/";

    @Override
    public void onEnable() {
        getLogger().info("Starting JarUtils...");
        try {
            prefix = getConfig().getString("prefix");
            lang = getConfig().getString("lang");
            announceTitleColor = getConfig().getString("announce-title-color");
            playAnnounceSound = getConfig().getBoolean("play-announce-sound");
            String version = getWebContent(url);
            if(!version.equals("1.0"))
                getLogger().info(prefix + "Version " + version + " Available!");
            getCommand("jarutils").setExecutor(new Help(this));
            getCommand("vanish").setExecutor(new Vanish(this));
            getCommand("announce").setExecutor(new Announce(this));
            getCommand("gmc").setExecutor(new gmc(this));
            getCommand("gms").setExecutor(new gms(this));
            getCommand("gma").setExecutor(new gma(this));
            getCommand("gmsp").setExecutor(new gmsp(this));
            getCommand("fly").setExecutor(new Fly(this));
            getCommand("report").setExecutor(new Report(this));
            getCommand("tempban").setExecutor(new TempBan(this));
            saveDefaultConfig();
            getConfig().options().copyDefaults(true);
            loadConfig(this);
            getLogger().info(lang.contains("en") ? "Started JarUtils succesfully!" : "JarUtils startato correttamente!");
        } catch (Exception e) {
            getLogger().info("Error occurred when tried to start JarUtils: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        getLogger().info(lang.contains("en") ? "Stopped JarUtils!" : "Jarutils Stoppato!");
    }

    public static String getWebContent(String urlString) throws IOException {
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

    public void loadConfig(Plugin plugin) {
        File cfile = new File(plugin.getDataFolder().getAbsolutePath() + "/config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(cfile);
        prefix = config.getString("prefix");
        lang = config.getString("lang");
        announceTitleColor = config.getString("announce-title-color");
        playAnnounceSound = config.getBoolean("play-announce-sound");
    }
}
