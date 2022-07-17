package org.mineplugin.locusazzurro.pyrotechnicraft.client.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;

class CraftButton extends Button {
    public CraftButton(int x, int y, Button.OnPress pressAction) {
        super(x, y, 32, 20, new TextComponent("Craft"), pressAction);
        //todo lang
    }
}

