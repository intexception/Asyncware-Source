package com.nquantum.module.player;

import cf.nquan.util.*;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.*;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.network.play.client.C0CPacketInput;
import nig.hero.settings.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Scaffold extends Module {

    public Random rand = new Random();

    public TimerHelper timer = new TimerHelper();
    public TimerHelper timerMotion = new TimerHelper();
    public float yaw;
    public float pitch;
    public int startSlot;
    BlockPos currentBlockPos = new BlockPos(-1, -1, -1);
    public static boolean isEnabled;
    private static List<Block> invalid = Arrays.asList(new Block[] { Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava,
            Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars,
            Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore,
            Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox,
            Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore,
            Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate,
            Blocks.stone_button, Blocks.wooden_button, Blocks.lever });
    private float rotation = 999.0F;
    int itemStackSize;
    int currentSlot;
    int currentItem;
    BlockPos blockUnder = null;
    BlockPos blockBef = null;
    EnumFacing facing = null;
    public static boolean placing = false;
    public static boolean expand;

    //private BlockData blockData;

    private int slot;
    public static float progressYaw;
    public static float progressPitch;
    public boolean grounded;
    public boolean headTurned;

    public Scaffold(){
        super("Scaffold", Keyboard.KEY_B, Category.PLAYER);
    }

    private static transient List<Block> invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane,
            Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava,
            Blocks.flowing_lava, Blocks.snow_layer, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest,
            Blocks.noteblock, Blocks.jukebox, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate,
            Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button,
            Blocks.wooden_button, Blocks.lever, Blocks.crafting_table, Blocks.furnace, Blocks.stone_slab,
            Blocks.wooden_slab, Blocks.stone_slab2, Blocks.brown_mushroom, Blocks.red_mushroom, Blocks.red_flower,
            Blocks.yellow_flower, Blocks.flower_pot);


    @Override
    public void setup() {
        Asyncware.instance.settingsManager.rSetting(new Setting("Tower", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Expand", this, false));
        expand = Asyncware.instance.settingsManager.getSettingByName("Expand").getValBoolean();
    }

    public Block getBlock(BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock();
    }

    public BlockPos getExpanded(BlockPos pos) {
        float plusexpand = 1.0F;
        float minusexpand = -1.0F;
        boolean same = mc.thePlayer.motionX == mc.thePlayer.motionZ;
        boolean x = !same && this.getPlus((float)mc.thePlayer.motionX) > this.getPlus((float)mc.thePlayer.motionZ);
        boolean z = !same && this.getPlus((float)mc.thePlayer.motionZ) > this.getPlus((float)mc.thePlayer.motionX);
        pos = pos.add((double)(x ? (mc.thePlayer.motionX > 0.0D ? plusexpand : (mc.thePlayer.motionX < 0.0D ? minusexpand : 0.0F)) : 0.0F), 0.0D, (double)(z ? (mc.thePlayer.motionZ > 0.0D ? plusexpand : (mc.thePlayer.motionZ < 0.0D ? minusexpand : 0.0F)) : 0.0F));
        return pos;
    }


    public float getPlus(float f) {
        if (f < 0.0F) {
            f = f - f - f;
        }

        return f;
    }


    public static float[] getFacePos(Vec3 vec) {
        double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = vec.yCoord + 0.5
                - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] {
                Minecraft.getMinecraft().thePlayer.rotationYaw
                        + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw),
                Minecraft.getMinecraft().thePlayer.rotationPitch
                        + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }

    public static Vec3 getVec3(BlockPos blockPos) {
        return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }


    public static boolean isValid(Item item) {
        if (!(item instanceof ItemBlock)) {
            return false;
        } else {
            ItemBlock iBlock = (ItemBlock) item;
            Block block = iBlock.getBlock();
            return !invalidBlocks.contains(block);
        }
    }




    public int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && isValid(item)) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }


    @EventTarget
    public void onRenderUI(EventRenderUI e){

        Gui ui = new Gui();
        ScaledResolution sr = new ScaledResolution(mc);
        int blocks = this.getBlockCount();
        String s = blocks + "";

        GL11.glPushMatrix();
       // Asyncware.renderer.drawString(s, sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, Colors.RGB(), true);

        GL11.glTranslatef((float) sr.getScaledWidth() / 2 - 30, (float) sr.getScaledHeight() / 2 - 20, 30);



     //   Gui.drawRect(81, 31, 19, 9, new Color(79, 79, 79, 255).getRGB());
     //   Gui.drawRect(81, 10, 2, 10, new Color(79, 79, 79, 255).getRGB());

       // ui.drawGradientRect(80, 30, 20, 10, new Color(24, 24, 24, 255).getRGB(), new Color(18, 18, 18, 255).getRGB());

      //  GL11.glTranslatef((float) -sr.getScaledWidth() / 2 - 30, (float) -sr.getScaledHeight() / 2 - 20, -30);
        Asyncware.renderer.drawString(s, 42, 13.5f, Colors.RGB(), true);
        Asyncware.renderer.drawString("blocks", Asyncware.renderer.getStringWidth(s) + 44, 13.5f, -1, true);
       // ui.drawGradientRect(81, 1, 19, 9, 0xCBDD3A, 0x3BAEDA);
        GL11.glPopMatrix();


    }

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {


    }

    @EventTarget
    public void onHeadRender(EventHeadRender event) {
        if(MovementUtil.isMoving()){
            event.headYaw = 180;
            event.headPitch = 85;
        }


    }


    @EventTarget
    public void onUpdate(EventUpdate event) {

        boolean tower = Asyncware.instance.settingsManager.getSettingByName("Tower").getValBoolean();

        double oldX = mc.thePlayer.lastTickPosX;
        double oldY = mc.thePlayer.lastTickPosY;
        double oldZ = mc.thePlayer.lastTickPosZ;


//        PacketUtil.sendPacketPlayer(new C0CPacketInput());
        blockUnder = null;
        blockBef = null;
        if(mc.thePlayer.getCurrentEquippedItem() == null) {
            return;
        }
        if(mc.thePlayer.getCurrentEquippedItem().getItem() == null) {
            return;
        }
        if(!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock)) {
            return;
        }
