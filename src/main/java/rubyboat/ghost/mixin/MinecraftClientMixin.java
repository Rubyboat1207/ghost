package rubyboat.ghost.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
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

    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Nullable public ClientPlayerInteractionManager interactionManager;

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
    @Inject(at = @At("HEAD"), method = "getGameVersion", cancellable = true)
    public void getGameVersion(CallbackInfoReturnable<String> cir){
        if(!Config.getVersion().equalsIgnoreCase("")){
            cir.setReturnValue(Config.getVersion());
        }
    }
    @Inject(at = @At("HEAD"), method = "getVersionType", cancellable = true)
    public void getVersionType(CallbackInfoReturnable<String> cir){
        if(!Config.getVersion().equalsIgnoreCase("")){
            cir.setReturnValue(Config.getVersion());
        }
    }

    @Inject(at = @At("HEAD"), method = "handleBlockBreaking", cancellable = true)
    public void cancelBlockBreaking(boolean breaking, CallbackInfo ci) {
        if (Config.getDurability() && breaking) {
            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
            int remaining = stack.getMaxDamage() - stack.getDamage();
            double percent = (double) remaining / (double) stack.getMaxDamage();
            if(percent <= Config.getDurabilityPercentage()) {
                this.interactionManager.cancelBlockBreaking();
                ci.cancel();
            }
        }
    }

}
