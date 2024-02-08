package com.unsa.healthcare.ui.view.main.statistics

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

class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mostPopularsChart: MostPopularsChart
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
    }
    override fun onDestroy() {
        super.onDestroy()
        WorkoutForegroundService.stopService(requireContext())
    }
}