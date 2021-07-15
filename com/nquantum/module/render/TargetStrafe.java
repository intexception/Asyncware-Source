package com.nquantum.module.render;

import cf.nquan.util.ESPUtil;
import cf.nquan.util.MovementUtil;
import cf.nquan.util.RenderUtil;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.event.impl.EventMove;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.module.combat.KillAura;
import nig.hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

public class TargetStrafe extends Module {
    private int direction = -1;
    private KillAura aura;
    public EntityLivingBase target;
    public float[] rots;


    public Random random = new Random();

    public TargetStrafe(){
        super("TargetStrafe", 0, Category.RENDER);

        Asyncware.instance.settingsManager.rSetting(new Setting("Radius", this, 2.0D, 0.1D, 10.0D, false));
        this.aura = new KillAura();
    }


    @EventTarget
    public void onUpdate(EventUpdate event){

        double rad = Asyncware.instance.settingsManager.getSettingByName("Radius").getValDouble();
       this.setDisplayName("Target Strafe \u00A77" + rad );
            if (mc.thePlayer.isCollidedHorizontally) {
                this.switchDirection();
            }

            if (mc.gameSettings.keyBindLeft.isPressed()) {
                this.direction = 1;
            }

            if (mc.gameSettings.keyBindRight.isPressed()) {
                this.direction = -1;
            }

    }
    private void switchDirection() {
        if (this.direction == 1) {
            this.direction = -1;
        } else {
            this.direction = 1;
        }

    }

    @EventTarget
    public void onRender3D(Event3D event) {
        double radius = Asyncware.instance.settingsManager.getSettingByName("Radius").getValDouble();
        if (this.canStrafe()) {
         //   this.drawCircle(this.aura.target, event.getPartialTicks(), (Double)radius);
        }

        for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityLivingBase) {
                    final EntityLivingBase e = (EntityLivingBase) entity;
                    if (e.isDead || e == mc.thePlayer )
                        continue;

             //       ESPUtil.drawCircleOnEntityFade(e, event.getPartialTicks());
                }
        }

    }

    public void strafe(EventMove event, double moveSpeed) {
        double radius = Asyncware.instance.settingsManager.getSettingByName("Radius").getValDouble();
        EntityLivingBase target = this.aura.target;
        float[] rotations = rots;
        if ((double)mc.thePlayer.getDistanceToEntity(target) <= radius) {
            MovementUtil.setSpeed(event, moveSpeed, rotations[0], this.direction, 0.0D);
        } else {
            MovementUtil.setSpeed(event, moveSpeed, rotations[0], this.direction, 1.0D);
        }

    }

    public static float[] getRotations(double posX, double posY, double posZ) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        double x = posX - player.posX;
        double y = posY - (player.posY + (double)player.getEyeHeight());
        double z = posZ - player.posZ;
        double dist = (double) MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float)(-(Math.atan2(y, dist) * 180.0D / 3.141592653589793D));
        return new float[]{yaw, pitch};
    }



    private void drawCircle(Entity entity, float partialTicks, double rad) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        RenderUtil.startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0F);
        GL11.glBegin(3);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - mc.getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - mc.getRenderManager().viewerPosZ;
        float r = 0.003921569F * (float) Color.WHITE.getRed();
        float g = 0.003921569F * (float)Color.WHITE.getGreen();
        float b = 0.003921569F * (float)Color.WHITE.getBlue();
        double pix2 = 6.283185307179586D;

        for(int i = 0; i <= 90; ++i) {
            GL11.glColor3f(r, g, b);
            GL11.glVertex3d(x + rad * Math.cos((double)i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin((double)i * 6.283185307179586D / 45.0D));
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        RenderUtil.endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    public boolean canStrafe() {


        return Asyncware.instance.moduleManager.getModuleByName("KillAura").isToggled();
    }
}
