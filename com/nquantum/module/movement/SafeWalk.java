package com.nquantum.module.movement;

import com.nquantum.module.Category;
import com.nquantum.module.Module;
import org.lwjgl.input.Keyboard;

public class SafeWalk extends Module {
    public SafeWalk(){
        super("SafeWalk", Keyboard.KEY_B, Category.MOVEMENT);
    }
}
