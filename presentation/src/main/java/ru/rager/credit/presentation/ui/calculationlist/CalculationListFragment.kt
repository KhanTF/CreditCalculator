package ru.rager.credit.presentation.ui.calculationlist
/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.adapters.recyclerview.SavedCreditCalculationParameterListAdapter
import ru.rager.credit.presentation.databinding.FragmentCalculationListBinding
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment
import ru.rager.credit.presentation.util.itemdecorations.LinearSpaceItemDecoration

class CalculationListFragment : BaseIndependentFragment<CalculationListViewModel, FragmentCalculationListBinding>() {

    companion object {
        fun getInstance() = CalculationListFragment()
    }

    private val listener = object : SavedCreditCalculationParameterListAdapter.Listener {
        override fun onOpen(savedCalculationParameterEntity: CreditCalculationParameterEntity.SavedCalculationParameterEntity) {
            viewModel.onOpenCreditCalculation(savedCalculationParameterEntity)
        }

        override fun onDelete(savedCalculationParameterEntity: CreditCalculationParameterEntity.SavedCalculationParameterEntity) {
        }
    }
    private val adapter = SavedCreditCalculationParameterListAdapter(listener)

    override val viewModelClass: Class<CalculationListViewModel>
        get() = CalculationListViewModel::class.java

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculationListBinding {
        return FragmentCalculationListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.calculationList.adapter = adapter
        binding.calculationList.layoutManager = LinearLayoutManager(requireContext())
        binding.calculationList.addItemDecoration(
            LinearSpaceItemDecoration(
                start = R.dimen.dp_16,
                top = R.dimen.dp_16,
                space = R.dimen.dp_8
            )
        )
        viewModel.calculationListLiveData.observe {
            adapter.data = it
        }
    }

}*/
