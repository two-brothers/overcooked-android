package com.twobrothers.overcooked.views.recipelist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.jakewharton.rxbinding.view.RxView
import com.twobrothers.overcooked.R
import com.twobrothers.overcooked.interfaces.IRecipeListContract

class RecipeListViewAdapter(private val presenter:IRecipeListContract.Presenter):RecyclerView.Adapter<RecipeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val layout:RelativeLayout = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recipe_list_item, parent, false) as RelativeLayout
        return RecipeListViewHolder(layout, presenter)
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        presenter.onBindRepositoryRowViewAtPosition(holder, position)
    }

    override fun getItemCount(): Int {
        return presenter.getRepositoriesRowsCount()
    }
}