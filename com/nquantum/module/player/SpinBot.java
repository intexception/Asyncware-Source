package com.nquantum.module.player;

import cf.nquan.util.Timer;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.*;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.module.movement.Fly;
import sun.security.provider.ConfigFile;

import java.util.Random;

public class SpinBot extends Module {

    Timer t = new Timer();
    private Random rn = new Random();
    public SpinBot(){
        super("SpinBot", 0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
    }
    @EventTarget
    public void onHeadRender(EventHeadRender nig){
        nig.headPitch = 90.0f;
        if(this.t.hasTimeElapsed(4L, true)){
            Random r = new Random();
            float min = 90.0f;
            float max = 175.0f;
            float yaw = min + r.nextFloat() * (max - min);

            nig.headYaw = yaw;

        }



    }

}
