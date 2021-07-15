package com.nquantum.module.misc;

import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;

import java.util.ArrayList;

public class Timer extends Module {
    public Timer() {
        super("Timer", 0, Category.MISC);
    }

    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<>();

        options.add("Speed");
        Asyncware.instance.settingsManager.rSetting(new Setting("Timer Speed", this, 1, 0, 50, true));
    }


    @EventTarget
    public void onEnable() {
        double speed = Asyncware.instance.settingsManager.getSettingByName("Timer Speed").getValDouble();

        System.out.println(speed);
        super.onEnable();

    }



    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
    }
}
