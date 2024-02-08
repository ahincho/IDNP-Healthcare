package com.unsa.healthcare.ui.view.main.statistics

import android.annotation.SuppressLint
import android.app.SearchManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.unsa.healthcare.core.WorkoutForegroundService
import com.unsa.healthcare.databinding.FragmentStatisticsBinding
import com.unsa.healthcare.ui.view.main.MainActivity
import com.unsa.healthcare.ui.viewmodel.main.MainViewModel
import android.database.Cursor
import android.database.MatrixCursor
import android.provider.BaseColumns
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import com.unsa.healthcare.R

class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mostPopularsChart: MostPopularsChart
    private lateinit var mostPopularsBarChart: MostPopularsBarChart
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        binding.root.setOnClickListener {
            WorkoutForegroundService.startService(requireContext())
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity
        mainViewModel = ViewModelProvider(mainActivity)[MainViewModel::class.java]
        mostPopularsChart = binding.mostPopularsChart
        mostPopularsBarChart = binding.mostPopularsBarChart
        mostPopularsBarChart.visibility = View.INVISIBLE

        val searchView = _binding!!.statisticsSearchView
        val switchView = _binding!!.switchPopularsChart

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(context, R.layout.search_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)

        searchView.suggestionsAdapter = cursorAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val filteredMedicines = mainViewModel.medicines.value?.filter {
                        medicine -> medicine.name.contains(query.orEmpty(), ignoreCase = true)
                }
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    filteredMedicines!!.forEachIndexed { index, suggestion ->
                        if (suggestion.name.contains(query, true))
                            cursor.addRow(arrayOf(index, suggestion.name))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true
            }
        })

        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            @SuppressLint("Range")
            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)

                // Do something with selection
                mostPopularsChart.updateList()
                mostPopularsChart.updateChart()
                mostPopularsBarChart.updateChart()
                return true
            }

        })

        switchView.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mostPopularsChart.visibility = View.INVISIBLE
                mostPopularsBarChart.visibility = View.VISIBLE
                mostPopularsBarChart.updateChart()
            } else {
                mostPopularsChart.visibility = View.VISIBLE
                mostPopularsBarChart.visibility = View.INVISIBLE
                mostPopularsChart.updateChart()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        WorkoutForegroundService.stopService(requireContext())
    }
}