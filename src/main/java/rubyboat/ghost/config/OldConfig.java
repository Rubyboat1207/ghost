package rubyboat.ghost.config;

import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import rubyboat.clothconfigextensions.builders.ButtonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

public class OldConfig {
    //I AM SO SORRY FOR ANYONE WHO HAS TO READ THIS CODE
    //to add to config, you must add it in 3 places:
    //1. add it to the config class
    //2. add it to the serialized config class
    //3. add it to the loadConfig method

    /*
        Challenge Ideas:
        Minecraft but only chunks I have been on render
        Minecraft but only one block renders at a time

        Notes:
        BlockRenderManager Exists
     */


    static String block = "diamond_block";
    static String camera_type = "";
    static int camera_distance = 4;
    static String path = "ghost_config.json";
    static boolean is_slippery = false;
    static String player_texture = "";
    static boolean is_sleeve = true;
    static int zoom_strength = 75;
    static boolean is_cyrus_mode = true;
    static boolean inNetherPortalEffect = false;
    static boolean bouncy = false;
    static boolean antfarm = false;
    static String inPowderSnowEffect = "none";
    static Integer fog = 0;
    static String title = "";
    static Integer color = 0; // Sky color
    static Integer time = -1;
    static Integer leaf = 0;
    static Integer grass = 0;
    static boolean render_arms = true;
    static boolean render_legs = true;
    static boolean render_body = true;
    static boolean render_head = true;
    static boolean technoblade = true;
    static float model_offset = 0;
    static String weather = "";
    static Integer water = 0;
    static Integer waterfog = 0;
    static String version = "";
    static int distance = 4;
    static boolean durability = false;
    static double durability_percentage = 10;
    //getters for size changers


    public static String[] blocks = {
            "diamond_block",
            "bedrock",
            "dirt",
            "pink_wool",
            "polished_andesite"
    };
    public static String[] camera_modes = {
            "normal",
            "topdown",
            "choppy"
    };
    public static String[] effects = {
            "none",
            "snow",
            "water",
            "lava"
    };
    public static String[] downfall = {
            "none",
            "rain",
            "thunder",
            "snow",
            "test"
    };

    public static String getBlock()
    {
        return Arrays.asList(blocks).contains(getConfig().block) ? getConfig().block : "poppy";
    }
    public static String getCamera_type()
    {
        return Arrays.asList(camera_modes).contains(getConfig().camera_type) ? getConfig().camera_type : "normal";
    }
    public static String getWeather(){
        return Arrays.asList(downfall).contains(getConfig().weather) ? getConfig().weather : "none";
    }

    public static boolean isSlippery()
    {
        return getConfig().is_slippery;
    }
    public static String getInPowderSnowEffect() {
        return Arrays.asList(effects).contains(getConfig().in_snow) ? getConfig().in_snow : "none";
    }
    public static String getPlayerTexture(){return getConfig().player_texture;}
    public static int getCameraDistance()
    {
        return getConfig().camera_distance;
    }
    public static String getTitle()
    {
        return getConfig().title;
    }
    public static int getZoomStrength()
    {
        return getConfig().zoom_strength;
    }
    public static boolean getBouncy()
    {
        return getConfig().bouncy;
    }
    public static boolean isTechnoblade()
    {
        return getConfig().technoblade;
    }
    public static boolean isSleeve(){return getConfig().is_sleeve;}
    public static boolean isPortal(){return getConfig().in_portal;}
    public static boolean disableNegatives(){return getConfig().is_cyrus_mode;}
    public static boolean isRender_arms(){return getConfig().render_arms;}
    public static boolean isRender_legs(){return getConfig().render_legs;}
    public static boolean isRender_body(){return getConfig().render_body;}
    public static boolean isRender_head(){return getConfig().render_head;}
    public static boolean isAntfarm(){return false;}
    public static float getModelOffset(){return getConfig().model_offset;}
    public static String title(){return getConfig().title;}
    public static Integer color(){return getConfig().biomecolor;}
    public static Integer fog(){return getConfig().fog;}
    public static Integer time(){return getConfig().time;}
    public static Integer leaf(){return getConfig().leaf;}
    public static Integer grass(){return getConfig().grass;}
    public static Integer water(){return getConfig().water;}
    public static Integer waterfog(){return getConfig().waterfog;}
    public static String getVersion(){return getConfig().version;}
    public static Integer getDistance(){return getConfig().distance;}
    public static boolean getDurability(){return getConfig().durability;}
    public static double getDurabilityPercentage(){return getConfig().durability_percentage / 100;}

