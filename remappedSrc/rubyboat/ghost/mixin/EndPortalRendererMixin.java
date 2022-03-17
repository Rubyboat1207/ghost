package rubyboat.ghost.mixin;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndPortalBlockEntityRenderer.class)
public class EndPortalRendererMixin {

    @Inject(at = @At("HEAD"), method = "getLayer", cancellable = true)
    protected void getLayer(CallbackInfoReturnable<RenderLayer> cir) {
        cir.setReturnValue(RenderLayer.getEndGateway());
    }

}
