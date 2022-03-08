package rubyboat.ghost.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rubyboat.ghost.client.GhostClient;
import rubyboat.ghost.config.Config;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci){
        if(GhostClient.keyBinding2.isPressed())
        {
            MinecraftClient.getInstance().setScreen(Config.MakeConfig().build());
        }
    }

}
