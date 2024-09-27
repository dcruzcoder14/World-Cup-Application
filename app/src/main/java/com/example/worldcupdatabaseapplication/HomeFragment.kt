package com.example.worldcupdatabaseapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.worldcupdatabaseapplication.databinding.ActivityMainBinding
import com.example.worldcupdatabaseapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set up buttons for navigation between HomeFragment and other fragments
        binding.btnAddTeam.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addTeamFragment)
        }

        binding.btnEditTeam.setOnClickListener() {
            navController.navigate(R.id.action_homeFragment_to_editFragment)
        }

        binding.btnDeleteTeam.setOnClickListener() {
            navController.navigate(R.id.action_homeFragment_to_deleteFragment)
        }

        binding.btnDisplayTeam.setOnClickListener() {
            navController.navigate(R.id.action_homeFragment_to_displayFragment)
        }

        // Set up other buttons here

        return binding.root
    }
}
