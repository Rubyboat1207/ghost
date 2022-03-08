package rubyboat.ghost.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class GhostClient implements ClientModInitializer {

    public static KeyBinding keyBinding;
    public static KeyBinding keyBinding2;
    public static KeyBinding spawnMob;
    private static final Logger LOGGER = LogManager.getLogger();


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
        spawnMob = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ghost.spawn_mob", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_K, // The keycode of the key
                "category.ghost.general" // The translation key of the keybinding's category.
        ));
    }
}
