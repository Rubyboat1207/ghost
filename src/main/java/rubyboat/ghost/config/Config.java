package rubyboat.ghost.config;

import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
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
    static String player_texture = "";
    static boolean is_sleeve = true;
    static boolean is_cyrus_mode = false;
    static boolean is_thirst = false;
    static boolean inNetherPortalEffect = false;
    static String inPowderSnowEffect = "none";
    static Integer fog = 000000;
    static String title = "";
    static Integer color = 0;

    public static String[] blocks = {
            "diamond_block",
            "bedrock",
            "dirt",
            "pink_wool",
            "polished_andesite",
            "dried_kelp"
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

    public static String getBlock()
    {
        return Arrays.asList(blocks).contains(getConfig().block) ? getConfig().block : "poppy";
    }
    public static String getCamera_type()
    {
        return Arrays.asList(camera_modes).contains(getConfig().camera_type) ? getConfig().camera_type : "normal";
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
    public static boolean isSleeve(){return getConfig().is_sleeve;}
    public static boolean isPortal(){return getConfig().in_portal;}
    public static boolean is_thirst(){return getConfig().is_thirst;}
    public static boolean isCyrusMode(){return getConfig().is_cyrus_mode;}
    public static String getTitle(){return getConfig().title;}
    public static Integer color(){return getConfig().biomecolor;}
    public static Integer fog(){return getConfig().fog;}
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
        Config.is_thirst = to_return.is_thirst;
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
        ConfigCategory challenges = builder.getOrCreateCategory(new TranslatableText("config_category.ghost.challenges"));
        ConfigCategory biome = builder.getOrCreateCategory(new TranslatableText("config_category.ghost.biome"));
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
        experimental.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.thirst"), Config.is_cyrus_mode)
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
        texture.addEntry(snow.build());
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
        public boolean is_thirst;
        public boolean is_cyrus_mode;

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
            this.is_thirst = Config.is_thirst;

        }
        public String serialized(){
            return new Gson().toJson(this);
        }
    }
}
