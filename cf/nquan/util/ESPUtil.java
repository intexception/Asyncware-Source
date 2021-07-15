package cf.nquan.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ESPUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        // GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    public static void blockESPBox(BlockPos blockPos)
    {
        double x =
                blockPos.getX()
                        - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double y =
                blockPos.getY()
                        - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double z =
                blockPos.getZ()
                        - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glColor4d(86, 66, 243, 0.15F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        //drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glColor4d(1, 1, 1, 0.5F);
        RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z,
                x + 1.0, y + 1.0, z + 0.9));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }
    public static void drawCircleOnEntityFade(EntityLivingBase entity, float partialTicks) {
        GL11.glPushMatrix();
        GlStateManager.disableTexture2D();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_LIGHTING);
        final double x = interpolate(entity.posX, entity.lastTickPosX, partialTicks) - mc.getRenderManager().getRenderPosX();
        final double y = interpolate(entity.posY, entity.lastTickPosY, partialTicks) - mc.getRenderManager().getRenderPosY();
        final double z = interpolate(entity.posZ, entity.lastTickPosZ, partialTicks) - mc.getRenderManager().getRenderPosZ();
        GL11.glLineWidth(1.0f);

        final ArrayList<Vec3> posArrayList = new ArrayList<>();
        float height = entity.getEyeHeight() / 1.5f + (MathHelper.sin(((float) entity.ticksExisted + partialTicks) / 15F) * 0.8f) + 0.1f;
        for (float rotation = 0; rotation < (Math.PI * 2.0) + (Math.PI * 2.0f / 100f); rotation += Math.PI * 2.0f / 100f) {
            final Vec3 pos = new Vec3(entity.width * Math.cos(rotation) + x, y + height, entity.width * Math.sin(rotation) + z);
            posArrayList.add(pos);
        }

        GL11.glEnable(GL11.GL_LINE_STIPPLE);
        GL11.glLineStipple(GL11.GL_LINE_BIT, (short) 0XFFFF);

        GL11.glBegin(GL11.GL_LINE_STRIP);

        int p = 0;
        for (Vec3 pos : posArrayList) {
            p += Math.PI * 2.0f;
            glColor(-1);
            //glColor(Colors.RGBX(10, 0.5f, 1.0f, p * 100));
            GL11.glVertex3d(pos.xCoord, pos.yCoord, pos.zCoord);
        }

        GL11.glEnd();

        GL11.glDisable(GL11.GL_LINE_STIPPLE);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GlStateManager.enableTexture2D();
        GL11.glLineWidth(1.0f);
        GL11.glPopMatrix();
    }

    public static void glColor(int color) {
        GlStateManager.color((float) (color >> 16 & 255) / 255F, (float) (color >> 8 & 255) / 255F, (float) (color & 255) / 255F, (float) (color >> 24 & 255) / 255F);
    }

    public static int getRainbowText(int x, int y, float saturation) {
        final long l = System.currentTimeMillis() - (x * 10L - y * 10L);
        return java.awt.Color.HSBtoRGB(l % (int)6000.0F / 6000.0F, saturation, 1f);
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }
}
