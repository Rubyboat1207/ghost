package rubyboat.clothconfigextensions.builders;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import rubyboat.clothconfigextensions.entries.ButtonEntry;

public class ButtonBuilder {
    Text value;
    Text id;
    ButtonWidget.PressAction onPress;
    int xbuffer = 0;

    public ButtonBuilder(Text component, Text value) {
        this.id = component;
        this.value = value;
    }

    public ButtonBuilder setOnPress(ButtonWidget.PressAction onPress) {
        this.onPress = onPress;
        return this;
    }

    public  ButtonBuilder setWidthBuffer(int xbuffer) {
        this.xbuffer = xbuffer;
        return this;
    }

    public ButtonEntry build() {
        return new ButtonEntry(id, value, onPress, xbuffer);
    }
}