    public static SerializedConfig config = null;
    public static SerializedConfig loadConfig()
    {
        File file = new File(path);
        String fromfile = "";
        try{
            fromfile = Files.readString(Path.of(path));
        }catch (Exception e)
        {
            try{
                Files.writeString(Path.of(path), new SerializedConfig().serialized());
                fromfile = Files.readString(Path.of(path));
            }catch (Exception ignored) { }
            e.printStackTrace();
        }
        SerializedConfig to_return = new Gson().fromJson(fromfile, SerializedConfig.class);

        OldConfig.is_sleeve = to_return.is_sleeve;
        OldConfig.block = to_return.block;
        OldConfig.is_slippery = to_return.is_slippery;
        OldConfig.camera_type = to_return.camera_type;
        OldConfig.player_texture = to_return.player_texture;
        OldConfig.inNetherPortalEffect = to_return.in_portal;
        OldConfig.inPowderSnowEffect = to_return.in_snow;
        OldConfig.title = to_return.title;
        OldConfig.color = to_return.biomecolor;
        OldConfig.is_cyrus_mode = to_return.is_cyrus_mode;
        OldConfig.time = to_return.time;
        OldConfig.leaf = to_return.leaf;
        OldConfig.grass = to_return.grass;
        OldConfig.weather = to_return.weather;
        OldConfig.zoom_strength = to_return.zoom_strength;
        OldConfig.render_arms = to_return.render_arms;
        OldConfig.render_legs = to_return.render_legs;
        OldConfig.render_body = to_return.render_body;
        OldConfig.render_head = to_return.render_head;
        OldConfig.model_offset = to_return.model_offset;
        OldConfig.water = to_return.water;
        OldConfig.waterfog = to_return.waterfog;
        OldConfig.bouncy = to_return.bouncy;
        OldConfig.distance = to_return.distance;
        OldConfig.technoblade = to_return.technoblade;
        OldConfig.durability = to_return.durability;
        OldConfig.durability_percentage = to_return.durability_percentage;
        return to_return;
    }


    public static SerializedConfig getConfig()
    {
        if(config == null)
        {
            config =loadConfig();
            return config;
        }
        return config;
    }

    public static void save() {
        save(true);
    }
    public static void save(boolean reload) {
        SerializedConfig config = new SerializedConfig();
        if(reload) {
            MinecraftClient.getInstance().worldRenderer.reload();
            MinecraftClient.getInstance().reloadResources();
        }
        try {
            Files.writeString(Path.of(path), config.serialized());
        } catch (IOException e) {
            e.printStackTrace();
        }
        OldConfig.config = loadConfig();
    }

