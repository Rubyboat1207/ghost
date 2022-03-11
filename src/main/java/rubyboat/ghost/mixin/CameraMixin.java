package rubyboat.ghost.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.client.GhostClient;
import rubyboat.ghost.config.Config;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow private boolean ready;

    @Shadow private BlockView area;

    @Shadow private Entity focusedEntity;

    @Shadow private boolean thirdPerson;

    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Shadow protected abstract void setPos(Vec3d pos);

    @Shadow private float yaw;

    @Shadow private float pitch;

    @Shadow protected abstract void moveBy(double x, double y, double z);

    @Shadow protected abstract double clipToSpace(double desiredCameraDistance);

    @Shadow private float cameraY;

    @Shadow private float lastCameraY;

    @Shadow protected abstract void setPos(double x, double y, double z);

    @Inject(at = @At("HEAD"), method = "update", cancellable = true)
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci)
    {
        this.ready = true;
        this.area = area;
        this.focusedEntity = focusedEntity;
        this.thirdPerson = thirdPerson;
        if(Config.getCamera_type().equalsIgnoreCase("normal") || Config.getCamera_type().equalsIgnoreCase("choppy"))
        {
            if(!Config.getCamera_type().equalsIgnoreCase("choppy"))
            {
                this.setRotation(focusedEntity.getYaw(tickDelta), focusedEntity.getPitch(tickDelta));
                this.setPos(MathHelper.lerp((double)tickDelta, focusedEntity.prevX, focusedEntity.getX()), MathHelper.lerp((double)tickDelta, focusedEntity.prevY, focusedEntity.getY()) + (double)MathHelper.lerp(tickDelta, this.lastCameraY, this.cameraY), MathHelper.lerp((double)tickDelta, focusedEntity.prevZ, focusedEntity.getZ()));
            }else
            {
                this.setRotation(focusedEntity.getYaw(), focusedEntity.getPitch());
                this.setPos(focusedEntity.getX(), focusedEntity.prevY + this.cameraY, focusedEntity.getZ());
            }
            if (thirdPerson) {
                if (inverseView) {
                    this.setRotation(this.yaw + 180.0f, -this.pitch);
                }
                this.moveBy(-this.clipToSpace(4.0), 0.0, 0.0);
            } else if (focusedEntity instanceof LivingEntity && ((LivingEntity)focusedEntity).isSleeping()) {
                Direction direction = ((LivingEntity)focusedEntity).getSleepingDirection();
                this.setRotation(direction != null ? direction.asRotation() - 180.0f : 0.0f, 0.0f);
                this.moveBy(0.0, 0.3, 0.0);
            }
        }else if(Config.getCamera_type().equalsIgnoreCase("topDown"))
        {
            this.thirdPerson = true;
            this.setRotation(focusedEntity.getYaw(), 90);
            this.setPos(MathHelper.lerp((double)tickDelta, focusedEntity.prevX, focusedEntity.getX()), MathHelper.lerp((double)tickDelta, focusedEntity.prevY + Config.getCameraDistance(), focusedEntity.getY() + Config.getCameraDistance()), MathHelper.lerp((double)tickDelta, focusedEntity.prevZ, focusedEntity.getZ()));
        }
        ci.cancel();
    }
    @Inject(at = @At("HEAD"), method = "getSubmersionType", cancellable = true)
    public void getSubmersionType(CallbackInfoReturnable<CameraSubmersionType> cir){
        if(Config.getInPowderSnowEffect().equalsIgnoreCase("snow")){
            cir.setReturnValue(CameraSubmersionType.POWDER_SNOW);
        }else if(Config.getInPowderSnowEffect().equalsIgnoreCase("lava")){
            cir.setReturnValue(CameraSubmersionType.LAVA);
        }else if(Config.getInPowderSnowEffect().equalsIgnoreCase("water")) {
            cir.setReturnValue(CameraSubmersionType.WATER);
        }

    }


}
