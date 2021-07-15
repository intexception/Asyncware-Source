package com.nquantum.module.combat;

import cf.nquan.util.Timer;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventGetBlockReach;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Reach extends Module {
    public Reach(){
        super("Reach", 0, Category.COMBAT);
        Asyncware.instance.settingsManager.rSetting(new Setting("Range", this, 3.0D, 3.0D, 120.0D, false));
    }
    @EventTarget
    public void onUpdate(EventUpdate nigger){
        Timer timer = new Timer();


            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityLivingBase) {
                    final EntityLivingBase e = (EntityLivingBase) entity;
                    if (e.isDead || e == mc.thePlayer )
                        continue;


                       // ChatUtil.sendMessagePrefix(entity.getName() + " - is on XYZ: " + Math.round(entity.posX) + ", " + Math.round(entity.posY) + ", " + Math.round(entity.posZ) + " ,");


                   // PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, true));

                  //  PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, false));
                }
            }


    }

    @EventTarget
    public void onGetBlockReach(EventGetBlockReach e){
        float reach = (float)Asyncware.instance.settingsManager.getSettingByName("Range").getValDouble();
        EventGetBlockReach event = (EventGetBlockReach) e;

        event.setCancelled(true);
        event.reach = (float) reach;
    }
}
