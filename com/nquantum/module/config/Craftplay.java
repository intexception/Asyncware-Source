package com.nquantum.module.config;

import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import com.nquantum.module.Module;

public class Craftplay extends Module {
    public Craftplay(){
        super("Craftplay", 0, Category.CONFIG);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        try{
            Asyncware.instance.settingsManager.getSettingByName("Array Color").setValString("Novo");
            Asyncware.instance.settingsManager.getSettingByName("YOffset").setValDouble(9);
            Asyncware.instance.settingsManager.getSettingByName("Fly Mode").setValString("Verus");
            Asyncware.instance.settingsManager.getSettingByName("Fly Speed").setValString("4");
            Asyncware.instance.settingsManager.getSettingByName("Speed").setValString("HypixelHop");
            Asyncware.instance.settingsManager.getSettingByName("Disabler Mode").setValString("Verus");
            Asyncware.instance.moduleManager.getModuleByName("ArrayList").toggle();
            Asyncware.instance.moduleManager.getModuleByName("Glint").toggle();
            Asyncware.instance.moduleManager.getModuleByName("InfoHUD").toggle();
            Asyncware.instance.moduleManager.getModuleByName("TargetHUD").toggle();
            Asyncware.instance.moduleManager.getModuleByName("HUD").toggle();
            Asyncware.instance.moduleManager.getModuleByName("Nametags").toggle();
            Asyncware.instance.moduleManager.getModuleByName("Reach").toggle();
            Asyncware.instance.moduleManager.getModuleByName("Velocity").toggle();
            Asyncware.instance.moduleManager.getModuleByName("Auto Armor").toggle();
            Asyncware.instance.moduleManager.getModuleByName("Disabler").toggle();
            Asyncware.instance.moduleManager.getModuleByName("ThunderDetector").toggle();

        } catch (Exception e){

        }





    }
}
