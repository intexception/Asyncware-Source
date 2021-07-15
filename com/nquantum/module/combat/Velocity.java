package com.nquantum.module.combat;

import cf.nquan.util.Strings;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventSendPacket;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Velocity extends Module {
    public Velocity(){
        super("Velocity", 0, Category.COMBAT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
          this.setDisplayName("Velocity \u00A77" + "Cancel 0% 0%");
    }


    @EventTarget
    public void onSendPacket(EventSendPacket n){
        if(n.getPacket() instanceof S12PacketEntityVelocity){
            n.setCancelled(true);
        }


        double y = 0.01;
        double xz = 0.01;


        if (this.mc.thePlayer.hurtTime > 0 && this.mc.thePlayer.fallDistance < 3.0f) {
            
            if (this.mc.thePlayer.moveForward == 0.0f && this.mc.thePlayer.moveStrafing == 0.0f) {

                this.mc.thePlayer.motionY -= y;
                this.mc.thePlayer.motionX *= xz;
                this.mc.thePlayer.motionZ *= xz;
                this.mc.thePlayer.motionY += y;
            } else {
                this.mc.thePlayer.motionY -= y;
                this.mc.thePlayer.motionX *= (xz + 0.2);
                this.mc.thePlayer.motionZ *= (xz + 0.2);
                this.mc.thePlayer.motionY += y;
            }
        }
    }
}
