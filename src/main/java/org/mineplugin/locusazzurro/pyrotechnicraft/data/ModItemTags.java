package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.mineplugin.locusazzurro.pyrotechnicraft.Pyrotechnicraft;

public class ModItemTags {

    public static final TagKey<Item> FORGE_SULFUR = forgeTag("dusts/sulfur");
    public static final TagKey<Item> FORGE_SALTPETER = forgeTag("dusts/saltpeter");
    public static final TagKey<Item> FORGE_COAL_DUST = forgeTag("dusts/coal");
    public static final TagKey<Item> FORGE_DIAMOND_DUST = forgeTag("dusts/diamond");
    public static final TagKey<Item> FORGE_IRON_DUST = forgeTag("dusts/iron");
    public static final TagKey<Item> TRAIL_EFFECT_ITEMS = modTag("firework/trail");
    public static final TagKey<Item> SPARKLE_EFFECT_ITEMS = modTag("firework/sparkle");

    private static TagKey<Item> forgeTag(String path){
        return ItemTags.create(new ResourceLocation("forge", path));
    }
    private static TagKey<Item> modTag(String path){
        return ItemTags.create(new ResourceLocation(Pyrotechnicraft.MOD_ID, path));
    }
}
