package rubyboat.ghost.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    int number;
    //new Random().nextInt(20,100000)

    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    public void init(CallbackInfo ci) {
        number = new Random().nextInt(20,100000);
    }


    @Inject(at = @At("RETURN"), method = "render", cancellable = true)
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        DeathScreen screen = ((DeathScreen)(Object)this);
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.of("Your death has contributed " + number + " zepto seconds towards the heat death of the universe"), screen.width / 2, 115, 16777215);
    }
}
