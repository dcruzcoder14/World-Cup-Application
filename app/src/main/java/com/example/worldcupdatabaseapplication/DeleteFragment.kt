package com.example.worldcupdatabaseapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcupdatabaseapplication.databinding.FragmentDeleteBinding

class DeleteFragment : Fragment() {

    // Binding object for the fragment
    private var _binding: FragmentDeleteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // ViewModel instance
    private val viewModel: DeleteTeamViewModel by viewModels()

    // Lazy initialized adapter for the recyclerview
    private val teamDeleteAdapter by lazy {
        // Pass in a click listener for the delete button
        TeamDeleteAdapter(requireContext()) { team ->
            viewModel.deleteTeam(team)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up a TextWatcher to observe changes to the search text input
        binding.searchDeleteText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Update the search results based on the new search query
                observeData(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        // Set up the RecyclerView with the adapter and layout manager
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.teamRecyclerView.apply {
            // Set the adapter for the RecyclerView
            adapter = teamDeleteAdapter
            // Set the layout manager for the RecyclerView
            layoutManager = LinearLayoutManager(requireContext())
            // Add a divider between items in the RecyclerView
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    override fun onStart() {
        super.onStart()
        // Start observing the data immediately when the Fragment starts
        observeData("")
    }

    private fun observeData(searchQuery: String) {
        // Use viewLifecycleOwner.lifecycleScope for asynchronous operations that require
        // view access and that are safe to be canceled when the Fragment's view is destroyed
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            // Observe the list of teams matching the search query and update the RecyclerView
            viewModel.searchTeamsDynamic(searchQuery).collect { teams ->
                teamDeleteAdapter.submitList(teams)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the binding object to avoid leaks
        _binding = null
    }
}
