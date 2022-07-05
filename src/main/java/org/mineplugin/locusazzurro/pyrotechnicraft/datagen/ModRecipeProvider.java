package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ModItemTags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ShapelessRecipeBuilder.shapeless(ItemRegistry.FIREWORK_ORB_CORE.get(), 2)
                .requires(Items.GUNPOWDER)
                .requires(Items.CLAY_BALL)
                .group("firework_orb_core")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer, modDir(getIngredientRecipeName(ItemRegistry.FIREWORK_ORB_CORE.get(), Items.GUNPOWDER)));
        ShapelessRecipeBuilder.shapeless(ItemRegistry.FIREWORK_ORB_CORE.get(), 2)
                .requires(ModItemTags.FORGE_SULFUR)
                .requires(ModItemTags.FORGE_SALTPETER)
                .requires(ModItemTags.FORGE_COAL_DUST)
                .requires(Items.CLAY_BALL)
                .group("firework_orb_core")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.FIREWORK_ORB_CORE.get(), 2)
                .requires(ItemRegistry.FIREWORK_MIXTURE.get())
                .requires(ItemRegistry.FIREWORK_MIXTURE.get())
                .requires(ItemRegistry.FIREWORK_MIXTURE.get())
                .requires(ItemRegistry.FIREWORK_MIXTURE.get())
                .requires(Items.CLAY_BALL)
                .group("firework_orb_core")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer, modDir(getIngredientRecipeName(ItemRegistry.FIREWORK_ORB_CORE.get(), ItemRegistry.FIREWORK_MIXTURE.get())));

        ShapelessRecipeBuilder.shapeless(ItemRegistry.FIREWORK_MIXTURE.get(), 4)
                .requires(ModItemTags.FORGE_SULFUR)
                .requires(ModItemTags.FORGE_SALTPETER)
                .requires(ModItemTags.FORGE_COAL_DUST)
                .group("firework_mixture")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.FIREWORK_MIXTURE.get(), 4)
                .requires(Items.GUNPOWDER)
                .group("firework_mixture")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer, modDir(getIngredientRecipeName(ItemRegistry.FIREWORK_MIXTURE.get(), Items.GUNPOWDER)));
    }

    private static String getIngredientRecipeName(ItemLike pResult, ItemLike pIngredient) {
        return getItemName(pResult) + "_with_" + getItemName(pIngredient);
    }

    private ResourceLocation modDir(String path){
        return new ResourceLocation(DataGenerators.MOD_ID, path);
    }

    @Override
    public String getName() {
        return DataGenerators.MOD_ID + " Recipes";
    }
}
