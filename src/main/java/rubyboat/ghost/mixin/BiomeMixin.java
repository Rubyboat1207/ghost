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
        if(Config.getConfigValueInt("sky_color") != 0) {
            cir.setReturnValue(Config.getConfigValueInt("sky_color"));
        }
    }
    @Inject(at = @At("HEAD"), method = "getFogColor", cancellable = true)
    public void getFogColor(CallbackInfoReturnable<Integer> cir){
        if(Config.getConfigValueInt("fog_color") != 0) {
            cir.setReturnValue(Config.getConfigValueInt("fog_color")); // + Config.fog()
        }
    }
    @Inject(at = @At("HEAD"), method = "getFoliageColor", cancellable = true)
    public void getFoliageColor(CallbackInfoReturnable<Integer> cir){
        if(Config.getConfigValueInt("foliage_color") != 0) {
            cir.setReturnValue(Config.getConfigValueInt("foliage_color"));
        }
    }
    @Inject(at = @At("HEAD"), method = "getGrassColorAt", cancellable = true)
    public void getGrassColorAt(double x, double z, CallbackInfoReturnable<Integer> cir){
        if(Config.getConfigValueInt("grass_color") != 0) {
            cir.setReturnValue(Config.getConfigValueInt("grass_color"));

        }
    }
    @Inject(at = @At("HEAD"), method = "getWaterColor", cancellable = true)
    public void getWaterColor(CallbackInfoReturnable<Integer> cir){
        if(Config.getConfigValueInt("water_color") != 0) {
            cir.setReturnValue(Config.getConfigValueInt("water_color"));
        }
    }
    @Inject(at = @At("HEAD"), method = "getWaterFogColor", cancellable = true)
    public void getWaterFogColor(CallbackInfoReturnable<Integer> cir){
        if(Config.getConfigValueInt("water_fog_color") != 0) {
            cir.setReturnValue(Config.getConfigValueInt("water_fog_color"));
        }
    }


}
