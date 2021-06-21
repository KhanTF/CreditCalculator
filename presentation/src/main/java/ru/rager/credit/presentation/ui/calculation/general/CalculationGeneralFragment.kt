package ru.rager.credit.presentation.ui.calculation.general

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import ru.rager.credit.presentation.R
import ru.rager.credit.presentation.databinding.FragmentCalculationGeneralBinding
import ru.rager.credit.presentation.ui.base.BaseFragment
import ru.rager.credit.presentation.ui.calculation.CalculationViewModel
import ru.rager.credit.presentation.util.EMPTY_STRING


class CalculationGeneralFragment : BaseFragment<CalculationViewModel, FragmentCalculationGeneralBinding>() {

    companion object {
        fun getInstance() = CalculationGeneralFragment()
    }

    override fun getViewDataBindingInstance(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculationGeneralBinding {
        return FragmentCalculationGeneralBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.creditOverpaymentsLiveData.observe { overPayments ->
            updatePieChart(
                pieChart = binding.pieChart,
                overPayments = overPayments,
                creditSum = viewModel.creditSum
            )
        }
    }

    private fun updatePieChart(pieChart: PieChart, overPayments: Double, creditSum: Double) {
        val piyEntryList = listOf(
            PieEntry(creditSum.toFloat(), getString(R.string.pie_chart_label_credit_sum)),
            PieEntry(overPayments.toFloat(), getString(R.string.pie_chart_label_overpayments))
        )
        val dataSet = PieDataSet(piyEntryList, EMPTY_STRING)
        dataSet.colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.color_pie_chart_1),
            ContextCompat.getColor(requireContext(), R.color.color_pie_chart_2),
        )
        val data = PieData(dataSet)
        data.setValueTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        data.setValueTextSize(resources.getDimension(R.dimen.dp_6))
        data.setValueTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL))

        val legend: Legend = pieChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.isWordWrapEnabled = true
        legend.textSize = 12f
        legend.formSize = 20f
        legend.formToTextSpace = 2f

        pieChart.data = data
        pieChart.contentDescription = EMPTY_STRING
        pieChart.setDrawEntryLabels(false)
        pieChart.isDrawHoleEnabled = false
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)

        pieChart.invalidate()
    }

}
