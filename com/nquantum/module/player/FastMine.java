package com.nquantum.module.player;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FastMine extends Module {
    public FastMine()
    {
        super("FastMine", 0, Category.PLAYER);
    }

    @Override
    public void onEnable() {
        this.mc.playerController.blockHitDelay = 0;
        boolean item = mc.thePlayer.getCurrentEquippedItem() == null;
        mc.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 100, 3));
    }

    @Override
    public void onDisable() {
        this.mc.playerController.blockHitDelay = 0;
        boolean item = mc.thePlayer.getCurrentEquippedItem() == null;
        mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
    }
}
