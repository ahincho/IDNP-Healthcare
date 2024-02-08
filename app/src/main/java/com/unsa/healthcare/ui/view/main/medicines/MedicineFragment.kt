package com.unsa.healthcare.ui.view.main.medicines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.unsa.healthcare.data.adapters.main.medicines.MedicineAdapter
import com.unsa.healthcare.data.models.Medicine
import com.unsa.healthcare.databinding.FragmentMedicineBinding
import com.unsa.healthcare.ui.view.main.MainActivity
import com.unsa.healthcare.ui.viewmodel.main.MainViewModel

class MedicineFragment : Fragment() {
    private var _binding: FragmentMedicineBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MedicineAdapter
    private val manager = GridLayoutManager(context, 2)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMedicineBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity
        mainViewModel = ViewModelProvider(mainActivity)[MainViewModel::class.java]
        mainViewModel.medicines.observe(viewLifecycleOwner) {
            initRecyclerView(mainViewModel.medicines.value ?: emptyList())
        }
        binding.medicineSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredMedicines = mainViewModel.medicines.value?.filter {
                        medicine -> medicine.name.contains(newText.orEmpty(), ignoreCase = true)
                }
                adapter.updateMedicines(filteredMedicines)
                return true
            }
        })
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.getMedicines()
    }
    private fun initRecyclerView(medicines: List<Medicine>) {
        adapter = MedicineAdapter(medicines)
        binding.medicinesRecyclerView.layoutManager = manager
        binding.medicinesRecyclerView.adapter = adapter
    }
}