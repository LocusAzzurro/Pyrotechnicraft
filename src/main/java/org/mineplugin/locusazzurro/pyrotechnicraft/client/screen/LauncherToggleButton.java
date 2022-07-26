package org.mineplugin.locusazzurro.pyrotechnicraft.client.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.PacketHandler;
import org.mineplugin.locusazzurro.pyrotechnicraft.network.ServerboundFireworkMissileCraftingTablePacket;

public class LauncherToggleButton extends Button {

    public LauncherToggleButton(int x, int y, Button.OnPress pressAction) {
        super(x, y, 32, 20, new TextComponent("+5"), pressAction);
    }

}
