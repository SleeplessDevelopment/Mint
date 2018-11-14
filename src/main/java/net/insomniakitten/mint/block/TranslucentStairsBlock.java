package net.insomniakitten.mint.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class TranslucentStairsBlock extends SimpleStairsBlock {
    public TranslucentStairsBlock(final Block material) {
        super(material);
    }

    @Override
    public int getOpacity(final IBlockState state, final IBlockReader reader, final BlockPos position) {
        return Blocks.WATER.getDefaultState().getOpacity(reader, position);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @Deprecated
    public boolean isSideInvisible(final IBlockState state, final IBlockState other, final EnumFacing face) {
        if (this != other.getBlock()) {
            return false;
        }

        final EnumFacing facing = state.get(BlockStairs.FACING);
        final EnumFacing otherFacing = other.get(BlockStairs.FACING);

        if (face == EnumFacing.UP) {
            final Half half = other.get(BlockStairs.HALF);

            return Half.TOP != half || otherFacing == facing;
        }

        if (otherFacing == facing.getOpposite()) {
            return face == facing;
        }

        if (face.getAxis().isHorizontal()) {
            return otherFacing == face.rotateY() || otherFacing == face.rotateYCCW();
        }

        return false;
    }
}
