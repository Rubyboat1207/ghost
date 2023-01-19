package rubyboat.ghost.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.config.Config;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class InteractionManagerMixin {
    @Shadow public abstract void cancelBlockBreaking();

    @Inject(at = @At("HEAD"), method = "attackBlock", cancellable = true)
    public void attackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (Config.getConfigValueBoolean("durability")) {
            ItemStack stack = MinecraftClient.getInstance().player.getStackInHand(Hand.MAIN_HAND);
            int remaining = stack.getMaxDamage() - stack.getDamage();
            double percent = (double) remaining / (double) stack.getMaxDamage();
            if(percent <= Config.getConfigValueInt("durability_percentage") / 100.0) {
                this.cancelBlockBreaking();
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
