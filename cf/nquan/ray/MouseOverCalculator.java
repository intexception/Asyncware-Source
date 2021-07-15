package cf.nquan.ray;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class MouseOverCalculator {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static BlockPos getPointedBlock(float partialTicks, float maxDistance) {
     //   mc.entityRenderer.destroyHoomans(partialTicks, maxDistance);
        return mc.objectMouseOver.getBlockPos();
    }
}