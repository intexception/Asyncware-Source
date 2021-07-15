package com.nquantum.module.render;

import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.clickgui.ClickGui;
import nig.hero.settings.Setting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("New");
        options.add("JellyLike");

        ArrayList<String> options1 = new ArrayList<>();
        options1.add("Lemon");
        options1.add("Hero");


        Asyncware.instance.settingsManager.rSetting(new Setting("Design", this, "New", options));
        Asyncware.instance.settingsManager.rSetting(new Setting("ClickGUI Mode", this, "Lemon", options1));

        Asyncware.instance.settingsManager.rSetting(new Setting("Sound", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("GuiRed", this, 25, 0, 255, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("GuiGreen", this, 97, 0, 255, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("GuiBlue", this, 255, 0, 255, true));
    }

    @Override
    public void onEnable() {
        String mode = Asyncware.instance.settingsManager.getSettingByName("ClickGUI Mode").getValString();
        super.onEnable();

        if (mode.equalsIgnoreCase("Lemon")) {
            mc.displayGuiScreen(Asyncware.instance.clickGui);
        }

        if(mode.equalsIgnoreCase("Hero")){
            mc.displayGuiScreen(Asyncware.instance.clickGui1);
        }

        toggle();
    }
}
