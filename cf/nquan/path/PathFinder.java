package cf.nquan.path;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PathFinder {
    public static BlockPos moveBlockToFace(BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.DOWN) {
            return pos.add(0, -1, 0);
        }
        if (face == EnumFacing.UP) {
            return pos.add(0, 1, 0);
        }
        if (face == EnumFacing.EAST) {
            return pos.add(1, 0, 0);
        }
        if (face == EnumFacing.WEST) {
            return pos.add(-1, 0, 0);
        }
        if (face == EnumFacing.NORTH) {
            return pos.add(0, 0, -1);
        }
        if (face == EnumFacing.SOUTH) {
            return pos.add(0, 0, 1);
        }
        return null;
    }

    private static BlockPos bypassStambles(final BlockPos location, boolean upToStambles) {
        BlockPos newPos = location;
        int addY = upToStambles ? 1 : -1;
        while (! isPosSecure(newPos)) {
            newPos = newPos.add(0, addY, 0);
        }
        return newPos;
    }

    public static double distanceBetween(final BlockPos start, final BlockPos end) {
        double diffX = start.getX() - end.getX();
        double diffY = start.getY() - end.getY();
        double diffZ = start.getZ() - end.getZ();
        return Math.sqrt(diffX*diffX + diffY*diffY + diffZ*diffZ);
    }

    public static BlockPos[] findPath(final BlockPos start, final BlockPos end) {
        double distance = distanceBetween(start, end);
        int teleports = (int) Math.ceil(distance);
        BlockPos[] path = new BlockPos[teleports];
        final double addX = ((double)end.getX() - (double)start.getX()) / (double) teleports;
        final double addY = ((double)end.getY() - (double)start.getY()) / (double) teleports;
        final double addZ = ((double)end.getZ() - (double)start.getZ()) / (double) teleports;
        for (int i = 0; i < teleports; i++) {
            path[i] = bypassStambles(start.add((i+1) * addX, (i+1) * addY, (i+1) * addZ), /*start.getY() <= end.getY()*/true);
        }
        return path;
    }



    public static boolean isPosSecure(BlockPos pos) {
        WorldClient world = Minecraft.getMinecraft().theWorld;
        return world.getBlockState(pos).getBlock().getMaterial() == Material.air && world.getBlockState(pos.add(0, 1, 0)).getBlock().getMaterial() == Material.air;
    }

}