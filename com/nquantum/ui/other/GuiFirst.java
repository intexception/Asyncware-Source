package com.nquantum.ui.other;

import cf.nquan.util.FontUtil;
import com.nquantum.Asyncware;
import com.nquantum.ui.ingame.Hud;
import javafx.scene.transform.Scale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiFirst extends GuiScreen {
    public GuiFirst(){

    }

    static Minecraft mc = Minecraft.getMinecraft();
    static ScaledResolution sr = new ScaledResolution(mc);

    public static FontUtil fontheader = new FontUtil("blazing/regular.ttf", Font.PLAIN, 90);

    public void initGui(){

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        fontheader.drawString("Welcome to Asyncware!", sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, -1);
        buttonList.add(new GuiButton(69, sr.getScaledWidth() / 2, 200, "Play!"));
    }

    public void mouseClicked(int mouseX, int mouseY, int button){
        String[] buttons = { "Cancel"};

        int count = 0;
        for(String name : buttons){
            float x = (width/buttons.length) * count + (width/buttons.length)/2f - mc.fontRendererObj.getStringWidth(name)/2f;
            float y = height - 33;

            if (mouseX >= x && mouseY >= y && mouseX < x + this.mc.fontRendererObj.getStringWidth(name) && mouseY < y + this.mc.fontRendererObj.FONT_HEIGHT) {
                switch(name){
                    case "Cancel":
                        mc.displayGuiScreen(new GuiMainMenu());
                        break;

                }
            }

            count++;
        }
    }

    public void onGuiClosed(){

    }
}
