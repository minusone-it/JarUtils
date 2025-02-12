package it.jar1;

import org.bukkit.plugin.java.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.command.*;
import it.jar1.commands.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import it.jar1.listeners.*;
import it.jar1.event.*;
import java.net.*;
import java.io.*;
import org.bukkit.configuration.file.*;

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
    
    public void onEnable() {
        this.getLogger().info("Starting JarUtils...");
        try {
            JarUtils.instance = this;
            this.prefix = this.getConfig().getString("prefix");
            JarUtils.lang = this.getConfig().getString("lang");
            JarUtils.announceTitleColor = this.getConfig().getString("announce-title-color");
            JarUtils.playAnnounceSound = this.getConfig().getBoolean("play-announce-sound");
            JarUtils.joinQuitMessages = this.getConfig().getBoolean("no-join-quit-message");
            this.muted_players = new ArrayList<Player>();
            this.muted_players_reasons = new HashMap<Player, String>();
            this.muted_players_duration = new HashMap<Player, String>();
            this.blocked_words = (List<String>)this.getConfig().getStringList("blocked-words");
            final String version = "1.0";
            if (!version.equals("1.0")) {
                this.getLogger().info(this.prefix + "Version " + version + " Available!");
            }
            this.getCommand("jarutils").setExecutor((CommandExecutor)new Help(this));
            this.getCommand("vanish").setExecutor((CommandExecutor)new Vanish(this));
            this.getCommand("announce").setExecutor((CommandExecutor)new Announce(this));
            this.getCommand("gmc").setExecutor((CommandExecutor)new gmc(this));
            this.getCommand("gms").setExecutor((CommandExecutor)new gms(this));
            this.getCommand("gma").setExecutor((CommandExecutor)new gma(this));
            this.getCommand("gmsp").setExecutor((CommandExecutor)new gmsp(this));
            this.getCommand("fly").setExecutor((CommandExecutor)new Fly(this));
            this.getCommand("report").setExecutor((CommandExecutor)new Report(this));
            this.getCommand("tempban").setExecutor((CommandExecutor)new TempBan(this));
            this.getCommand("unban").setExecutor((CommandExecutor)new UnBan(this));
            this.getCommand("mute").setExecutor((CommandExecutor)new Mute());
            this.getCommand("unmute").setExecutor((CommandExecutor)new UnMute(this));
            this.getServer().getPluginManager().registerEvents((Listener)new JoinListener(this), (Plugin)this);
            this.getServer().getPluginManager().registerEvents((Listener)new QuitListener(this), (Plugin)this);
            this.getServer().getPluginManager().registerEvents((Listener)new ChatMessageListener(this), (Plugin)this);
            this.getConfig().options().copyDefaults(true);
            this.loadConfig((Plugin)this);
            new TickListener().runTaskTimer((Plugin)this, 0L, 1L);
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
        final FileConfiguration config = (FileConfiguration)YamlConfiguration.loadConfiguration(cfile);
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
