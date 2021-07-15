package com.nquantum.module.render;

import cf.nquan.util.ESPUtil;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.block.Block;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class BypassXray extends Module {
    public static ArrayList xrayBlocks = new ArrayList();

    public BypassXray()
    {
        super("BypassXray", Keyboard.KEY_N, Category.RENDER);
        xrayBlocks.add(Block.getBlockFromName("coal_ore"));
        xrayBlocks.add(Block.getBlockFromName("iron_ore"));
        xrayBlocks.add(Block.getBlockFromName("gold_ore"));
        xrayBlocks.add(Block.getBlockFromName("redstone_ore"));
        xrayBlocks.add(Block.getBlockById(74));
        xrayBlocks.add(Block.getBlockFromName("lapis_ore"));
        xrayBlocks.add(Block.getBlockFromName("diamond_ore"));
        xrayBlocks.add(Block.getBlockFromName("emerald_ore"));
        xrayBlocks.add(Block.getBlockFromName("quartz_ore"));
        xrayBlocks.add(Block.getBlockFromName("clay"));
        xrayBlocks.add(Block.getBlockFromName("glowstone"));
        xrayBlocks.add(Block.getBlockById(8));
        xrayBlocks.add(Block.getBlockById(9));
        xrayBlocks.add(Block.getBlockById(10));
        xrayBlocks.add(Block.getBlockById(11));
        xrayBlocks.add(Block.getBlockFromName("crafting_table"));
        xrayBlocks.add(Block.getBlockById(61));
        xrayBlocks.add(Block.getBlockById(62));
        xrayBlocks.add(Block.getBlockFromName("torch"));
        xrayBlocks.add(Block.getBlockFromName("ladder"));
        xrayBlocks.add(Block.getBlockFromName("tnt"));
        xrayBlocks.add(Block.getBlockFromName("coal_block"));
        xrayBlocks.add(Block.getBlockFromName("iron_block"));
        xrayBlocks.add(Block.getBlockFromName("gold_block"));
        xrayBlocks.add(Block.getBlockFromName("diamond_block"));
        xrayBlocks.add(Block.getBlockFromName("emerald_block"));
        xrayBlocks.add(Block.getBlockFromName("redstone_block"));
        xrayBlocks.add(Block.getBlockFromName("lapis_block"));
        xrayBlocks.add(Block.getBlockFromName("fire"));
        xrayBlocks.add(Block.getBlockFromName("mossy_cobblestone"));
        xrayBlocks.add(Block.getBlockFromName("mob_spawner"));
        xrayBlocks.add(Block.getBlockFromName("end_portal_frame"));
        xrayBlocks.add(Block.getBlockFromName("enchanting_table"));
        xrayBlocks.add(Block.getBlockFromName("bookshelf"));
        xrayBlocks.add(Block.getBlockFromName("command_block"));
        xrayBlocks.add(Block.getBlockFromName("bone_block"));
    }


    public void onDisable() {
        mc.renderGlobal.loadRenderers();
        super.onDisable();
    }

    public void onEnable() {
        mc.renderGlobal.loadRenderers();
        super.onEnable();
    }

    @EventTarget
    public void onRender3D(Event3D e){
        for(Object o : mc.theWorld.loadedTileEntityList){
            if(o instanceof TileEntityMobSpawner){
                ESPUtil.blockESPBox(((TileEntityMobSpawner) o).getPos());
            }
            if(o instanceof TileEntityChest){
                ESPUtil.blockESPBox(((TileEntityChest) o).getPos());
            }
        }
    }

    public static boolean isXRayBlock(Block blockToCheck) {
        return xrayBlocks.contains(blockToCheck);
    }
}
