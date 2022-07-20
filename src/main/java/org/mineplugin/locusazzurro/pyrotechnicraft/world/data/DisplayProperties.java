package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

import net.minecraft.nbt.CompoundTag;
import org.mineplugin.locusazzurro.pyrotechnicraft.client.color.FireworkMissileColorer;

public record DisplayProperties(int baseColor, int patternColor, int sparkColor) {

    private final static int DEFAULT_BASE = FireworkMissileColorer.DEFAULT_BASE_COLOR;
    private final static int DEFAULT_PATTERN = FireworkMissileColorer.DEFAULT_PATTERN_COLOR;
    private final static int DEFAULT_SPARK = 0xffffff;
    private final static String BASE_COLOR = "BaseColor";
    private final static String PATTERN_COLOR = "PatternColor";
    private final static String SPARK_COLOR = "SparkColor";

    public static DisplayProperties deserialize(CompoundTag tag){
        int baseColor = tag.contains(BASE_COLOR) ? tag.getInt(BASE_COLOR) : DEFAULT_BASE;
        int patternColor = tag.contains(PATTERN_COLOR) ? tag.getInt(PATTERN_COLOR) : DEFAULT_PATTERN;
        int sparkColor = tag.contains(SPARK_COLOR) ? tag.getInt(SPARK_COLOR) : DEFAULT_SPARK;
        return new DisplayProperties(baseColor, patternColor, sparkColor);
    }

    public CompoundTag serialize(){
        CompoundTag tag = new CompoundTag();
        tag.putInt(BASE_COLOR, baseColor);
        tag.putInt(PATTERN_COLOR, patternColor);
        tag.putInt(SPARK_COLOR, sparkColor);
        return tag;
    }

    public static DisplayProperties createDefault(){
        return new DisplayProperties(DEFAULT_BASE, DEFAULT_PATTERN, DEFAULT_SPARK);
    }
}
