package rubyboat.ghost.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.config.Config;

import java.util.logging.Logger;

@Environment(EnvType.CLIENT)
@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At("HEAD"), method = "getSlipperiness", cancellable = true)
    public void tick(CallbackInfoReturnable<Float> cir)
    {
        if(Config.isSlippery())
        {
            if(MinecraftClient.getInstance().player != null)
            {
                if(MinecraftClient.getInstance().player.getVehicle() == null)
                {
                    cir.setReturnValue(1.001F);
                }
                if(MinecraftClient.getInstance().player.getVehicle() != null){
                    if(MinecraftClient.getInstance().player.getVehicle().getType() == EntityType.PIG){
                        cir.setReturnValue(1.001F);
                    }
                }

            }else
            {
                cir.setReturnValue(1.001F);
            }

        }
    }
}
