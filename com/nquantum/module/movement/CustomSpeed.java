package com.nquantum.module.movement;

import cf.nquan.util.MovementUtil;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;

import java.util.Random;

public class CustomSpeed extends Module {

    private static double currX;
    private static double currZ;
    private static double currY;

    public CustomSpeed() {
        super("Custom Speed", 0, Category.MOVEMENT);
    }
    @Override
    public void onEnable() {
        super.onEnable();
        mc.timer.timerSpeed = 1.0f;
        mc.timer.ticksPerSecond = 20;
    }


    @EventTarget
    public void onUpdate(EventUpdate e){

        mc.gameSettings.keyBindJump.pressed = false;

        currX = mc.thePlayer.posX;
        currZ = mc.thePlayer.posZ;
        currY = mc.thePlayer.posY;

        if(!mc.thePlayer.isInWater() || !mc.thePlayer.isInLava()){
            if(MovementUtil.isOnGround(0.00001) && MovementUtil.isMoving()){
                mc.thePlayer.jump();
                mc.thePlayer.motionY = 0.32f;
            }

            if(MovementUtil.isOnGround(1)){
                mc.timer.ticksPerSecond = 20;
            } else if(mc.thePlayer.fallDistance < 1.5){
                mc.timer.ticksPerSecond = 22.0f + new Random().nextFloat();
            }

            mc.thePlayer.setSprinting(true);
            MovementUtil.setMotion((float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + 0.023f);
        }
    }
    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1.0f;
        mc.timer.ticksPerSecond = 20;
    }
}
