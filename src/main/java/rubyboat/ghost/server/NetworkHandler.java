package rubyboat.ghost.server;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import rubyboat.ghost.Main;
import rubyboat.ghost.client.GhostClient;

public class NetworkHandler {
    public static final Identifier ALLOW_MIDAIR_BLOCKS = Identifier.of(Main.MOD_ID, "allow_midair_blocks");


    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(AllowMidairBlocksPayload.ID, AllowMidairBlocksPayload.CODEC);


        ClientPlayNetworking.registerGlobalReceiver(AllowMidairBlocksPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                GhostClient.AllowMidAirGhostBlocks = true;
            });
        });
    }

    public static void sendAllowMidAirBlocks(ServerPlayerEntity player, boolean allow) {
        ServerPlayNetworking.send(player, new AllowMidairBlocksPayload(allow));
    }



    public record AllowMidairBlocksPayload(Boolean allowPlace) implements CustomPayload {
        public static final Id<AllowMidairBlocksPayload> ID = CustomPayload.id(ALLOW_MIDAIR_BLOCKS.toString().replace(':', '_'));
        public static final PacketCodec<PacketByteBuf, AllowMidairBlocksPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, AllowMidairBlocksPayload::allowPlace, AllowMidairBlocksPayload::new);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}
