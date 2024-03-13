package org.mineplugin.locusazzurro.pyrotechnicraft.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ItemRegistry;
import org.mineplugin.locusazzurro.pyrotechnicraft.data.ModItemTags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.FIREWORK_ORB_CORE.get(), 2)
                .requires(Items.GUNPOWDER)
                .requires(Items.CLAY_BALL)
                .group("firework_orb_core")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer, modDir(getIngredientRecipeName(ItemRegistry.FIREWORK_ORB_CORE.get(), Items.GUNPOWDER)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.FIREWORK_ORB_CORE.get(), 2)
                .requires(ModItemTags.FORGE_SULFUR)
                .requires(ModItemTags.FORGE_SALTPETER)
                .requires(ModItemTags.FORGE_COAL_DUST)
                .requires(Items.CLAY_BALL)
                .group("firework_orb_core")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.FIREWORK_ORB_CORE.get(), 2)
                .requires(ItemRegistry.FIREWORK_MIXTURE.get())
                .requires(ItemRegistry.FIREWORK_MIXTURE.get())
                .requires(ItemRegistry.FIREWORK_MIXTURE.get())
                .requires(ItemRegistry.FIREWORK_MIXTURE.get())
                .requires(Items.CLAY_BALL)
                .group("firework_orb_core")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer, modDir(getIngredientRecipeName(ItemRegistry.FIREWORK_ORB_CORE.get(), ItemRegistry.FIREWORK_MIXTURE.get())));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.FIREWORK_MIXTURE.get(), 4)
                .requires(ModItemTags.FORGE_SULFUR)
                .requires(ModItemTags.FORGE_SALTPETER)
                .requires(ModItemTags.FORGE_COAL_DUST)
                .group("firework_mixture")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.FIREWORK_MIXTURE.get(), 4)
                .requires(Items.GUNPOWDER)
                .group("firework_mixture")
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(pFinishedRecipeConsumer, modDir(getIngredientRecipeName(ItemRegistry.FIREWORK_MIXTURE.get(), Items.GUNPOWDER)));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.SPHERE_EXPLOSION_PATTERN.get(), 1)
                .define('P', Items.PAPER)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("F F")
                .pattern(" P ")
                .pattern("F F")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.RING_EXPLOSION_PATTERN.get(), 1)
                .define('P', Items.PAPER)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern(" F ")
                .pattern("FPF")
                .pattern(" F ")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BURST_EXPLOSION_PATTERN.get(), 1)
                .define('P', Items.PAPER)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("F F")
                .pattern("F F")
                .pattern(" P ")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.INSTANT_FUSE.get(), 2)
                .define('S', Items.STRING)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("FSF")
                .pattern("FSF")
                .pattern("FSF")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.REGULAR_FUSE.get(), 2)
                .define('S', Items.STRING)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("FSF")
                .pattern(" S ")
                .pattern("FSF")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.EXTENDED_FUSE.get(), 2)
                .define('S', Items.WHEAT)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("FSF")
                .pattern(" S ")
                .pattern("FSF")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FIREWORK_WRAPPING_PAPER.get(), 4)
                .define('P', Items.PAPER)
                .define('R', Items.RED_DYE)
                .define('W', Items.WHITE_DYE)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("FRF")
                .pattern("RPW")
                .pattern("FWF")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FIREWORK_HOMING_MODULE.get(), 1)
                .define('Q', Items.QUARTZ)
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_NUGGET)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern(" I ")
                .pattern("RQR")
                .pattern("IFI")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.HOMING_ARRAY_SCRIPT.get())
                .requires(ItemRegistry.FIREWORK_HOMING_MODULE.get())
                .requires(Items.PAPER)
                .unlockedBy("has_homing_module", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_HOMING_MODULE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.HOMING_ARRAY.get())
                .define('C', Items.COMPARATOR)
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GLASS)
                .define('H', ItemRegistry.FIREWORK_HOMING_MODULE.get())
                .pattern(" IG")
                .pattern("ICI")
                .pattern("HI ")
                .unlockedBy("has_homing_module", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_HOMING_MODULE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.FIREWORK_ORB_SHOOTER.get())
                .define('B', Items.BAMBOO)
                .define('S', Items.FLINT_AND_STEEL)
                .pattern("  B")
                .pattern(" B ")
                .pattern("S  ")
                .unlockedBy("has_orb", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_ORB.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.FIREWORK_LAUNCHER.get())
                .define('I', Items.IRON_INGOT)
                .define('S', Items.FLINT_AND_STEEL)
                .define('B', ItemTags.BUTTONS)
                .define('W', ItemRegistry.FIREWORK_WRAPPING_PAPER.get())
                .pattern("WI ")
                .pattern("I I")
                .pattern("SIB")
                .unlockedBy("has_missile", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MISSILE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.FIRECRACKER.get())
                .define('P', Items.PAPER)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern("PFP")
                .pattern("PFP")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.FLICKER_STICK.get())
                .define('S', Items.STICK)
                .define('F', ItemRegistry.FIREWORK_MIXTURE.get())
                .pattern(" F")
                .pattern("S ")
                .unlockedBy("has_firework_mixture", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MIXTURE.get()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.FIREWORK_ORB_CRAFTING_TABLE.get(), 1)
                .define('O', ItemRegistry.FIREWORK_ORB_CORE.get())
                .define('C', Items.CRAFTING_TABLE)
                .pattern("O")
                .pattern("C")
                .unlockedBy("has_firework_orb", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_ORB_CORE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.FIREWORK_MISSILE_CRAFTING_TABLE.get(), 1)
                .define('M', Items.FIREWORK_ROCKET)
                .define('C', Items.CRAFTING_TABLE)
                .pattern("M")
                .pattern("C")
                .unlockedBy("has_firework_orb", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_ORB_CORE.get()))
                .save(pFinishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.FIREWORK_LAUNCHER_STAND.get(), 1)
                .define('C', Items.COMPARATOR)
                .define('D', Items.DISPENSER)
                .define('F', Items.FLINT_AND_STEEL)
                .pattern("C")
                .pattern("D")
                .pattern("F")
                .unlockedBy("has_firework_missile", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIREWORK_MISSILE.get()))
                .save(pFinishedRecipeConsumer);
    }

    private static String getIngredientRecipeName(ItemLike pResult, ItemLike pIngredient) {
        return getItemName(pResult) + "_with_" + getItemName(pIngredient);
    }

    private ResourceLocation modDir(String path){
        return new ResourceLocation(DataGenerators.MOD_ID, path);
    }

}
