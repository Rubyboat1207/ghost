package rubyboat.ghost.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.config.Config;

@Environment(EnvType.CLIENT)
@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At("HEAD"), method = "getSlipperiness", cancellable = true)
    public void tick(CallbackInfoReturnable<Float> cir)
    {
        if(Config.getConfigValueBoolean("frozen"))
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

    @Inject(at = @At("HEAD"), method = "onEntityLand", cancellable = true)
    public void onLandedUpon(BlockView world, Entity entity, CallbackInfo ci)
    {
        if (!entity.bypassesLandingEffects() && Config.getConfigValueBoolean("bouncy")) {
            this.bounce(entity);
            ci.cancel();
        }
    }

    public void bounce(Entity entity){
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0D) {
            double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
        }
    }
}
