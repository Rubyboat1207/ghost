package rubyboat.ghost.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import rubyboat.ghost.server.NetworkHandler;

@Environment(EnvType.CLIENT)
public class GhostClient implements ClientModInitializer {

    public static KeyBinding keyBinding;
    public static KeyBinding keyBinding2;
    private static final Logger LOGGER = LogManager.getLogger();
    public static float roundTo(float number, int decimalPoints) {
        return (float) (Math.floor(number * Math.pow(10, decimalPoints)) * Math.pow(10, -decimalPoints));
    }

    public static boolean AllowMidAirGhostBlocks = false;

    @Override
    public void onInitializeClient() {
        LOGGER.log(Level.INFO, AbstractBlock.class.getName());
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ghost.createghostblock", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_G, // The keycode of the key
                "category.ghost.general" // The translation key of the keybinding's category.
        ));
        keyBinding2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ghost.menu", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_RIGHT_SHIFT, // The keycode of the key
                "category.ghost.general" // The translation key of the keybinding's category.
        ));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            AllowMidAirGhostBlocks = false;
        });
        NetworkHandler.registerS2CPackets();
    }
}
