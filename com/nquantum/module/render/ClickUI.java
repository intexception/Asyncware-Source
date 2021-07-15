package com.nquantum.module.render;

import com.nquantum.misc.menu.Menu;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickUI extends Module {
    public ClickUI() {
        super("ClickUI", Keyboard.KEY_Y, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(new com.nquantum.ui.clickgui.ClickUI());
        //mc.displayGuiScreen(new Menu());
    }
}
