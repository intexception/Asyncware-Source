package com.nquantum.ui.other;

import cf.nquan.util.GuiUtil;
import com.nquantum.Asyncware;
import javafx.scene.transform.Scale;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class GuiCredits extends GuiScreen {
    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        float offset = 0;
        ScaledResolution sr = new ScaledResolution(mc);
        GuiUtil.drawRect(sr.getScaledWidth(), sr.getScaledHeight(), 0, 0, new Color(24, 24, 24, 255).getRGB());

        Asyncware.renderer1.drawString("Credits", 8, 8, -1, false);

        Asyncware.renderer.drawString("Ceepies - Good friend, helped with making client, some HUD parts and support.", 8, 30 + offset, -1, false);
        offset += 12;
        Asyncware.renderer.drawString("Gabrik - One of our developers, helped with few modules, including TP-Aura and more.", 8, 30 + offset, -1, false);
        offset += 12;
        Asyncware.renderer.drawString("Chmvrek - I skidded few things from your client, hope you don't blame me lmao.", 8, 30 + offset, -1, false);
        offset += 12;
        Asyncware.renderer.drawString("Zajchu - He's a toxic, and he motivated me to make a good clickgui.", 8, 30 + offset, -1, false);
        offset += 12;
        Asyncware.renderer.drawString("KtttKot - Good friend and homie or something.", 8, 30 + offset, -1, false);
        offset += 12;
        Asyncware.renderer.drawString("eyno - Helped making config system and good friend.", 8, 30 + offset, -1, false);
        offset += 12;
        Asyncware.renderer.drawString("wipet - Good friend too, I helped him with something.", 8, 30 + offset, -1, false);
        offset += 16;


        Asyncware.rendererTags.drawString("Honorable mentions: ", 8, 30 + offset, -1, false);
        offset += 18;


        Asyncware.renderer.drawString("todo: honorable mentions", 8, 30 + offset, -1, false);
        offset += 10;
        Asyncware.renderer.drawString("todo: honorable mentions.", 8, 30 + offset, -1, false);
        offset += 10;
        Asyncware.renderer.drawString("todo: honorable mentions.", 8, 30 + offset, -1, false);
        offset += 10;
        Asyncware.renderer.drawString("todo: honorable mentions.", 8, 30 + offset, -1, false);
        offset += 10;



        offset += 15;

        Asyncware.rendererTags.drawString("And all rest of my friends :D", 8, 30 + offset, -1, false);
        offset += 15;


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return super.doesGuiPauseGame();
    }
}
