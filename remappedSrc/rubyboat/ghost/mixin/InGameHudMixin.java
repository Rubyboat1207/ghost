package rubyboat.ghost.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rubyboat.ghost.client.GhostClient;
import rubyboat.ghost.config.Config;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow public abstract TextRenderer getTextRenderer();

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Inject(at = @At("HEAD"), method = "renderStatusBars")
    public void renderStatusBars(MatrixStack matrices, CallbackInfo ci){
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity != null) {
            if(Config.is_thirst())
            {
                String to_print = "Thirst: ";
                //MatrixStack matrices, TextRenderer textRenderer, String text, int centerX, int y, int color
                int k = (this.scaledWidth - this.getTextRenderer().getWidth(to_print)) / 2;
                this.getTextRenderer().draw(matrices, "Thirst: ", k, this.scaledHeight - 100, 0x00aaff);
            }
        }
    }
}
