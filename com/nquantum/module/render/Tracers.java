package com.nquantum.module.render;

import cf.nquan.util.misc.drawLine;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module {
    public Tracers(){
        super("Tracers", 0, Category.RENDER);
    }

    @EventTarget
    public void onRender3D(Event3D e){
        for (int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
            Entity entity = mc.theWorld.loadedEntityList.get(i);
            if (entity != null && entity != mc.thePlayer) {
                //if (entity instanceof EntityPlayer)
                final boolean bobbing = mc.gameSettings.viewBobbing;
                mc.gameSettings.viewBobbing = false;
                new drawLine(0, mc.thePlayer.getEyeHeight(), 0, entity.posX - mc.thePlayer.posX, entity.posY - mc.thePlayer.posY, entity.posZ - mc.thePlayer.posZ, 0.2f);
                //The line should not be drawn from ^ eye height, but instead from the middle of the player's camera. If you want me to do this, just remind me, cos I can't do it rn - Foggy
            }
            //but look at this
        }
    }

}
