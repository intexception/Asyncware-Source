package com.nquantum.module.render;

import cf.nquan.util.GuiUtil;
import cf.nquan.util.RenderUtil;
import cf.nquan.util.RotationUtil;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event2D;
import com.nquantum.event.impl.Event3D;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

public class Radar extends Module {
    public Radar(){
        super("Radar", 0, Category.RENDER);
    }



    @EventTarget
    public void onRenderUI(EventRenderUI event){
        if (!mc.gameSettings.showDebugInfo) {
            GL11.glPushMatrix();

            int x = 6;
            int y = 30;
            int width = 100;
            int height = 100;
            float cx = x + (width / 2f);
            float cy = y + (height / 2f);

            int cl = new Color(64, 64, 64, 255).getRGB();
          //  GuiUtil.drawRoundedRect(x + 1, y + 1, x + width, y + height,3.2f, new Color(114, 114, 114, 255).getRGB());
            RenderUtil.drawRect(x - 1.8f, y- 1.8f, x + width  +1.8f, y + height + 1.8f,new Color(82, 82, 82, 255).getRGB());
            RenderUtil.drawRect(x - 1.2f, y- 1.2f, x + width + 1.2f, y + height + 1.2f,new Color(47, 47, 47, 255).getRGB());

            RenderUtil.drawRect(x - 0.8f, y- 0.8f, x + width + 0.8f, y + height + 0.8f,new Color(47,47, 47, 255).getRGB());
            RenderUtil.drawRect(x , y, x + width, y + height,new Color(28, 28, 28, 255).getRGB());
           // GuiUtil.drawRoundedRect(x, y, x + width, y + height,3.2f, new Color(15, 15, 15, 255).getRGB());
            RenderUtil.drawRectSized(x + (width / 2f) - 0.5f, y, 1, height, cl);
            RenderUtil.drawRectSized(x, y + (height / 2f) - 0.5f, width, 1,cl);
            RenderUtil.drawRectSized(cx - 1, cy - 1, 2, 2, cl);

            int maxDist = 12 / 2;
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (qualifies(entity)) {
                    // X difference
                    double dx = RenderUtil.lerp(entity.prevPosX, entity.posX, 20)
                            - RenderUtil.lerp(mc.thePlayer.prevPosX, mc.thePlayer.posX,
                            20);
                    // Z difference
                    double dz = RenderUtil.lerp(entity.prevPosZ, entity.posZ, 20)
                            - RenderUtil.lerp(mc.thePlayer.prevPosZ, mc.thePlayer.posZ,
                            20);

                    // Make sure they're within the available rendering range
                    if ((dx * dx + dz * dz) <= (maxDist * maxDist)) {
                        float dist = MathHelper.sqrt_double(dx * dx + dz * dz);
                        double[] vector = getLookVector(
                                RotationUtil.getRotations(entity)[0] - (float) RenderUtil.lerp(mc.thePlayer.prevRotationYawHead, mc.thePlayer.rotationYawHead, 20));
                        if (entity instanceof EntityMob) {
                            RenderUtil.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(0, 252, 103).getRGB());
                        } else if (entity instanceof EntityPlayer) {
                            RenderUtil.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(231, 27, 27).getRGB());
                        } else if (entity instanceof EntityAnimal || entity instanceof EntitySquid || entity instanceof EntityVillager || entity instanceof EntityGolem) {
                            RenderUtil.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(248, 178, 0).getRGB());
                        } else if (entity instanceof EntityItem) {
                            RenderUtil.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(0, 147, 241).getRGB());
                        }
                    }
                }
            }
        }
        GL11.glPopMatrix();


    }


    public double[] getLookVector(float yaw) {
        yaw *= MathHelper.deg2Rad;
        return new double[]{
                -MathHelper.sin(yaw),
                MathHelper.cos(yaw)
        };
    }

    private boolean qualifies(Entity entity) {
        return true;

    }
}
