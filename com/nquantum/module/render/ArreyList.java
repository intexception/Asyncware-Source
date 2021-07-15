package com.nquantum.module.render;

import cf.nquan.util.Colors;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class ArreyList extends Module {
    private static Minecraft mc = Minecraft.getMinecraft();
    private net.minecraft.client.gui.FontRenderer font = mc.fontRendererObj;

    public static int color;
    public static int count;
    public ArreyList() {
        super("ArrayList", Keyboard.KEY_NONE, Category.RENDER);
    }


    @Override
    public void setup() {
        java.util.ArrayList < String > options = new java.util.ArrayList < > ();
        java.util.ArrayList < String > designs = new java.util.ArrayList < > ();
        java.util.ArrayList < String > colors = new java.util.ArrayList < > ();
        designs.add("Vibesense");
        designs.add("Weird");
        designs.add("Gamesense");
        options.add("Rect");
        options.add("YOffset");
        options.add("Custom Font");

        colors.add("Astolfo");
        colors.add("Novo");
        colors.add("Rainbow");
        colors.add("ChillRainbow");
        Asyncware.instance.settingsManager.rSetting(new Setting("Rect", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("YOffset", this, 10, 2, 40, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("X", this, 0, -120, 120, true));

        Asyncware.instance.settingsManager.rSetting(new Setting("Y", this, -4, -120, 120, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Array R", this, 255, 0, 255, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Array G", this, 0, 0, 255, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Array B", this, 0, 0, 255, true));



        Asyncware.instance.settingsManager.rSetting(new Setting("Custom Font", this, true));

        Asyncware.instance.settingsManager.rSetting(new Setting("Array Design", this, "New", designs));
        Asyncware.instance.settingsManager.rSetting(new Setting("Array Color", this, "New", colors));


    }


    @EventTarget
    public void onRenderUI(EventRenderUI e) {

        double offsety = Asyncware.instance.settingsManager.getSettingByName("YOffset").getValDouble();
        boolean isFont = Asyncware.instance.settingsManager.getSettingByName("Custom Font").getValBoolean();
        boolean isRect = Asyncware.instance.settingsManager.getSettingByName("Rect").getValBoolean();
        boolean isDort = Asyncware.instance.settingsManager.getSettingByName("Rect").getValBoolean();

        String arrStyle = Asyncware.instance.settingsManager.getSettingByName("Array Design").getValString();



        if(isFont && !arrStyle.equalsIgnoreCase("Weird")){
            drawArrayWithFont();
        } else

        if(!isFont && !arrStyle.equalsIgnoreCase("Weird")){
            drawArrayWithoutFont();
        } else
        if(arrStyle.equalsIgnoreCase("Weird")){
            drawArrayDortware();
        }
        /*
        ScaledResolution sr = new ScaledResolution(mc);

        ArrayList < Module > enabledMods = new ArrayList < Module > ();
        for (Module m: Asyncware.instance.moduleManager.getModules())
            if (m.isToggled())
                enabledMods.add(m);

        enabledMods.sort((m1, m2) -> Asyncware.renderer.getStringWidth(m2.getDisplayName()) - Asyncware.renderer.getStringWidth(m1.getDisplayName()));

        int count = 0;
        int y = 2;

        double X = Asyncware.instance.settingsManager.getSettingByName("X").getValDouble();
        double Y = Asyncware.instance.settingsManager.getSettingByName("Y").getValDouble();

        GL11.glPushMatrix();
        GL11.glTranslated(X, Y, 0);
        for (Module m: enabledMods) {

            double offsety = Asyncware.instance.settingsManager.getSettingByName("YOffset").getValDouble();
            boolean isFont = Asyncware.instance.settingsManager.getSettingByName("Custom Font").getValBoolean();
            boolean isRect = Asyncware.instance.settingsManager.getSettingByName("Rect").getValBoolean();
            String arrStyle = Asyncware.instance.settingsManager.getSettingByName("Array Design").getValString();

            if (isRect) {

                // MINI RECT
                //	Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) + -12, y - 4, sr.getScaledWidth(),  4 +  Asyncware.renderer.getFontHeight() + y + 2 , Colors.Astolfo(count * 100, 1.0f, 0.5f));
                if (arrStyle.equalsIgnoreCase("Gamesense")) {
                    // BIG RECT
                    GlStateManager.pushMatrix();
                    //Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(),  0 +  Asyncware.renderer.getFontHeight() + y , new Color(20, 20, 20, 120).getRGB());
                    Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(), 2 + Asyncware.renderer.getFontHeight() + y, new Color(12, 12, 12, 230).getRGB());
                    //Gui.drawRect(sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName())  - 8, count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), sr.getScaledWidth(), 6 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), new Color(24, 24, 24,255).getRGB());

                    Gui.drawRect(958, count * (mc.fontRendererObj.FONT_HEIGHT + 6), 960, 4 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 7), Colors.RGBX(2, 1.0f, 0.5f, count * 120));

                    //mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 4, 4 + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), Colors.Astolfo(count * 100, 1.0f, 0.5f));
                    GlStateManager.popMatrix();
                } else {
                    // BIG RECT

                    //Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(),  0 +  Asyncware.renderer.getFontHeight() + y , new Color(20, 20, 20, 120).getRGB());
                    GlStateManager.pushMatrix();
                    Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 8, y + 1, sr.getScaledWidth(), 1.1f + Asyncware.renderer.getFontHeight() + y, new Color(24, 24, 24, 196).getRGB());
                 //mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 4, 4 + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), Colors.Astolfo(count * 100, 1.0f, 0.5f));
                    GlStateManager.popMatrix();
                }
            } else {
                Gui.drawRect(sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 10, y - 1, sr.getScaledWidth(), 2 + Asyncware.renderer.getFontHeight() + y, new Color(20, 20, 20, 0).getRGB());

            }

            if (isFont) {
                           Asyncware.renderer.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 5, y + 1, Colors.fadeBetween(0x5642F3, 0x392CA2, ((System.currentTimeMillis() + (count * 100)) % 1000 / (1000 / 2.0f))), true);

            } else {

            }



            y += (int) offsety;
            count++;

        }

        GL11.glPopMatrix();

         */
    }

    public void drawArrayWithFontNovo(){

        ScaledResolution sr = new ScaledResolution(mc);

        double offsety = Asyncware.instance.settingsManager.getSettingByName("YOffset").getValDouble();
        boolean isFont = Asyncware.instance.settingsManager.getSettingByName("Custom Font").getValBoolean();
        boolean isRect = Asyncware.instance.settingsManager.getSettingByName("Rect").getValBoolean();
        String arrStyle = Asyncware.instance.settingsManager.getSettingByName("Array Design").getValString();


        double X = Asyncware.instance.settingsManager.getSettingByName("X").getValDouble();
        double Y = Asyncware.instance.settingsManager.getSettingByName("Y").getValDouble();

        ArrayList < Module > enabledMods = new ArrayList < Module > ();
        for (Module m : Asyncware.instance.moduleManager.getModules())
            if (m.isToggled())
                enabledMods.add(m);

        enabledMods.sort((m1, m2) -> Asyncware.rendererNovo.getStringWidth(m2.getDisplayName()) - Asyncware.rendererNovo.getStringWidth(m1.getDisplayName()));


        count = 0;
        int y = 2;


        GL11.glPushMatrix();
        GL11.glTranslated(X, Y, 0);


        for (Module m: enabledMods) {
            String colorStyle = Asyncware.instance.settingsManager.getSettingByName("Array Color").getValString();

            int currentR = (int) Asyncware.instance.settingsManager.getSettingByName("Array R").getValDouble();
            int currentG = (int) Asyncware.instance.settingsManager.getSettingByName("Array G").getValDouble();
            int currentB = (int) Asyncware.instance.settingsManager.getSettingByName("Array B").getValDouble();


            if(colorStyle.equalsIgnoreCase("Astolfo")){
                color = Colors.Astolfo(count * 1 + 80, 1.0f, 0.5f);
            }
            if(colorStyle.equalsIgnoreCase("Novo")){
                int cur = new Color(currentR, currentG, currentB, 255).getRGB();
                color =  Colors.fadeBetween(cur, Colors.darker(cur, 0.28f), ((System.currentTimeMillis() + (count * 50)) % 1000 / (1000 / 2.0f)));
            }
            if(colorStyle.equalsIgnoreCase("Rainbow")){
                color = Colors.RGBX(4, 1.1f, 1.0f, count * 600);
            }
            if(colorStyle.equalsIgnoreCase("ChillRainbow")){
                color = Colors.RGBX(5, 0.5f, 1.0f, count * 300);
            }
            GlStateManager.pushMatrix();

            Gui.drawRect(sr.getScaledWidth() - Asyncware.rendererNovo.getStringWidth(m.getDisplayName()) - 7.2f, y + 1 , sr.getScaledWidth(),   Asyncware.rendererNovo.getFontHeight() + y, new Color(29, 29, 29, 0).getRGB());
            Gui.drawRect(sr.getScaledWidth() - 1.6f, y + 1 , sr.getScaledWidth(), Asyncware.rendererNovo.getFontHeight() + y,color);

            //  Gui.drawRect(sr.getScaledWidth() - Asyncware.rendererT.getStringWidth(m.getDisplayName()) - 7.2f, y + 2 , sr.getScaledWidth() - Asyncware.rendererT.getStringWidth(m.getDisplayName()) - 6, 1.1f + Asyncware.rendererT.getFontHeight() + y,color);


            //mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 4, 4 + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), Colors.Astolfo(count * 100, 1.0f, 0.5f));
            Asyncware.rendererNovo.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.rendererNovo.getStringWidth(m.getDisplayName()) - 5, y , color, true);

            GlStateManager.popMatrix();
            y += (int) offsety;
            count ++;
        }
        GL11.glPopMatrix();
    }

    public void drawArrayWithFont(){

        ScaledResolution sr = new ScaledResolution(mc);

        double offsety = Asyncware.instance.settingsManager.getSettingByName("YOffset").getValDouble();
        boolean isFont = Asyncware.instance.settingsManager.getSettingByName("Custom Font").getValBoolean();
        boolean isRect = Asyncware.instance.settingsManager.getSettingByName("Rect").getValBoolean();
        String arrStyle = Asyncware.instance.settingsManager.getSettingByName("Array Design").getValString();


        double X = Asyncware.instance.settingsManager.getSettingByName("X").getValDouble();
        double Y = Asyncware.instance.settingsManager.getSettingByName("Y").getValDouble();

        ArrayList < Module > enabledMods = new ArrayList < Module > ();
        for (Module m : Asyncware.instance.moduleManager.getModules())
            if (m.isToggled())
                enabledMods.add(m);

        enabledMods.sort((m1, m2) -> Asyncware.rendererT.getStringWidth(m2.getDisplayName()) - Asyncware.rendererT.getStringWidth(m1.getDisplayName()));


        count = 0;
        int y = 2;


        GL11.glPushMatrix();
        GL11.glTranslated(X, Y, 0);


        for (Module m: enabledMods) {
            String colorStyle = Asyncware.instance.settingsManager.getSettingByName("Array Color").getValString();

            int currentR = (int) Asyncware.instance.settingsManager.getSettingByName("Array R").getValDouble();
            int currentG = (int) Asyncware.instance.settingsManager.getSettingByName("Array G").getValDouble();
            int currentB = (int) Asyncware.instance.settingsManager.getSettingByName("Array B").getValDouble();


            if(colorStyle.equalsIgnoreCase("Astolfo")){
                color = Colors.Astolfo(count * 1 + 80, 1.0f, 0.5f);
            }
            if(colorStyle.equalsIgnoreCase("Novo")){
                int cur = new Color(currentR, currentG, currentB, 255).getRGB();
                color =  Colors.fadeBetween(cur, Colors.darker(cur, 0.48f), ((System.currentTimeMillis() + (count * 100)) % 1000 / (1000 / 2.0f)));
            }
            if(colorStyle.equalsIgnoreCase("Rainbow")){
                color = Colors.RGBX(4, 1.1f, 1.0f, count * 600);
            }
            if(colorStyle.equalsIgnoreCase("ChillRainbow")){
                color = Colors.RGBX(5, 0.5f, 1.0f, count * 300);
            }
            GlStateManager.pushMatrix();

            Gui.drawRect(sr.getScaledWidth() - Asyncware.rendererT.getStringWidth(m.getDisplayName()) - 7.2f, y + 1 , sr.getScaledWidth(),   Asyncware.rendererT.getFontHeight() + y, new Color(29, 29, 29, 158).getRGB());
            Gui.drawRect(sr.getScaledWidth() - 1.2f, y + 1 , sr.getScaledWidth(), Asyncware.rendererT.getFontHeight() + y,color);

          //  Gui.drawRect(sr.getScaledWidth() - Asyncware.rendererT.getStringWidth(m.getDisplayName()) - 7.2f, y + 2 , sr.getScaledWidth() - Asyncware.rendererT.getStringWidth(m.getDisplayName()) - 6, 1.1f + Asyncware.rendererT.getFontHeight() + y,color);


            //mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 4, 4 + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), Colors.Astolfo(count * 100, 1.0f, 0.5f));
            Asyncware.rendererT.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.rendererT.getStringWidth(m.getDisplayName()) - 5, y , color, true);

            GlStateManager.popMatrix();
            y += (int) offsety;
            count ++;
        }
        GL11.glPopMatrix();
    }
    public static int RGBX(float seconds, float saturation, float brightness, long index) {
        float rgb = (System.currentTimeMillis() + index) % (int)(seconds * 5000) /  (float)(seconds * 5000);
        int color = Color.HSBtoRGB(rgb, 0.5f, 1);
        return color;
    }


    public void drawArrayDortware(){

        ScaledResolution sr = new ScaledResolution(mc);
        double offsety = Asyncware.instance.settingsManager.getSettingByName("YOffset").getValDouble();
        boolean isFont = Asyncware.instance.settingsManager.getSettingByName("Custom Font").getValBoolean();
        boolean isRect = Asyncware.instance.settingsManager.getSettingByName("Rect").getValBoolean();
        String arrStyle = Asyncware.instance.settingsManager.getSettingByName("Array Design").getValString();

        double X = Asyncware.instance.settingsManager.getSettingByName("X").getValDouble();
        double Y = Asyncware.instance.settingsManager.getSettingByName("Y").getValDouble();

        ArrayList < Module > enabledMods = new ArrayList < Module > ();
        for (Module m: Asyncware.instance.moduleManager.getModules())
            if (m.isToggled())
                enabledMods.add(m);
        count = 0;
        int y = 2;


        enabledMods.sort((m1, m2) -> Asyncware.dort1.getStringWidth(m2.getDisplayName()) - Asyncware.dort1.getStringWidth(m1.getDisplayName()));

        Asyncware.dort.drawString("A", 1, 2f, Colors.RGB(), true);
        Asyncware.dort.drawString("syncware b" + Asyncware.instance.version, 8f, 2f, -1, true);


        GL11.glPushMatrix();
        GL11.glTranslated(X, Y, 0);


        Gui i = new Gui();
        //i.drawGradientRect(sr.getScaledWidth(), 2f, sr.getScaledWidth() - 117.5f, 0.9f, Colors.RGB(), Colors.RGBX(2, 1.0f, 0.5f, 10));

        for (Module m: enabledMods) {
            String colorStyle = Asyncware.instance.settingsManager.getSettingByName("Array Color").getValString();


            if(colorStyle.equalsIgnoreCase("Astolfo")){
                color = Colors.Astolfo(count * 1 + 80, 1.0f, 0.5f);
            }
            if(colorStyle.equalsIgnoreCase("Novo")){
                color = new Color(57, 44, 162,255).getRGB();
            }
            if(colorStyle.equalsIgnoreCase("Rainbow")){
                color = Colors.RGBX(3, 1.0f, 1.0f, count * 300);
            }
            if(colorStyle.equalsIgnoreCase("ChillRainbow")){
                color = Colors.RGBX(5, 0.5f, 1.0f, count * 300);
            }


            Gui.drawRect(sr.getScaledWidth() - Asyncware.dort1.getStringWidth(m.getDisplayName()) - 6.6f, y , sr.getScaledWidth(), 1 + Asyncware.dort1.getFontHeight() + y, new Color(12, 12, 12, 116).getRGB());
            Gui.drawRect(sr.getScaledWidth() - 1.6f, y , sr.getScaledWidth(), 1 + Asyncware.dort1.getFontHeight() + y, RGBX(0.8f, 1.0f, 1.0f, count * 120));

            // Gui.drawRect(sr.getScaledWidth() - font.getStringWidth(m.getDisplayName()) - 8, y + 1, sr.getScaledWidth(), 1.1f+  + font.FONT_HEIGHT + y, Colors.fadeBetween(0x5642F3, 0x392CA2, ((System.currentTimeMillis() + (count * 100)) % 1000 / (1000 / 2.0f))));
          //  font.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - font.getStringWidth(m.getDisplayName()) - 2, y, color);
            Asyncware.dort1.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.dort1.getStringWidth(m.getDisplayName()) - 5, y, RGBX(0.8f, 1.0f, 1.0f, count * 120), true);
            y += (int) offsety;
            count++;
        }
        GL11.glPopMatrix();
    }

    public void drawArrayWithoutFont(){
        ScaledResolution sr = new ScaledResolution(mc);
        double offsety = Asyncware.instance.settingsManager.getSettingByName("YOffset").getValDouble();
        boolean isFont = Asyncware.instance.settingsManager.getSettingByName("Custom Font").getValBoolean();
        boolean isRect = Asyncware.instance.settingsManager.getSettingByName("Rect").getValBoolean();
        String arrStyle = Asyncware.instance.settingsManager.getSettingByName("Array Design").getValString();

        double X = Asyncware.instance.settingsManager.getSettingByName("X").getValDouble();
        double Y = Asyncware.instance.settingsManager.getSettingByName("Y").getValDouble();

        ArrayList < Module > enabledMods = new ArrayList < Module > ();
        for (Module m: Asyncware.instance.moduleManager.getModules())
            if (m.isToggled())
                enabledMods.add(m);
        count = 0;
        int y = 2;

        /*

        enabledMods.sort((m1, m2) -> font.getStringWidth(m2.getDisplayName()) - font.getStringWidth(m1.getDisplayName()));


         */

        enabledMods.sort((m1, m2) -> Asyncware.dort1.getStringWidth(m2.getDisplayName()) - Asyncware.dort1.getStringWidth(m1.getDisplayName()));

        GL11.glPushMatrix();
        GL11.glTranslated(X, Y, 0);

        for (Module m: enabledMods) {
            String colorStyle = Asyncware.instance.settingsManager.getSettingByName("Array Color").getValString();


            if(colorStyle.equalsIgnoreCase("Astolfo")){
                color = Colors.Astolfo(count * 1 + 80, 1.0f, 0.5f);
            }
            if(colorStyle.equalsIgnoreCase("Novo")){
                color = new Color(57, 44, 162,255).getRGB();
            }
            if(colorStyle.equalsIgnoreCase("Rainbow")){
                color = Colors.RGBX(3, 1.0f, 1.0f, count * 300);
            }
            if(colorStyle.equalsIgnoreCase("ChillRainbow")){
                color = Colors.RGBX(5, 0.5f, 1.0f, count * 300);
            }

            /*
            Gui.drawRect(sr.getScaledWidth() - font.getStringWidth(m.getDisplayName()) - 6, y - 2, sr.getScaledWidth() + 2, 2 + font.FONT_HEIGHT + y, color);
            Gui.drawRect(sr.getScaledWidth() - font.getStringWidth(m.getDisplayName()) - 5, y - 2, sr.getScaledWidth(), 1 + font.FONT_HEIGHT + y, new Color(12, 12, 12, 255).getRGB());



            // Gui.drawRect(sr.getScaledWidth() - font.getStringWidth(m.getDisplayName()) - 8, y + 1, sr.getScaledWidth(), 1.1f+  + font.FONT_HEIGHT + y, Colors.fadeBetween(0x5642F3, 0x392CA2, ((System.currentTimeMillis() + (count * 100)) % 1000 / (1000 / 2.0f))));
            font.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - font.getStringWidth(m.getDisplayName()) - 2, y, color);


 */


            Asyncware.dort1.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.dort1.getStringWidth(m.getDisplayName()),  count * (this.mc.fontRendererObj.FONT_HEIGHT + 1), color, true);
            y += (int) offsety;

            count++;
        }
        GL11.glPopMatrix();
    }
}

