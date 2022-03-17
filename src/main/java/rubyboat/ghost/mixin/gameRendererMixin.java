package rubyboat.ghost.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.client.GhostClient;
import rubyboat.ghost.config.Config;


@Mixin(GameRenderer.class)
@Environment(EnvType.CLIENT)
public class gameRendererMixin {
    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    public void getZoomLevel(CallbackInfoReturnable<Double> callbackInfo) {
        if(GhostClient.zoom.isPressed()) {
            double fov = callbackInfo.getReturnValue();
            callbackInfo.setReturnValue(fov * (1 - (Config.getZoomStrength() / 100)));
        }
    }
}
