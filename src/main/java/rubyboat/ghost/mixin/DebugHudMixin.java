package rubyboat.ghost.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import rubyboat.ghost.config.Config;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {

    @Shadow @Final private MinecraftClient client;

    @ModifyArg(
            method = "getLeftText",
            at = @At(
                    value="INVOKE",
                    target="Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=XYZ: %.3f / %.5f / %.3f"
                    )
            )
    )
    Object[] DisableYNegativesOnXYZ(Object[] args) {
        if(Config.disableNegatives()) {
            args[1] = (double) args[1] + 64;
        }
        return args;
    }

    @ModifyArg(
        method = "getLeftText",
        at = @At(
            value="INVOKE",
            target="Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
            ordinal = 0
        ),
        slice = @Slice(
            from = @At(
                value = "CONSTANT",
                args = "stringValue=Block: %d %d %d [%d %d %d]"
            )
        )
    )
    Object[] DisableYNegativesOnBlock(Object[] args) {
        if(Config.disableNegatives()) {
            args[1] = (int) args[1] + 64;
        }
        return args;
    }

    int getAddition() {
        return Math.abs(MinecraftClient.getInstance().player.getWorld().getDimension().minY()) ;
    }
}
