package com.nquantum.module.render;

import cf.nquan.util.RenderUtil;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.module.combat.KillAura;
import nig.hero.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class ESP extends Module {


    private double animHealth = 1;
    private double width;
    private int colorPrimary;
    private int colorSecondary;

    public ESP() {
        super("ESP",0 , Category.RENDER);
    }


    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<>();
        options.add("CSGO");
        options.add("Box");
        options.add("Astolfo");
        options.add("Color Ring");




        Asyncware.instance.settingsManager.rSetting(new Setting("ESP Mode", this, "CSGO", options));
    }


    @EventTarget
    public void onRender3D(Event3D event){

        String mode = Asyncware.instance.settingsManager.getSettingByName("ESP Mode").getValString();
       // GlStateManager.pushMatrix();
       // GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
       // GL11.glPolygonOffset(1.0f, 1000000.0f);
       // GL11.glDisable(GL11.GL_TEXTURE_2D);
       // GL11.glDisable(GL11.GL_DEPTH_TEST);
       // GL11.glDepthMask(false);
//
       // GlStateManager.color(255.0F, 55.0F, 55.0F, 255);
       // GlStateManager.enableBlend();
       // GlStateManager.blendFunc(770, 771);
       // GlStateManager.alphaFunc(516, 0.003921569F);
       // GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
       // GL11.glDisable(GL11.GL_POLYGON_OFFSET_LINE);
       // GL11.glPolygonOffset(1.0f, 1100000.0f);
       // GL11.glEnable(GL11.GL_TEXTURE_2D);
       // GL11.glEnable(GL11.GL_LIGHTING);
       // GL11.glDisable(GL_LINE_SMOOTH);
       // GlStateManager.popMatrix();
        if(mode.equalsIgnoreCase("CSGO")){
            for(Object e : mc.theWorld.loadedEntityList) {
                if(e instanceof EntityPlayer) {
                    EntityPlayer player = mc.thePlayer;
                    if(!(e == player) && !player.isInvisible()) {
                        double x = (((EntityPlayer) e).lastTickPosX + (((EntityPlayer) e).posX - ((EntityPlayer) e).lastTickPosX) * ((Event3D) event).partialTicks);
                        double y = (((EntityPlayer) e).lastTickPosY + (((EntityPlayer) e).posY - ((EntityPlayer) e).lastTickPosY) * ((Event3D) event).partialTicks);
                        double z = (((EntityPlayer) e).lastTickPosZ + (((EntityPlayer) e).posZ - ((EntityPlayer) e).lastTickPosZ) * ((Event3D) event).partialTicks);
                        drawBox((EntityLivingBase) e,x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ);

                    }
                }
            }
        }

        if(mode.equalsIgnoreCase("Astolfo")){
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

        for(Object e : mc.theWorld.loadedEntityList) {
            if(e instanceof EntityPlayer) {
                EntityPlayer target = mc.thePlayer;

                }
            }
        }





         /*
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase e = (EntityLivingBase) entity;
                if (e.isDead || e == mc.thePlayer )
                    continue;

                ESPUtil.drawCircleOnEntityFade(e, event.getPartialTicks());
            }
        }

          */




    @EventTarget
    public void onRenderUI(EventRenderUI e){
        GL11.glPushMatrix();

        GL11.glPopMatrix();
    }


    public void drawRat(EntityLivingBase entity, double posX, double posY, double posZ) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.01, -0.01, 0.01);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        mc.getTextureManager().bindTexture(new ResourceLocation("asyncware/astolfo.png"));
        Gui.drawModalRectWithCustomSizedTexture(-150,-275,0,0,300,300, 326, 300);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.popMatrix();
    }

    public void drawBox(EntityLivingBase entity, double posX, double posY, double posZ) {
        int colorrr = 0;
        float scale = (float)(0.15f + mc.thePlayer.getDistanceSq(entity.posX, entity.posY, entity.posZ) / 7000.0);
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);


        GL11.glDisable(2896);
        GL11.glDisable(2929);
       // GlStateManager.scale(-0.1, -0.1, 0.1);

        if(entity.getHealth() < 10){
           colorrr = new Color(255, 50, 50, 100).getRGB();
        }
        else{
            colorrr = new Color(45, 243, 72, 100).getRGB();
        }

        RenderUtil.drawRectSized(2, 5, mc.thePlayer.width, 0.02f, new Color(245, 245, 245, 255).getRGB());

        KillAura ak = new KillAura();
        if(ak.isToggled()){
            Gui.drawRect(entity.height + 1.3f, -19.4f, -3.1f, entity.height - 0.3f, colorrr);
        }

        if (Asyncware.instance.moduleManager.getModuleByName("TargetHUD").isToggled() && ak.isToggled()){

        }


        GlStateManager.scale(-scale, -scale, scale);
        GL11.glDisable(2896);
        GL11.glDisable(2929);

        GL11.glTranslatef(0, 6.7f, 0);
        Gui.drawRect(8f, -entity.getHealth() - 0.2F, 8.55f, -3 + 0.2f, new Color(0, 0, 0, 254).getRGB());

        Gui.drawRect(8.15f, -entity.getHealth(), 8.35f, -3, new Color(56, 255, 0,255).getRGB());

        GlStateManager.scale(0.1, 0.1, 0.1);
        //Asyncware.csgoRenderer.drawString(Math.round(entity.getHealth()) + "HP", 80 - mc.fontRendererObj.getStringWidth(Math.round(entity.getHealth()) + "HP") / 2 + 15, (int) (-entity.getHealth() * 10), -1, true);
        GL11.glTranslatef(15.0f, 300.0f, 1.0f);
        GL11.glScaled(2.5f, 2.5f, 2.5f);
        //Asyncware.csgoRenderer.drawString(entity.getName(), 0 - mc.fontRendererObj.getStringWidth(entity.getName()) / 2, -210, -1, true);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.popMatrix();
    }




}
