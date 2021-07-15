package com.nquantum.module.render;

import cf.nquan.util.Strings;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;

public class Fullbright extends Module {
    private float oldBrightness;

    public Fullbright() {
        super("Bright", 0, Category.RENDER);
    }

    @Override
    public void setup() {
        super.setup();
        Asyncware.instance.settingsManager.rSetting(new Setting("Bright Strength", this, 3, 0, 10, true));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        oldBrightness = mc.gameSettings.gammaSetting;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {

        double strength = Asyncware.instance.settingsManager.getSettingByName("Bright Strength").getValDouble();
        this.setDisplayName("Bright \u00A77" + Strings.capitalizeOnlyFirstLetter(strength + ""));
        mc.gameSettings.gammaSetting = (float)strength;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.gameSettings.gammaSetting = oldBrightness;
    }
}
