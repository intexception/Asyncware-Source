package com.nquantum.module.render;

import cf.nquan.util.Colors;
import cf.nquan.util.ESPUtil;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class
Nametags extends Module {
    private int scale = 3;
    public Scoreboard scoreboard;
    public ScoreObjective scoreobjective;
    public Score score;

    public Nametags(){
        super("Nametags", 0, Category.RENDER);
    }

    @EventTarget
    public void onRender3D(Event3D event){

        for(Object e : mc.theWorld.loadedEntityList) {
            if(e instanceof EntityPlayer) {
                EntityPlayer player = mc.thePlayer;
                if(!(e == player) && !player.isInvisible()) {
                    double x = (((EntityPlayer) e).lastTickPosX + (((EntityPlayer) e).posX - ((EntityPlayer) e).lastTickPosX) * ((Event3D) event).partialTicks);
                    double y = (((EntityPlayer) e).lastTickPosY + (((EntityPlayer) e).posY - ((EntityPlayer) e).lastTickPosY) * ((Event3D) event).partialTicks);
                    double z = (((EntityPlayer) e).lastTickPosZ + (((EntityPlayer) e).posZ - ((EntityPlayer) e).lastTickPosZ) * ((Event3D) event).partialTicks);
                    drawRat((EntityLivingBase) e,x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ);
                }
            }
        }

    }


    @EventTarget
    public void onUpdate(){
        if(mc.thePlayer != null){
             scoreboard = mc.thePlayer.getWorldScoreboard();
             scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
             score = scoreboard.getValueFromObjective(mc.thePlayer.getName(), scoreobjective);
        }
    }


/*
    public void rat(){
        for (EntityPlayer entity : mc.theWorld.playerEntities) {

            if (entity.isInvisible() || entity == mc.thePlayer)
                continue;

            GL11.glPushMatrix();


            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
            //float distance = mc.thePlayer.getDistanceToEntity(entity);


            GL11.glTranslated(x, y + entity.getEyeHeight() + 1.7, z);
            GL11.glNormal3f(0, 1, 0);
            if (mc.gameSettings.thirdPersonView == 2) {
                GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
                GlStateManager.rotate(-mc.getRenderManager().playerViewX, 1, 0, 0);
            } else {
                GlStateManager.rotate(-mc.thePlayer.rotationYaw, 0, 1, 0);
                GlStateManager.rotate(mc.thePlayer.rotationPitch, 1, 0, 0);
            }
            float distance = mc.thePlayer.getDistanceToEntity(entity),
                    scaleConst_1 = 0.02672f, scaleConst_2 = 0.10f;
            double maxDist = 7.0;


            float scaleFactor = (float) (distance <= maxDist ? maxDist * scaleConst_2 : (double) (distance * scaleConst_2));
            scaleConst_1 *= scaleFactor;

            float scaleBet = (float) (scale * 10E-3);
            scaleConst_1 = Math.min(scaleBet, scaleConst_1);


            GL11.glScalef(-scaleConst_1, -scaleConst_1, .2f);

            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GL11.glDisable(GL11.GL_DEPTH_TEST);


            String colorCode = entity.getHealth() > 15 ? "\247a" : entity.getHealth() > 10 ? "\247e" : entity.getHealth() > 7 ? "\2476" : "\247c";
            int colorrectCode = entity.getHealth() > 15 ? 0xff4DF75B : entity.getHealth() > 10 ? 0xffF1F74D : entity.getHealth() > 7 ? 0xffF7854D : 0xffF7524D;
            String thing = entity.getName() + " " + colorCode + (int) entity.getHealth();
            float namewidth = (float) fr.getStringWidth(thing);


            Gui.drawRect(-namewidth / 2 - 2, 42, namewidth / 2 + 2, 40, 0x90080808);


            if (healthBar.isEnabled())
                Gui.drawRect(-namewidth / 2 - 15, 42, namewidth / 2 + 15 - (1 - (entity.getHealth() / entity.getMaxHealth())) * (namewidth + 4), 40, colorrectCode);

            if (background.isEnabled())
                Gui.drawRect(-namewidth / 2 - 15, 20, namewidth / 2 + 15, 40, 0x90202020);


            fr.drawCenteredStringWithShadow(entity.getName(), -20, 23, -1);
            fr.drawCenteredStringWithShadow(colorCode + (int) entity.getHealth(), namewidth / 2, 23, -1);

            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);


            double movingArmor = 1.2;

            if (namewidth <= 65) {
                movingArmor = 2;
            }
            if (namewidth <= 85) {
                movingArmor = 1.2;
            }

            if (namewidth <= 100) {
                movingArmor = 1.1;
            }

            if (armor.isEnabled()) {
                for (int index = 0; index < 5; index++) {

                    if (entity.getEquipmentInSlot(index) == null)
                        continue;


                    renderItem(entity.getEquipmentInSlot(index), (int) (index * 19 / movingArmor) - 30, -10);


                }
            }

            GL11.glPopMatrix();

        }
    }

 */
    public void drawRat(EntityLivingBase entity, double posX, double posY, double posZ){


        float scale = (float)(0.25f + mc.thePlayer.getDistanceSq(entity.posX, entity.posY, entity.posZ) / 10000.0);
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
      //  GlStateManager.scale(-0.21, -0.2, 0.2);
        GlStateManager.scale(-scale, -scale, scale);
        GL11.glDisable(2896);
        GL11.glDisable(2929);

      //  Gui.drawRect(8f, -entity.getHealth() - 0.2F, 8.55f, 2 - 0.2f, new Color(84, 84, 84, 254).getRGB());

       // Gui.drawRect(8.15f, -entity.getHealth(), 8.35f, 2, new Color(62,255, 9,255).getRGB());

        GlStateManager.scale(0.1, 0.1, 0.1);
       // Asyncware.csgoRenderer.drawString(Math.round(entity.getHealth()) + "HP", 80 - mc.fontRendererObj.getStringWidth(Math.round(entity.getHealth()) + "HP") / 2 + 15, (int) (-entity.getHealth() * 10), -1, true);
        GL11.glTranslatef(15.0f, 400.0f, 1.0f);
        GL11.glScaled(2.5f, 2.5f, 2.5f);
        Gui.drawRect(30, -210, -30, -200, new Color(14, 14, 14, 100).getRGB());
        int cur = new Color(20, 255, 181, 255).getRGB();
        Gui.drawRect(entity.getHealth() + 10, -201, -30, -200, Colors.fadeBetween(cur, Colors.darker(cur, 0.28f), ((System.currentTimeMillis() + (100)) % 1000 / (1000 / 2.0f))));

      //  Gui.drawRect( Asyncware.renderer1.getStringWidth(entity.getName()) , -225, -30, -205, new Color(0, 0, 0, 91).getRGB());


       // Asyncware.renderer1.drawScaledString(entity.getName(), -29, -225, new Color(255, 255, 255, 255).getRGB(), false, 0.5f);
      //  Asyncware.renderer1.drawScaledString(entity.getName(), 2, 29, -225, new Color(0, 0 ,0, 255).getRGB(), false, 0.5f);
        Asyncware.csgoRenderer.drawString(entity.getName(), 0 - Asyncware.csgoRenderer.getStringWidth(entity.getName()) / 2 , -210, -1, false);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.popMatrix();
    }
}
