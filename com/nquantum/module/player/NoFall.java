package com.nquantum.module.player;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("No Fall", Keyboard.KEY_B, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.fallDistance > 2F)
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
    }
}
