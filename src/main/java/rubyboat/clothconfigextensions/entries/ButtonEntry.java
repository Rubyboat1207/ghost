package rubyboat.clothconfigextensions.entries;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.gui.AbstractConfigScreen;
import me.shedaniel.clothconfig2.gui.entries.TooltipListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ButtonEntry extends TooltipListEntry<Object> {
    public static final int LINE_HEIGHT = 12;
    private final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    Text buttonInnerText;
    private int savedWidth = -1;
    private int savedX = -1;
    private int savedY = -1;

    public int xbuffer;
    public int ybuffer;

    private final List<ButtonWidget> widgets;
    private ButtonWidget button;
    private List<OrderedText> wrappedLines;
    @ApiStatus.Internal
    @Deprecated
    public ButtonEntry(Text fieldName, Text text, ButtonWidget.PressAction onPress, int xbuffer) {
        this(fieldName, text, null, onPress, xbuffer);
    }

    @ApiStatus.Internal
    @Deprecated
    public ButtonEntry(Text fieldName, Text text, Supplier<Optional<Text[]>> tooltipSupplier, ButtonWidget.PressAction onPress, int xbuffer) {
        super(fieldName, tooltipSupplier);
        this.buttonInnerText = text;
        this.wrappedLines = Collections.emptyList();
        this.button = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getWidth(text) + 6 + xbuffer, 20, text, onPress);
        widgets = Lists.newArrayList(button);
    }

    @Override
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
        super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, isHovered, delta);
        if (this.savedWidth != entryWidth || this.savedX != x || this.savedY != y) {
            this.wrappedLines = this.textRenderer.wrapLines(this.buttonInnerText, entryWidth);
            this.savedWidth = entryWidth;
            this.savedX = x;
            this.savedY = y;
        }
        button.x = getConfigScreen().width / 2 - button.getWidth() / 2;
        button.y = y;
        button.render(matrices, mouseX, mouseY, delta);


        Style style = this.getTextAt(mouseX, mouseY);
        AbstractConfigScreen configScreen = this.getConfigScreen();
        if (style != null && configScreen != null) {
            configScreen.renderTextHoverEffect(matrices, style, mouseX, mouseY);
        }
    }

    @Override
    public int getItemHeight() {
        if (savedWidth == -1) return LINE_HEIGHT;
        int lineCount = this.wrappedLines.size();
        return lineCount == 0 ? 0 : 15 + lineCount * LINE_HEIGHT;
    }

    @Override
    public List<? extends Selectable> narratables() {
        return null;
    }

    @Nullable
    private Style getTextAt(double x, double y) {
        int lineCount = this.wrappedLines.size();

        if (lineCount > 0) {
            int textX = (int) Math.floor(x - this.savedX);
            int textY = (int) Math.floor(y - 4 - this.savedY);
            if (textX >= 0 && textY >= 0 && textX <= this.savedWidth && textY < LINE_HEIGHT * lineCount + lineCount) {
                int line = textY / LINE_HEIGHT;
                if (line < this.wrappedLines.size()) {
                    OrderedText orderedText = this.wrappedLines.get(line);
                    return this.textRenderer.getTextHandler().getStyleAt(orderedText, textX);
                }
            }
        }
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Optional<Object> getDefaultValue() {
        return Optional.empty();
    }

    @Override
    public void save() {

    }

    @Override
    public List<? extends Element> children() {
        return widgets;
    }
}