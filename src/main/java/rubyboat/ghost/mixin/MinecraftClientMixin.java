package rubyboat.ghost.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.client.GhostClient;
import rubyboat.ghost.config.Config;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow protected abstract String getWindowTitle();

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci){
        if(GhostClient.keyBinding2.isPressed())
        {
            MinecraftClient.getInstance().setScreen(Config.MakeConfig().build());
        }
    }
    @Inject(at = @At("HEAD"), method = "getWindowTitle", cancellable = true)
    public void getWindowTitle(CallbackInfoReturnable<String> cir){
        if(!Config.getTitle().equalsIgnoreCase("")){
            cir.setReturnValue(Config.getTitle());
        }
    }
}
