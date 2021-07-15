package cf.nquan.util;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {
    private static final ArrayList depth = new ArrayList();


    public static void setupLineSmooth(){
        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL13.GL_MULTISAMPLE);
        GL11.glEnable(GL13.GL_SAMPLE_ALPHA_TO_COVERAGE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
    }


    public static void drawRectSized(float x, float y, float width, float height, int color) {
        drawRect(x, y, x + width, y + height, color);
    }
    public static double lerp(double v0, double v1, double t) {
        return (1.0 - t) * v0 + t * v1;
    }
    public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1,
                                         final int col1, final int col2) {
        drawRect(x, y, x2, y2, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glHint(3154, 4354);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
    }


    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = ((float) 1 / 255) * c.getRed();
        float g = ((float) 1 / 255) * c.getGreen();
        float b = ((float) 1 / 255) * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }


    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        //GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawInternalRoundedRect3( float x,  float y, float x1,  float y1,  int borderC,  int insideC) {
        enableGL2D();
        x *= 2.0F;
        y *= 2.0F;
        x1 *= 2.0F;
        y1 *= 2.0F;
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawVLine(x, y + 4.0F, y1 - 5.0F, borderC);
        drawVLine(x1 - 1.0F, y + 4.0F, y1 - 5.0F, borderC);
        drawHLine(x + 5.0F, x1 - 6.0F, y, borderC);
      	   /* drawHLine(x + 3.0F, x1 - 4.0F, y1, 0xaaafafaf);
      	    drawHLine(x + 4.0F, x1 - 5.0F, y1+0.5f, 0x80cfcfcf);
      	    drawHLine(x + 6.0F, x1 - 7.0F, y1+1, 0x20e7e7e7);*/
        //GlStateManager.enableAlpha();
        //TOP RIGHT
        drawHLine(x1 - 4, x1 - 4.0F, y, insideC);
        drawHLine(x1 - 1, x1 - 1.0F, y + 3, insideC);

        //TOP LEFT
        drawHLine(x + 3.0F, x + 3.0F, y, insideC);
        drawHLine(x + 0.0F, x + 0.0F, y + 3, insideC);

        //BOTTOM LEFT
        drawHLine(x + 0.0F, x + 0.0F, y1 - 4, insideC);
        drawHLine(x + 2.0F, x + 2.0F, y1 - 0, insideC);

        //BOTTOM RIGHT
        drawHLine(x1 - 1, x1 - 1.0F, y1 - 4, insideC);
        drawHLine(x1 - 4, x1 - 4.0F, y1 - 1, insideC);

        drawHLine(x + 5.0F, x1 - 6.0F, y1 - 1.0F, borderC);
        //drawHLine(x + 2.0F, x + 1.0F, y + 1.0F, borderC);
        //drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
        // drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
        // drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);
        Gui.drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, borderC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }
    public static void drawInternalRoundedRect2( float x,  float y, float x1,  float y1,  int borderC,  int insideC) {
        enableGL2D();
        x *= 2.0F;
        y *= 2.0F;
        x1 *= 2.0F;
        y1 *= 2.0F;
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawVLine(x, y + 5.5F, y1 - 6.5F, borderC);
        drawVLine(x + 2, y + 3F, y1 - 4F, borderC);

        //drawVLine(x1, y + 5.5F, y1 - 6.5F, borderC);
        drawVLine(x1 - 3, y + 3F, y1 - 4F, borderC);

        drawVLine(x1 - 1.0F, y + 5.5F, y1 - 6.5F, borderC);

        drawHLine(x + 6.5F, x1 - 7.5F, y, borderC);
        drawHLine(x + 4F, x1 - 4.5F, y + 2, borderC);
      	   /* drawHLine(x + 3.0F, x1 - 4.0F, y1, 0xaaafafaf);
      	    drawHLine(x + 4.0F, x1 - 5.0F, y1+0.5f, 0x80cfcfcf);
      	    drawHLine(x + 6.0F, x1 - 7.0F, y1+1, 0x20e7e7e7);*/
        //GlStateManager.enableAlpha();
        //TOP RIGHT
        drawHLine(x1 - 5.5f, x1 - 6.5F, y, insideC);
        drawHLine(x1 - 1, x1 - 1.0F, y + 5.5f, insideC);

        //TOP LEFT
        drawHLine(x + 4.5F, x + 5.5F, y, insideC);
        drawVLine(x + 0.0F, y + 3.5f, y + 6.5f, insideC);

        //BOTTOM LEFT
        //    drawVLine(x + 1.5F, y1 - 4f, y1 - 7.5f, borderC);
        //   drawHLine(x + 4.5F, x + 5.5F, y1  - 2.5f, borderC);

        drawVLine(x + 0F, y1 - 7.5f, y1 - 5f, insideC);
        drawVLine(x + 5.5F, y1 - 2.5f, y1 - 0.0f, insideC);

        //BOTTOM RIGHT
        drawHLine(x1 - 1, x1 - 1.0F, y1 - 6.5f, insideC);
        drawHLine(x1 - 5.5f, x1 - 6.5F, y1 - 1, insideC);

        drawHLine(x + 4F, x1 - 4.5F, y1 - 2.5F, borderC);
        drawHLine(x + 6.5F, x1 - 8.0F, y1 - 1.0F, borderC);

        //drawHLine(x + 2.0F, x + 1.0F, y + 1.0F, borderC);
        //drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
        // drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
        // drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);


        Gui.drawRect(x + 3F, y + 3F, x1 - 3F, y1 - 3F, borderC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }

    public static void drawVLine(double d, float y, float x1, int y1)
    {
        if (x1 < y)
        {
            float var5 = y;
            y = x1;
            x1 = var5;
        }
        Gui.drawRect(d, y + 1.0F, d + 1.0F, x1, y1);
    }

    public static void drawVLine(final float x, final float y, final float x1, final float y1, final float width, final int color) {
        if (width <= 0.0f) {
            return;
        }
        GL11.glPushMatrix();
        pre3D();
        final float var11 = (color >> 24 & 0xFF) / 255.0f;
        final float var12 = (color >> 16 & 0xFF) / 255.0f;
        final float var13 = (color >> 8 & 0xFF) / 255.0f;
        final float var14 = (color & 0xFF) / 255.0f;
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        final int shade = GL11.glGetInteger(2900);
        GlStateManager.shadeModel(7425);
        GL11.glColor4f(var12, var13, var14, var11);
        final float line = GL11.glGetFloat(2849);
        GL11.glLineWidth(width);
        GL11.glBegin(3);
        GL11.glVertex3d((double)x, (double)y, 0.0);
        GL11.glVertex3d((double)x1, (double)y1, 0.0);
        GL11.glEnd();
        GlStateManager.shadeModel(shade);
        GL11.glLineWidth(line);
        post3D();
        GL11.glPopMatrix();
    }


    public static void drawInternalRoundedRect1( float x,  float y, float x1,  float y1,  int borderC,  int insideC) {
        enableGL2D();
        x *= 2.0F;
        y *= 2.0F;
        x1 *= 2.0F;
        y1 *= 2.0F;
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawVLine(x, y + 4.0F, y1 - 5.0F, borderC);
        drawVLine(x1 - 1.0F, y + 4.0F, y1 - 5.0F, borderC);
        drawHLine(x + 5.0F, x1 - 6.0F, y, borderC);
   	   /* drawHLine(x + 3.0F, x1 - 4.0F, y1, 0xaaafafaf);
   	    drawHLine(x + 4.0F, x1 - 5.0F, y1+0.5f, 0x80cfcfcf);
   	    drawHLine(x + 6.0F, x1 - 7.0F, y1+1, 0x20e7e7e7);*/
        //GlStateManager.enableAlpha();
        //TOP RIGHT
        drawHLine(x1 - 4, x1 - 4.0F, y, insideC);
        drawHLine(x1 - 1, x1 - 1.0F, y + 3, insideC);

        //TOP LEFT
        drawHLine(x + 3.0F, x + 3.0F, y, insideC);
        drawHLine(x + 0.0F, x + 0.0F, y + 3, insideC);

        //BOTTOM LEFT
        drawHLine(x + 0.0F, x + 0.0F, y1 - 4, insideC);
        drawHLine(x + 2.0F, x + 2.0F, y1 - 0, insideC);

        //BOTTOM RIGHT
        drawHLine(x1 - 1, x1 - 1.0F, y1 - 4, insideC);
        drawHLine(x1 - 4, x1 - 4.0F, y1 - 1, insideC);

        drawHLine(x + 5.0F, x1 - 6.0F, y1 - 1.0F, borderC);
        //drawHLine(x + 2.0F, x + 1.0F, y + 1.0F, borderC);
        //drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
        // drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
        // drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);
        Gui.drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, borderC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }
    public static void pre() {
        if (depth.isEmpty()) {
            GL11.glClearDepth(1.0D);
            GL11.glClear(256);
        }

    }

    public static void mask() {
        depth.add(0, GL11.glGetInteger(2932));
        GL11.glEnable(6145);
        GL11.glDepthMask(true);
        GL11.glDepthFunc(513);
        GL11.glColorMask(false, false, false, true);
    }

    public static void render() {
        render(514);
    }

    public static void render(int gl) {
        GL11.glDepthFunc(gl);
        GL11.glColorMask(true, true, true, true);
    }

    public static void post() {
        GL11.glDepthFunc((Integer)depth.get(0));
        depth.remove(0);
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }
    public static void drawRoundedShadow( float x,  float y, float x1,  float y1) {
        enableGL2D();
        x *= 2.0F;
        y *= 2.0F;
        x1 *= 2.0F;
        y1 *= 2.0F;
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        //drawInternalRoundedRect2(x - 01.5F, y-01.6F, x1+1.6F, y1+1.5F, 0xff606060, 0x90606060);
        drawInternalRoundedRect2(x - 02.5F, y-01.6F, x1+2.6F, y1+2.5F, 0x30505050, 0x10505050);
        drawInternalRoundedRect2(x - 01.5F, y-01.6F, x1+1.6F, y1+1.5F, 0x50505050, 0x30606060);
        drawInternalRoundedRect2(x - 0.5F, y-0.6F, x1+0.6F, y1+0.5F, 0x60505050, 0x50505050);

        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }
    public static void drawHLine(double d, double e, final float x1, final int y1) {
        if (e < d) {
            final float var5 = (float) d;
            d = e;
            e = var5;
        }
        Gui.drawRect(d, x1, e + 1.0f, x1 + 1.0f, y1);
    }

    public static void drawRoundedRectWithShadow( float x,  float y, float x1,  float y1,  int borderC,  int insideC) {
        enableGL2D();
        x *= 2.0F;
        y *= 2.0F;
        x1 *= 2.0F;
        y1 *= 2.0F;
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        //drawInternalRoundedRect2(x - 01.5F, y-01.6F, x1+1.6F, y1+1.5F, 0xff606060, 0x90606060);
        drawInternalRoundedRect2(x - 02.5F, y-01.6F, x1+2.6F, y1+2.5F, new Color(23, 23, 23, 100).getRGB(), 0x10505050);
        drawInternalRoundedRect2(x - 01.5F, y-01.6F, x1+1.6F, y1+1.5F, new Color(23, 23, 23, 100).getRGB(), 0x30606060);
        drawInternalRoundedRect2(x - 0.5F, y-0.6F, x1+0.6F, y1+0.5F, new Color(23, 23, 23, 100).getRGB(), 0x50505050);
        drawVLine(x, y + 2.0F, y1 - 3.0F, borderC);
        drawVLine(x1 - 1.0F, y + 2.0F, y1 - 3.0F, borderC);
        drawHLine(x + 3.0F, x1 - 4.0F, y, borderC);
        // GlStateManager.enableBlend();
        //  GlStateManager.disableAlpha();
   	   /* drawHLine(x + 3.0F, x1 - 4.0F, y1, 0xaaafafaf);
   	    drawHLine(x + 4.0F, x1 - 5.0F, y1+0.5f, 0x80cfcfcf);
   	    drawHLine(x + 6.0F, x1 - 7.0F, y1+1, 0x20e7e7e7);*/

        //GlStateManager.enableAlpha();
        //TOP RIGHT
        drawHLine(x1 - 3, x1 - 3.0F, y, insideC);
        drawHLine(x1 - 1, x1 - 1.0F, y + 2, insideC);

        //TOP LEFT
        drawHLine(x + 2.0F, x + 2.0F, y, insideC);
        drawHLine(x + 0.0F, x + 0.0F, y + 2, insideC);

        //BOTTOM LEFT
        drawHLine(x + 0.0F, x + 0.0F, y1 - 3, insideC);
        drawHLine(x + 2.0F, x + 2.0F, y1 - 1, insideC);

        //BOTTOM RIGHT
        drawHLine(x1 - 1, x1 - 1.0F, y1 - 3, insideC);
        drawHLine(x1 - 3, x1 - 3.0F, y1 - 1, insideC);

        drawHLine(x + 3.0F, x1 - 4.0F, y1 - 1.0F, borderC);
        //drawHLine(x + 2.0F, x + 1.0F, y + 1.0F, borderC);
        //drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
        // drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
        // drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);
        Gui.drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, borderC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }
    public static void renderRoundedQuad(Vec3d from, Vec3d to, int rad, Color col) {
        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(col.getRed() / 255F, col.getGreen() / 255F, col.getBlue() / 255F, col.getAlpha() / 255F);
        GL11.glBegin(GL11.GL_POLYGON);
        {
            int initial = -90;
            double[][] map = new double[][]{
                    new double[]{to.x,to.y},
                    new double[]{to.x,from.y},
                    new double[]{from.x,from.y},
                    new double[]{from.x,to.y}
            };
            for(int i = 0;i<4;i++) {
                double[] current = map[i];
                initial += 90;
                for(int r = initial;r<(360/4+initial);r++) {
                    double rad1 = Math.toRadians(r);
                    double sin = Math.sin(rad1)*rad;
                    double cos = Math.cos(rad1)*rad;
                    GL11.glVertex2d(current[0]+sin,current[1]+cos);
                }
            }
        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }

    public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(770, 771);
        // GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(red, green, blue, alpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(770, 771);
        // GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(770, 771);
        // GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(770, 771);
        // GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawSolidEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(770, 771);
        // GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(770, 771);
        // GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
//		GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL_BLEND);
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight() - 0.2, 0.0D);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL_BLEND);
//		GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        int[] colors = new int[] { 10, 0, 30, 15 };
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

//		worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
//		worldRenderer.pos(-259, 102, 19).endVertex();
//		worldRenderer.pos(-259, 104, 17).endVertex();
//		worldRenderer.pos(-257, 102, 19).endVertex();
//		worldRenderer.pos(-257, 104, 19).endVertex();
//		tessellator.draw();

        worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        tessellator.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        tessellator.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
    }
}

