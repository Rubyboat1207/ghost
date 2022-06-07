package rubyboat.ghost.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
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

import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void setBlockStateWithoutNeighborUpdates(BlockPos pos, BlockState state);

    @Shadow public abstract void setTimeOfDay(long timeOfDay);

    @Shadow public abstract ClientChunkManager getChunkManager();

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci)
    {
        if(GhostClient.keyBinding.isPressed())
        {
            if(client.player.isCreative() || (client.player.getWorld().getBlockState(client.player.getBlockPos().add(0,-1,0)).getBlock() != Blocks.AIR || client.player.getWorld().getBlockState(client.player.getBlockPos().add(0,-2,0)).getBlock() != Blocks.AIR || client.player.getWorld().getBlockState(client.player.getBlockPos().add(0,-3,0)).getBlock() != Blocks.AIR))
            {
                BlockPos suggestedBlockpos = this.client.player.getBlockPos().add(0,-1,0);
                this.setBlockStateWithoutNeighborUpdates(suggestedBlockpos, Registry.BLOCK.get(new Identifier("minecraft", Config.getBlock())).getDefaultState());
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
                        setBlockStateWithoutNeighborUpdates(block, Blocks.AIR.getDefaultState());
                    }
                    break;
                }
            }


        }
        if(Config.time() != -1){
            this.setTimeOfDay(Config.time());
        }
    }
}