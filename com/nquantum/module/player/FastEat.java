package com.nquantum.module.player;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FastEat extends Module {
    public FastEat()
    {
        super("FastEat", 0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate nigger){
        if (mc.thePlayer.inventory.getCurrentItem() == null) {
            return;
        }
        if (!(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood)) {
            return;
        }
        if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
            for (int i = 0; i < 32; i++) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
            }
        }
    }

}
