package rubyboat.ghost.mixin;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.config.Config;

@Mixin(Biome.class)
public abstract class BiomeMixin {
    @Shadow public abstract int getSkyColor();

    @Inject(at = @At("HEAD"), method = "getSkyColor", cancellable = true)
    public void getSkyColor(CallbackInfoReturnable<Integer> cir) {
        if(!Config.color().equals(000000)){
            cir.setReturnValue(Config.color()); // + Config.color()
        }
    }
    @Inject(at = @At("HEAD"), method = "getFogColor", cancellable = true)
    public void getFogColor(CallbackInfoReturnable<Integer> cir){
        if(!Config.color().equals(000000)){
            cir.setReturnValue(Config.fog()); // + Config.fog()
        }
    }

}
