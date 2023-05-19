package com.example.a5046ass2_project.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a5046ass2_project.map.Property
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.databinding.FragmentBarChartBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class BarChartFragment : Fragment() {

    private var _binding: FragmentBarChartBinding? = null
    private val binding get() = _binding!!

    private lateinit var reportViewModel: ReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        reportViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory()
        )[ReportViewModel::class.java]
        reportViewModel.getDateFromRoom()

        _binding = FragmentBarChartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.barChartButton.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.reportViewContainer, BarChartFragment())
                .disallowAddToBackStack()
                .commit()
        }

        binding.lineChartButton.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.reportViewContainer, LineChartFragment())
                .disallowAddToBackStack()
                .commit()
        }

//        val textView: TextView = binding.textNotifications
//        barViewModel.text.observe(viewLifecycleOwner) {
////            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportViewModel.getPropertyList().observe(viewLifecycleOwner, Observer {
            initData(it)
        })

    }

    private fun initData(propertyList: List<Property>) {
        val xAxisValues: List<String> = ArrayList(
            listOf("House", "Apartment", "TownHouse")
        )
        var numOfHouse = 0
        var numOfApartment = 0
        var numOfTownHouse = 0
        for (property in propertyList) {
            when (property.property_type) {
                xAxisValues[0] -> numOfHouse++
                xAxisValues[1] -> numOfApartment++
                xAxisValues[2] -> numOfTownHouse++

            }
        }

        val barEntries: MutableList<BarEntry> = ArrayList()
        barEntries.add(BarEntry(0f, numOfHouse.toFloat()))
        barEntries.add(BarEntry(1f, numOfApartment.toFloat()))
        barEntries.add(BarEntry(2f, numOfTownHouse.toFloat()))
        val barDataSet = BarDataSet(barEntries, "Property Type")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        val barData = BarData(barDataSet)
        binding.barChart.data = barData
        barData.barWidth = 1.0f
        binding.barChart.visibility = View.VISIBLE
        binding.barChart.animateY(4000)
        //description will be displayed as "Description Label" if not provided
        //description will be displayed as "Description Label" if not provided
        val description = Description()
//        description.text = "Daily Steps"
        binding.barChart.description = description
        //refresh the chart
        //refresh the chart
        binding.barChart.invalidate()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}