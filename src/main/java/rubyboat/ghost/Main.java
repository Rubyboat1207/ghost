package rubyboat.ghost;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import rubyboat.ghost.server.NetworkHandler;

public class Main implements ModInitializer {
    public static final String MOD_ID = "ghost";

    @Override
    public void onInitialize() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            NetworkHandler.sendAllowMidAirBlocks(handler.player, true);
        });
    }
}
