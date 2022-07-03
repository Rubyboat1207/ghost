package rubyboat.ghost.mixin;

import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.config.Config;

@Mixin(PigEntityModel.class) //EndPortalBlockEntityRenderer.class
public class PigEntityModelMixin {
    @Inject(at = @At("HEAD"), method = "getTexturedModelData", cancellable = true)
    private static void getTexture(Dilation dilation, CallbackInfoReturnable<TexturedModelData> cir) {
        if(!Config.isTechnoblade()) {
            ModelData modelData = QuadrupedEntityModel.getModelData(6, dilation);
            ModelPartData modelPartData = modelData.getRoot();
            modelPartData.addChild("head", ModelPartBuilder.create().
                            //Main Pig Head (assumed)
                                    uv(0, 0).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, dilation).
                            //Pig Snout (assumed)
                                    uv(16, 16).cuboid(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, dilation),
                    //I dont know what this part of the code does tbh
                    ModelTransform.pivot(0.0F, 12.0F, -6.0F)
            );
            cir.setReturnValue(TexturedModelData.of(modelData, 64, 64));
            return;
        }

        ModelData modelData = QuadrupedEntityModel.getModelData(6, dilation);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().
                //Main Pig Head (assumed)
                uv(0, 0).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, dilation).
                //Pig Snout (assumed)
                uv(16, 16).cuboid(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, dilation).
                //Crown
                uv(0, 32).cuboid(-4.01F, -6.0F, -8.0F, 8.01F, 3.0F, 8.01F, dilation),
                //I dont know what this part of the code does tbh
                ModelTransform.pivot(0.0F, 12.0F, -6.0F)
        );
        cir.setReturnValue(TexturedModelData.of(modelData, 64, 64));
    }
}
