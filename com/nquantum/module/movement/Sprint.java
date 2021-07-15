package com.nquantum.module.movement;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Keyboard.KEY_M, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0)
            mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.thePlayer.setSprinting(false);
    }
}
