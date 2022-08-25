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

    @Shadow public abstract float getDownfall();

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
    @Inject(at = @At("HEAD"), method = "getFoliageColor", cancellable = true)
    public void getFoliageColor(CallbackInfoReturnable<Integer> cir){
        if(!Config.leaf().equals(000000)){
            cir.setReturnValue(Config.leaf());
        }
    }
    @Inject(at = @At("HEAD"), method = "getGrassColorAt", cancellable = true)
    public void getGrassColorAt(double x, double z, CallbackInfoReturnable<Integer> cir){
        if(!Config.grass().equals(000000)){
            cir.setReturnValue(Config.grass());

        }
    }
    @Inject(at = @At("HEAD"), method = "getWaterColor", cancellable = true)
    public void getWaterColor(CallbackInfoReturnable<Integer> cir){
        if(!Config.water().equals(000000)){
            cir.setReturnValue(Config.water());
        }
    }
    @Inject(at = @At("HEAD"), method = "getWaterFogColor", cancellable = true)
    public void getWaterFogColor(CallbackInfoReturnable<Integer> cir){
        if(!Config.waterfog().equals(000000)){
            cir.setReturnValue(Config.waterfog());
        }
    }


}
