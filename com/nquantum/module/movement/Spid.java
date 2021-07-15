package com.nquantum.module.movement;

import cf.nquan.util.MovementUtil;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventPreMotionUpdate;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Spid extends Module {

    private String mode = Asyncware.instance.settingsManager.getSettingByName("Speed Mode").getValString();;

    public Spid() {
        super("Speed", Keyboard.KEY_U, Category.MOVEMENT);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Y-Port");
        options.add("Bhop");
        Asyncware.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "Y-Port", options));
       // Asyncware.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "Bhop", options));


    }

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {

        this.setDisplayName("Speed");

        if(mode.equalsIgnoreCase("Y-Port")) {
            if (isOnLiquid())
                return;
            if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                if (mc.thePlayer.ticksExisted % 2 != 0)
                    event.y += .4;
                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? .45F : .2F);
                mc.timer.timerSpeed = 1.095F;
            }
        }

        if(mode.equalsIgnoreCase("Bhop")){
            if(mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                mc.thePlayer.jump();
                event.y += 0.5;
                MovementUtil.setSpeed(1.4D);
                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 3 == 0 ? .23F : .2F);

            }
        }







    }

    @EventTarget
    public void onUpdate(EventUpdate e){


    }


    private boolean isOnLiquid() {
        boolean onLiquid = false;
        final int y = (int)(mc.thePlayer.boundingBox.minY - .01);
        for(int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if(block != null && !(block instanceof BlockAir)) {
                    if(!(block instanceof BlockLiquid))
                        return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
}
