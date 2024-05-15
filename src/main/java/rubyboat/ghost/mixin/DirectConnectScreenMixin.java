package rubyboat.ghost.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.DirectConnectScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

@Mixin(DirectConnectScreen.class)
public class DirectConnectScreenMixin extends Screen {
    protected DirectConnectScreenMixin(Text title) {
        super(title);
    }

    //ConnectScreen.connect(this, this.client, ServerAddress.parse(entry.address), entry);
    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    public void init(CallbackInfo ci) {
        ButtonWidget.Builder builder = ButtonWidget.builder(Text.of("Server Roulette!"),  (button) -> {
            Random r = new Random();
            String ip = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
            InetAddress server = null;
            try {
                server = InetAddress.getByName(ip);
                while(!server.isReachable(500)) {
                    System.out.println("Sending Ping Request to " + ip);
                    ip = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
                    server = InetAddress.getByName(ip);
                }
            } catch (Exception ignore) {}

            ServerInfo entry = new ServerInfo("Random Server", ip, ServerInfo.ServerType.OTHER);

            assert this.client != null;
            ConnectScreen.connect(this, this.client, ServerAddress.parse(entry.address), entry, true, null);
        });

        builder.position(this.width / 2 - 100, this.height / 4 + 96 + 12 + 49);
        builder.size(200, 20);



        this.addDrawableChild(builder.build());
    }
}
