package com.unsa.healthcare.ui.view.main.reminders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsa.healthcare.data.adapters.main.reminders.ReminderAdapter
import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.databinding.FragmentReminderBinding
import com.unsa.healthcare.ui.view.main.MainActivity
import com.unsa.healthcare.ui.viewmodel.main.MainViewModel

class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ReminderAdapter
    private val manager = LinearLayoutManager(context)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity
        mainViewModel = ViewModelProvider(mainActivity)[MainViewModel::class.java]
        mainViewModel.reminders.observe(viewLifecycleOwner) {
            initRecyclerView(mainViewModel.reminders.value ?: emptyList())
        }
        binding.reminderSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredReminders = mainViewModel.reminders.value?.filter {
                        reminder -> reminder.medicine.contains(newText.orEmpty(), ignoreCase = true)
                }
                adapter.updateReminders(filteredReminders)
                return true
            }
        })
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.getMedicines()
    }
    private fun initRecyclerView(reminders: List<ReminderEntity>) {
        adapter = ReminderAdapter(reminders)
        binding.remindersRecyclerView.layoutManager = manager
        binding.remindersRecyclerView.adapter = adapter
    }
}