package rubyboat.ghost.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.ClothConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import rubyboat.clothconfigextensions.builders.ButtonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Config {
    static JsonObject config;
    static String path = "ghost_config.json";

    public static String[] downfall = {
            "none",
            "rain",
            "thunder",
            "snow",
            "test"
    };

    public static int getConfigValueInt(String path) {
        if(config == null) {
            loadConfig();
        }
        if (getConfig().get(path) == null) {
            if(getFallbackConfig().get(path) == null) {
                return 0;
            }
            return getFallbackConfig().get(path).getAsInt();
        }
        return getConfig().get(path).getAsInt();
    }

    public static double getConfigValueDouble(String path) {
        if(config == null) {
            loadConfig();
        }
        if (getConfig().get(path) == null) {
            if(getFallbackConfig().get(path) == null) {
                return 0;
            }
            return getFallbackConfig().get(path).getAsDouble();
        }
        return getConfig().get(path).getAsDouble();
    }

    public static float getConfigValueFloat(String path) {
        if(config == null) {
            loadConfig();
        }
        if (getConfig().get(path) == null) {
            if(getFallbackConfig().get(path) == null) {
                return 0;
            }
            return getFallbackConfig().get(path).getAsFloat();
        }
        return getConfig().get(path).getAsFloat();
    }

    public static boolean getConfigValueBoolean(String path) {
        if(config == null) {
            loadConfig();
        }
        if(getConfig().get(path) == null) {
            if(getFallbackConfig().get(path) == null) {
                return false;
            }
            return getFallbackConfig().get(path).getAsBoolean();
        }
        return getConfig().get(path).getAsBoolean();
    }

    public static String getConfigValueString(String path) {
        if(config == null) {
            loadConfig();
        }
        if(getConfig().get(path) == null) {
            if(getFallbackConfig().get(path) == null) {
                return "";
            }
            return getFallbackConfig().get(path).getAsString();
        }
        return getConfig().get(path).getAsString();
    }
    static boolean requestedReload = false;
    static void onConfigValChanged(String path) {
        if(Arrays.stream(requiresReload).toList().contains(path)) {
            requestedReload = true;
        }
    }

    static void setConfigValue(String path, int value) {
        if(getConfigValueInt(path) == value) {
            return;
        }
        onConfigValChanged(path);
        getConfig().addProperty(path, value);
    }

    static void setConfigValue(String path, double value) {
        if(getConfigValueDouble(path) == value) {
            return;
        }
        onConfigValChanged(path);
        getConfig().addProperty(path, value);
    }

    static void setConfigValue(String path, boolean value) {
        if(getConfigValueBoolean(path) == value) {
            return;
        }
        onConfigValChanged(path);
        getConfig().addProperty(path, value);
    }

    static void setConfigValue(String path, String value) {
        if(getConfigValueString(path).equals(value)) {
            return;
        }
        onConfigValChanged(path);
        getConfig().addProperty(path, value);
    }

    static final String[] requiresReload = {
        "fog_color",
        "grass_color",
        "foliage_color",
        "sky_color",
        "water_color",
        "water_fog_color"
    };
    static JsonObject getFallbackConfig() {
        JsonObject fallback = new JsonObject();

        // -- General --
        fallback.addProperty("block", "diamond_block");
        fallback.addProperty("frozen", false);
        fallback.addProperty("bouncy", false);
        fallback.addProperty("disable_negatives", false);
        fallback.addProperty("title", "");
        fallback.addProperty("camera_distance", 4);
        fallback.addProperty("version", "");
        fallback.addProperty("version", "");
        fallback.addProperty("durability", false);
        fallback.addProperty("durability_percentage", 10);

        // -- Visuals --
        fallback.addProperty("player_texture", "");
        fallback.addProperty("sleeve_visibility", true);
        fallback.addProperty("hud_effect", "none");
        fallback.addProperty("fog_color", 0);
        fallback.addProperty("grass_color", 0);
        fallback.addProperty("foliage_color", 0);
        fallback.addProperty("sky_color", 0);
        fallback.addProperty("water_color", 0);
        fallback.addProperty("water_fog_color", 0);
        fallback.addProperty("time", 0);
        fallback.addProperty("render_head", true);
        fallback.addProperty("render_arms", true);
        fallback.addProperty("render_body", true);
        fallback.addProperty("render_legs", true);
        //fallback.addProperty("render_armor", true);
        fallback.addProperty("model_offset", 0);
        fallback.addProperty("technoblade", true);

        // -- Gamemodes --
        fallback.addProperty("camera_type", "normal");

        return fallback;
    }

    static void loadConfig() {
        File file = new File(path);
        String fromfile = "";
        try{
            fromfile = Files.readString(Path.of(path));
        }catch (Exception e)
        {
            try{
                Files.writeString(Path.of(path), getFallbackConfig().toString());
                fromfile = Files.readString(Path.of(path));
            }catch (Exception ignored) {
                config = getFallbackConfig();
                System.out.println(config);
                return;
            }
        }
        config = new Gson().fromJson(fromfile, JsonObject.class);
    }

    static JsonObject getConfig() {
        if(config == null) {
            loadConfig();
        }
        return config;
    }

    public static void savePresets() {
        ClothConfigScreen screen = (ClothConfigScreen) MinecraftClient.getInstance().currentScreen;
        screen.saveAll(true);
    }

    public static void save() {
        save(false);
        if(requestedReload) {
            reload();
            requestedReload = false;
        }
    }
    public static void save(boolean reload) {
        if(reload) {
            reload();
        }

        try {
            Files.writeString(Path.of(path), config.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadConfig();
    }

    static void reload() {
        MinecraftClient.getInstance().worldRenderer.reload();
        MinecraftClient.getInstance().reloadResources();
    }

    static void GenerateGeneral(ConfigBuilder builder, ConfigEntryBuilder entryBuilder) {
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config_category.ghost.general"));

        final String[] blocks = {
            "diamond_block",
            "bedrock",
            "dirt",
            "pink_wool",
            "polished_andesite"
        };

        general.addEntry(entryBuilder.startStringDropdownMenu(Text.translatable("entry.ghost.ghost_block"), getConfigValueString("block"))
            .setSelections(Arrays.asList(blocks))
            .setSuggestionMode(false)
            .setSaveConsumer(newValue -> setConfigValue("block", newValue))
            .build()
        );
        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.is_slippery"), getConfigValueBoolean("frozen"))
                .setSaveConsumer(newValue -> setConfigValue("frozen", newValue))
                .build()
        );
        general.addEntry(entryBuilder.startStrField(Text.translatable("entry.ghost.title"), getConfigValueString("title"))
                .setSaveConsumer(newValue -> setConfigValue("title", newValue))
                .build()
        );
        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.cyrus_mode"), getConfigValueBoolean("disable_negatives"))
            .setSaveConsumer(newValue -> setConfigValue("disable_negatives", newValue))
            .build()
        );
        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.bouncy"), getConfigValueBoolean("bouncy"))
            .setSaveConsumer(newValue -> setConfigValue("bouncy", newValue))
            .build()
        );
        general.addEntry(entryBuilder.startStrField(Text.translatable("entry.ghost.version"), getConfigValueString("version"))
            .setSaveConsumer(newValue -> setConfigValue("version", newValue))
            .build()
        );
        general.addEntry(entryBuilder.startIntField(Text.translatable("entry.ghost.distance"), getConfigValueInt("camera_distance"))
            .setSaveConsumer(newValue -> setConfigValue("camera_distance", newValue))
            .build()
        );
        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.durability"), getConfigValueBoolean("durability"))
            .setSaveConsumer((newValue) -> setConfigValue("durability", newValue))
            .build()
        );
        if(getConfigValueBoolean("durability")) {
            general.addEntry(entryBuilder.startDoubleField(Text.translatable("entry.ghost.durability_percentage"), getConfigValueDouble("durability_percentage"))
                .setSaveConsumer(newValue -> setConfigValue("durability_percentage", newValue))
                .setMin(0)
                .setMax(100)
                .build()
            );
        }
    }

    static void GenerateVisuals(ConfigBuilder builder, ConfigEntryBuilder entryBuilder) {
        ConfigCategory visuals = builder.getOrCreateCategory(Text.translatable("config_category.ghost.texture"));

        final String[] effects = {
                "none",
                "snow",
                "water",
                "lava"
        };

        visuals.addEntry(entryBuilder.startStrField(Text.translatable("entry.ghost.player_texture"), getConfigValueString("player_texture"))
            .setSaveConsumer(newValue -> setConfigValue("player_texture", newValue))
            .setTooltip(Text.translatable("tooltip.ghost.player_texture"))
            .build()
        );
        visuals.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.is_sleeve"), getConfigValueBoolean("sleeve_visibility"))
                .setSaveConsumer(newValue -> setConfigValue("sleeve_visibility", newValue))
                .build()
        );
        // -- Hud Visual Effects
        visuals.addEntry(entryBuilder.startStringDropdownMenu(Text.translatable("entry.ghost.snow"), getConfigValueString("hud_effect"))
                .setSelections(Arrays.asList(effects))
                .setSuggestionMode(false)
                .setSaveConsumer(newValue -> setConfigValue("hud_effect", newValue))
                .build()
        );
        // -- Update Model Visibility
        visuals.addEntry(entryBuilder.startTextDescription(
            Text.translatable("label.ghost.model_visibility")).
            build()
        );
        visuals.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.render_arms"), getConfigValueBoolean("render_arms"))
            .setSaveConsumer(newValue -> setConfigValue("render_arms", newValue))
            .build()
        );
        visuals.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.render_legs"), getConfigValueBoolean("render_legs"))
            .setSaveConsumer(newValue -> setConfigValue("render_legs", newValue))
            .build()
        );
        visuals.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.render_body"), getConfigValueBoolean("render_body"))
            .setSaveConsumer(newValue -> setConfigValue("render_body", newValue))
            .build()
        );
        visuals.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.render_head"), getConfigValueBoolean("render_head"))
                .setSaveConsumer(newValue -> setConfigValue("render_head", newValue))
                .build()
        );
