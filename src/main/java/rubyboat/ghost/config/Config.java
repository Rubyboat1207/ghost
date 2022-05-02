package rubyboat.ghost.config;

import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.ColorFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Config {
    static String block = "diamond_block";
    static String camera_type = "";
    static int camera_distance = 10;
    static String path = "ghost_config.json";
    static boolean is_slippery = false;
    static String player_texture = "none";
    static boolean is_sleeve = true;
    static int zoom_strength = 75;
    static boolean is_cyrus_mode = true;
    static boolean inNetherPortalEffect = false;
    static String inPowderSnowEffect = "none";
    static Integer fog = 000000;
    static String title = "Minecraft";
    static Integer color = 0;
    static Integer time = 0;
    static Integer leaf = 0;
    static Integer grass = 0;
    static boolean render_arms = true;
    static boolean render_legs = true;
    static boolean render_body = true;
    static boolean render_head = true;
    static float model_offset = 0;
    static String weather = "";
    static Integer water = 0;
    static Integer waterfog = 0;
    static String version = "";
    static int distance = 0;

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
    public static boolean isSleeve(){return getConfig().is_sleeve;}
    public static boolean isPortal(){return getConfig().in_portal;}
    public static boolean isCyrusMode(){return getConfig().is_cyrus_mode;}
    public static boolean isRender_arms(){return getConfig().render_arms;}
    public static boolean isRender_legs(){return getConfig().render_legs;}
    public static boolean isRender_body(){return getConfig().render_body;}
    public static boolean isRender_head(){return getConfig().render_head;}
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

        Config.is_sleeve = to_return.is_sleeve;
        Config.block = to_return.block;
        Config.is_slippery = to_return.is_slippery;
        Config.camera_type = to_return.camera_type;
        Config.player_texture = to_return.player_texture;
        Config.inNetherPortalEffect = to_return.in_portal;
        Config.inPowderSnowEffect = to_return.in_snow;
        Config.title = to_return.title;
        Config.color = to_return.biomecolor;
        Config.is_cyrus_mode = to_return.is_cyrus_mode;
        Config.time = to_return.time;
        Config.leaf = to_return.leaf;
        Config.grass = to_return.grass;
        Config.weather = to_return.weather;
        Config.zoom_strength = to_return.zoom_strength;
        Config.render_arms = to_return.render_arms;
        Config.render_legs = to_return.render_legs;
        Config.render_body = to_return.render_body;
        Config.render_head = to_return.render_head;
        Config.model_offset = to_return.model_offset;
        Config.water = to_return.water;
        Config.waterfog = to_return.waterfog;
        Config.version = to_return.version;
        Config.distance = to_return.distance;
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

    public static ConfigBuilder MakeConfig()
    {
        SerializedConfig sc = getConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(MinecraftClient.getInstance().currentScreen)
                .setTitle(new TranslatableText("title.ghost.config"));
        builder.setSavingRunnable(() -> {
            SerializedConfig config = new SerializedConfig();
            MinecraftClient.getInstance().worldRenderer.reload();
            try {
                Files.writeString(Path.of(path), config.serialized());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Config.config = loadConfig();
        });

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("config_category.ghost.general"));
        ConfigCategory experimental = builder.getOrCreateCategory(new TranslatableText("config_category.ghost.experimental"));
        ConfigCategory texture = builder.getOrCreateCategory(new TranslatableText("config_category.ghost.texture"));
        ConfigCategory biome = builder.getOrCreateCategory(new TranslatableText("config_category.ghost.biome"));
        ConfigCategory time = builder.getOrCreateCategory(new TranslatableText("config_category.ghost.time"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        //---ENTRIES
        DropdownMenuBuilder<String> blockmenu = entryBuilder.startStringDropdownMenu(new TranslatableText("entry.ghost.ghost_block"), Config.block)
                .setSelections(Arrays.asList(blocks))
                .setSuggestionMode(false)
                .setSaveConsumer(newValue -> Config.block = newValue
                );
        DropdownMenuBuilder<String> cameramenu = entryBuilder.startStringDropdownMenu(new TranslatableText("entry.ghost.camera_type"), Config.camera_type)
                .setSelections(Arrays.asList(camera_modes))
                .setSuggestionMode(false)
                .setSaveConsumer(newValue -> Config.camera_type = newValue
                );

        DropdownMenuBuilder<String> weather = entryBuilder.startStringDropdownMenu(new TranslatableText("entry.ghost.weather"), Config.weather)
                .setSelections(Arrays.asList(downfall))
                .setSuggestionMode(false)
                .setSaveConsumer(newValue -> Config.weather = newValue
                );
        general.addEntry(blockmenu.build());
        experimental.addEntry(cameramenu.build());
        if(camera_type.equalsIgnoreCase("topdown"))
        {
            experimental.addEntry(entryBuilder.startIntSlider(new TranslatableText("entry.ghost.camera_distance"), Config.camera_distance, 0, 100).setSaveConsumer(newValue -> Config.camera_distance = newValue).build());
        }
        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.is_slippery"), Config.is_slippery).setSaveConsumer(newValue -> Config.is_slippery = newValue).build());
        texture.addEntry(entryBuilder.startStrField(new TranslatableText("entry.ghost.player_texture"), Config.player_texture)
                .setSaveConsumer(newValue -> Config.player_texture = newValue)
                .setTooltip(new TranslatableText("tooltip.ghost.player_texture"))
                .build()
        );
        texture.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.is_sleeve"), Config.is_sleeve).setSaveConsumer(newValue -> Config.is_sleeve = newValue).build());
        general.addEntry(entryBuilder.startStrField(new TranslatableText("entry.ghost.title"), Config.title).setSaveConsumer(newValue -> Config.title = newValue).build());
        biome.addEntry(entryBuilder.startColorField(new TranslatableText("entry.ghost.color"), Config.color)
                .setSaveConsumer(newValue -> Config.color = newValue)
                .setTooltip(new TranslatableText("tooltip.ghost.color"))
                .build());
        experimental.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.cyrus_mode"), Config.is_cyrus_mode)
                .setSaveConsumer(newValue -> Config.is_cyrus_mode = newValue)
                .build()
        );
        biome.addEntry(entryBuilder.startColorField(new TranslatableText("entry.ghost.fog"), Config.fog)
                .setSaveConsumer(newValue -> Config.fog = newValue)
                .setTooltip(new TranslatableText("tooltip.ghost.fog"))
                .build());
        DropdownMenuBuilder<String> snow = entryBuilder.startStringDropdownMenu(new TranslatableText("entry.ghost.snow"), Config.inPowderSnowEffect)
                .setSelections(Arrays.asList(effects))
                .setSuggestionMode(false)
                .setSaveConsumer(newValue -> Config.inPowderSnowEffect = newValue
                );

       biome.addEntry(entryBuilder.startColorField(new TranslatableText("entry.ghost.leaf"), Config.leaf)
                .setSaveConsumer(newValue -> Config.leaf = newValue)
                .setTooltip(new TranslatableText("tooltip.ghost.leaf"))
                .build());
        biome.addEntry(entryBuilder.startColorField(new TranslatableText("entry.ghost.grass"), Config.grass)
                .setSaveConsumer(newValue -> Config.grass = newValue)
                .setTooltip(new TranslatableText("tooltip.ghost.grass"))
                .build());
        texture.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.render_arms"), Config.render_arms)
                .setSaveConsumer(newValue -> Config.render_arms = newValue)
                .build()
        );
        texture.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.render_legs"), Config.render_legs)
                .setSaveConsumer(newValue -> Config.render_legs = newValue)
                .build()
        );
        texture.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.render_body"), Config.render_body)
                .setSaveConsumer(newValue -> Config.render_body = newValue)
                .build()
        );
        texture.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.render_head"), Config.render_head)
                .setSaveConsumer(newValue -> Config.render_head = newValue)
                .build()
        );
        texture.addEntry(snow.build());
        time.addEntry(entryBuilder.startIntSlider(new TranslatableText("entry.ghost.time"), Config.time, -1 , 24000)
                .setDefaultValue(0)
                .setMin(-1)
                .setMax(24000)
                .setTooltip(new TranslatableText("tooltip.ghost.time"))
                .setSaveConsumer(newValue -> Config.time = newValue)
                .build()
        );
        general.addEntry(entryBuilder.startIntSlider(new TranslatableText("entry.ghost.zoom_strength"), Config.zoom_strength, 50 , 95)
                .setDefaultValue(0)
                .setMin(50)
                .setMax(95)
                .setSaveConsumer(newValue -> Config.zoom_strength = newValue)
                .build()
        );
        texture.addEntry(entryBuilder.startFloatField(new TranslatableText("entry.ghost.playerModelOffset"), Config.model_offset)
                .setSaveConsumer(newValue -> Config.model_offset = newValue)
                .build());
        biome.addEntry(entryBuilder.startColorField(new TranslatableText("entry.ghost.water"), Config.water)
                .setSaveConsumer(newValue -> Config.water = newValue)
                .setTooltip(new TranslatableText("tooltip.ghost.water"))
                .build());
        biome.addEntry(entryBuilder.startColorField(new TranslatableText("entry.ghost.waterfog"), Config.waterfog)
                .setSaveConsumer(newValue -> Config.waterfog = newValue)
                .setTooltip(new TranslatableText("tooltip.ghost.waterfog"))
                .build());
        general.addEntry(entryBuilder.startStrField(new TranslatableText("entry.ghost.version"), Config.version)
                .setSaveConsumer(newValue -> Config.version = newValue)
                .build());
        general.addEntry(entryBuilder.startIntField(new TranslatableText("entry.ghost.distance"), Config.distance)
                .setSaveConsumer(newValue -> Config.distance = newValue)
                .build());
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
        public float model_offset;
        public Integer water;
        public Integer waterfog;
        public String version;
        public int distance;

        public int zoom_strength;

        public SerializedConfig()
        {
            this.block = Config.block;
            this.is_slippery = Config.is_slippery;
            this.camera_type = Config.camera_type;
            this.camera_distance = Config.camera_distance;
            this.player_texture = Config.player_texture;
            this.is_sleeve = Config.is_sleeve;
            this.in_snow = Config.inPowderSnowEffect;
            this.in_portal = Config.inNetherPortalEffect;
            this.title = Config.title;
            this.biomecolor = Config.color;
            this.fog = Config.fog;
            this.is_cyrus_mode = Config.is_cyrus_mode;
            this.time = Config.time;
            this.leaf = Config.leaf;
            this.grass = Config.grass;
            this.weather = Config.weather;
            this.zoom_strength = Config.zoom_strength;
            this.render_arms = Config.render_arms;
            this.render_legs = Config.render_legs;
            this.render_body = Config.render_body;
            this.render_head = Config.render_head;
            this.model_offset = Config.model_offset;
            this.water = Config.water;
            this.waterfog = Config.waterfog;
            this.version = Config.version;
            this.distance = Config.distance;
        }
        public String serialized(){
            return new Gson().toJson(this);
        }
    }
}
