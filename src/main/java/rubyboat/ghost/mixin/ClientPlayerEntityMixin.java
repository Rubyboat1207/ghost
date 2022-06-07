package rubyboat.ghost.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.client.GhostClient;
import rubyboat.ghost.config.Config;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "tickMovement", at = @At("RETURN"))
    public void returntick(CallbackInfo ci) {
        if(Config.isAntfarm()) {
            ((ClientPlayerEntity)(Object)this).setPosition(Math.floor(((ClientPlayerEntity)(Object)this).getX()) + 0.5, ((ClientPlayerEntity)(Object)this).getY(), ((ClientPlayerEntity)(Object)this).getZ());
        }
    }
}
