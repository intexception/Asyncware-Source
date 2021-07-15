package cf.nquan.util;


import com.nquantum.event.impl.EventMove;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;

import java.util.ArrayList;

public class MovementUtil {
    protected static Minecraft mc = Minecraft.getMinecraft();


    public static double defaultSpeed() {
        double baseSpeed = 0.2873D;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            //  if(((Options) settings.get(MODE).getValue()).getSelected().equalsIgnoreCase("Hypixel")){
            // 	baseSpeed *= (1.0D + 0.225D * (amplifier + 1));
            // }else
            baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
        }
        return baseSpeed;
    }
    public static void strafe(double speed) {
        float a = mc.thePlayer.rotationYaw * 0.017453292F;
        float l = mc.thePlayer.rotationYaw * 0.017453292F - (float) Math.PI * 1.5f;
        float r = mc.thePlayer.rotationYaw * 0.017453292F + (float) Math.PI * 1.5f;
        float rf = mc.thePlayer.rotationYaw * 0.017453292F + (float) Math.PI * 0.19f;
        float lf = mc.thePlayer.rotationYaw * 0.017453292F + (float) Math.PI * -0.19f;
        float lb = mc.thePlayer.rotationYaw * 0.017453292F - (float) Math.PI * 0.76f;
        float rb = mc.thePlayer.rotationYaw * 0.017453292F - (float) Math.PI * -0.76f;
        if (mc.gameSettings.keyBindForward.pressed) {
            if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
                mc.thePlayer.motionX -= (double) (MathHelper.sin(lf) * speed);
                mc.thePlayer.motionZ += (double) (MathHelper.cos(lf) * speed);
            } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
                mc.thePlayer.motionX -= (double) (MathHelper.sin(rf) * speed);
                mc.thePlayer.motionZ += (double) (MathHelper.cos(rf) * speed);
            } else {
                mc.thePlayer.motionX -= (double) (MathHelper.sin(a) * speed);
                mc.thePlayer.motionZ += (double) (MathHelper.cos(a) * speed);
            }
        } else if (mc.gameSettings.keyBindBack.pressed) {
            if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed) {
                mc.thePlayer.motionX -= (double) (MathHelper.sin(lb) * speed);
                mc.thePlayer.motionZ += (double) (MathHelper.cos(lb) * speed);
            } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed) {
                mc.thePlayer.motionX -= (double) (MathHelper.sin(rb) * speed);
                mc.thePlayer.motionZ += (double) (MathHelper.cos(rb) * speed);
            } else {
                mc.thePlayer.motionX += (double) (MathHelper.sin(a) * speed);
                mc.thePlayer.motionZ -= (double) (MathHelper.cos(a) * speed);
            }
        } else if (mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
            mc.thePlayer.motionX += (double) (MathHelper.sin(l) * speed);
            mc.thePlayer.motionZ -= (double) (MathHelper.cos(l) * speed);
        } else if (mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed) {
            mc.thePlayer.motionX += (double) (MathHelper.sin(r) * speed);
            mc.thePlayer.motionZ -= (double) (MathHelper.cos(r) * speed);
        }

    }
    public static void setMotion(double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    public static boolean checkTeleport(double x, double y, double z,double distBetweenPackets){
        double distx = mc.thePlayer.posX - x;
        double disty = mc.thePlayer.posY - y;
        double distz = mc.thePlayer.posZ - z;
        double dist = Math.sqrt(mc.thePlayer.getDistanceSq(x, y, z));
        double distanceEntreLesPackets = distBetweenPackets;
        double nbPackets = Math.round(dist / distanceEntreLesPackets + 0.49999999999) - 1;

        double xtp = mc.thePlayer.posX;
        double ytp = mc.thePlayer.posY;
        double ztp = mc.thePlayer.posZ;
        for (int i = 1; i < nbPackets;i++){
            double xdi = (x - mc.thePlayer.posX)/( nbPackets);
            xtp += xdi;

            double zdi = (z - mc.thePlayer.posZ)/( nbPackets);
            ztp += zdi;

            double ydi = (y - mc.thePlayer.posY)/( nbPackets);
            ytp += ydi;
            AxisAlignedBB bb = new AxisAlignedBB(xtp - 0.3, ytp, ztp - 0.3, xtp + 0.3, ytp + 1.8, ztp + 0.3);
            if(!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()){
                return false;
            }

        }
        return true;
    }



    public static int getJumpEffect() {
        if (mc.thePlayer.isPotionActive(Potion.jump))
            return mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        else
            return 0;
    }
    public static int getSpeedEffect() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        else
            return 0;
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
    }

    public static Block getBlockAtPosC(double x, double y, double z) {
        EntityPlayer inPlayer = Minecraft.getMinecraft().thePlayer;
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(inPlayer.posX + x, inPlayer.posY + y, inPlayer.posZ + z)).getBlock();
    }

    public static float getDistanceToGround(Entity e) {
        if (mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround) {
            return 0.0F;
        }
        for (float a = (float) e.posY; a > 0.0F; a -= 1.0F) {
            int[] stairs = {53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180};
            int[] exemptIds = {
                    6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59,
                    63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94,
                    104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150,
                    157, 171, 175, 176, 177};
            Block block = mc.theWorld.getBlockState(new BlockPos(e.posX, a - 1.0F, e.posZ)).getBlock();
            if (!(block instanceof BlockAir)) {
                if ((Block.getIdFromBlock(block) == 44) || (Block.getIdFromBlock(block) == 126)) {
                    return (float) (e.posY - a - 0.5D) < 0.0F ? 0.0F : (float) (e.posY - a - 0.5D);
                }
                int[] arrayOfInt1;
                int j = (arrayOfInt1 = stairs).length;
                for (int i = 0; i < j; i++) {
                    int id = arrayOfInt1[i];
                    if (Block.getIdFromBlock(block) == id) {
                        return (float) (e.posY - a - 1.0D) < 0.0F ? 0.0F : (float) (e.posY - a - 1.0D);
                    }
                }
                j = (arrayOfInt1 = exemptIds).length;
                for (int i = 0; i < j; i++) {
                    int id = arrayOfInt1[i];
                    if (Block.getIdFromBlock(block) == id) {
                        return (float) (e.posY - a) < 0.0F ? 0.0F : (float) (e.posY - a);
                    }
                }
                return (float) (e.posY - a + block.getBlockBoundsMaxY() - 1.0D);
            }
        }
        return 0.0F;
    }


    public static float[] getRotationsBlock(BlockPos block, EnumFacing face) {
        double x = block.getX() + 0.5 - mc.thePlayer.posX +  (double) face.getFrontOffsetX()/2;
        double z = block.getZ() + 0.5 - mc.thePlayer.posZ +  (double) face.getFrontOffsetZ()/2;
        double y = (block.getY() + 0.5);
        double d1 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - y;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
        if (yaw < 0.0F) {
            yaw += 360f;
        }
        return new float[]{yaw, pitch};
    }
    public static boolean isBlockAboveHead(){
        AxisAlignedBB bb =new AxisAlignedBB(mc.thePlayer.posX - 0.3, mc.thePlayer.posY+mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ + 0.3,
                mc.thePlayer.posX + 0.3, mc.thePlayer.posY+2.5 ,mc.thePlayer.posZ - 0.3);
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty();
    }
    public static boolean isCollidedH(double dist){
        AxisAlignedBB bb =new AxisAlignedBB(mc.thePlayer.posX - 0.3, mc.thePlayer.posY+2, mc.thePlayer.posZ + 0.3,
                mc.thePlayer.posX + 0.3, mc.thePlayer.posY+3 ,mc.thePlayer.posZ - 0.3);
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.3 + dist, 0, 0)).isEmpty()) {
            return true;
        }else if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(-0.3 - dist, 0, 0)).isEmpty()) {
            return true;
        }else if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0, 0, 0.3 + dist)).isEmpty()) {
            return true;
        }else if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0, 0, -0.3 - dist)).isEmpty()) {
            return true;
        }
        return false;
    }
    public static boolean isRealCollidedH(double dist){
        AxisAlignedBB bb =new AxisAlignedBB(mc.thePlayer.posX - 0.3, mc.thePlayer.posY + 0.5, mc.thePlayer.posZ + 0.3,
                mc.thePlayer.posX + 0.3, mc.thePlayer.posY+1.9 ,mc.thePlayer.posZ - 0.3);
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.3 + dist, 0, 0)).isEmpty()) {
            return true;
        }else if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(-0.3 - dist, 0, 0)).isEmpty()) {
            return true;
        }else if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0, 0, 0.3 + dist)).isEmpty()) {
            return true;
        }else if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0, 0, -0.3 - dist)).isEmpty()) {
            return true;
        }
        return false;
    }
    public static boolean isMovingOnGround() {
        return isMoving() && mc.thePlayer.onGround;
    }

    public static float getRetarded() {
        return 0.2873F;
    }

    public static double getJumpHeight(double speed) {
        return mc.thePlayer.isPotionActive(Potion.jump) ? speed + 0.1D * (double) (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) : speed;
    }

    public static void sendPosition(double x, double y, double z, boolean ground, boolean moving) {
        if (!moving) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + y, mc.thePlayer.posZ, ground));
        } else {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z, ground));
        }
    }

    public static Block getBlockAtPos(BlockPos inBlockPos) {
        IBlockState s = mc.theWorld.getBlockState(inBlockPos);
        return s.getBlock();
    }




    public static float getDirection() {
        float var1 = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        var1 *= 0.017453292f;
        return var1;
    }

    public static void damagePlayer(){
        //mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.4f, mc.thePlayer.posZ, false));

        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.007, mc.thePlayer.posZ, false));
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
    }

    public static void setSpeed(final double moveSpeed) {
        setSpeed(moveSpeed, Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe, Minecraft.getMinecraft().thePlayer.movementInput.moveForward);
    }

    public static void setSpeed(final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            }
            else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        final double offsetX = Math.cos(Math.toRadians(yaw + 90.0f));
        final double offsetZ = Math.sin(Math.toRadians(yaw + 90.0f));
        mc.thePlayer.motionX = forward * moveSpeed * offsetX + strafe * moveSpeed * offsetZ;
        mc.thePlayer.motionZ = forward * moveSpeed * offsetZ - strafe * moveSpeed * offsetX;
    }

    public static void strafe(final float speed) {
        if(!isMoving())
            return;

        final double yaw = getDirection();
        Minecraft.getMinecraft().thePlayer.motionX = -Math.sin(yaw) * speed;
        Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static void setSpeed(EventMove moveEvent, double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, (double)mc.thePlayer.movementInput.moveStrafe, (double)mc.thePlayer.movementInput.moveForward);
    }

    public static void setSpeed(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0D) {
            if (pseudoStrafe > 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
            } else if (pseudoStrafe < 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (pseudoForward > 0.0D) {
                forward = 1.0D;
            } else if (pseudoForward < 0.0D) {
                forward = -1.0D;
            }
        }

        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }

        double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
        moveEvent.x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        moveEvent.z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }


    public static void damageVerus() {
        double val1 = 0;

        for (int i = 0; i <= 6; i++) {
            val1 += 0.5;
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY + val1, mc.thePlayer.posZ, true));
            mc.thePlayer.sendQueue.addToSendQueue(
                    new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
        }

        double val2 = mc.thePlayer.posY + val1;

        ArrayList<Float> vals = new ArrayList<>();

        vals.add(0.07840000152587834f);
        vals.add(0.07840000152587834f);
        vals.add(0.23052736891295922f);
        vals.add(0.30431682745754074f);
        vals.add(0.37663049823865435f);
        vals.add(0.44749789698342113f);
        vals.add(0.5169479491049742f);
        vals.add(0.5850090015087517f);
        vals.add(0.6517088341626192f);
        vals.add(0.1537296175885956f);

        for (float value : vals) {
            val2 -= value;
            mc.thePlayer.sendQueue.addToSendQueue(
                    new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, val2, mc.thePlayer.posZ, false));
        }
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));

        mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));

    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875D;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
            baseSpeed *= 1.0D + 0.2D * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        return baseSpeed;
    }

    public static boolean isMoving() {
        return mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F;
    }

    public static double defaultMoveSpeed() {
        return mc.thePlayer.isSprinting() ? 0.28700000047683716 : 0.22300000488758087;
    }

    public static double getLastDistance() {
        return Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ);
    }

    public static boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public static double jumpHeight() {
        if (mc.thePlayer.isPotionActive(Potion.jump))
            return 0.419999986886978 + 0.1 * (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1);
        else
            return 0.419999986886978;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJumpHeight += (float) (amplifier + 1) * 0.1F;
        }

        return baseJumpHeight;
    }



    public static float getSpeed() {
        return (float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }



    public double getTickDist() {
        double xDist = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
        double zDist = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
        return Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));
    }


}

