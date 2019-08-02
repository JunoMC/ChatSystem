package xyz.juno.chatsystem.textapi;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class ActionBar {

    @Deprecated
    public static boolean DEBUG;

    private JSONObject json;

    public ActionBar(String text) {
        Preconditions.checkNotNull(text);
        this.json = Title.convert(text);
    }

    public ActionBar(JSONObject json) {
        Preconditions.checkNotNull(json);
        Preconditions.checkArgument(!json.isEmpty());
        this.json = json;
    }

    @Deprecated
    public static void send(Player player, String message) {
        new ActionBar(message).send(player);
    }

    @Deprecated
    public static void sendToAll(String message) {
        new ActionBar(message).sendToAll();
    }

    public void send(Player player) {
        Preconditions.checkNotNull(player);
        try {
            Class<?> clsIChatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent");
            Class<?> clsChatMessageType = ServerPackage.MINECRAFT.getClass("ChatMessageType");
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Object chatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, json.toString());
            Object chatMessageType = clsChatMessageType.getMethod("valueOf", String.class).invoke(null, "GAME_INFO");
            Object packetPlayOutChat = ServerPackage.MINECRAFT.getClass("PacketPlayOutChat").getConstructor(clsIChatBaseComponent, clsChatMessageType).newInstance(chatBaseComponent, chatMessageType);
            playerConnection.getClass().getMethod("sendPacket", ServerPackage.MINECRAFT.getClass("Packet")).invoke(playerConnection, packetPlayOutChat);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            send(player);
        }
    }

    public void setText(String text) {
        Preconditions.checkNotNull(text);
        this.json = Title.convert(text);
    }

    public void setJsonText(JSONObject json) {
        Preconditions.checkNotNull(json);
        Preconditions.checkArgument(!json.isEmpty());
        this.json = json;
    }

}