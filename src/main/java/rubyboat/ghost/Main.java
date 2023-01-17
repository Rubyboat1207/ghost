package rubyboat.ghost;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import rubyboat.ghost.server.NetworkHandler;

public class Main implements ModInitializer {
    public static final String MOD_ID = "ghost";

    @Override
    public void onInitialize() {
        System.out.println("test");
        ServerPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            System.out.println("sent allow midair blocks");
            NetworkHandler.sendAllowMidAirBlocks(handler.player, true);
        });
    }
}
