package rubyboat.ghost.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
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
        var tex = Config.getConfigValueString("player_texture");

        if(!tex.equals("")){
            cir.setReturnValue(new Identifier("textures/" + tex + ".png"));
        }
    }

    @Inject(at = @At("RETURN"), method = "getPositionOffset(Lnet/minecraft/client/network/AbstractClientPlayerEntity;F)Lnet/minecraft/util/math/Vec3d;", cancellable = true)
    public void getTexture(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, CallbackInfoReturnable<Vec3d> cir) {
        cir.setReturnValue(cir.getReturnValue().add(0, Config.getConfigValueFloat("model_offset"), 0));
    }

    @Inject(at = @At("HEAD"), method = "renderArm", cancellable = true)
    private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        var tex = Config.getConfigValueString("player_texture");

        if(!tex.equalsIgnoreCase("")){
            PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = ((PlayerEntityRenderer)(Object)this).getModel();
            this.setModelPose(player);
            playerEntityModel.handSwingProgress = 0.0F;
            playerEntityModel.sneaking = false;
            playerEntityModel.leaningPitch = 0.0F;
            playerEntityModel.setAngles(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            arm.pitch = 0.0F;
            arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(new Identifier("textures/" + tex + ".png"))), light, OverlayTexture.DEFAULT_UV);
            sleeve.pitch = 0.0F;
            if(Config.getConfigValueBoolean("sleeve_visibility")){
                sleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(new Identifier("textures/" + tex + ".png"))), light, OverlayTexture.DEFAULT_UV);
            }
            ci.cancel();
        }
    }
    @Inject(at = @At("RETURN"), method = "setModelPose", cancellable = true)
    private void renderArm(AbstractClientPlayerEntity player, CallbackInfo ci) {
        PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = (PlayerEntityModel)(((PlayerEntityRenderer)(Object)this).getModel());
        //-----arms
        if(!Config.getConfigValueBoolean("render_arms"))
        {
            playerEntityModel.leftSleeve.visible = false;
            playerEntityModel.rightSleeve.visible = false;
            playerEntityModel.rightArm.visible = false;
            playerEntityModel.leftArm.visible = false;
        }
        //-----body
        if(!Config.getConfigValueBoolean("render_body"))
        {
            playerEntityModel.body.visible = false;
            playerEntityModel.jacket.visible = false;
        }
        //-----legs
        if(!Config.getConfigValueBoolean("render_legs"))
        {
            playerEntityModel.leftLeg.visible = false;
            playerEntityModel.rightLeg.visible = false;
            playerEntityModel.leftPants.visible = false;
            playerEntityModel.rightPants.visible = false;
        }
        //----head
        if(!Config.getConfigValueBoolean("render_head"))
        {
            playerEntityModel.head.visible = false;
            playerEntityModel.hat.visible = false;
        }

    }

}
