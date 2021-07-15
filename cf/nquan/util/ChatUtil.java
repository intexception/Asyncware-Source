package cf.nquan.util;

public class ChatUtil {
    public static String prefix = "§7[§bAsyncware§7]§f ";

    public static void sendMessage(String msg){
        PlayerUtil.sendMessage(msg);
    }

    public static void sendMessagePrefix(String msg){
        PlayerUtil.sendMessage(prefix + msg);
    }
}
