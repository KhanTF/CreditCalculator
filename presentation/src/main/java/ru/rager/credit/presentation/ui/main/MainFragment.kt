package ru.rager.credit.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.FragmentMainBinding
import ru.rager.credit.presentation.model.MainMenuItem
import ru.rager.credit.presentation.ui.base.BaseIndependentFragment
import ru.rager.credit.presentation.adapters.MainMenuAdapter
import ru.rager.credit.presentation.util.itemdecorations.LinearSpaceItemDecoration

class MainFragment : BaseIndependentFragment<MainViewModel, FragmentMainBinding>(), MainMenuAdapter.MainMenuListener {

    companion object {
        fun getInstance() = MainFragment()
    }


    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMainMenuList()
    }

    private fun setupMainMenuList() {
        val mainMenuAdapter = MainMenuAdapter(this@MainFragment)
        binding.menuList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainMenuAdapter
            addItemDecoration(
                LinearSpaceItemDecoration(
                    start = R.dimen.margin_16,
                    top = R.dimen.margin_16,
                    space = R.dimen.margin_16
                )
            )
        }
        viewModel.mainMenuListLiveData.observe {
            mainMenuAdapter.mainMenuList = it
        }
    }

    override fun onMainMenu(mainMenuItem: MainMenuItem) {
        when (mainMenuItem) {
            is MainMenuItem.CalculatePaymentMainMenuItem -> viewModel.onOpenPaymentCalculator()
        }
    }


}
