package cf.nquan.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;

public class GraphUtil {
    private static ArrayList<Double> speeds = new ArrayList<>(Collections.nCopies(50, 0.0));
    public static int maxHeight = 5;

    public static void speedGraph(){

        int posX = 2;
        int posY = 160;
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);

        GL11.glBegin(GL11.GL_QUADS);
        GlStateManager.color(4 / 255.0f, 7 / 255.0f, 32 / 255.0f, 153 / 255.0f);
        GL11.glVertex2f(1,posY-maxHeight-2);
        GL11.glVertex2f(1, posY);
        GL11.glVertex2f(2+speeds.size(), posY);
        GL11.glVertex2f(2+speeds.size(), posY-maxHeight-2);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GlStateManager.color(1f, 1f, 1f, 1f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        float lastPoint = posY;
        for (double speed : speeds) {
            float pos = MathHelper.clamp_float((float) (posY - speed*2), 160 - maxHeight, 160);
            GL11.glVertex2f(posX, lastPoint);
            GL11.glVertex2f(posX, pos);
            lastPoint = pos;
            posX++;
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }



}
