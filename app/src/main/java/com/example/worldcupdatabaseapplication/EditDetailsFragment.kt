package com.example.worldcupdatabaseapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.worldcupdatabaseapplication.databinding.FragmentEditDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDetailsFragment : Fragment() {

    private var _binding: FragmentEditDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: EditDetailsFragmentArgs by navArgs()
    private val viewModel: EditTeamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populate the Spinner with continents
        val continents = resources.getStringArray(R.array.continents)
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, continents)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.continentSpinner.adapter = arrayAdapter

        // Observe the team data and populate the fields
        viewModel.getTeam(args.teamId.toInt()).observe(viewLifecycleOwner) { team ->
            team?.let {
                binding.teamNameEditText.setText(it.teamName)
                binding.continentSpinner.setSelection(arrayAdapter.getPosition(it.continent))
                binding.playedEditText.setText(it.played.toString())
                binding.wonEditText.setText(it.won.toString())
                binding.drawnEditText.setText(it.drawn.toString())
                binding.lostEditText.setText(it.lost.toString())
            }
        }

        // Set click listener for the "Update" button
        binding.updateButton.setOnClickListener {
            val teamName = binding.teamNameEditText.text.toString()
            val continent = binding.continentSpinner.selectedItem.toString()
            val played = binding.playedEditText.text.toString().toIntOrNull()
            val won = binding.wonEditText.text.toString().toIntOrNull()
            val drawn = binding.drawnEditText.text.toString().toIntOrNull()
            val lost = binding.lostEditText.text.toString().toIntOrNull()

            // Check if all fields are filled in and have valid values
            if (teamName.isNotBlank() && continent.isNotBlank() && played != null && won != null &&
                drawn != null && lost != null) {

                // Check if the team name is a valid country name
                if (isValidCountryName(teamName)) {

                    // Call the viewModel's checkTeamExists function
                    lifecycleScope.launch {
                        val teamExists = viewModel.checkTeamExists(teamName, args.teamId.toInt())

                        // Check if the team with the given name already exists
                        if (!teamExists) {
                            // Create an updatedTeam object with the new values
                            val updatedTeam = Team(
                                teamID = args.teamId.toInt(),
                                teamName = teamName,
                                continent = continent,
                                played = played,
                                won = won,
                                drawn = drawn,
                                lost = lost
                            )

                            // Check if the game statistics are valid
                            val areStatsValid = viewModel.areGameStatsValid(updatedTeam)

                            if (areStatsValid) {
                                // Update the team in the database
                                viewModel.updateTeam(updatedTeam)

                                // Show a toast message and navigate to the EditFragment
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Team updated successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    findNavController().navigate(R.id.action_editDetailsFragment_to_editFragment)
                                }
                            } else {
                                // Show a toast message for invalid game statistics
                                Toast.makeText(
                                    requireContext(),
                                    "Invalid game statistics!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            // Show a toast message for team with the same name already exists
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    requireContext(),
                                    "Team with the same name already exists!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                } else {
                    // Show a toast message for invalid country name
                    Toast.makeText(requireContext(), "Invalid country name!", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Show a toast message for empty or missing fields
                Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // This function checks if the given teamName is a valid country name
    private fun isValidCountryName(teamName: String): Boolean {
        // Define a list of valid country names
        val countries = listOf("Argentina", "Brazil", "France", "Germany",
            "Spain", "Croatia", "Netherlands", "Portugal", "Morocco", "England")

        // Return true if the given teamName is found in the list of valid countries,
        // otherwise, return false
        return countries.contains(teamName)
    }

}

