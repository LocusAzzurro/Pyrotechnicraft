package org.mineplugin.locusazzurro.pyrotechnicraft.client.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

class CraftButton extends Button {

    private static final TranslatableComponent TEXT_CRAFT = new TranslatableComponent("screen.pyrotechnicraft.firework_crafting_table.button.craft");
    public CraftButton(int x, int y, Button.OnPress pressAction) {
        super(x, y, 32, 20, TEXT_CRAFT, pressAction);
        //todo lang
    }
}

