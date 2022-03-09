package rubyboat.ghost.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ibm.icu.text.ArabicShaping;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import rubyboat.ghost.client.GhostClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
    static boolean is_endtexture = false;
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
    public static boolean isEndTexture(){return getConfig().is_endtexture;}
    public static int getCameraDistance()
    {
        return getConfig().camera_distance;
    }

    public static SerializedConfig getConfig()
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


        Config.block = to_return.block;
        Config.is_slippery = to_return.is_slippery;
        Config.camera_type = to_return.camera_type;
        Config.is_endtexture = to_return.is_endtexture;
        return to_return;
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
        });

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("config_category.ghost.general"));
        ConfigCategory experimental = builder.getOrCreateCategory(new TranslatableText("config_category.ghost.experimental"));
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
        experimental.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.is_slippery"), Config.is_slippery).setSaveConsumer(newValue -> Config.is_slippery = newValue).build());
        experimental.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("entry.ghost.is_endtexture"), Config.is_endtexture).setSaveConsumer(newValue -> Config.is_endtexture = newValue).build());
        //Build
        return builder;
    }
    public static class SerializedConfig
    {
        public String block;
        public boolean is_slippery;
        public String camera_type;
        public int camera_distance;
        public boolean is_endtexture;

        public SerializedConfig()
        {
            this.block = Config.block;
            this.is_slippery = Config.is_slippery;
            this.camera_type = Config.camera_type;
            this.camera_distance = Config.camera_distance;
            this.is_endtexture = Config.is_endtexture;

        }
        public String serialized(){
            return new Gson().toJson(this);
        }
    }
}
