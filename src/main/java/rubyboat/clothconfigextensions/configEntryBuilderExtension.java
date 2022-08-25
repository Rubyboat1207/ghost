package rubyboat.clothconfigextensions;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ModifierKeyCode;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry;
import me.shedaniel.clothconfig2.impl.ConfigEntryBuilderImpl;
import me.shedaniel.clothconfig2.impl.builders.*;
import net.minecraft.text.Text;
import rubyboat.clothconfigextensions.builders.ButtonBuilder;

import java.util.List;
import java.util.UUID;

public class configEntryBuilderExtension implements ConfigEntryBuilder {

    @Override
    public Text getResetButtonKey() {
        return null;
    }

    @Override
    public ConfigEntryBuilder setResetButtonKey(Text text) {
        return null;
    }

    @Override
    public IntListBuilder startIntList(Text text, List<Integer> list) {
        return null;
    }

    @Override
    public LongListBuilder startLongList(Text text, List<Long> list) {
        return null;
    }

    @Override
    public FloatListBuilder startFloatList(Text text, List<Float> list) {
        return null;
    }

    @Override
    public DoubleListBuilder startDoubleList(Text text, List<Double> list) {
        return null;
    }

    @Override
    public StringListBuilder startStrList(Text text, List<String> list) {
        return null;
    }

    @Override
    public SubCategoryBuilder startSubCategory(Text text) {
        return null;
    }

    @Override
    public SubCategoryBuilder startSubCategory(Text text, List<AbstractConfigListEntry> list) {
        return null;
    }

    @Override
    public BooleanToggleBuilder startBooleanToggle(Text text, boolean b) {
        return null;
    }

    @Override
    public StringFieldBuilder startStrField(Text text, String s) {
        return null;
    }

    @Override
    public ColorFieldBuilder startColorField(Text text, int i) {
        return null;
    }

    @Override
    public TextFieldBuilder startTextField(Text text, String s) {
        return null;
    }

    @Override
    public TextDescriptionBuilder startTextDescription(Text text) {
        return null;
    }

    @Override
    public <T extends Enum<?>> EnumSelectorBuilder<T> startEnumSelector(Text text, Class<T> aClass, T t) {
        return null;
    }

    @Override
    public <T> SelectorBuilder<T> startSelector(Text text, T[] ts, T t) {
        return null;
    }

    public ButtonBuilder startButtonBuilder(Text value) {
        return new ButtonBuilder(Text.of(UUID.randomUUID().toString()), value);
    }

    @Override
    public IntFieldBuilder startIntField(Text text, int i) {
        return null;
    }

    @Override
    public LongFieldBuilder startLongField(Text text, long l) {
        return null;
    }

    @Override
    public FloatFieldBuilder startFloatField(Text text, float v) {
        return null;
    }

    @Override
    public DoubleFieldBuilder startDoubleField(Text text, double v) {
        return null;
    }

    @Override
    public IntSliderBuilder startIntSlider(Text text, int i, int i1, int i2) {
        return null;
    }

    @Override
    public LongSliderBuilder startLongSlider(Text text, long l, long l1, long l2) {
        return null;
    }

    @Override
    public KeyCodeBuilder startModifierKeyCodeField(Text text, ModifierKeyCode modifierKeyCode) {
        return null;
    }

    @Override
    public <T> DropdownMenuBuilder<T> startDropdownMenu(Text text, DropdownBoxEntry.SelectionTopCellElement<T> selectionTopCellElement, DropdownBoxEntry.SelectionCellCreator<T> selectionCellCreator) {
        return null;
    }
}
