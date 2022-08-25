package rubyboat.ghost.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rubyboat.ghost.client.GhostClient;
import rubyboat.ghost.config.Config;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void setTimeOfDay(long timeOfDay);

    @Shadow public abstract ClientChunkManager getChunkManager();

    @Shadow public abstract boolean setBlockState(BlockPos pos, BlockState state, int flags, int maxUpdateDepth);

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci)
    {
        if(GhostClient.keyBinding.isPressed())
        {
            if(client.player.isCreative() || (client.player.getWorld().getBlockState(client.player.getBlockPos().add(0,-1,0)).getBlock() != Blocks.AIR || client.player.getWorld().getBlockState(client.player.getBlockPos().add(0,-2,0)).getBlock() != Blocks.AIR || client.player.getWorld().getBlockState(client.player.getBlockPos().add(0,-3,0)).getBlock() != Blocks.AIR))
            {
                BlockPos suggestedBlockpos = this.client.player.getBlockPos().add(0,-1,0);
                this.setBlockState(suggestedBlockpos, Registry.BLOCK.get(new Identifier("minecraft", Config.getBlock())).getDefaultState(), 0, 0);
            }
        }
        if(Config.isAntfarm())
        {
            BlockPos pos = this.client.player.getBlockPos().add(10,0,0);
            for(BlockPos blockpos : BlockPos.iterate(pos.add(-10,0,5), pos.add(0,0,-2)))
            {
                if(((ClientWorld)(Object)this).getBlockState(blockpos).getBlock() != Blocks.AIR)
                {
                    for(BlockPos block : BlockPos.iterate(pos.add(-9,-10,-20), pos.add(10,10,20)))
                    {
                        setBlockState(block, Blocks.AIR.getDefaultState(), 0, 0);
                    }
                    break;
                }
            }


        }
    }

}