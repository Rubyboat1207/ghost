package rubyboat.ghost.mixin;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.config.Config;

import java.util.Objects;

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
    @Inject(at = @At("HEAD"), method = "getDownfall", cancellable = true) //none of this owkr aHAHHAHA
    public void getDownfall(CallbackInfoReturnable<Float> cir){
        if(Objects.equals(Config.getWeather(), "rain")){
            cir.setReturnValue(1f);
        }else if (Config.getWeather() == "thunder"){
            cir.setReturnValue(2f);
        }else if (Config.getWeather() == "snow"){
            cir.setReturnValue(3f);
        }else if (Config.getWeather() == "test"){
            cir.setReturnValue(4f);
        }
    }


}
