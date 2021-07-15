package com.nquantum.module.combat;

import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventReceivePacket;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class AntiBot extends Module {
    public AntiBot() {
        super("AntiBot", Keyboard.KEY_M, Category.COMBAT);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Advanced");
        options.add("Watchdog");
        Asyncware.instance.settingsManager.rSetting(new Setting("AntiBot Mode", this, "Advanced", options));
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        String mode = Asyncware.instance.settingsManager.getSettingByName("AntiBot Mode").getValString();

        if(mode.equalsIgnoreCase("Advanced") && event.getPacket() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer)event.getPacket();
            double posX = packet.getX() / 32D;
            double posY = packet.getY() / 32D;
            double posZ = packet.getZ() / 32D;

            double diffX = mc.thePlayer.posX - posX;
            double diffY = mc.thePlayer.posY - posY;
            double diffZ = mc.thePlayer.posZ - posZ;

            double dist = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

            if(dist <= 17D && posX != mc.thePlayer.posX && posY != mc.thePlayer.posY && posZ != mc.thePlayer.posZ)
                event.setCancelled(true);
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = Asyncware.instance.settingsManager.getSettingByName("AntiBot Mode").getValString();
        this.setDisplayName("AntiBot \u00A77" + mode);

        if(mode.equalsIgnoreCase("Watchdog"))
            for (Object entity : mc.theWorld.loadedEntityList)
                if (((Entity) entity).isInvisible() && entity != mc.thePlayer)
                    mc.theWorld.removeEntity((Entity) entity);
    }
}
