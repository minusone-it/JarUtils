package it.jar1;

import it.jar1.commands.*;
import it.jar1.listeners.*;
import me.clip.placeholderapi.PlaceholderAPI;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import me.neznamy.tab.api.event.plugin.PlaceholderRegisterEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.*;
import java.util.*;

public final class JarUtils extends JavaPlugin
{
    public String prefix;
    private static JarUtils instance;
    public static String lang;
    public static boolean playAnnounceSound;
    public static boolean joinQuitMessages;
    public static String announceTitleColor;
    public List<Player> muted_players;
    public HashMap<Player, String> muted_players_reasons;
    public HashMap<Player, String> muted_players_duration;
    public List<String> blocked_words;
    public boolean onMaintenance;

    public void onEnable() {
        this.getLogger().info("Starting JarUtils...");
        try {
            JarUtils.instance = this;
            this.prefix = this.getConfig().getString("prefix");
            JarUtils.lang = this.getConfig().getString("lang");
            JarUtils.announceTitleColor = this.getConfig().getString("announce-title-color");
            JarUtils.playAnnounceSound = this.getConfig().getBoolean("play-announce-sound");
            JarUtils.joinQuitMessages = this.getConfig().getBoolean("no-join-quit-message");
            this.muted_players = new ArrayList<>();
            this.muted_players_reasons = new HashMap<>();
            this.muted_players_duration = new HashMap<>();
            this.blocked_words = this.getConfig().getStringList("blocked-words");
            this.onMaintenance = false;
            //final String version = "1.0";
            //if (!version.equals("1.0")) {
            //    this.getLogger().info(this.prefix + "Version " + version + " Available!");
            //}
            this.getCommand("jarutils").setExecutor(new Help(this));
            this.getCommand("vanish").setExecutor(new Vanish(this));
            this.getCommand("announce").setExecutor(new Announce(this));
            this.getCommand("gmc").setExecutor(new gmc(this));
            this.getCommand("gms").setExecutor(new gms(this));
            this.getCommand("gma").setExecutor(new gma(this));
            this.getCommand("gmsp").setExecutor(new gmsp(this));
            this.getCommand("fly").setExecutor(new Fly(this));
            this.getCommand("report").setExecutor(new Report(this));
            this.getCommand("tempban").setExecutor(new TempBan(this));
            this.getCommand("unban").setExecutor(new UnBan(this));
            this.getCommand("maintenance").setExecutor(new maintenance(this));
            //this.getCommand("mute").setExecutor(new TempMute(this));
            //this.getCommand("unmute").setExecutor(new UnMute(this));
            this.getServer().getPluginManager().registerEvents(new JoinListener(this), this);
            this.getServer().getPluginManager().registerEvents(new QuitListener(this), this);
            this.getServer().getPluginManager().registerEvents(new ChatMessageListener(this), this);
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            this.getServer().getMessenger().registerIncomingPluginChannel(this, "jarutils:tempban", new TempBan(this));
            TabAPI.getInstance().getEventBus().register(PlaceholderRegisterEvent.class, event -> {
                //TabPlayer tabPlayer = event.();
            });
            this.getConfig().options().copyDefaults(true);
            this.loadConfig(this);

            this.getLogger().info(JarUtils.lang.contains("en") ? "Started JarUtils succesfully!" : "JarUtils startato correttamente!");
        }
        catch (Exception e) {
            this.getLogger().info("Error occurred when tried to start JarUtils: " + e.getMessage());
        }
    }

    public void onDisable() {
        this.getLogger().info(JarUtils.lang.contains("en") ? "Stopped JarUtils!" : "Jarutils Stoppato!");
    }

    public static String getWebContent(final String urlString) throws IOException {
        final URL url = new URL(urlString);
        final HttpURLConnection connection = followRedirects(url);
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            final StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        }
        finally {
            connection.disconnect();
        }
    }

    private static HttpURLConnection followRedirects(final URL url) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        final int status = connection.getResponseCode();
        if (status == 301 || status == 302) {
            final String newUrl = connection.getHeaderField("Location");
            return followRedirects(new URL(newUrl));
        }
        return connection;
    }

    public void loadConfig(final Plugin plugin) {
        final File cfile = new File(plugin.getDataFolder().getAbsolutePath() + "/config.yml");
        final FileConfiguration config = YamlConfiguration.loadConfiguration(cfile);
        this.prefix = config.getString("prefix");
        JarUtils.lang = config.getString("lang");
        JarUtils.announceTitleColor = config.getString("announce-title-color");
        JarUtils.playAnnounceSound = config.getBoolean("play-announce-sound");
        JarUtils.joinQuitMessages = config.getBoolean("no-join-quit-message");
    }

    public static JarUtils getInstance() {
        return JarUtils.instance;
    }
}
