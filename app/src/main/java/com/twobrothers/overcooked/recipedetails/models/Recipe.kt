package com.twobrothers.overcooked.recipedetails.models

import com.twobrothers.overcooked.core.IngredientMeasurementUnit

data class Recipe(
    val id: String,
    val title: String,
    val serves: Int,
    val prepTime: Int,
    val cookTime: Int,
    val ingredients: List<Ingredient>,
    val method: List<String>
)

sealed class Ingredient

data class HeadingIngredient(
    val title: String
) : Ingredient()

data class FreeTextIngredient(
    val description: String
) : Ingredient()

data class QuantifiedIngredient(
    val amount: Double,
    val measurementUnit: IngredientMeasurementUnit,
    val food: Food,
    val endDescription: String
) : Ingredient()