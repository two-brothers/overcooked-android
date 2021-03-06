package com.twobrothers.overcooked.interactive

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.twobrothers.overcooked.R
import com.twobrothers.overcooked.core.framework.viewModelFactory
import com.twobrothers.overcooked.databinding.FragmentInteractiveStepBinding
import com.twobrothers.overcooked.recipedetails.models.FreeTextIngredient
import com.twobrothers.overcooked.recipedetails.models.InteractiveStep
import com.twobrothers.overcooked.recipedetails.models.QuantifiedIngredient
import com.twobrothers.overcooked.recipedetails.presentation.getQuantifiedIngredientReadableFormat
import kotlinx.android.synthetic.main.fragment_interactive_step.layout_ingredients

class InteractiveStepFragment : Fragment() {

    companion object {

        private const val ARGUMENT_INTERACTIVE_STEP = "InteractiveStepFragment.Argument.Step"
        private const val ARGUMENT_SERVES = "InteractiveStepFragment.Argument.Serves"

        @JvmStatic
        fun newInstance(step: InteractiveStep, serves: Int): InteractiveStepFragment {
            val fragment = InteractiveStepFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARGUMENT_INTERACTIVE_STEP, step)
            arguments.putSerializable(ARGUMENT_SERVES, serves)
            fragment.arguments = arguments
            return fragment
        }

    }

    private lateinit var viewModel: InteractiveStepViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Init ViewModel
        val step = arguments?.get(ARGUMENT_INTERACTIVE_STEP) as InteractiveStep
        val serves = arguments?.getInt(ARGUMENT_SERVES) ?: 0
        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory {
                InteractiveStepViewModel(
                    step,
                    serves
                )
            }
        ).get(InteractiveStepViewModel::class.java)

        // Init Data Binding
        val binding = FragmentInteractiveStepBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Init view model observers
        viewModel.ingredients.observe(this, Observer {
            layout_ingredients.removeAllViews()
            val serves = it.second
            it.first?.map {
                val view = when (it) {
                    is QuantifiedIngredient -> {
                        val view = layoutInflater.inflate(
                            R.layout.view_checkable_ingredient,
                            layout_ingredients,
                            false
                        )
                        view.findViewById<TextView>(R.id.text_description).text =
                            getQuantifiedIngredientReadableFormat(requireContext(), it, serves)
                        view
                    }
                    is FreeTextIngredient -> {
                        val view = layoutInflater.inflate(
                            R.layout.view_checkable_ingredient,
                            layout_ingredients,
                            false
                        )
                        view.findViewById<TextView>(R.id.text_description).text = it.description
                        view
                    }
                    else -> throw throw IllegalStateException("Unable to map interactive ingredient type")
                }
                layout_ingredients.addView(view)
            }
        })

        return binding.root
    }

}