
package com.nquantum.module.combat;

import cf.nquan.path.PathFinder;
import cf.nquan.util.Colors;
import cf.nquan.util.ESPUtil;
import cf.nquan.util.PlayerUtil;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.event.impl.EventPostMotionUpdate;
import com.nquantum.event.impl.EventPreMotionUpdate;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class TpAura extends Module {
    public TpAura() {
        super("TpAura", 0, Category.COMBAT);
    }

    private ArrayList<BlockPos> lastPath = new ArrayList<>();
    private double oldX;
    private double oldY;
    EntityPlayer target;
    private double oldZ;
    private BlockPos serverPos;
    private boolean resetPos;
    private long lastAttack = 0;
    private double animHealth = 1;
    private double width;
    private int colorPrimary;
    private int colorSecondary;

    public void teleport(BlockPos pos) {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pos.getX(), pos.getY(), pos.getZ(), true));
        serverPos = pos;
    }

    public void teleport(double x, double y, double z) {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
        serverPos = new BlockPos(x, y, z);
    }

    @Override
    public void setup() {
       this.setDisplayName("TP Aura \u00A77" + "Path");
       Asyncware.instance.settingsManager.rSetting(new Setting("Gaps Length", this, 4, 2, 10, true));
       Asyncware.instance.settingsManager.rSetting(new Setting("Max Distance", this, 20.0, 10.0, 300.0, false));
       Asyncware.instance.settingsManager.rSetting(new Setting("Multi Targets", this, 1, 1, 10, true));
       Asyncware.instance.settingsManager.rSetting(new Setting("Show RayTrace", this, true));
       Asyncware.instance.settingsManager.rSetting(new Setting("APS", this, 2, 1.0, 10, false));
       Asyncware.instance.settingsManager.rSetting(new Setting("One Hit", this, false));
    }


    public void preHit() {
        KillAura ka = new KillAura();
        long delay = (long) (1000 / Asyncware.instance.settingsManager.getSettingByName("APS").getValDouble());  // millis
        double range = Asyncware.instance.settingsManager.getSettingByName("Max Distance").getValDouble();
        int maxTargets = (int) Asyncware.instance.settingsManager.getSettingByName("Multi Targets").getValDouble();
        oldX = mc.thePlayer.posX;
        oldY = mc.thePlayer.posY;
        oldZ = mc.thePlayer.posZ;
        serverPos = mc.thePlayer.getPosition();
        if (System.currentTimeMillis() - lastAttack >= delay) {
            if (maxTargets == 1) {
                target = (EntityPlayer) ka.target;
                if (target != null) {
                    if (canAttack(target)) {
                        lastPath.clear();
                        reachAndAttackPlayer(serverPos, target);
                    }
                }
            }

            else {
                Set<EntityPlayer> targets = PlayerUtil.getClosests(range, maxTargets);
                boolean cleared = false;
                for (EntityPlayer target : targets) {
                    if (target != null) {
                        if (canAttack(target)) {
                            if (! cleared) {
                                lastPath.clear();
                                cleared = true;
                            }
                            reachAndAttackPlayer(serverPos, target);
                        }
                    }
                }
            }
        }
    }

    public void postHit() {
        if (resetPos) {
            double gap = Asyncware.instance.settingsManager.getSettingByName("Gaps Length").getValDouble();
            BlockPos latestBlockRunned = serverPos;
            final BlockPos[] path = PathFinder.findPath(latestBlockRunned, new BlockPos(oldX, oldY+1, oldZ));
            for (BlockPos pos : path) {
                if (cf.nquan.path.PathFinder.distanceBetween(latestBlockRunned, pos) >= gap) {
                    teleport(pos);
                    latestBlockRunned = pos;
                }
            }
            teleport(oldX, oldY, oldZ);
            resetPos = false;
        }
    }

    private void reachAndAttackPlayer(BlockPos from, EntityPlayer target) {
        double gap = Asyncware.instance.settingsManager.getSettingByName("Gaps Length").getValDouble();
        final BlockPos[] path = PathFinder.findPath(from, target.getPosition().add(0, 1, 0));
        BlockPos latestBlockRunned = from;
        for (int i = 0; i < path.length; i++)
        {
            if (PathFinder.distanceBetween(latestBlockRunned, path[i]) >= gap && PathFinder.distanceBetween(serverPos, target.getPosition()) > 3) {
                teleport(path[i]);
                lastPath.add(path[i]);
                latestBlockRunned = path[i];
            }
        }
        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer, target);
        lastAttack = System.currentTimeMillis();
        resetPos = true;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (Asyncware.instance.settingsManager.getSettingByName("One Hit").getValBoolean()) {
            preHit();
            postHit();
            toggle();
        }
    }

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {
        preHit();
    }

    @EventTarget
    public void onPost(EventPostMotionUpdate event) {
        postHit();
    }


    @EventTarget
    public void onRenderUI(EventRenderUI e){


        if (!( target== null)  && Asyncware.instance.moduleManager.getModuleByName("KillAura").isToggled() && Asyncware.instance.moduleManager.getModuleByName("TargetHUD").isToggled()) {
            colorPrimary = Colors.Astolfo(200, 0.7f, 0.5f);
            colorSecondary = Colors.Astolfo(200, 1.0f, 0.5f);

            GL11.glPushMatrix();
            width = 140 - 32.5;
            GL11.glTranslated(GuiScreen.width / 2 - -120,  GuiScreen.height / 2 + 20, GuiScreen.width / 2);

            Gui.drawRect(-22.5f, 0, 128 - 3.5f, 50,  new Color(24, 24, 24, 181).getRGB());

            GL11.glTranslatef(-22.0f, -2.2f, 0);

            mc.fontRendererObj.drawString(target.getName(), 30, 8, -1, true);

            // \u2764
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glTranslatef(-15, -15, 0);
            mc.fontRendererObj.drawString( Math.round(target.getHealth()) + "", 30, 25, Colors.Astolfo(200, 1.0f, 0.5f), true);
            mc.fontRendererObj.drawString(  "\u2764", mc.fontRendererObj.getStringWidth(Math.round(target.getHealth()) + "") + 32, 25, Colors.Astolfo(200, 1.0f, 0.5f), true);

            GL11.glTranslatef(15, 15, 0);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            mc.fontRendererObj.drawString( "", 30, 25, -1, true);

            GuiInventory.drawEntityOnScreen(15, 47, 20, 2, 2, target);

            animHealth += ((target.getHealth() - animHealth) / 32) * 0.7;
            if (animHealth < 0 || animHealth > target.getMaxHealth()) {
                animHealth = target.getHealth();
            }
            else {
                GL11.glTranslatef(30, 0, 0);
                Gui.drawRect(20f, 40.5f, (int)( width), 48.5f, colorPrimary);
                Gui.drawRect(0f, 40.5f, (int) ((animHealth / target.getMaxHealth()) * width), 48.5f,  colorSecondary);
            }
            GL11.glScalef(2f,2f,2f);
            //Asyncware.renderer1.drawString(target.getHealth() + "\u2764", 2, 2,Colors.Astolfo(100, 1.0f, 0.5f), true);
            GL11.glPopMatrix();
            //  Gui.drawRect(350.0D, 10.0D, 120.0D, 170.0D, new Color(9, 19, 34, 167).getRGB());

        }



    }


    @EventTarget
    public void onRender3D(Event3D event) {
        if ((System.currentTimeMillis() - lastAttack <= 500) && Asyncware.instance.settingsManager.getSettingByName("Show RayTrace").getValBoolean()) {
            for (BlockPos pos : lastPath) {
                ESPUtil.blockESPBox(pos);
            }
        }
    }

    public boolean canAttack(EntityPlayer target) {
        return target != mc.thePlayer && (!target.capabilities.isCreativeMode) && (!target.isDead) && target.isEntityAlive() && target.getHealth() > 0;
    }

}

