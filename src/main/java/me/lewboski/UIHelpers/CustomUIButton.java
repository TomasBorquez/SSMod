package me.lewboski.UIHelpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class CustomUIButton extends GuiButton {
    private boolean shouldBeEnabled;

    public CustomUIButton(int buttonId, int x, int y, String buttonText, boolean shouldBeEnabled) {
        super(buttonId, x, y, buttonText);
        this.shouldBeEnabled = shouldBeEnabled;
    }

    public CustomUIButton(int buttonId, int x, int y, String buttonText, boolean shouldBeEnabled, int width, int height) {
        super(buttonId, x, y, buttonText);
        this.width = width;
        this.height = height;
        this.shouldBeEnabled = shouldBeEnabled;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.enabled = shouldBeEnabled;
        super.drawButton(mc, mouseX, mouseY);
    }

    public void setShouldBeEnabled(boolean shouldBeEnabled) {
        this.shouldBeEnabled = shouldBeEnabled;
    }
}
