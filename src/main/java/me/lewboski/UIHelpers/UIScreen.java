package me.lewboski.UIHelpers;

import me.lewboski.GeneralHelper.InventoryHelper;
import me.lewboski.PlayerStateHelper.AutoSellHelper;
import me.lewboski.PlayerStateHelper.CroppingHelper;
import me.lewboski.PlayerStateHelper.MiningHelper;
import me.lewboski.SSMod;
import me.lewboski.Utils.TimeUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;

public class UIScreen extends GuiScreen {
    private CustomUIButton miningButton;
    private CustomUIButton croppingButton;
    private CustomUIButton resetButton;
    private CustomUIButton autoSellButton;

    @Override
    public void initGui() {
        try {
            int buttonWidth = 80; // Smaller width
            int buttonHeight = 20; // Default height, adjust as needed
            int xPosition = this.width - buttonWidth - 10; // 10 pixels from the right edge
            int yPosition = this.height - buttonHeight - 10; // 10 pixels from the bottom

            this.resetButton = new CustomUIButton(0, xPosition, yPosition, "Reset", true, buttonWidth, buttonHeight);
            this.miningButton = new CustomUIButton(1, this.width / 2 - 100, this.height / 2 - 24, "Activate Mining", true);
            this.croppingButton = new CustomUIButton(2, this.width / 2 - 100, this.height / 2 + 4, "Activate Cropping", true);
            this.autoSellButton = new CustomUIButton(3, this.width / 2 - 100, this.height / 2 + 32, "Activate AutoSell", true);

            this.buttonList.add(miningButton);
            this.buttonList.add(croppingButton);
            this.buttonList.add(autoSellButton);
            this.buttonList.add(resetButton);
        } catch (Exception e) {
            System.out.println("Error caught for 'initGui'");
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            updateButtonDisplayText();
            updateButtonEnabledStatus();

            CroppingHelper.stopCropping();
            this.drawDefaultBackground();
            super.drawScreen(mouseX, mouseY, partialTicks);
        } catch (Exception e) {
            System.out.println("Error caught for 'drawScreen'");
        }
    }

    private void updateButtonDisplayText() {
        try {
            String miningText = (SSMod.currentState == SSMod.PlayerState.MINING) ? "Deactivate Mining" : "Activate Mining";
            String croppingText = (SSMod.currentState == SSMod.PlayerState.CROPPING) ? "Deactivate Cropping" : "Activate Cropping";
            String autoSellText = (SSMod.currentState == SSMod.PlayerState.AUTOSELL) ? "Deactivate AutoSell" : "Activate AutoSell";

            this.miningButton.displayString = miningText;
            this.croppingButton.displayString = croppingText;
            this.autoSellButton.displayString = autoSellText;
        } catch (Exception e) {
            System.out.println("Error caught for 'updateButtonDisplayText'");
        }
    }

    private void updateButtonEnabledStatus() {
        this.miningButton.setShouldBeEnabled(isStateOneOf(SSMod.PlayerState.MINING, SSMod.PlayerState.IDLE));
        this.croppingButton.setShouldBeEnabled(isStateOneOf(SSMod.PlayerState.CROPPING, SSMod.PlayerState.IDLE));
        this.autoSellButton.setShouldBeEnabled(isStateOneOf(SSMod.PlayerState.AUTOSELL, SSMod.PlayerState.IDLE));
    }

    private boolean isStateOneOf(SSMod.PlayerState @NotNull ... states) {
        try {
            for (SSMod.PlayerState state : states) {
                if (SSMod.currentState == state) return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error caught for 'isStateOneOf'");
            return false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        try {
            if (button == miningButton) {
                InventoryHelper.closeInventory();
                TimeUtil.sleep();
                MiningHelper.toggleCheck();
            } else if (button == croppingButton) {
                InventoryHelper.closeInventory();
                TimeUtil.sleep();
                CroppingHelper.toggleCheck();
            } else if (button == autoSellButton) {
                InventoryHelper.closeInventory();
                TimeUtil.sleep();
                AutoSellHelper.toggleCheck();
            } else if (button == resetButton) {
                SSMod.stopAll();
            }
        } catch (Exception e) {
            System.out.println("Error caught for 'actionPerformed'");
        }
    }
}
