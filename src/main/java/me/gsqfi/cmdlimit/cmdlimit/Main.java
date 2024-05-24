package me.gsqfi.cmdlimit.cmdlimit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin implements Listener {
    public static Main plugin;
    public static List<String> cmd_list;
    public static String limit_type;
    public static String msg;

    @Override
    public void onEnable() {
        plugin = this;
        reloadConfig();

        getCommand("cmdlimit").setExecutor(this);
        getServer().getPluginManager().registerEvents(this,this);

        getLogger().info("§aPlugin loaded!");
    }

    @Override
    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
        limit_type = this.getConfig().getString("limit_type");
        cmd_list = this.getConfig().getStringList("commands");
        msg = this.getConfig().getString("msg").replace('&','§');
    }

    @EventHandler
    public void c(PlayerCommandPreprocessEvent e){
        if (e.getPlayer().isOp()) {
            return;
        }

        String message = e.getMessage();
        String s = message.split(" ")[0];
        boolean b = cmd_list.contains(s);
        if (limit_type.equalsIgnoreCase("blacklist") && b) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(msg);
            return;
        }
        if (limit_type.equalsIgnoreCase("whitelist") && !b){
            e.setCancelled(true);
            e.getPlayer().sendMessage(msg);
            return;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("§aReloaded!");
        return false;
    }
}