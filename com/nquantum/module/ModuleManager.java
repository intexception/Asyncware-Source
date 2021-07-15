package com.nquantum.module;

import com.nquantum.module.combat.*;
import com.nquantum.module.config.Craftplay;
import com.nquantum.module.exploit.*;
import com.nquantum.module.misc.*;
import com.nquantum.module.movement.*;
import com.nquantum.module.player.*;
import com.nquantum.module.render.*;
import com.sun.javafx.collections.VetoableListDecorator;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        // COMBAT
        modules.add(new KillAura());

        modules.add(new AutoArmor());
        modules.add(new Criticals());
        modules.add(new Reach());
        modules.add(new Velocity());
        modules.add(new TpAura());

        // MOVEMENT
        modules.add(new Sprint());
        modules.add(new Step());
        //modules.add(new Flight());
        modules.add(new Fly());
        modules.add(new LongJump());
        modules.add(new Speed());
        modules.add(new Phase());
        modules.add(new CustomSpeed());
        modules.add(new CustomLongjump());
        modules.add(new JumpAir());
        modules.add(new Scaffold());
        modules.add(new NoSlowdown());

        modules.add(new Nametags());
        modules.add(new SpinBot());
        // RENDER
        modules.add(new Fullbright());
        modules.add(new ClickGUI());
        modules.add(new Trajectories());
        modules.add(new ArreyList());
        modules.add(new BetterChat());
        modules.add(new Jello());
        modules.add(new HUD());
        modules.add(new SwordAnimation());
        modules.add(new ClickUI());
        modules.add(new ESP());
        modules.add(new Tracers());
        modules.add(new TargetHUD());
        modules.add(new BypassXray());
        modules.add(new InfoHUD());
        modules.add(new TargetStrafe());
        modules.add(new Glint());
        modules.add(new HVHEngine());
        modules.add(new Radar());
        modules.add(new BreadCrumbs());
       // modules.add(new MotionBlur());
        //modules.add(new BedTimer());

        // PLAYER
        modules.add(new NoFall());
        modules.add(new Jesus());
        modules.add(new InstaBow());
        modules.add(new InvMove());
        modules.add(new Disabler());
        modules.add(new ChestStealer());
        modules.add(new SafeWalk());
        modules.add(new AntiVoid());
        modules.add(new AutoSoup());
        modules.add(new FastEat());
        modules.add(new FastMine());
        modules.add(new ServerCrasher());
        modules.add(new ThunderDetector());
        modules.add(new ClickTP());


        // MISC
        modules.add(new PacketLogger());
        modules.add(new Timer());
        modules.add(new Spammer());
        modules.add(new BedBreaker());
        modules.add(new NoScoreboard());
        modules.add(new NoArmorStandBB());
        modules.add(new Blink());
        // NONE

        //CONFIG
        modules.add(new Craftplay());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Module> getModules(Category category) {
        List<Module> modules = new ArrayList<Module>();
        for (int i = 0; i < this.modules.size(); i++) {
            Module module = this.modules.get(i);
            if (module.getCategory().equals(category))
                modules.add(module);
        }

        return modules;
    }
}


