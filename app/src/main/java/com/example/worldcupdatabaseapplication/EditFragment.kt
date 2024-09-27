package com.example.worldcupdatabaseapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcupdatabaseapplication.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    private val viewModel: EditTeamViewModel by viewModels()
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TeamListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter for the team recycler view, and set its click listener to navigate to EditDetailsFragment
        adapter = TeamListAdapter { teamId: Int ->
            val action = EditFragmentDirections.actionEditFragmentToEditDetailsFragment(teamId.toString())
            view.findNavController().navigate(action)
        }

        // Set the layout manager and adapter for the team recycler view
        binding.teamRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.teamRecyclerView.adapter = adapter

        // Add a text change listener to the search edit text view, which updates the recycler view when the search query changes
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.searchTeams(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        // Observe the search results from the view model and update the adapter with them
        viewModel.searchResults.observe(viewLifecycleOwner) { teams ->
            adapter.setTeams(teams)
        }

        // Observe all teams from the view model and show them if the search query is empty
        viewModel.getAllTeams().observe(viewLifecycleOwner) { teams ->
            if (binding.searchEditText.text.isEmpty()) {
                adapter.setTeams(teams)
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