//        visuals.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.render_armor"), getConfigValueBoolean("render_armor"))
//                .setSaveConsumer(newValue -> setConfigValue("render_armor", newValue))
//                .build()
//        );
        visuals.addEntry(entryBuilder.startFloatField(Text.translatable("entry.ghost.model_offset"), getConfigValueFloat("model_offset"))
            .setSaveConsumer(newValue -> setConfigValue("model_offset", newValue))
            .build()
        );
        // -- Other Entities
        visuals.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.technoblade"), getConfigValueBoolean("technoblade"))
            .setSaveConsumer(newValue -> setConfigValue("technoblade", newValue))
            .setTooltip(Text.translatable("tooltip.ghost.technoblade"))
            .build()
        );

    }

    static void GenerateWorld(ConfigBuilder builder, ConfigEntryBuilder entryBuilder) {
        ConfigCategory world = builder.getOrCreateCategory(Text.translatable("config_category.ghost.world"));


        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.color"), getConfigValueInt("sky_color"))
                .setSaveConsumer(newValue -> setConfigValue("sky_color", newValue))
                .setTooltip(Text.translatable("tooltip.ghost.color"))
                .build()
        );
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.fog"), getConfigValueInt("fog_color"))
                .setSaveConsumer(newValue -> setConfigValue("fog_color", newValue))
                .setTooltip(Text.translatable("tooltip.ghost.fog"))
                .build()
        );
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.leaf"),getConfigValueInt("foliage_color"))
                .setSaveConsumer(newValue -> setConfigValue("foliage_color", newValue))
                .setTooltip(Text.translatable("tooltip.ghost.leaf"))
                .build()
        );
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.grass"), getConfigValueInt("grass_color"))
                .setSaveConsumer(newValue -> setConfigValue("grass_color", newValue))
                .setTooltip(Text.translatable("tooltip.ghost.grass"))
                .build()
        );
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.water"), getConfigValueInt("water_color"))
                .setSaveConsumer(newValue -> setConfigValue("water_color", newValue))
                .setTooltip(Text.translatable("tooltip.ghost.water"))
                .build()
        );
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.waterfog"), getConfigValueInt("water_fog_color"))
                .setSaveConsumer(newValue -> setConfigValue("water_fog_color", newValue))
                .setTooltip(Text.translatable("tooltip.ghost.waterfog"))
                .build()
        );
        world.addEntry(entryBuilder.startIntSlider(Text.translatable("entry.ghost.time"), getConfigValueInt("time"), -1 , 24000)
                .setDefaultValue(0)
                .setMin(-1)
                .setMax(24000)
                .setTooltip(Text.translatable("tooltip.ghost.time"))
                .setSaveConsumer(newValue -> setConfigValue("time", newValue))
                .build()
        );
        // -- Presets
        world.addEntry(entryBuilder.startTextDescription(Text.translatable("label.ghost.world_presets")).build());
        world.addEntry(new ButtonBuilder(Text.of(UUID.randomUUID().toString()), Text.translatable("entry.ghost.plains_color")).setOnPress(button -> {
            setConfigValue("grass_color", 0x7aca60);
            savePresets();
        }).build());

        world.addEntry(new ButtonBuilder(Text.of(UUID.randomUUID().toString()), Text.translatable("entry.ghost.ocean_color")).setOnPress(button -> {
            setConfigValue("water_color", 0x00ccaa);
            setConfigValue("water_fog_color", 0x00ccaa);
            savePresets();
        }).build());

        world.addEntry(new ButtonBuilder(Text.of(UUID.randomUUID().toString()), Text.translatable("entry.ghost.jungle_color")).setOnPress(button -> {
            setConfigValue("foliage_color", 0x40cf00);
            setConfigValue("grass_color", 0x40cf00);
            savePresets();
        }).build());
        //12700
        world.addEntry(new ButtonBuilder(Text.of(UUID.randomUUID().toString()), Text.translatable("entry.ghost.sunset")).setOnPress(button -> {
            setConfigValue("time", 12700);
            savePresets();
        }).build());
    }

    static void GenerateGamemodes(ConfigBuilder builder, ConfigEntryBuilder entryBuilder) {
        ConfigCategory gamemodes = builder.getOrCreateCategory(Text.translatable("config_category.ghost.gamemodes"));

        final String[] camera_modes = {
                "normal",
                "topdown",
                "choppy"
        };

        gamemodes.addEntry(entryBuilder.startStringDropdownMenu(Text.translatable("entry.ghost.camera_type"), getConfigValueString("camera_type"))
                .setSelections(Arrays.asList(camera_modes))
                .setSuggestionMode(false)
                .setSaveConsumer(newValue -> setConfigValue("camera_type", newValue))
                .build()
        );
    }

    public static ConfigBuilder GenerateConfig() {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(MinecraftClient.getInstance().currentScreen)
                .setTitle(Text.translatable("title.ghost.config"))
                .setSavingRunnable(Config::save);
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        GenerateGeneral(builder, entryBuilder);
        GenerateVisuals(builder, entryBuilder);
        GenerateWorld(builder, entryBuilder);
        GenerateGamemodes(builder, entryBuilder);

        return builder;
    }
}
