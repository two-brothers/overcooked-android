package com.twobrothers.overcooked.presenters

import android.os.Bundle
import com.twobrothers.overcooked.app.ApiClient
import com.twobrothers.overcooked.interfaces.IRecipeContract
import com.twobrothers.overcooked.models.recipe.RecipeModel
import com.twobrothers.overcooked.views.recipe.IngredientViewAdapter
import com.twobrothers.overcooked.views.recipe.MethodViewAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class RecipePresenter(private val view:IRecipeContract.View) : IRecipeContract.Presenter {

    private val disposable = CompositeDisposable()
    private var methodList = ArrayList<String>()
    private var ingredientList = mutableListOf<Any>()

    override fun onStart(args: Bundle?) {
        disposable.add(
                ApiClient.getRecipeById("5c70f646f36d3de9a97aee3f") // args!!.getString("id")
                        .subscribeBy(
                                onSuccess = {
                                    view.render(it)
                                    methodList = it.method
                                    view.onMethodDataSetChanged()
                                    /* if (it.data == null) {
                                        return@subscribeBy
                                    }

                                    println(it.data.recipe.ingredients)

                                    view.render(it.data.recipe)
                                    methodList = it.data.recipe.method
                                    ingredientList = it.data.recipe.ingredients

                                    view.onMethodDataSetChanged()
                                    view.onIngredientDataSetChanged()*/
                                },
                                onError =  { it.printStackTrace() }
                        )
        )
    }

    override fun onStop() {
        disposable.dispose()
    }

    override fun onBindMethodRepositoryRowViewAtPosition(holder: MethodViewAdapter.Holder, position: Int) {
        val method = methodList[position]
        holder.render(position + 1, method)
    }

    override fun getMethodRepositoriesRowsCount(): Int {
        return methodList.size
    }

    override fun onBindIngredientRepositoryRowViewAtPosition(holder: IngredientViewAdapter.Holder, position: Int) {
        // val ingredient = ingredientList[position]
        // println(ingredient is FreeText)
        holder.render()
    }

    override fun getIngredientRepositoriesRowsCount(): Int {
        return ingredientList.size
    }

}