/*
 //Asyncware.renderer.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 7, y + 1, Colors.fadeBetween(0x484AA8, 0x2E86EF, ((System.currentTimeMillis() + (count * 100)) % 1000 / (1000 / 2.0f))), true);
/Asyncware.renderer.drawString(m.getDisplayName(), sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName()) - 7, y + 1, Colors.Astolfo(count * 100, 1.0f, 0.5f), true);
//Asyncware.renderer.drawString(m.getMode(), sr.getScaledWidth() - Asyncware.renderer.getStringWidth(m.getDisplayName() + m.getMode()) + 5, y + 1, new Color(24, 24, 24, 255).getRGB(), false);
 // Gui.drawRect(sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName()) - 7, y - 1, sr.getScaledWidth(),  4 + mc.fontRendererObj.FONT_HEIGHT + y , new Color(0, 0, 0, 80).getRGB());
//
   //Gui.drawRect(sr.getScaledWidth() - 2, 2, sr.getScaledWidth(), 2 + Asyncware.renderer.getFontHeight() + y, Colors.RGBX(2, 1.0f, 0.5f, count * 120));
                    //	Gui.drawRect(958, count * (mc.fontRendererObj.FONT_HEIGHT + 6), 960, 4 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 7), Colors.RGBX(2, 1.0f, 0.5f, count * 120));

                    //Gui.drawRect(sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayName())  - 8, count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), sr.getScaledWidth(), 6 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), new Color(24, 24, 24,255).getRGB());

                    //Gui.drawRect(958, count * (mc.fontRendererObj.FONT_HEIGHT + 6), 960, -6 + this.mc.fontRendererObj.FONT_HEIGHT + count * (this.mc.fontRendererObj.FONT_HEIGHT + 7), Colors.RGBX(2, 1.0f, 0.5f, count * 120));
                    //Gui.drawRect(958, count * (Asyncware.renderer.getFontHeight() + 1), 960, -20 +Asyncware.renderer.getFontHeight() + count * (this.mc.fontRendererObj.FONT_HEIGHT + 6), Colors.RGBX(2, 1.0f, 0.5f, count * 120));
















 */