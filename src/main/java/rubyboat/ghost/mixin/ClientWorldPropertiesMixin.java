package rubyboat.ghost.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.config.Config;

@Mixin(ClientWorld.Properties.class)
public class ClientWorldPropertiesMixin {
    @Inject(at = @At("HEAD"), method = "getTimeOfDay", cancellable = true)
    public void tick(CallbackInfoReturnable<Long> cir) {
        if(Config.getConfigValueInt("time") != -1){
            cir.setReturnValue(Long.valueOf("" + Config.getConfigValueInt("time")));
        }
    }
}
