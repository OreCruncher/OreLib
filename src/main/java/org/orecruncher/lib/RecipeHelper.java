package org.orecruncher.lib;

/*
 * Copyright (c) 2018 modmuss50 and Gigabit101
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;

/**
 * This class contains a bunch of helper methods for adding recipes in code in
 * minecraft 1.12
 *
 * Feel free to use this code for your projects.
 *
 * Also please, please point out places where this can be improved.
 */
public class RecipeHelper {

	/**
	 * Adds a shaped recipe that supports string input paramers corresponding to an
	 * oredict entry, can also be used for recipes without ore dict ingredients
	 *
	 * @param output
	 *                   The stack that should be produced
	 * @param params
	 *                   Input ingredients
	 */
	public static void addShapedOreRecipe(ItemStack output, Object... params) {
		final ResourceLocation location = getNameForRecipe(output);
		final ShapedOreRecipe recipe = new ShapedOreRecipe(location, output, params);
		recipe.setRegistryName(location);
		GameData.register_impl(recipe);
	}

	/**
	 * Adds a basic shaped recipe
	 *
	 * @param output
	 *                   The stack that should be produced
	 * @param params
	 *                   Input ingredients
	 */
	public static void addShapedRecipe(ItemStack output, Object... params) {
		final ResourceLocation location = getNameForRecipe(output);
		final CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(params);
		final ShapedRecipes recipe = new ShapedRecipes(output.getItem().getRegistryName().toString(), primer.width,
				primer.height, primer.input, output);
		recipe.setRegistryName(location);
		GameData.register_impl(recipe);
	}

	/**
	 * Adds a shapeless ore recipe
	 *
	 * @param output
	 *                   The stack that should be produced
	 * @param input
	 *                   Input ingredients
	 */
	public static void addShapelessOreRecipe(ItemStack output, Object... input) {
		final ResourceLocation location = getNameForRecipe(output);
		final ShapelessOreRecipe recipe = new ShapelessOreRecipe(location, output, input);
		recipe.setRegistryName(location);
		GameData.register_impl(recipe);
	}

	/**
	 * Adds a basic shapeless recipe
	 *
	 * @param output
	 *                   The stack that should be produced
	 * @param input
	 *                   Input ingredients
	 */
	public static void addShapelessRecipe(ItemStack output, Object... input) {
		final ResourceLocation location = getNameForRecipe(output);
		final ShapelessRecipes recipe = new ShapelessRecipes(location.getNamespace(), output, buildInput(input));
		recipe.setRegistryName(location);
		GameData.register_impl(recipe);
	}

	/**
	 * Generates a unique name based of the active mod, and the ItemStack that the
	 * recipe outputs
	 *
	 * @param output
	 *                   an ItemStack, usually the one the the recipe produces
	 * @return a unique ResourceLocation based off the item item
	 */
	public static ResourceLocation getNameForRecipe(ItemStack output) {
		final ModContainer activeContainer = Loader.instance().activeModContainer();
		final ResourceLocation baseLoc = new ResourceLocation(activeContainer.getModId(),
				output.getItem().getRegistryName().getPath());
		ResourceLocation recipeLoc = baseLoc;
		int index = 0;
		while (CraftingManager.REGISTRY.containsKey(recipeLoc)) {
			index++;
			recipeLoc = new ResourceLocation(activeContainer.getModId(), baseLoc.getPath() + "_" + index);
		}
		return recipeLoc;
	}

	/**
	 * Converts an object array into a NonNullList of Ingredients
	 */
	public static NonNullList<Ingredient> buildInput(Object[] input) {
		final NonNullList<Ingredient> list = NonNullList.create();
		for (final Object obj : input) {
			if (obj instanceof Ingredient) {
				list.add((Ingredient) obj);
			} else {
				Ingredient ingredient = CraftingHelper.getIngredient(obj);
				if (ingredient == null) {
					ingredient = Ingredient.EMPTY;
				}
				list.add(ingredient);
			}
		}
		return list;
	}

	public static void addSmelting(Block input, ItemStack output, float xp) {
		GameRegistry.addSmelting(input, output, xp);
	}

	public static void addSmelting(Item input, ItemStack output, float xp) {
		GameRegistry.addSmelting(input, output, xp);
	}

	public static void addSmelting(ItemStack input, ItemStack output, float xp) {
		GameRegistry.addSmelting(input, output, xp);
	}

	public static void addSmelting(ItemStack input, ItemStack output) {
		addSmelting(input, output, 1F);
	}

	public static void addSmelting(Item input, ItemStack output) {
		addSmelting(input, output, 1F);
	}

	public static void addSmelting(Block input, ItemStack output) {
		addSmelting(input, output, 1F);
	}
}