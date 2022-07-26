package org.mineplugin.locusazzurro.pyrotechnicraft.world.block.container;

import net.minecraft.world.inventory.ContainerData;

public class FireworkLauncherStandContainerData implements ContainerData {

    public static final int ROTATION_DATA_SLOT_ID = 0;
    public static final int ANGLE_DATA_SLOT_ID = 1;
    private int rotation;
    private int angle;

    @Override
    public int get(int pIndex) {
        return switch (pIndex) {
            case ROTATION_DATA_SLOT_ID -> this.rotation;
            case ANGLE_DATA_SLOT_ID -> this.angle;
            default -> 0;
        };
    }

    @Override
    public void set(int pIndex, int pValue) {
        switch (pIndex){
            case ROTATION_DATA_SLOT_ID -> this.rotation = pValue;
            case ANGLE_DATA_SLOT_ID -> this.angle = pValue;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
