package com.nquantum.module.render;

import cf.nquan.util.Colors;
import cf.nquan.util.GuiUtil;
import cf.nquan.util.Strings;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.module.combat.KillAura;
import nig.hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class TargetHUD extends Module {

    private double animHealth = 1;
    private double width;
    private int colorPrimary;
    private int colorSecondary;
    public EntityLivingBase target;

    public TargetHUD(){
        super("TargetHUD", 0, Category.RENDER);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Asyncware");
        options.add("Astolfo");
        Asyncware.instance.settingsManager.rSetting(new Setting("TargetHUD X", this, 0, -1000, 1000, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("TargetHUD X", this, 0, -1000, 1000, true));

        Asyncware.instance.settingsManager.rSetting(new Setting("TargetHUD Mode", this, "Asyncware", options));

    }

    public static ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    @EventTarget
    public void onRenderUI(EventRenderUI event){

        KillAura ak = new KillAura();
        String mode = Asyncware.instance.settingsManager.getSettingByName("TargetHUD Mode").getValString();

        this.setDisplayName("TargetHUD \u00A77" + Strings.capitalizeOnlyFirstLetter(mode));
        if(mode.equalsIgnoreCase("Asyncware")){
            target = (EntityLivingBase) ak.target;
           target = getClosest(mc.playerController.getBlockReachDistance());


            if (!(target == null) && Asyncware.instance.moduleManager.getModuleByName("Killaura").isToggled()) {
                if(target.getHealth() < 20) {

                    colorPrimary = new Color(57, 255, 51,120).getRGB();
                    colorSecondary = new Color(93, 255, 85,120).getRGB();

                }
                if(target.getHealth() < 10) {
                    colorPrimary = new Color(255, 91, 5,120).getRGB();
                    colorSecondary = new Color(255, 168, 86,120).getRGB();
                }
                if(target.getHealth() < 5) {
                    colorPrimary = new Color(255, 0, 7,120).getRGB();
                    colorSecondary = new Color(255, 73, 87,120).getRGB();
                }
                GL11.glPushMatrix();
                width = 140 - 32.5;
                GL11.glTranslated(GuiScreen.width / 2 - 40,  GuiScreen.height / 2 + 20, GuiScreen.width / 2);
                GuiUtil.drawRoundedRect(-22.5f, 0, 148 - 3.5f, 50, 2,  new Color(9, 19, 34, 167).getRGB());
                //     Gui.drawRect(-22.5f, 0, 145 - 32.5f, 50, new Color(9, 19, 34, 167).getRGB());



                GL11.glTranslatef(-22.0f, -2.2f, 0);
                Asyncware.renderer1.drawString(target.getName(), 3, 3, Colors.Astolfo(100, 1.0f, 0.5f), true);



                Asyncware.renderer.drawString("Health: " + Math.round(target.getHealth()), 3, 25, -1, true);


                GuiInventory.drawEntityOnScreen(143, 47, 20, 2, 2, target);

                animHealth += ((target.getHealth() - animHealth) / 32) * 0.7;
                if (animHealth < 0 || animHealth > target.getMaxHealth()) {
                    animHealth = target.getHealth();
                }
                else {
                    Gui.drawRect(0f, 47.5f, (int) ((animHealth / target.getMaxHealth()) * width + 6), 45.5f, colorPrimary);
                    Gui.drawRect(0f, 47.5f, (int) ((animHealth / target.getMaxHealth()) * width), 45.5f,  colorSecondary);
                }
                GL11.glScalef(2f,2f,2f);
                //Asyncware.renderer1.drawString(target.getHealth() + "\u2764", 2, 2,Colors.Astolfo(100, 1.0f, 0.5f), true);
                GL11.glPopMatrix();
                //  Gui.drawRect(350.0D, 10.0D, 120.0D, 170.0D, new Color(9, 19, 34, 167).getRGB());

            }
        }

        else{

            KillAura ka = new KillAura();
            EntityLivingBase target =(EntityLivingBase) mc.pointedEntity;

            if (!(target == null)  && Asyncware.instance.moduleManager.getModuleByName("Killaura").isToggled()) {
                colorPrimary = Colors.Astolfo(200, 0.7f, 0.5f);
                colorSecondary = Colors.Astolfo(200, 1.0f, 0.5f);

                GL11.glPushMatrix();
                width = 140 - 32.5;
                GL11.glTranslated(GuiScreen.width / 2 - 40,  GuiScreen.height / 2 + 20, GuiScreen.width / 2);

                Gui.drawRect(-22.5f, 0, 128 - 3.5f, 50,  new Color(24, 24, 24, 181).getRGB());

                GL11.glTranslatef(-22.0f, -2.2f, 0);

                mc.fontRendererObj.drawString(target.getName(), 30, 8, -1, false);

                // \u2764
                GL11.glScalef(2.0f, 2.0f, 2.0f);
                GL11.glTranslatef(-15, -15, 0);
                mc.fontRendererObj.drawString( Math.round(target.getHealth()) + "", 30, 25, Colors.Astolfo(200, 1.0f, 0.5f), true);
                mc.fontRendererObj.drawString(  "\u2764", mc.fontRendererObj.getStringWidth(Math.round(target.getHealth()) + "") + 32, 25, Colors.Astolfo(200, 1.0f, 0.5f), true);

                GL11.glTranslatef(15, 15, 0);
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                mc.fontRendererObj.drawString( "", 30, 25, -1, true);

                GuiInventory.drawEntityOnScreen(15, 47, 20, 2, 2, target);

                animHealth += ((target.getHealth() - animHealth) / 32) * 0.7;
                if (animHealth < 0 || animHealth > target.getMaxHealth()) {
                    animHealth = target.getHealth();
                }
                else {
                    GL11.glTranslatef(30, 0, 0);
                    Gui.drawRect(20f, 40.5f, (int) ((animHealth / target.getMaxHealth()) * width + 6), 48.5f, colorPrimary);
                    Gui.drawRect(0f, 40.5f, (int) ((animHealth / target.getMaxHealth()) * width), 48.5f,  colorSecondary);
                }
                GL11.glScalef(2f,2f,2f);
                //Asyncware.renderer1.drawString(target.getHealth() + "\u2764", 2, 2,Colors.Astolfo(100, 1.0f, 0.5f), true);
                GL11.glPopMatrix();
                //  Gui.drawRect(350.0D, 10.0D, 120.0D, 170.0D, new Color(9, 19, 34, 167).getRGB());

            }
        }



    }
    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) entity;

            }
        }
        return target;
    }

}
