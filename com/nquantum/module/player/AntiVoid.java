package com.nquantum.module.player;

import cf.nquan.util.PacketUtil;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventPreMotionUpdate;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.Random;

public class AntiVoid extends Module {
    public AntiVoid(){
        super("AntiVoid", 0, Category.PLAYER);
    }

    private double lastGroundY;

    public boolean isBlockUnder() {
        for (int offset = 0; offset < mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); offset += 2) {
            AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);

            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).isEmpty())
                return true;
        }
        return false;
    }

    @EventTarget
    public void onPre(EventPreMotionUpdate nigga){
        boolean blockUnderneath = false;
        for (int i = 0; i < mc.thePlayer.posY + 2; i++) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)
                continue;
            blockUnderneath = true;
        }

        if (blockUnderneath)
            return;

        if (mc.thePlayer.fallDistance < 2)
            return;

        if (!mc.thePlayer.onGround && !mc.thePlayer.isCollidedVertically) {
            mc.thePlayer.motionY += 0.12;
        }

    }
}