    public static ConfigBuilder MakeConfig()
    {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(MinecraftClient.getInstance().currentScreen)
                .setTitle(Text.translatable("title.ghost.config"));
        builder.setSavingRunnable(OldConfig::save);

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config_category.ghost.general"));
        //ConfigCategory experimental = builder.getOrCreateCategory(Text.translatable("config_category.ghost.experimental"));
        ConfigCategory texture = builder.getOrCreateCategory(Text.translatable("config_category.ghost.texture"));
        ConfigCategory world = builder.getOrCreateCategory(Text.translatable("config_category.ghost.world"));
        ConfigCategory gamemodes = builder.getOrCreateCategory(Text.translatable("config_category.ghost.gamemodes"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        //---ENTRIES

        DropdownMenuBuilder<String> cameramenu = entryBuilder.startStringDropdownMenu(Text.translatable("entry.ghost.camera_type"), OldConfig.camera_type)
                .setSelections(Arrays.asList(camera_modes))
                .setSuggestionMode(false)
                .setSaveConsumer(newValue -> OldConfig.camera_type = newValue
                );
        general.addEntry(entryBuilder.startStringDropdownMenu(Text.translatable("entry.ghost.ghost_block"), OldConfig.block)
                .setSelections(Arrays.asList(blocks))
                .setSuggestionMode(false)
                .setSaveConsumer(newValue -> OldConfig.block = newValue)
                .build()
        );
        gamemodes.addEntry(cameramenu.build());
        if(camera_type.equalsIgnoreCase("topdown"))
        {
            gamemodes.addEntry(entryBuilder.startIntSlider(Text.translatable("entry.ghost.camera_distance"), OldConfig.camera_distance, 3, 100).setSaveConsumer(newValue -> OldConfig.camera_distance = newValue).build());
        }
        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.is_slippery"), OldConfig.is_slippery).setSaveConsumer(newValue -> OldConfig.is_slippery = newValue).build());
        texture.addEntry(entryBuilder.startStrField(Text.translatable("entry.ghost.player_texture"), OldConfig.player_texture)
                .setSaveConsumer(newValue -> OldConfig.player_texture = newValue)
                .setTooltip(Text.translatable("tooltip.ghost.player_texture"))
                .build()
        );
        texture.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.is_sleeve"), OldConfig.is_sleeve).setSaveConsumer(newValue -> OldConfig.is_sleeve = newValue).build());
        texture.addEntry(entryBuilder.startTextDescription(Text.translatable("label.ghost.model_visibility")).build());
        //Update Model Visibility
        texture.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.render_arms"), OldConfig.render_arms)
                .setSaveConsumer(newValue -> OldConfig.render_arms = newValue)
                .build()
        );
        texture.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.render_legs"), OldConfig.render_legs)
                .setSaveConsumer(newValue -> OldConfig.render_legs = newValue)
                .build()
        );
        texture.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.render_body"), OldConfig.render_body)
                .setSaveConsumer(newValue -> OldConfig.render_body = newValue)
                .build()
        );
        texture.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.render_head"), OldConfig.render_head)
                .setSaveConsumer(newValue -> OldConfig.render_head = newValue)
                .build()
        );
        texture.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.technoblade"), OldConfig.technoblade)
            .setSaveConsumer(newValue -> OldConfig.technoblade = newValue)
            .setTooltip(Text.translatable("tooltip.ghost.technoblade"))
            .build()
        );

        /*gamemodes.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.AntFarm"), Config.antfarm)
                .setSaveConsumer(newValue -> Config.antfarm = newValue)
                .build()
        );*/
        general.addEntry(entryBuilder.startStrField(Text.translatable("entry.ghost.title"), OldConfig.title).setSaveConsumer(newValue -> OldConfig.title = newValue).build());
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.color"), OldConfig.color)
                .setSaveConsumer(newValue -> OldConfig.color = newValue)
                .setTooltip(Text.translatable("tooltip.ghost.color"))
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.cyrus_mode"), OldConfig.is_cyrus_mode)
                .setSaveConsumer(newValue -> OldConfig.is_cyrus_mode = newValue)
                .build()
        );
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.fog"), OldConfig.fog)
                .setSaveConsumer(newValue -> OldConfig.fog = newValue)
                .setTooltip(Text.translatable("tooltip.ghost.fog"))
                .build());
        DropdownMenuBuilder<String> snow = entryBuilder.startStringDropdownMenu(Text.translatable("entry.ghost.snow"), OldConfig.inPowderSnowEffect)
                .setSelections(Arrays.asList(effects))
                .setSuggestionMode(false)
                .setSaveConsumer(newValue -> OldConfig.inPowderSnowEffect = newValue
                );

       world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.leaf"), OldConfig.leaf)
                .setSaveConsumer(newValue -> OldConfig.leaf = newValue)
                .setTooltip(Text.translatable("tooltip.ghost.leaf"))
                .build());
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.grass"), OldConfig.grass)
                .setSaveConsumer(newValue -> OldConfig.grass = newValue)
                .setTooltip(Text.translatable("tooltip.ghost.grass"))
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.bouncy"), OldConfig.bouncy)
                .setSaveConsumer(newValue -> OldConfig.bouncy = newValue)
                .build()
        );
        texture.addEntry(snow.build());

        texture.addEntry(entryBuilder.startFloatField(Text.translatable("entry.ghost.player_model_offset"), OldConfig.model_offset)
                .setSaveConsumer(newValue -> OldConfig.model_offset = newValue)
                .build());
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.water"), OldConfig.water)
                .setSaveConsumer(newValue -> OldConfig.water = newValue)
                .setTooltip(Text.translatable("tooltip.ghost.water"))
                .build());
        world.addEntry(entryBuilder.startColorField(Text.translatable("entry.ghost.waterfog"), OldConfig.waterfog)
                .setSaveConsumer(newValue -> OldConfig.waterfog = newValue)
                .setTooltip(Text.translatable("tooltip.ghost.waterfog"))
                .build());
        world.addEntry(entryBuilder.startIntSlider(Text.translatable("entry.ghost.time"), OldConfig.time, -1 , 24000)
                .setDefaultValue(0)
                .setMin(-1)
                .setMax(24000)
                .setTooltip(Text.translatable("tooltip.ghost.time"))
                .setSaveConsumer(newValue -> OldConfig.time = newValue)
                .build()
        );
        general.addEntry(entryBuilder.startStrField(Text.translatable("entry.ghost.version"), OldConfig.version)
                .setSaveConsumer(newValue -> OldConfig.version = newValue)
                .build());
        general.addEntry(entryBuilder.startIntField(Text.translatable("entry.ghost.distance"), OldConfig.distance)
                .setSaveConsumer(newValue -> OldConfig.distance = newValue)
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("entry.ghost.durability"), OldConfig.durability)
                .setSaveConsumer((newValue) -> OldConfig.durability = newValue)
                .build());
        if(OldConfig.durability) {
            general.addEntry(entryBuilder.startDoubleField(Text.translatable("entry.ghost.durability_percentage"), OldConfig.durability_percentage)
                .setSaveConsumer(newValue -> OldConfig.durability_percentage = newValue)
                .setMin(0)
                .setMax(100)
                .build());
        }
        world.addEntry(entryBuilder.startTextDescription(Text.translatable("label.ghost.world_presets")).build());
        world.addEntry(new ButtonBuilder(Text.of(UUID.randomUUID().toString()), Text.translatable("entry.ghost.plains_color")).setOnPress(button -> {
            OldConfig.grass = 0x7aca60;
            config.grass = 0x7aca60;
            MinecraftClient.getInstance().setScreen(null);
            save();
        }).build());

        world.addEntry(new ButtonBuilder(Text.of(UUID.randomUUID().toString()), Text.translatable("entry.ghost.ocean_color")).setOnPress(button -> {
            OldConfig.water = 0x00ccaa;
            config.water = 0x00ccaa;
            OldConfig.waterfog = 0x00ccaa;
            config.waterfog = 0x00ccaa;
            MinecraftClient.getInstance().setScreen(null);
            save();
        }).build());

        world.addEntry(new ButtonBuilder(Text.of(UUID.randomUUID().toString()), Text.translatable("entry.ghost.jungle_color")).setOnPress(button -> {
            OldConfig.grass = 0x40cf00;
            config.grass = 0x40cf00;
            OldConfig.leaf = 0x40cf00;
            config.leaf = 0x40cf00;
            MinecraftClient.getInstance().setScreen(null);
            save();
        }).build());
        //12700
        world.addEntry(new ButtonBuilder(Text.of(UUID.randomUUID().toString()), Text.translatable("entry.ghost.sunset")).setOnPress(button -> {
            OldConfig.time = 12700;
            config.time = 12700;
            MinecraftClient.getInstance().setScreen(null);
            save();
        }).build());
        texture.addEntry(new ButtonBuilder(Text.of(UUID.randomUUID().toString()), Text.translatable("entry.ghost.moleman")).setOnPress(button -> {
            OldConfig.render_arms = false;
            OldConfig.render_legs = false;
            OldConfig.render_body = false;
            OldConfig.render_head = true;
            OldConfig.model_offset = -1.4f;
            config.render_arms = false;
            config.render_legs = false;
            config.render_body = false;
            config.render_head = true;
            config.model_offset = -1.4f;
            MinecraftClient.getInstance().setScreen(null);
            save();
        }).build());
        //Build
        return builder;
    }
    public static class SerializedConfig
    {
        public String block;
        public boolean is_slippery;
        public String camera_type;
        public int camera_distance;
        public String player_texture;
        public boolean is_sleeve;
        public boolean in_portal;
        public String in_snow;
        public String title;
        public Integer biomecolor;
        public Integer fog;
        public boolean is_cyrus_mode;
        public Integer time;
        public Integer leaf;
        public Integer grass;
        public String weather;
        public boolean render_arms;
        public boolean render_legs;
        public boolean render_body;
        public boolean render_head;
        public boolean technoblade;
        public boolean bouncy;
        public boolean antfarm;
        public float model_offset;
        public Integer water;
        public Integer waterfog;
        public String version;
        public int distance;

        public int zoom_strength;
        public boolean durability;
        public double durability_percentage;

        public SerializedConfig()
        {
            this.block = OldConfig.block;
            this.is_slippery = OldConfig.is_slippery;
            this.camera_type = OldConfig.camera_type;
            this.camera_distance = OldConfig.camera_distance;
            this.player_texture = OldConfig.player_texture;
            this.is_sleeve = OldConfig.is_sleeve;
            this.in_snow = OldConfig.inPowderSnowEffect;
            this.in_portal = OldConfig.inNetherPortalEffect;
            this.title = OldConfig.title;
            this.biomecolor = OldConfig.color;
            this.fog = OldConfig.fog;
            this.is_cyrus_mode = OldConfig.is_cyrus_mode;
            this.time = OldConfig.time;
            this.leaf = OldConfig.leaf;
            this.grass = OldConfig.grass;
            this.weather = OldConfig.weather;
            this.zoom_strength = OldConfig.zoom_strength;
            this.render_arms = OldConfig.render_arms;
            this.render_legs = OldConfig.render_legs;
            this.render_body = OldConfig.render_body;
            this.render_head = OldConfig.render_head;
            this.model_offset = OldConfig.model_offset;
            this.water = OldConfig.water;
            this.waterfog = OldConfig.waterfog;
            this.version = OldConfig.version;
            this.distance = OldConfig.distance;
            this.bouncy = OldConfig.bouncy;
            this.antfarm = OldConfig.antfarm;
            this.technoblade = OldConfig.technoblade;
            this.durability = OldConfig.durability;
            this.durability_percentage = OldConfig.durability_percentage;
        }
        public String serialized(){
            return new Gson().toJson(this);
        }
    }
}
