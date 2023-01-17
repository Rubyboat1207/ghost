package rubyboat.ghost.server;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import rubyboat.ghost.Main;
import rubyboat.ghost.client.GhostClient;

public class NetworkHandler {
    public static final Identifier ALLOW_MIDAIR_BLOCKS = new Identifier(Main.MOD_ID, "allow_midair_blocks");


    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ALLOW_MIDAIR_BLOCKS, (client, handler, buf, responseSender) -> {
            GhostClient.AllowMidAirGhostBlocks = buf.readBoolean();
            System.out.println("allow midair blocks: " + GhostClient.AllowMidAirGhostBlocks);
        });
    }

    public static void sendAllowMidAirBlocks(ServerPlayerEntity player, boolean allow) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(allow);
        ServerPlayNetworking.send(player, ALLOW_MIDAIR_BLOCKS, buf);
    }
}
