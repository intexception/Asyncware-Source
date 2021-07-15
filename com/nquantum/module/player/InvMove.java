package com.nquantum.module.player;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.misc.menu.Menu;
import com.nquantum.module.Category;
import com.nquantum.module.Module;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class InvMove extends Module {
    public InvMove(){
        super("InvMove", 0, Category.PLAYER);
    }

    private final KeyBinding[] moveKeys = { mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindForward, mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSprint };


    @EventTarget
    public void onUpdate(EventUpdate nigger){
        if(!(mc.currentScreen instanceof GuiChat)) {
            int length = moveKeys.length;
            for (int i = 0; i < length; i++) {
                moveKeys[i].pressed = Keyboard.isKeyDown(moveKeys[i].getKeyCode());
            }
        }
    }
 }
