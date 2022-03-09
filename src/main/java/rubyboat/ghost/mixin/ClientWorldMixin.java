package rubyboat.ghost.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
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

    @Shadow public abstract void addEntity(int id, Entity entity);

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci)
    {
        if(GhostClient.keyBinding.isPressed())
        {
            BlockPos suggestedBlockpos = this.client.player.getBlockPos().add(0,-1,0);
            this.setBlockStateWithoutNeighborUpdates(suggestedBlockpos, Registry.BLOCK.get(new Identifier("minecraft", Config.getBlock())).getDefaultState());
        }
        if(GhostClient.spawnMob.wasPressed())
        {

        }
    }
}