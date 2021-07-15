package com.nquantum.module.movement;

import cf.nquan.util.BypassUtil;
import cf.nquan.util.MovementUtil;
import cf.nquan.util.PacketUtil;
import cf.nquan.util.Strings;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventMove;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.module.render.TargetStrafe;
import nig.hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Speed extends Module {
    TargetStrafe s;
    public Speed() {
        super("Speed", Keyboard.KEY_X, Category.MOVEMENT);
    }
    public static EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

    public cf.nquan.util.Timer timer = new cf.nquan.util.Timer();
    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Vanilla");
        options.add("VanillaHop");
        options.add("Kokscraft");
        options.add("HypixelHop");
        options.add("Hypixel-Port");
        options.add("VerusHop");
        options.add("Verus");
        options.add("Verus-Packet");
        options.add("Test");



        Asyncware.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "Vanilla", options));
        Asyncware.instance.settingsManager.rSetting(new Setting("Speed", this, 0.2D, 0.01D, 1.0D, false));
    }

    @EventTarget
    public void onMove(EventMove event){

        if(Asyncware.instance.moduleManager.getModuleByName("TargetStrafe").isToggled()){
            s.strafe(event, 2);
        }
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        s = new TargetStrafe();
        String mode = Asyncware.instance.settingsManager.getSettingByName("Speed Mode").getValString();
        double speed = Asyncware.instance.settingsManager.getSettingByName("Speed").getValDouble();
        boolean move = MovementUtil.isMoving();
        this.setDisplayName("Speed \u00A77" + Strings.capitalizeOnlyFirstLetter(mode));

        if(mode.equalsIgnoreCase("Verus")) {

            if(mc.thePlayer.onGround) {
                if (timer.hasTimeElapsed(1000L, true)) {
                    mc.thePlayer.setSpeed(3F);
                }else if (timer.hasTimeElapsed(6000L, true)) {
                    mc.thePlayer.setSpeed(1F);
                }

            }
        }
        if(mode.equalsIgnoreCase("Vanilla")){
            if(mc.thePlayer.onGround && MovementUtil.isMovingOnGround()){
                mc.thePlayer.motionY = 0.40;
            }

            MovementUtil.setMotion(0.5);

        }


        if (mode.equalsIgnoreCase("VanillaHop")) {

            mc.timer.timerSpeed
                     = 6.5f;
        }
        if(mode.equalsIgnoreCase("VerusHop")){
           if(MovementUtil.isMovingOnGround()){
               mc.thePlayer.jump();
           }




           mc.gameSettings.fovSetting = 100;
           MovementUtil.setMotion(0.33f);
        }
        if(mode.equalsIgnoreCase("Kokscraft")) {

            if (MovementUtil.isOnGround(0.00000001) && MovementUtil.isMoving()) {
                mc.thePlayer.jump();
                event.setCancelled(true);
            }


            if(MovementUtil.isMoving() && mc.thePlayer.onGround) {
                MovementUtil.setMotion((float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + ((float)0.093f));

            }


            //mc.thePlayer.posX += .00000000003f;


        }
        if(mode.equalsIgnoreCase("Hypixel-Port")){
             if(MovementUtil.isMovingOnGround()){
                mc.timer.timerSpeed = 1.0f;
                if(this.timer.hasTimeElapsed(200L, true)){
                    mc.thePlayer.motionY = 0.070f;
                    mc.timer.timerSpeed = 1.4f;
                    PacketUtil.sendPacketPlayer(new C03PacketPlayer());
                    MovementUtil.setSpeed(new EventMove(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), 1.1f);

                }
            }


            /*
            if (isOnLiquid())
                return;
            if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                if (mc.thePlayer.ticksExisted % 2 != 0)
                    mc.thePlayer.motionY += .004;
                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? .45F : .2F);
                mc.timer.timerSpeed = 1.105F;
            }

             */
        }
        if(mode.equalsIgnoreCase("HypixelHop")){
            mc.gameSettings.keyBindJump.pressed = false;

            if (!mc.thePlayer.isInWater()) {

                mc.thePlayer.noClip = true;

                if (MovementUtil.isOnGround(0.00000001) && MovementUtil.isMoving()) {

                    //MovementUtils.setMotion((float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + ((float)hypixelSpeed.getValue() * 15));
                    mc.thePlayer.motionY = 0.32;

                    event.setCancelled(true);

                }

                if (MovementUtil.isOnGround(1)) {
                    mc.timer.ticksPerSecond = 20;
                }else if (mc.thePlayer.fallDistance < 1.5) {
                    //mc.timer.ticksPerSecond = 22.5f + new Random().nextFloat();
                }

                
              //  mc.thePlayer.motionY = 0.2E-5f;
                mc.thePlayer.setSprinting(true);
                //MovementUtils.strafe((float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + 0.01f);
                MovementUtil.setMotion((float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + ((float)0.0126f));

            }
        }


    }


    /*
    @EventTarget
    public void onPre(EventPreMotionUpdate event) {
        String mode = Asyncware.instance.settingsManager.getSettingByName("Speed Mode").getValString();
        if(mode.equalsIgnoreCase("Hypixel-Port")) {
            if (isOnLiquid())
                return;
            if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                if (mc.thePlayer.ticksExisted % 2 != 0)
                    event.y += .4;
                mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? .45F : .2F);
                mc.timer.timerSpeed = 1.095F;
            }

        }
    }


     */

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
