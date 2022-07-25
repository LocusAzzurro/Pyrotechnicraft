package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;
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

        ShapedRecipeBuilder.shaped(ItemRegistry.SPHERE_EXPLOSION_PATTERN.get(), 1)
                .define('P', Items.PAPER)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("F F")
                .pattern(" P ")
                .pattern("F F")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.RING_EXPLOSION_PATTERN.get(), 1)
                .define('P', Items.PAPER)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern(" F ")
                .pattern("FPF")
                .pattern(" F ")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.BURST_EXPLOSION_PATTERN.get(), 1)
                .define('P', Items.PAPER)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("F F")
                .pattern("F F")
                .pattern(" P ")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.INSTANT_FUSE.get(), 2)
                .define('S', Items.STRING)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("FSF")
                .pattern("FSF")
                .pattern("FSF")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.REGULAR_FUSE.get(), 2)
                .define('S', Items.STRING)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("FSF")
                .pattern(" S ")
                .pattern("FSF")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.EXTENDED_FUSE.get(), 2)
                .define('S', Items.WHEAT)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("FSF")
                .pattern(" S ")
                .pattern("FSF")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.FIREWORK_WRAPPING_PAPER.get(), 4)
                .define('P', Items.PAPER)
                .define('R', Items.RED_DYE)
                .define('W', Items.WHITE_DYE)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("FRF")
                .pattern("RPW")
                .pattern("FWF")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.FIREWORK_HOMING_MODULE.get(), 1)
                .define('Q', Items.QUARTZ)
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_NUGGET)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern(" I ")
                .pattern("RQR")
                .pattern("IFI")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.HOMING_ARRAY_SCRIPT.get())
                .requires(ItemRegistry.FIREWORK_HOMING_MODULE.get())
                .requires(Items.PAPER)
                .unlockedBy("has_homing_module", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_HOMING_MODULE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.HOMING_ARRAY.get())
                .define('C', Items.COMPARATOR)
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GLASS)
                .define('H', ItemRegistry.FIREWORK_HOMING_MODULE.get())
                .pattern(" IG")
                .pattern("ICI")
                .pattern("HI ")
                .unlockedBy("has_homing_module", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_HOMING_MODULE.get()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.FIREWORK_ORB_CRAFTING_TABLE.get(), 1)
                .define('O', ItemRegistry.FIREWORK_ORB_CORE.get())
                .define('C', Items.CRAFTING_TABLE)
                .pattern("O")
                .pattern("C")
                .unlockedBy("has_firework_orb", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_ORB_CORE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get(), 1)
                .define('M', Items.FIREWORK_ROCKET)
                .define('C', Items.CRAFTING_TABLE)
                .pattern("M")
                .pattern("C")
                .unlockedBy("has_firework_orb", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_ORB_CORE.get()))
                .save(pFinishedRecipeConsumer);
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
