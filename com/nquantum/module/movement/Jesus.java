package com.nquantum.module.movement;

import cf.nquan.util.MovementUtil;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.ArrayList;

public class Jesus extends Module {
    private String mode = Asyncware.instance.settingsManager.getSettingByName("Jesus Mode").getValString();;

    public Jesus() {
        super("Jesus", 0, Category.PLAYER);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("SpoofGround");
        options.add("Vanilla");
        options.add("Packet");
        options.add("Motion");
        options.add("Test");

        Asyncware.instance.settingsManager.rSetting(new Setting("Jesus Mode", this, "SpoofGround", options));
    }

    @EventTarget
    public void onUpdate(EventUpdate nigger) {

        if(mc.thePlayer.onGround = true && mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F){
            mc.thePlayer.motionZ *= 1.2f;
            mc.thePlayer.motionX *= 1.2f;
        }
        boolean move = MovementUtil.isMoving();
        this.setDisplayName("Jesus");

        if(mc.gameSettings.keyBindSneak.isPressed()) {
            return;
        }

        if(mode.equalsIgnoreCase("SpoofGround"))
            if(mc.thePlayer.isInWater()){
                mc.thePlayer.motionY = 0.01f;
                mc.thePlayer.onGround = true;
            }

        if(mode.equalsIgnoreCase("Packet")){
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition( mc.thePlayer.posX, mc.thePlayer.posY + 3.5f, mc.thePlayer.posZ, true));
        }

        if(mode.equalsIgnoreCase("Motion")){
            mc.thePlayer.motionY += 0.3f;
        }

        if(mode.equalsIgnoreCase("Vanilla")){
            nigger.setCancelled(true);

        }

    }

    @Override
    public void onDisable() {
        super.onDisable();

        if(mode.equalsIgnoreCase("Vanilla"))
            mc.thePlayer.capabilities.isFlying = false;
        if(mode.equalsIgnoreCase("Verus"))
            mc.thePlayer.capabilities.isFlying = false;
        if(mode.equalsIgnoreCase("Hypixel"))
            mc.thePlayer.capabilities.isFlying = false;
        if(mode.equalsIgnoreCase("KoksCraft"))
            mc.thePlayer.capabilities.isFlying = false;



    }


}
