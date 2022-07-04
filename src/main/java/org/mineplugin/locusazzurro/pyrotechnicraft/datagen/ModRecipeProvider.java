package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ModItemTags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    private static final String MAIN_GROUP = DataGenerators.MOD_ID;

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ShapelessRecipeBuilder.shapeless(ItemRegistry.FIREWORK_ORB_CORE.get(), 2)
                .requires(ModItemTags.FORGE_SULFUR)
                .requires(ModItemTags.FORGE_SALTPETER)
                .requires(ModItemTags.FORGE_COAL_DUST)
                .requires(Items.CLAY_BALL)
                .group(MAIN_GROUP)
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer);
    }
}
