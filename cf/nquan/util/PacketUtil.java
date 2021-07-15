package cf.nquan.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendPacket(Packet packet){
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendPacketPlayer(Packet packet){
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

    public static void sendPacketPlayerNoEvent(Packet packet){
        mc.thePlayer.sendQueue.getNetworkManager().sendPacket(packet);
    }

    public static void sendPacketNoEvent(Packet packet){
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

}
