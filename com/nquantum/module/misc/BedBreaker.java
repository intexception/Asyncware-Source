package com.nquantum.module.misc;

import cf.nquan.util.GuiUtil;
import cf.nquan.util.PacketUtil;
import cf.nquan.util.RenderUtil;
import cf.nquan.util.Timer;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.module.combat.KillAura;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BedBreaker extends Module {

    cf.nquan.util.Timer timer = new Timer();
    public int tm = 15;
    BlockPos pos;

    public BedBreaker(){
        super("BedBreaker", 0, Category.MISC);
    }


    @EventTarget
    public void onRender3D(Event3D niggaLol){
        if (pos != null) {
            drawFaggot(pos.getX(), pos.getY(), pos.getZ(), 255, 0, 0);
        }
    }

    public void drawFaggot(double ax, double ay, double az, int red, int green, int blue){
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double x = ax - renderManager.renderPosX;
        double y = ay - renderManager.renderPosY;
        double z = az - renderManager.renderPosZ;
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1.0F);
        GL11.glColor4d(0, 0, 0, 1.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4d(255, 255, 255, 1.0F);
        RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1, y + 1.0, z + 1));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @EventTarget
    public void onUpdate(EventUpdate event){


        for(int x = -3; x < 3; x++) {
            for(int y = -3; y < 3; y++) {
                for(int z = -3; z < 3; z++) {
                    pos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);

                    KillAura rots = new KillAura();
                    if (pos.getBlock() instanceof BlockBed) {


                        if (pos != null) {
                            drawESP(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5625, pos.getZ() + 1);
                        }
                        PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.NORTH));
                        PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.NORTH));
                      ////  mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                        tm = 15;
                    }
                }
            }
        }
        }



    static Minecraft mc = Minecraft.getMinecraft();
    public void drawESP(double x, double y, double z, double x2, double y2, double z2) {
        double x3 = x - RenderManager.renderPosX;
        double y3 = y - RenderManager.renderPosY;
        double z3 = z - RenderManager.renderPosZ;
        double x4 = x2 - RenderManager.renderPosX;
        double y4 = y2 - RenderManager.renderPosY;
        Color color = new Color(255, 20, 20, 80);
        drawFilledBBESP(new AxisAlignedBB(x3, y3, z3, x4, y4, z2 - RenderManager.renderPosZ), color);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void drawFilledBBESP(AxisAlignedBB axisalignedbb, Color color) {
        GL11.glPushMatrix();
        float red = (float)color.getRed()/255;
        float green = (float)color.getGreen()/255;
        float blue = (float)color.getBlue()/255;
        float alpha = 0.3f;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        RenderUtil.drawBoundingBox(axisalignedbb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    private boolean blockChecks(Block block) {
        return block == Blocks.bed;
    }

    public static float[] getRotations(BlockPos block, EnumFacing face){
        double x = block.getX() + 0.5 - mc.thePlayer.posX + (double)face.getFrontOffsetX()/2;
        double z = block.getZ() + 0.5 - mc.thePlayer.posZ + (double)face.getFrontOffsetZ()/2;
        double d1 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() -(block.getY() + 0.5);
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0D / Math.PI);
        if(yaw < 0.0F){
            yaw += 360f;
        }
        return  new float[]{yaw, pitch};
    }

    private EnumFacing getClosestEnum(BlockPos pos){
        EnumFacing closestEnum = EnumFacing.UP;
        float rotations = MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[0]);
        if(rotations >= 45 && rotations <= 135){
            closestEnum = EnumFacing.EAST;
        }else if((rotations >= 135 && rotations <= 180) ||
                (rotations <= -135 && rotations >= -180)){
            closestEnum = EnumFacing.SOUTH;
        }else if(rotations <= -45 && rotations >= -135){
            closestEnum = EnumFacing.WEST;
        }else if((rotations >= -45 && rotations <= 0) ||
                (rotations <= 45 && rotations >= 0)){
            closestEnum = EnumFacing.NORTH;
        }
        if (MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[1]) > 75 ||
                MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[1]) < -75){
            closestEnum = EnumFacing.UP;
        }
        return closestEnum;
    }

}
