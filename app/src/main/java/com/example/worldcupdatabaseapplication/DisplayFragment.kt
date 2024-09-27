package com.example.worldcupdatabaseapplication

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcupdatabaseapplication.databinding.FragmentDisplayBinding

class DisplayFragment : Fragment() {

    private lateinit var displayTeamViewModel: DisplayTeamViewModel
    private lateinit var teamDisplayAdapter: TeamDisplayAdapter
    private var _binding: FragmentDisplayBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = context.applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDisplayBinding.inflate(inflater, container, false)

        // Initialize the RecyclerView and set its adapter
        teamDisplayAdapter = TeamDisplayAdapter()
        binding.recyclerView.adapter = teamDisplayAdapter

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        // Get the ViewModel instance and observe the list of teams
        displayTeamViewModel = ViewModelProvider(this)[DisplayTeamViewModel::class.java]

        displayTeamViewModel.teams.observe(viewLifecycleOwner) { teams ->
            teamDisplayAdapter.setTeams(teams)
        }

        // Set up the radio button listener for sorting teams
        setupRadioGroup()

        return binding.root
    }

    private fun setupRadioGroup() {
        // Set the OnCheckedChangeListener for the radio group
        binding.sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Use a when expression to determine which radio button was selected
            when (checkedId) {
                // If the "Sort by Name" radio button is selected, observe the LiveData object
                // returned by getTeamsSortedByName(), and update the teamDisplayAdapter with the new
                // list of teams when the data changes.
                R.id.sortByNameRadioButton -> {
                    displayTeamViewModel.getTeamsSortedByName().observe(viewLifecycleOwner) { teams ->
                        teamDisplayAdapter.setTeams(teams)
                    }
                }
                // If the "Sort by Continent" radio button is selected, observe the LiveData object
                // returned by getTeamsSortedByContinent(), and update the teamDisplayAdapter with the
                // new list of teams when the data changes.
                R.id.sortByContinentRadioButton -> {
                    displayTeamViewModel.getTeamsSortedByContinent().observe(viewLifecycleOwner) { teams ->
                        teamDisplayAdapter.setTeams(teams)
                    }
                }
                // If the "Sort by Points" radio button is selected, observe the LiveData object
                // returned by getTeamsSortedByPoints(), and update the teamDisplayAdapter with the new
                // list of teams when the data changes.
                R.id.sortByPointsRadioButton -> {
                    displayTeamViewModel.getTeamsSortedByPoints().observe(viewLifecycleOwner) { teams ->
                        teamDisplayAdapter.setTeams(teams)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


