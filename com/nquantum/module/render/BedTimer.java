package com.nquantum.module.render;

import cf.nquan.util.GuiUtil;
import cf.nquan.util.MovementUtil;
import cf.nquan.util.RenderUtil;
import cf.nquan.util.Timer;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.GLSLSandboxShader;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class BedTimer extends Module {

    Timer timer = new Timer();
    public int tm = 20;

    public BedTimer(){
        super("BedTimer", 0, Category.RENDER);
    }


    @EventTarget
    public void onRenderUI(EventRenderUI e){
        if(timer.hasTimeElapsed(1000, true)) {
            tm--;
        }
        ScaledResolution sr = new ScaledResolution(mc);
        GlStateManager.pushMatrix();
        GlStateManager.translate(sr.getScaledWidth() / 2, 30, 0);
       // GuiUtil.drawRoundedRect(80.0f, 50.0f, -80.0f, 1.0f, 12, new Color(24, 24, 24, 255).getRGB());
        GuiUtil.drawRoundedRect(-80.5f, 0, 148 - 82.5f, 50, 3,  new Color(24, 24, 24, 180).getRGB());


        GlStateManager.popMatrix();
        Asyncware.rendererTags.drawString("FlyTimer", sr.getScaledWidth() / 2 - 78, 32, -1, false);
        Asyncware.renderer.drawString(tm + " seconds left", sr.getScaledWidth() / 2 - 78, 48, -1, false);
    }



}
