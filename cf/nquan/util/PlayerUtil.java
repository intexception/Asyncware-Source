package cf.nquan.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

import java.util.HashSet;

public class PlayerUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static void sendMessage(String message) {
        sendMessage(message, true);
    }

    public static void sendMessage(String message, boolean prefix) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
    }


    public static EntityPlayer getClosest(double range) {
        double biggerDistance = range;
        EntityPlayer target = null;
        for (EntityPlayer entity : mc.theWorld.playerEntities) {
            if (entity != mc.thePlayer && entity.getDistanceToEntity(mc.thePlayer) < biggerDistance) {
                if (entity.isOnSameTeam(mc.thePlayer)) continue;

                biggerDistance = entity.getDistanceToEntity(mc.thePlayer);
                target = entity;
            }
        }

        return target;
    }

    public static double round(double number, int decimals) {
        if (decimals < 0) {
            throw new IllegalArgumentException("Decimals must be almost 0");
        }

        return Math.round(Math.pow(10,decimals)*number)/Math.pow(10,decimals);
    }

    public static HashSet<EntityPlayer> getClosests(double range) {
        HashSet<EntityPlayer> targets = new HashSet<>();
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player != mc.thePlayer && player.getDistanceToEntity(mc.thePlayer) < range) {
                if (player.isOnSameTeam(mc.thePlayer)) continue;
                targets.add(player);
            }
        }

        return targets;
    }

    public static HashSet<EntityPlayer> getClosests(double range, int limit) {
        HashSet<EntityPlayer> targets = new HashSet<>();
        int playersAdded = 0;
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (playersAdded < limit) {
                if (player != mc.thePlayer && player.getDistanceToEntity(mc.thePlayer) < range) {
                    if (player.isOnSameTeam(mc.thePlayer)) continue;
                    targets.add(player);
                    playersAdded++;
                }
            }
        }

        return targets;
    }

    public static float rotationNormalToMc(float yaw) {
        if (yaw < 0) {
            yaw += 360;
        }
        else if (yaw >= 360) {
            yaw -= 360;
        }
        float result = yaw >= 180 ? yaw - 360 : yaw;
        if (result < -180) {
            return result + 360;
        }
        else if (result >= 180) {
            return result - 360;
        }
        return result;
    }

    public static float rotationMcToNormal(float yaw) {
        if (yaw < -180) {
            yaw += 360;
        }
        else if (yaw >= 180) {
            yaw -= 360;
        }
        float result = yaw < 0 ? yaw + 360 : yaw;
        if (result < 0) {
            return result + 360;
        }
        else if (result >= 360) {
            return result - 360;
        }
        return result;
    }

    public static float[] getRotationsNeeded(EntityPlayer entity) {
        if (entity == null) {
            return null;
        }

        final double diffX = entity.posX - mc.thePlayer.posX;
        final double diffZ = entity.posZ - mc.thePlayer.posZ;
        double diffY;
        final EntityLivingBase entityLivingBase = entity;
        diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
    }

    public static synchronized boolean faceEntity(EntityPlayer entity, boolean clientSide) {
        final float[] rotations = getRotationsNeeded(entity);

        if (rotations != null) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(rotations[0], rotations[1], true));
            if (clientSide) {
                mc.thePlayer.rotationYaw = rotations[0];
                mc.thePlayer.rotationPitch = rotations[1];
            }
        }

        return false;
    }

    public void attack(EntityPlayer target) {
        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer, target);
    }


}
