package com.nquantum.module.render;

import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;

public class Glint extends Module {
    public Glint(){
        super("Glint", 0, Category.RENDER);
    }

    @Override
    public void setup() {
        Asyncware.instance.settingsManager.rSetting(new Setting("Glint R", this, 255, 0, 255, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Glint G", this, 50, 0, 255, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Glint B", this, 50, 0, 255, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Rainbow Glint", this, false));



    }
}
