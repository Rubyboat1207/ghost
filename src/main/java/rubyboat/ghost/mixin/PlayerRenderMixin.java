package rubyboat.ghost.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.config.Config;

@Mixin(PlayerEntityRenderer.class) //EndPortalBlockEntityRenderer.class
public abstract class PlayerRenderMixin {

    @Shadow protected abstract void setModelPose(AbstractClientPlayerEntity player);

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
    public void getTexture(AbstractClientPlayerEntity abstractClientPlayerEntity, CallbackInfoReturnable<Identifier> cir) {
        if(!Config.getPlayerTexture().equals("")){
            cir.setReturnValue(new Identifier("textures/" + Config.getPlayerTexture() + ".png"));

            //abstractClientPlayerEntity.getSkinTexture()
        }
    }

    @Inject(at = @At("HEAD"), method = "renderArm", cancellable = true)
    private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        if(!Config.getPlayerTexture().equalsIgnoreCase("")){
            PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = ((PlayerEntityRenderer)(Object)this).getModel();
            this.setModelPose(player);
            playerEntityModel.handSwingProgress = 0.0F;
            playerEntityModel.sneaking = false;
            playerEntityModel.leaningPitch = 0.0F;
            playerEntityModel.setAngles(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            arm.pitch = 0.0F;
            arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(new Identifier("textures/" + Config.getPlayerTexture() + ".png"))), light, OverlayTexture.DEFAULT_UV);
            sleeve.pitch = 0.0F;
            if(Config.isSleeve()){
                sleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(new Identifier("textures/" + Config.getPlayerTexture() + ".png"))), light, OverlayTexture.DEFAULT_UV);
            }
            ci.cancel();
        }
    }


}
