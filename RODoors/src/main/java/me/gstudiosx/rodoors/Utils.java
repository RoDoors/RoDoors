package me.gstudiosx.rodoors;

import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: not sure if I should keep this class and move methods in different places or move this class
public class Utils {
    public static byte[] hexBytes(String str) {
        // for what this is used for this isn't really valid as you need 20 bytes, but I added this bit anyway
        if (str.equalsIgnoreCase("0")) {
            return new byte[]{0};
        }

        byte[] data = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            data[i / 2] = (byte) (Character.digit(str.charAt(i), 16) << 4 + Character.digit(str.charAt(i + 1), 16));
        }
        return data;
    }

    public static String getMessage(Configuration configuration, String key, Collection<String> def) {
        // TODO: also clean up ths method

        if (configuration.isString(key)) {
            return configuration.getString(key, String.join("\n", def));
        }

        List<String> message = configuration.getStringList(key);
        if (message.isEmpty())
            message = new ArrayList<>(def);

        return String.join("\n", message);
    }

    public static String toMiniMessage(char c, String legacy) {
        // legit stolen from some javascript thing LMFAO
        // PS: I couldn't be assed to fix all the times I used legacy in minimessage
        // TODO: probably clean this up a bit also make it more efficient as replaceAll is pretty heavy

        return legacy
                .replaceAll(c + "0", "<black>")
                .replaceAll(c + "1", "<dark_blue>")
                .replaceAll(c + "2", "<dark_green>")
                .replaceAll(c + "3", "<dark_aqua>")
                .replaceAll(c + "4", "<dark_red>")
                .replaceAll(c + "5", "<dark_purple>")
                .replaceAll(c + "6", "<gold>")
                .replaceAll(c + "7", "<gray>")
                .replaceAll(c + "8", "<dark_gray>")
                .replaceAll(c + "9", "<blue>")
                .replaceAll(c + "a", "<green>")
                .replaceAll(c + "b", "<aqua>")
                .replaceAll(c + "c", "<red>")
                .replaceAll(c + "d", "<light_purple>")
                .replaceAll(c + "e", "<yellow>")
                .replaceAll(c + "f", "<white>")
                .replaceAll(c + "n", "<underlined>")
                .replaceAll(c + "m", "<strikethrough>")
                .replaceAll(c + "k", "<obfuscated>")
                .replaceAll(c + "o", "<italic>")
                .replaceAll(c + "l", "<bold>")
                .replaceAll(c + "r", "<reset>");
    }
}