//		if(mc.thePlayer.isSprinting() && mc.thePlayer.onGround) {
//			mc.thePlayer.motionX *= 0.7;
//			mc.thePlayer.motionZ *= 0.7;
//		}
        BlockPos under = new BlockPos(mc.thePlayer.posX + mc.thePlayer.motionX * 2, mc.thePlayer.posY - 0.01, mc.thePlayer.posZ + mc.thePlayer.motionZ * 2);
        BlockPos expand = new BlockPos(mc.thePlayer.posX + 1 + mc.thePlayer.motionX * 2, mc.thePlayer.posY - 0.01, mc.thePlayer.posZ + 1  + mc.thePlayer.motionZ * 2);

            if(getBlock(under).getMaterial() == Material.air) {
                blockUnder = new BlockPos(mc.thePlayer.posX + mc.thePlayer.motionX * 2, mc.thePlayer.posY - 0.01, mc.thePlayer.posZ + mc.thePlayer.motionZ * 2);
                for(EnumFacing facing : EnumFacing.values()) {
                    BlockPos offset = blockUnder.offset(facing);
                    if(getBlock(offset).getMaterial() != Material.air) {
                        this.facing = facing;
                        blockBef = offset;
                        break;
                    }
                }
                if(blockBef != null) {
                    float[] rots = getFacePos(getVec3(blockBef));
//				event.yaw = rots[0];
                    //  PacketUtil.sendPacketPlayer(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.cameraYaw, 90.0F, true));

                }
            }

        if(getBlock(under).getMaterial() == Material.air) {
            blockUnder = new BlockPos(mc.thePlayer.posX + mc.thePlayer.motionX * 2, mc.thePlayer.posY - 0.01, mc.thePlayer.posZ + mc.thePlayer.motionZ * 2 - 0.1f);
            for(EnumFacing facing : EnumFacing.values()) {
                BlockPos offset = blockUnder.offset(facing);
                if(getBlock(offset).getMaterial() != Material.air) {
                    this.facing = facing;
                    blockBef = offset;
                    break;
                }
            }
            if(blockBef != null) {
                float[] rots = getFacePos(getVec3(blockBef));
//				event.yaw = rots[0];
              //  PacketUtil.sendPacketPlayer(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.cameraYaw, 90.0F, true));

            }
        }
        MovingObjectPosition pos = mc.theWorld.rayTraceBlocks(getVec3(blockUnder).addVector(0.5, 0.5, 0.5),
                getVec3(blockBef).addVector(0.5, 0.5, 0.5));

        Vec3 hitVec = pos.hitVec;

        if ( mc.gameSettings.keyBindJump.isKeyDown() && !MovementUtil.isMoving() && tower) {
            MovementUtil.setMotion(0);

            mc.rightClickDelayTimer = 0;
            if (mc.thePlayer.onGround) {
                mc.rightClick();
                if (MovementUtil.isOnGround(0.76) && !MovementUtil.isOnGround(0.75) && mc.thePlayer.motionY > 0.23 && mc.thePlayer.motionY < 0.25) {
                    mc.thePlayer.motionY = Math.round(mc.thePlayer.posY) - mc.thePlayer.posY;
                    mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,  mc.thePlayer.getCurrentEquippedItem(), blockBef, pos.sideHit, hitVec);

                }
                if (MovementUtil.isOnGround(1.0E-4)) {
                    mc.thePlayer.swingItem();
                    mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,  mc.thePlayer.getCurrentEquippedItem(), blockBef, pos.sideHit, hitVec);

                    mc.thePlayer.motionY = 0.41999998688697815;
                    if (timer.hasTimeElapsed(1500)) {
                        mc.thePlayer.motionY = -0.28;
                        timer.reset();
                    }
                } else if (mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 1.0E-4 && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 1.0E-4) {
                    mc.thePlayer.motionY = 0.0;
                }
            } else if (mc.theWorld.getBlockState(blockUnder).getBlock().getMaterial().isReplaceable()) {
                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,  mc.thePlayer.getCurrentEquippedItem(), blockBef, pos.sideHit, hitVec);

                mc.thePlayer.motionY = 0.41955;
            }
        }


        placing = false;
        if(blockUnder == null) {
            return;
        }
        if(blockBef == null) {
            return;
        }
        placing = true;

        if(pos == null) {
            return;
        }

        float f = 0;
        float f1 = 0;
        float f2 = 0;
        mc.thePlayer.swingItem();
        if(mc.thePlayer.fallDistance > 1){
            mc.thePlayer.setPosition(oldX, oldY, oldZ);
        }
        mc.thePlayer.swingItem();
        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,  mc.thePlayer.getCurrentEquippedItem(), blockBef, pos.sideHit, hitVec);

        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,  mc.thePlayer.getCurrentEquippedItem(), blockBef, pos.sideHit, hitVec);


    }


}
