package org.mineplugin.locusazzurro.pyrotechnicraft.util;

import net.minecraft.util.FastColor;

public class ColorUtil {

    public static float redF(int color){
        return (color >> 16 & 0xff) / 255f;
    }

    public static float greenF(int color){
        return (color >> 8 & 0xff) / 255f;
    }

    public static float blueF(int color){
        return (color & 0xff) / 255f;
    }

    public static int color(int r, int g, int b){
        return FastColor.ARGB32.color(0,r,g,b);
    }

    public static int color(float r, float g, float b){
        return FastColor.ARGB32.color(0, (int) (r * 255f), (int) (g * 255f), (int) (b * 255f));
    }

}
