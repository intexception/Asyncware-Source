package com.nquantum.ui.clickgui;

import cf.nquan.util.Colors;
import cf.nquan.util.Strings;
import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickUI extends GuiScreen {

    ArrayList<Frame> frames;


    @Override
    public void initGui(){
        if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (mc.entityRenderer.theShaderGroup != null) {
                mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    @Override
    public void onGuiClosed(){
        if (mc.entityRenderer.theShaderGroup != null) {
        mc.entityRenderer.theShaderGroup.deleteShaderGroup();
        mc.entityRenderer.theShaderGroup = null;
    }}

    public ClickUI(){
        frames = new ArrayList<>();
        int offset = 0;
        for(Category category : Category.values()){

            frames.add(new Frame(category, 4 + offset, 10));
            offset += 105;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        ScaledResolution sr = new ScaledResolution(mc);
        Gui uii = new Gui();
        super.drawScreen(mouseX, mouseY, partialTicks);
        for(Frame frame : frames){

            frame.render(mouseX, mouseY);
            Frame.count++;
        }
      //  uii.drawGradientRect(sr.getScaledWidth(), sr.getScaledHeight(), 1, 1, Colors.RGB(), new Color(2, 2, 2, 0).getRGB());

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for(Frame frame : frames){
            frame.onClick(mouseX, mouseY, mouseButton);
        }
    }
}
