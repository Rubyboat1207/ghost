package rubyboat.ghost.mixin;


import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.*;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rubyboat.ghost.client.GhostClient;
import rubyboat.ghost.config.Config;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow @Nullable private ChunkPos pos;

    @Shadow public abstract void resetChunk();

    @Shadow protected abstract World getWorld();

    @Shadow @Nullable protected abstract String getServerWorldDebugString();

    @Shadow protected abstract WorldChunk getClientChunk();

    @Shadow @Nullable protected abstract WorldChunk getChunk();

    @Shadow @Final private static Map<Type, String> HEIGHT_MAP_TYPES;

    @Shadow @Nullable protected abstract ServerWorld getServerWorld();

    @Inject(at = @At("HEAD"), method = "getLeftText", cancellable = true)
    protected void getLayer(CallbackInfoReturnable<List<String>> cir) {
        IntegratedServer integratedServer = this.client.getServer();
        ClientConnection clientConnection = this.client.getNetworkHandler().getConnection();
        float f = clientConnection.getAveragePacketsSent();
        float g = clientConnection.getAveragePacketsReceived();
        String string;
        if (integratedServer != null) {
            string = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", integratedServer.getTickTime(), f, g);
        } else {
            string = String.format("\"%s\" server, %.0f tx, %.0f rx", this.client.player.getServerBrand(), f, g);
        }

        BlockPos blockPos = this.client.getCameraEntity().getBlockPos();
        String[] var10000;
        String var10003;
        if (this.client.hasReducedDebugInfo()) {
            var10000 = new String[9];
            var10003 = SharedConstants.getGameVersion().getName();
            var10000[0] = "Minecraft " + var10003 + " (" + this.client.getGameVersion() + "/" + ClientBrandRetriever.getClientModName() + ")";
            var10000[1] = this.client.fpsDebugString;
            var10000[2] = string;
            var10000[3] = this.client.worldRenderer.getChunksDebugString();
            var10000[4] = this.client.worldRenderer.getEntitiesDebugString();
            var10003 = this.client.particleManager.getDebugString();
            var10000[5] = "P: " + var10003 + ". T: " + this.client.world.getRegularEntityCount();
            var10000[6] = this.client.world.asString();
            var10000[7] = "";
            var10000[8] = String.format("Chunk-relative: %d %d %d", blockPos.getX() & 15, blockPos.getY() & 15, blockPos.getZ() & 15);
            cir.setReturnValue(Lists.newArrayList(var10000));
        } else {
            Entity entity = this.client.getCameraEntity();
            Direction direction = entity.getHorizontalFacing();
            String string2;
            switch(direction) {
                case NORTH:
                    string2 = "Towards negative Z";
                    break;
                case SOUTH:
                    string2 = "Towards positive Z";
                    break;
                case WEST:
                    string2 = "Towards negative X";
                    break;
                case EAST:
                    string2 = "Towards positive X";
                    break;
                default:
                    string2 = "Invalid";
            }

            ChunkPos chunkPos = new ChunkPos(blockPos);
            if (!Objects.equals(this.pos, chunkPos)) {
                this.pos = chunkPos;
                this.resetChunk();
            }

            World world = this.getWorld();
            LongSet longSet = world instanceof ServerWorld ? ((ServerWorld)world).getForcedChunks() : LongSets.EMPTY_SET;
            var10000 = new String[7];
            var10003 = SharedConstants.getGameVersion().getName();
            var10000[0] = Config.getTitle() + " " + var10003 + " (" + this.client.getGameVersion() + "/" + ClientBrandRetriever.getClientModName() + ("release".equalsIgnoreCase(this.client.getVersionType()) ? "" : "/" + this.client.getVersionType()) + ")";
            var10000[1] = this.client.fpsDebugString;
            var10000[2] = string;
            var10000[3] = this.client.worldRenderer.getChunksDebugString();
            var10000[4] = this.client.worldRenderer.getEntitiesDebugString();
            var10003 = this.client.particleManager.getDebugString();
            var10000[5] = "P: " + var10003 + ". T: " + this.client.world.getRegularEntityCount();
            var10000[6] = this.client.world.asString();
            List<String> list = Lists.newArrayList(var10000);
            String string3 = this.getServerWorldDebugString();
            if (string3 != null) {
                list.add(string3);
            }

            Identifier var10001 = this.client.world.getRegistryKey().getValue();
            list.add(var10001 + " FC: " + ((LongSet)longSet).size());
            list.add("");
            list.add(String.format(Locale.ROOT, "XYZ: %.3f / " + (!Config.isCyrusMode() ? GhostClient.roundTo((float) this.client.getCameraEntity().getY(), 3) : (this.client.getCameraEntity().getY() > 320 ? "Build Height+ ( "+ GhostClient.roundTo((float) this.client.getCameraEntity().getY() + 64f, 3) + " )" : GhostClient.roundTo((float) this.client.getCameraEntity().getY() + 64f, 3))) +  " / %.3f", this.client.getCameraEntity().getX(), this.client.getCameraEntity().getZ()));
            list.add(String.format("Block: %d %d %d", blockPos.getX(), blockPos.getY(), blockPos.getZ()));
            list.add(String.format("Chunk: %d %d %d [%d %d in r.%d.%d.mca]", chunkPos.x, ChunkSectionPos.getSectionCoord(blockPos.getY()), chunkPos.z, chunkPos.getRegionRelativeX(), chunkPos.getRegionRelativeZ(), chunkPos.getRegionX(), chunkPos.getRegionZ()));
            list.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, string2, MathHelper.wrapDegrees(entity.getYaw()), MathHelper.wrapDegrees(entity.getPitch())));
            WorldChunk worldChunk = this.getClientChunk();
            int m;
            if (worldChunk.isEmpty()) {
                list.add("Waiting for chunk...");
            } else {
                int i = this.client.world.getChunkManager().getLightingProvider().getLight(blockPos, 0);
                int j = this.client.world.getLightLevel(LightType.SKY, blockPos);
                int k = this.client.world.getLightLevel(LightType.BLOCK, blockPos);
                list.add("Client Light: " + i + " (" + j + " sky, " + k + " block)");
                WorldChunk worldChunk2 = this.getChunk();
                StringBuilder stringBuilder = new StringBuilder("CH");
                Type[] var21 = Type.values();
                int var22 = var21.length;

                Type type;
                for(m = 0; m < var22; ++m) {
                    type = var21[m];
                    if (type.shouldSendToClient()) {
                        stringBuilder.append(" ").append((String)HEIGHT_MAP_TYPES.get(type)).append(": ").append(worldChunk.sampleHeightmap(type, blockPos.getX(), blockPos.getZ()));
                    }
                }

                list.add(stringBuilder.toString());
                stringBuilder.setLength(0);
                stringBuilder.append("SH");
                var21 = Type.values();
                var22 = var21.length;

                for(m = 0; m < var22; ++m) {
                    type = var21[m];
                    if (type.isStoredServerSide()) {
                        stringBuilder.append(" ").append((String)HEIGHT_MAP_TYPES.get(type)).append(": ");
                        if (worldChunk2 != null) {
                            stringBuilder.append(worldChunk2.sampleHeightmap(type, blockPos.getX(), blockPos.getZ()));
                        } else {
                            stringBuilder.append("??");
                        }
                    }
                }

                list.add(stringBuilder.toString());
                if (blockPos.getY() >= this.client.world.getBottomY() && blockPos.getY() < this.client.world.getTopY()) {
                    Registry var27 = this.client.world.getRegistryManager().get(Registry.BIOME_KEY);
                    RegistryEntry<Biome> var10002 = this.client.world.getBiome(blockPos);
                    list.add("Biome: " + var27.getId(var10002));
                    long l = 0L;
                    float h = 0.0F;
                    if (worldChunk2 != null) {
                        h = world.getMoonSize();
                        l = worldChunk2.getInhabitedTime();
                    }

                    LocalDifficulty localDifficulty = new LocalDifficulty(world.getDifficulty(), world.getTimeOfDay(), l, h);
                    list.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f (Day %d)", localDifficulty.getLocalDifficulty(), localDifficulty.getClampedLocalDifficulty(), this.client.world.getTimeOfDay() / 24000L));
                }
            }

            ServerWorld serverWorld = this.getServerWorld();
            if (serverWorld != null) {
                ServerChunkManager serverChunkManager = serverWorld.getChunkManager();
                ChunkGenerator chunkGenerator = serverChunkManager.getChunkGenerator();
                MultiNoiseUtil.MultiNoiseSampler multiNoiseSampler = chunkGenerator.getMultiNoiseSampler();
                BiomeSource biomeSource = chunkGenerator.getBiomeSource();
                biomeSource.addDebugInfo(list, blockPos, multiNoiseSampler);
                SpawnHelper.Info info = serverChunkManager.getSpawnInfo();
                if (info != null) {
                    Object2IntMap<SpawnGroup> object2IntMap = info.getGroupToCount();
                    m = info.getSpawningChunkCount();
                    list.add("SC: " + m + ", " + (String) Stream.of(SpawnGroup.values()).map((group) -> {
                        char var10002 = Character.toUpperCase(group.getName().charAt(0));
                        return var10002 + ": " + object2IntMap.getInt(group);
                    }).collect(Collectors.joining(", ")));
                } else {
                    list.add("SC: N/A");
                }
            }

            ShaderEffect shaderEffect = this.client.gameRenderer.getShader();
            if (shaderEffect != null) {
                list.add("Shader: " + shaderEffect.getName());
            }

            String var29 = this.client.getSoundManager().getDebugString();
            list.add(var29 + String.format(" (Mood %d%%)", Math.round(this.client.player.getMoodPercentage() * 100.0F)));
            cir.setReturnValue(list);
        }
    }
}
