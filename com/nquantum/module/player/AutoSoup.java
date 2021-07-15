package com.nquantum.module.player;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;

public class AutoSoup extends Module {
    public AutoSoup(){
        super("AutoSoup", 0 , Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate e){
        if (mc.thePlayer.getHealth() <= 13) {
            if (this.doesHotbarHaveSoups()) {
                this.eatAndDropSoup();
            } else {
                this.getSoupFromInventory();
            }
        }
    }
    private boolean isHotbarFull() {
        for(int index = 36; index < 45; ++index) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }

        return true;
    }

    private boolean doesHotbarHaveSoups() {
        for(int index = 36; index < 45; ++index) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && this.isStackSoup(stack)) {
                return true;
            }
        }

        return false;
    }

    private void eatAndDropSoup() {
        for(int index = 36; index < 45; ++index) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && this.isStackSoup(stack)) {
                this.stackBowls();
                int oldslot = mc.thePlayer.inventory.currentItem;
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index - 36));
                mc.playerController.updateController();
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, stack, 0.0F, 0.0F, 0.0F));
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldslot));
                break;
            }
        }

    }

    private void getSoupFromInventory() {
        if (!(mc.currentScreen instanceof GuiChest)) {
            this.stackBowls();

            for(int index = 9; index < 36; ++index) {
                ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                if (stack != null && this.isStackSoup(stack)) {
                    mc.playerController.windowClick(0, index, 0, 1, mc.thePlayer);
                    break;
                }
            }

        }
    }

    private boolean isStackSoup(ItemStack stack) {
        return stack == null ? false : stack.getItem() instanceof ItemSoup;
    }


    private void stackBowls() {
        if (!(mc.currentScreen instanceof GuiChest)) {
            for(int index = 9; index < 45; ++index) {
                ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                if (stack != null && stack.getItem() == Items.bowl) {
                    mc.playerController.windowClick(0, index, 0, 0, mc.thePlayer);
                    mc.playerController.windowClick(0, 18, 0, 0, mc.thePlayer);
                }
            }

        }
    }
}
