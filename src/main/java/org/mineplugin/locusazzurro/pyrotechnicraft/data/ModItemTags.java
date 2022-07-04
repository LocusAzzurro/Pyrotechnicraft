package org.mineplugin.locusazzurro.pyrotechnicraft.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

    public static final TagKey<Item> FORGE_SULFUR = forgeTag("dusts/sulfur");
    public static final TagKey<Item> FORGE_SALTPETER = forgeTag("dusts/saltpeter");
    public static final TagKey<Item> FORGE_COAL_DUST = forgeTag("dusts/coal");

    private static TagKey<Item> forgeTag(String path){
        return ItemTags.create(new ResourceLocation("forge", path));
    }

}
