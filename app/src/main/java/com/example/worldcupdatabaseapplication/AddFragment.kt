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
import com.example.worldcupdatabaseapplication.databinding.FragmentAddBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddTeamViewModel by viewModels()

    //Inflate the layout for this fragment and return the inflated view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the array of continents from resources
        val continents = resources.getStringArray(R.array.continents)

        // Create an ArrayAdapter to populate the Spinner with the continents array
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, continents)

        // Set the layout for the dropdown items in the Spinner
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Assign the ArrayAdapter to the Spinner
        binding.continentSpinner.adapter = arrayAdapter

        // Set the OnClickListener for the "Add" button
        binding.submitTeamButton.setOnClickListener {

            // Retrieve the user's inputs from the EditText fields and Spinner
            val teamName = binding.teamNameEditText.text.toString()
            val continent = binding.continentSpinner.selectedItem.toString()
            val played = binding.playedEditText.text.toString().toIntOrNull()
            val won = binding.wonEditText.text.toString().toIntOrNull()
            val drawn = binding.drawnEditText.text.toString().toIntOrNull()
            val lost = binding.lostEditText.text.toString().toIntOrNull()

            // Check if all required fields have valid input values
            if (teamName.isNotBlank() && continent.isNotBlank() && played != null && won != null &&
                drawn != null && lost != null) {

                // Check if the input teamName is a valid country name using the isValidCountryName function
                if (isValidCountryName(teamName)) {

                    // Launch a coroutine to perform database operations on a background thread
                    lifecycleScope.launch {

                        // Check if the team already exists in the database
                        val teamExists = viewModel.teamExists(teamName)

                        // If the team already exists, show a toast message
                        if (teamExists) {
                            Toast.makeText(requireContext(), "Team already exists in database!", Toast.LENGTH_SHORT).show()
                        } else {

                            // Create a new Team object with the user's input values
                            val newTeam = Team(
                                teamName = teamName, continent = continent,
                                played = played, won = won, drawn = drawn, lost = lost
                            )

                            // Insert the new Team object into the database
                            val insertResult = viewModel.insertTeam(newTeam)

                            // If the insertion is successful, show a success toast and navigate back to the home fragment
                            if (insertResult) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Team added successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    findNavController().navigate(R.id.action_addFragment_to_homeFragment)
                                }
                            } else {

                                // If the insertion is unsuccessful, show an error toast message
                                Toast.makeText(
                                    requireContext(),
                                    "Invalid game stats. Please check again.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                } else {

                    // If the input teamName is not a valid country name, show an error toast message
                    Toast.makeText(requireContext(), "Invalid country name!", Toast.LENGTH_SHORT).show()
                }
            } else {

                // If not all required fields have valid input values, show an error toast message
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

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference
        _binding = null
    }
}
