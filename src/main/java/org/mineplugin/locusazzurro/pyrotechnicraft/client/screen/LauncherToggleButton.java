package org.mineplugin.locusazzurro.pyrotechnicraft.client.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Supplier;

public class LauncherToggleButton extends Button {

    public LauncherToggleButton(int x, int y, boolean positive, Button.OnPress pressAction) {
        super(x, y, 32, 20, Component.literal(positive ? "+" : "-"), pressAction, c -> Component.empty());
    }

}
