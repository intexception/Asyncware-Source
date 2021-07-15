package com.nquantum.module.movement;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import org.lwjgl.input.Keyboard;

public class Step extends Module {
    public Step() {
        super("Step", Keyboard.KEY_L, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.thePlayer.stepHeight = 1.5F;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.thePlayer.stepHeight = .5F;
    }
}
