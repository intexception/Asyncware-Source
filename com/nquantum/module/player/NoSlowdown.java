package com.nquantum.module.player;

import cf.nquan.util.BypassUtil;
import cf.nquan.util.MovementUtil;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventPostMotionUpdate;
import com.nquantum.event.impl.EventPreMotionUpdate;
import com.nquantum.event.impl.EventReceivePacket;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.module.render.BypassXray;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowdown extends Module {

    private boolean blocking;

    public NoSlowdown(){
        super("NoSlowdown", 0, Category.MISC);
    }

    @EventTarget
    public void onPacketReceived(EventReceivePacket niggerfaggot){
        if (niggerfaggot.getPacket() instanceof C07PacketPlayerDigging)
            blocking = false;
        if (niggerfaggot.getPacket() instanceof C08PacketPlayerBlockPlacement)
            blocking = true;
    }

    @EventTarget
    public void onPre(EventPreMotionUpdate fag){

        final double niggerCuntSexFuckFart = MovementUtil.getBaseMoveSpeed();
        this.setDisplayName("No Slowdown");
        if (isHoldingSword() && blocking && mc.thePlayer.isBlocking()) {
            MovementUtil.setMotion((((((((((((((((((((((((((((((((((((((((((((((((((((((0.2))))))))))))))))))))))))))))))))))))))))))))))))))))));
            mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-.8, -.8, -.8), EnumFacing.DOWN));
            blocking = false;
        }
    }

    @EventTarget
    public void onPost(EventPostMotionUpdate niggacunt){
        if (isHoldingSword() && !blocking && mc.thePlayer.isBlocking()) {
            MovementUtil.setMotion((((((((((((((((((((((((((((((((((((((((((((((((((((((0.2))))))))))))))))))))))))))))))))))))))))))))))))))))));
            mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-.8, -.8, -.8), 255, mc.thePlayer.getCurrentEquippedItem(), 0, 0, 0));
            blocking = true;
        }
    }

    private boolean isHoldingSword() {
        return mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
    }
}
