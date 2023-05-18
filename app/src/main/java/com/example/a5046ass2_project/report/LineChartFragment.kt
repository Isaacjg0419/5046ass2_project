package com.example.a5046ass2_project.report

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a5046ass2_project.databinding.FragmentLineChartBinding
import com.example.a5046ass2_project.Map.Property
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class LineChartFragment : Fragment() {
    private var _binding: FragmentLineChartBinding? = null
    private val binding get() = _binding!!
    private lateinit var reportViewModel: ReportViewModel

    //    private var propertyList: List<Property> = listOf()
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
        _binding = FragmentLineChartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.searchBtn.setOnClickListener {
            handleDate()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reportViewModel.getPropertyList().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                toastMessage("There is no data in this period")
            } else{
                initData(it)
            }
        })
    }

    private fun initData(propertyList: List<Property>) {
        var chart: LineChart = binding.lineChart

        chart.description.isEnabled = false

        chart.setDrawGridBackground(false)

        chart.data = generateLineData(propertyList, chart)
        chart.animateX(3000)


        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.orientation = Legend.LegendOrientation.HORIZONTAL

        chart.axisRight.isEnabled = false

        val xAxis: XAxis = chart.xAxis
        xAxis.isEnabled = true
    }


    private fun generateLineData(propertyList: List<Property>, chart:LineChart): LineData {
        for (property in propertyList) {
            when (property.property_type) {

            }
        }
        val result = propertyList
            .groupBy { it.publish_date }
            .mapValues { entry -> entry.value.sumOf { it.price } / entry.value.size }
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(result.keys.map { formatDate(it) })

        val lineEntries: MutableList<Entry> = ArrayList()
        var index = 0f
        for (item in result) {
            lineEntries.add(Entry(index,item.value.toFloat()))
            index++
        }

        val lineDataSet = LineDataSet(lineEntries, "Average price")
        lineDataSet.lineWidth = (result.size/8).toFloat();
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        return LineData(lineDataSet)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatDate(date: Date?): String? {
        return date?.let { SimpleDateFormat("MMM dd, YYYY").format(it) }
    }

    private fun handleDate(){
        val startCalendar = Calendar.getInstance()
        startCalendar.set(
            binding.startDate.year, binding.startDate.month,
            binding.startDate.dayOfMonth
        )

        Log.d(ContentValues.TAG, "Start Date: ${Date(startCalendar.timeInMillis)}")
        val startDate = Date(startCalendar.timeInMillis)

        val endCalendar2 = Calendar.getInstance()
        endCalendar2.set(
            binding.endDate.year, binding.endDate.month,
            binding.endDate.dayOfMonth
        )

        Log.d(ContentValues.TAG, "End Date: ${Date(endCalendar2.timeInMillis)}")
        val endDate = Date(endCalendar2.timeInMillis)
        if (startDate.before(endDate)){
            reportViewModel.getPropertyListByPeriod(startDate, endDate)
        }
        else {
            toastMessage("Start date should be before end date")
        }
    }
    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}