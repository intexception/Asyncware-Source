package com.nquantum.misc.menu;

import cf.nquan.util.Colors;
import cf.nquan.util.GuiUtil;
import cf.nquan.util.Strings;
import cf.nquan.util.font.GlyphPageFontRenderer;
import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Menu extends GuiScreen {

    private GlyphPageFontRenderer fmt;
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        GL11.glPushMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void assNiggaPics(){
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL13.GL_MULTISAMPLE);
        GL11.glEnable(GL13.GL_SAMPLE_ALPHA_TO_COVERAGE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
    }
    @Override
    protected void drawHoveringText(List<String> textLines, int x, int y) {
        super.drawHoveringText(textLines, x, y);
    }

    @Override
    public void initGui() {
       fmt = GlyphPageFontRenderer.create("SF UI Display Light", 11, false, false, false);
       for(int i = 0; i < 4; i++){
           if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
               if (mc.entityRenderer.theShaderGroup != null) {
                   mc.entityRenderer.theShaderGroup.deleteShaderGroup();
               }
               mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
           }
       }

        super.initGui();
    }

    @Override
    public void handleInput() throws IOException {
        super.handleInput();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        if (mc.entityRenderer.theShaderGroup != null) {
            mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            mc.entityRenderer.theShaderGroup = null;
            super.onGuiClosed();
        }
    }}

    /*
    *
    *
    *
    *   // GL11.glPushMatrix();
       // assNiggaPics();
       //// GuiUtil.drawRoundedRect(sr.getScaledWidth() / 2 + 232, sr.getScaledHeight() / 2 + 142, 228, 98, -6.3f, new Color(48, 48, 48, 255).getRGB());
       //// GuiUtil.drawRoundedRect(sr.getScaledWidth() / 2 + 230, sr.getScaledHeight() / 2 + 140, 230, 100, -6.3f, new Color(32, 32, 32, 255).getRGB());
//
       // Gui i = new Gui();
       // i.drawGradientRect(sr.getScaledWidth() / 2 + 233, sr.getScaledHeight() / 2 + 143, 227, 97, new Color(0, 180, 219, 255).getRGB(), new Color(0, 131, 176, 255).getRGB());
       // //Gui.drawRect(sr.getScaledWidth() / 2 + 233, sr.getScaledHeight() / 2 + 143, 227, 97, new Color(28, 33, 48, 255).getRGB());
       // Gui.drawRect(sr.getScaledWidth() / 2 + 230, sr.getScaledHeight() / 2 + 140, 230, 100, new Color(21, 20, 32, 255).getRGB());
//
//
       //// Asyncware.csgoRenderer.drawString(Asyncware.instance.name, sr.getScaledWidth() / 3 - 88, sr.getScaledHeight() / 5 + 1, 0x000000, false);
       //// Asyncware.csgoRenderer.drawString(Asyncware.instance.name, sr.getScaledWidth() / 3 - 89, sr.getScaledHeight() / 5, -1, false);
//
       // GL11.glTranslatef(110f, 0 ,0);
//
       // Gui.drawRect(200, sr.getScaledHeight() / 2 + 140, 120, 100, new Color(28, 33, 48, 255).getRGB());
       // GL11.glTranslatef(-110f, 0 ,0);
//
       // double y = 2;
//
       // for(Category category : Category.values()){
//
       //     fmt.drawString(Strings.capitalizeOnlyFirstLetter(category.name()), sr.getScaledWidth() / 3 - 75, sr.getScaledHeight() / 3 + y, -1, false);
//
       //     //Asyncware.renderer.drawString(Strings.capitalizeOnlyFirstLetter(category.name()), sr.getScaledWidth() / 3 - 70, sr.getScaledHeight() / 3 + y, -1, false);
       //     y += 20;
//
       // }
       // /*
       // for(Module m : Asyncware.instance.moduleManager.getModules()){
//
       //     Asyncware.renderer.drawString(m.getName(), sr.getScaledWidth() / 3 - 88, sr.getScaledHeight() / 5 + y, -1, false);
       //     y += 10;
       // }
//
       //  */
//
// GL11.glScalef(0.93f, 0.93f, 0.93f);
// int count = 10;
//// GuiUtil.drawChromaStringModern(Asyncware.instance.name, sr.getScaledWidth() / 3 - 68, sr.getScaledHeight() / 4 + 1, false);
// Asyncware.rendererTags.drawString(Asyncware.instance.name, sr.getScaledWidth() / 3 - 68, sr.getScaledHeight() / 4 + 1, Colors.fadeBetween(0x00B4DB, 0x0083B0, ((System.currentTimeMillis() + (count * 100)) % 1000 / (1000 / 2.0f))), false);
//
// GL11.glPopMatrix();
// */