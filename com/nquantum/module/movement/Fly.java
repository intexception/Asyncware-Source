package com.nquantum.module.movement;

import cf.nquan.util.*;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventMove;
import com.nquantum.event.impl.EventPreMotionUpdate;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Fly extends Module {
    public boolean bow;
    public Fly() {
        super("Fly", Keyboard.KEY_F, Category.MOVEMENT);
    }

    private double startY;
    public boolean isPre;
    public ArrayList<Packet> parray;
    ArrayList<Packet> parray2;
    private boolean isPost;

    private double lastDist;
    private Timer hypixelTimer = new Timer();
    public cf.nquan.util.Timer timer = new cf.nquan.util.Timer();

    public static EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Vanilla");
        options.add("Verus-Packet");
        options.add("Verus");
        options.add("VerusNew");
        options.add("Verus-Nigger");
        options.add("Collision");
        options.add("Paper");
        options.add("Float");
        options.add("VictoryCraft");
        options.add("Gravity");
        options.add("Kokscraft-Old");
        options.add("Kokscraft");
        options.add("Hypixel");
        options.add("Hypixel2");
        options.add("Hypixel3");
        options.add("Motion");
        options.add("Glide");
        options.add("Dev");

        Asyncware.instance.settingsManager.rSetting(new Setting("Fly Mode", this, "Vanilla", options));
        Asyncware.instance.settingsManager.rSetting(new Setting("Fly Speed", this, 3, 0, 10, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Boost", this, false));

    }

    public void onEnable() {
        String mode = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        if(mode.equalsIgnoreCase("Verus") || mode.equalsIgnoreCase("VerusNew")){
            MovementUtil.damageVerus();
        }
        if(mode.equalsIgnoreCase("VerusNew")){
            PacketUtil.sendPacketPlayer(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.002, mc.thePlayer.posZ, false));
            PacketUtil.sendPacketPlayer(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            PacketUtil.sendPacketPlayer(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));

        }

        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1;
        mc.thePlayer.motionY = 0;
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        mc.thePlayer.capabilities.isFlying = false;


    }

    @EventTarget
    public void onUpdate(EventUpdate nigger) {
        boolean faggot = MovementUtil.isMovingOnGround();

        boolean boost = Asyncware.instance.settingsManager.getSettingByName("Boost").getValBoolean();
        this.lastDist = mc.thePlayer.lastTickPosX - mc.thePlayer.posX;
        String mode = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        boolean move = MovementUtil.isMoving();
        double speed = Asyncware.instance.settingsManager.getSettingByName("Fly Speed").getValDouble();
        this.setMode(mode);
        this.setDisplayName("Fly \u00A77" + Strings.capitalizeOnlyFirstLetter(mode));
        if (mode.equalsIgnoreCase("Vanilla")) {
            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;


            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY = speed;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY = -speed;
            }

            MovementUtil.setSpeed(speed);
            mc.thePlayer.capabilities.setFlySpeed((float) speed - ((float) speed / 2));
        }
        if (mode.equalsIgnoreCase("VictoryCraft")) {


            MovementUtil.setSpeed(1.0D);


            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY -= 0.01f, mc.thePlayer.posZ, false));
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY -= 0.01f, mc.thePlayer.posZ, false));

            if (this.timer.hasTimeElapsed(200L, true)) {
                mc.timer.timerSpeed = 1.0f;
                MovementUtil.setSpeed(2.01D);

            }


        }
        if (mode.equalsIgnoreCase("Glide")) {


            startY = mc.thePlayer.posY;
            final boolean shouldBlock = mc.thePlayer.posY + 0.1 >= startY && mc.gameSettings.keyBindJump.isKeyDown();
            if (mc.thePlayer.isSneaking()) {
                mc.thePlayer.motionY = -0.4f;
            } else if (mc.gameSettings.keyBindJump.isKeyDown() && !shouldBlock) {

                mc.thePlayer.motionY = 0.4f;
            } else {
                mc.thePlayer.motionY = -0.04;
            }

        }
        if (mode.equalsIgnoreCase("Kokscraft")) {
            // spoof the ground
            mc.thePlayer.onGround = true;

            mc.thePlayer.interactAt(mc.thePlayer, new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
            mc.thePlayer.motionY = 0;

            PacketUtil.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw + 0.3f, mc.thePlayer.rotationPitch + 0.3f, true));


            PacketUtil.sendPacketPlayer(new C03PacketPlayer());

            // set the fake camera yaw
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
            MovementUtil.setSpeed(speed);
            if (mc.thePlayer.ticksExisted % 10 == 0 && mc.thePlayer.onGround) {
                // push into ground
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                //PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 3.725, mc.thePlayer.posZ, false));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            }
            if (this.timer.hasTimeElapsed(1200L, true)) {
                MovementUtil.setSpeed(2.0f);
                if (mc.thePlayer.onGround) {
                   // MovementUtil.damagePlayer();
                }
                mc.thePlayer.motionY = 0.005268723;


            }

        }
        if (mode.equalsIgnoreCase("Kokscraft-Old")) {
            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            mc.thePlayer.motionX *= 1.7;
            mc.thePlayer.motionZ *= 1.7;
        }
        if (mode.equalsIgnoreCase("Float")) {
            MovementUtil.setSpeed(0.5f);
            mc.thePlayer.motionY = 0;
            mc.thePlayer.onGround = true;
            if (this.timer.hasTimeElapsed(300L, true)) {
                mc.thePlayer.onGround = false;
                MovementUtil.setSpeed(5f);
                mc.thePlayer.motionY = 0.10;
            }
        }
        if (mode.equalsIgnoreCase("Paper")) {

            double y = 0.1;

            for (int i = 0; i < 15; i++) {
                if (this.timer.hasTimeElapsed(2L, true)) {
                    y += 0.621;

                    MovementUtil.setMotion(2.3);
                    // mc.thePlayer.motionX *= 1.2;
                    // mc.thePlayer.motionZ *= 1.2;


                }
            }

            if (this.timer.hasTimeElapsed(1000L, true)) {
                mc.thePlayer.motionY = -1;
            }
          //  mc.timer.timerSpeed = 0.4f;
         //   MovementUtil.setSpeed(1.0);
            mc.thePlayer.motionY = y;
        }
        if (mode.equalsIgnoreCase("Verus")) {


            //    for(int i = 0; i < 30; i++){
            //        PacketUtil.sendPacketPlayer(new C03PacketPlayer());
            //    }


            mc.thePlayer.onGround = true;
            mc.thePlayer.capabilities.isFlying = true;


            MovementUtil.setSpeed(speed);
            if (this.timer.hasTimeElapsed(1000L, true)) {
                MovementUtil.setSpeed(speed);
                if (mc.thePlayer.onGround) {
               //     MovementUtil.damagePlayer();
                    MovementUtil.strafe(2);
                    PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.02, mc.thePlayer.posZ, false));
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - (0.02), mc.thePlayer.posZ );


                }

            }

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY = 0.5;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY = -0.5;
            }
        }
        if (mode.equalsIgnoreCase("Verus-Nigger")) {
            if (!mc.thePlayer.isCollidedVertically) {
                mc.thePlayer.onGround = true;
                mc.timer.timerSpeed = 0.941f;
                if (mc.thePlayer.ticksExisted % 8 == 0 && !mc.gameSettings.keyBindJump.isKeyDown() && MovementUtil.isMoving()) {
                    if (!bow)
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.41999998688697815, mc.thePlayer.posZ);
                    else {
                        mc.thePlayer.motionX = 0;
                        mc.thePlayer.motionZ = 0;
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0784000015258789, mc.thePlayer.posZ, false));
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - (0.0784000015258789 * 2), mc.thePlayer.posZ);
                    }
                    bow = !bow;
                    mc.thePlayer.onGround = false;
                } else mc.thePlayer.motionY = 0;

                if (!mc.gameSettings.keyBindJump.isKeyDown())
                    MovementUtil.strafe((float) 0.4);
                mc.thePlayer.isCollidedHorizontally = false;

                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.thePlayer.motionY = 0.5;

                }


                if (mc.thePlayer.posY != Math.round(mc.thePlayer.posY)
                        && mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, Math.round(mc.thePlayer.posY),
                            mc.thePlayer.posZ);
                }
            }

        }
        if (mode.equalsIgnoreCase("VerusNew")) {
            /*
            mc.timer.timerSpeed = 0.75f;
            if (mc.thePlayer.movementInput.jump) {
                mc.thePlayer.motionY = 1.0;
            }
            else if (mc.thePlayer.movementInput.sneak) {
                mc.thePlayer.motionY = -1.0;
            }
            else {
                mc.thePlayer.motionY = 0.0;
            }
            MovementUtil.setSpeed((mc.thePlayer.ticksExisted % 3 == 0) ? 4.5 : 0.0);
            if (mc.thePlayer.ticksExisted % 5 == 0) {
                for (int i = 0; i < this.parray.size(); i += 2) {
                    if (this.parray.get(i) != null) {
                        mc.thePlayer.sendQueue.addToSendQueue(this.parray.get(i));
                    }
                }
                this.parray.clear();
            }
            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;
            MovementUtil.setSpeed(0.5D);
            if (mc.thePlayer.onGround) {


                PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.02, mc.thePlayer.posZ, false));
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - (0.02), mc.thePlayer.posZ);
                ;

            }

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY = 0.01;
            }





             */


            //    for(int i = 0; i < 30; i++){
            //        PacketUtil.sendPacketPlayer(new C03PacketPlayer());
            //    }


            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;

            mc.timer.timerSpeed = 0.1f;
            MovementUtil.setSpeed(speed * 3);

            if(this.timer.hasTimeElapsed(30L, true)){
                mc.thePlayer.motionY = 0.1f;
                if(this.timer.hasTimeElapsed(20L, true)){
                    mc.thePlayer.motionY = -0.4f;

                }
            }

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY = 0.5;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY = -0.5;
            }
         }
        if (mode.equalsIgnoreCase("Verus-Packet")) {
            /*

            mc.thePlayer.motionY = 0;
            mc.thePlayer.onGround = true;

            MovementUtil.setSpeed(speed);



            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY += 0.2f;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY -= 0.2f;
            }

            mc.timer.timerSpeed = 0.5f;

            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;
            MovementUtil.setSpeed(speed);
            if (this.timer.hasTimeElapsed(1585L, true)) {
                MovementUtil.setSpeed(speed);
                if (mc.thePlayer.onGround) {

                    //PacketUtil.sendPacketPlayer(new C17PacketCustomPayload());
                    MovementUtil.damagePlayer();
                }
                mc.thePlayer.motionY = 0.25;

            }

            */

            MovementUtil.setSpeed(speed);
            if(this.timer.hasTimeElapsed(200L, true)){
                MovementUtil.setSpeed(0.0);
            }

            int d = 0x12;

            startY = mc.thePlayer.posY;
            final boolean shouldBlock = mc.thePlayer.posY + 0.1 >= startY && mc.gameSettings.keyBindJump.isKeyDown();
            if (mc.thePlayer.isSneaking()) {
                mc.thePlayer.motionY = -0.4f;
            } else if (mc.gameSettings.keyBindJump.isKeyDown() && !shouldBlock) {

                mc.thePlayer.motionY = 0.4f;
            } else {
                mc.thePlayer.motionY = -0.04;
            }

        }
        if (mode.equalsIgnoreCase("Hypixel")) {

            if (boost) {
                if (isPre) {
                    this.hypixelTimer.reset();
                    if (this.hypixelTimer.hasTimeElapsed(2000L, false)) {

                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-5, mc.thePlayer.posZ);
                        this.hypixelTimer.reset();
                    }


                }
                if (isPost) {
                    final double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
                    final double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
                    this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                }


                if (this.timer.hasTimeElapsed(10L, false)) {


                    MovementUtil.setSpeed(1.2D);
                }
            }


            MovementUtil.setSpeed(1);
            //mc.thePlayer.setSpeed(0.7f);

            if (mc.thePlayer.onGround = true) {
                MovementUtil.setSpeed(1.2D);
            }


            double x;
            double z;

            mc.timer.timerSpeed = 1.0f;
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.04f, mc.thePlayer.posZ, false));
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.04f, mc.thePlayer.posZ, true));

            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.0E-8, mc.thePlayer.posZ);
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-8, mc.thePlayer.posZ);

            //   PacketUtil.sendPacketPlayer(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));


            x = mc.thePlayer.posX - 1.461D;
            z = mc.thePlayer.posZ - 1.261D;

            // double playerX, double playerY, double playerZ, float playerYaw, float playerPitch, boolean playerIsOnGround
            PacketUtil.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw + 0.3f, mc.thePlayer.rotationPitch + 0.3f, true));
            if (this.timer.hasTimeElapsed(120L, true)) {


                mc.timer.timerSpeed = 0.67f;
                mc.thePlayer.motionY += 0.000001f;
                mc.thePlayer.onGround = false;
            }


            mc.thePlayer.motionY = 0.0;
            mc.thePlayer.onGround = true;


            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05F, mc.thePlayer.posZ, true));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));


        }
        if (mode.equalsIgnoreCase("Hypixel2")) {

            ArrayList<S08PacketPlayerPosLook> queue = new ArrayList<>();

            queue.add(new S08PacketPlayerPosLook());

            queue.clear();

            PacketUtil.sendPacket(new C03PacketPlayer());

            if (this.timer.hasTimeElapsed(1000L, false)) {
                MovementUtil.damageVerus();
            }

            if (MovementUtil.isMoving()) {
                mc.thePlayer.motionX = (-(Math.sin(n()) * speed));
                mc.thePlayer.motionZ = (Math.cos(n()) * speed);
            }

            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                return;
            }
            ;
            PacketUtil.sendPacket(new C0CPacketInput());
            double oldX = mc.thePlayer.posX;
            double oldY = mc.thePlayer.posY;
            double oldZ = mc.thePlayer.posZ;
            boolean oldGround = mc.thePlayer.onGround;

            float oldYaw = mc.thePlayer.rotationYaw;
            float oldPitch = mc.thePlayer.rotationPitch;

            if (this.timer.hasTimeElapsed(20L, true)) {
                PacketUtil.sendPacketPlayer(new C03PacketPlayer.C04PacketPlayerPosition(oldX, oldY, oldZ, oldGround));
            }


            BypassUtil.getMaxFallDist();

            double newX = mc.thePlayer.posX;
            double newY = mc.thePlayer.posY;
            double newZ = mc.thePlayer.posZ;

            PacketUtil.sendPacketPlayer(new C03PacketPlayer.C06PacketPlayerPosLook(newX, newY - 0.1E-5, newZ, oldYaw, oldPitch, false));


            if (mc.gameSettings.keyBindForward.isKeyDown()) {


            }
            mc.thePlayer.motionY = 1.0E-8f;
            if (this.timer.hasTimeElapsed(40L, true)) {
                MovementUtil.setSpeed(1.0405);
            }


        }
        if (mode.equalsIgnoreCase("Gravity")) {
            if (this.timer.hasTimeElapsed(90L, true) && mc.thePlayer.onGround == false) {
                mc.thePlayer.motionY += 0.10f;
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.10f, mc.thePlayer.posZ, true));

                if (this.timer.hasTimeElapsed(200L, true)) {

                    MovementUtil.setSpeed(2.0f);
                }

            }
        }
        if(mode.equalsIgnoreCase("Collision")){

            mc.thePlayer.motionY = 0.001F;

           MovementUtil.setMotion(0.30584398743952F);
           if(this.timer.hasTimeElapsed(80L, true)){

               MovementUtil.setMotion(0.62584398743952F);
           }

            if (mc.thePlayer.isCollidedVertically && faggot) {
                MovementUtil.setMotion(MovementUtil.getBaseMoveSpeed() / 129);
            }

              if(mc.gameSettings.keyBindJump.isKeyDown()){
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.41435454535245f, mc.thePlayer.posZ);
            }

        }

    }


    @EventTarget
    public void onPre(EventPreMotionUpdate niggers){
        String mode = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        if(mode.equalsIgnoreCase("Dev")){
            mc.thePlayer.capabilities.isFlying = true;
            mc.thePlayer.onGround = true;

          // if (mc.thePlayer.ticksExisted % 8 == 0 && !mc.gameSettings.keyBindJump.isKeyDown() && MovementUtil.isMoving()) {
          //     if (!bow)
          //         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.41999998688697815, mc.thePlayer.posZ);
          //     else {
          //         mc.thePlayer.motionX = 0;
          //         mc.thePlayer.motionZ = 0;
          //         mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0784000015258789, mc.thePlayer.posZ, false));
          //         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - (0.0784000015258789 * 2), mc.thePlayer.posZ);
          //     }
          //     bow = !bow;
          //     mc.thePlayer.onGround = false;
          // } else mc.thePlayer.motionY = 0;
        }
    }


    @EventTarget
    public void onMove(EventMove fag){
            String mode = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
            boolean move = MovementUtil.isMoving();
            double speed = Asyncware.instance.settingsManager.getSettingByName("Fly Speed").getValDouble();
            if(mode.equalsIgnoreCase("Hypixel3")){
                mc.thePlayer.motionY = 0;

                double y = mc.thePlayer.posY + 1.0E-9;

                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    y += 1.0E-8;
                }

                fag.y = y;
            }
        }

    public float n() {
        float var1 = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0F) {
            var1 += 180.0F;
        }
        float forward = 1.0F;
        if (mc.thePlayer.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (mc.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
        }
        if (mc.thePlayer.moveStrafing > 0.0F) {
            var1 -= 90.0F * forward;
        }
        if (mc.thePlayer.moveStrafing < 0.0F) {
            var1 += 90.0F * forward;
        }
        var1 *= 0.017453292F;

        return var1;
    }
}