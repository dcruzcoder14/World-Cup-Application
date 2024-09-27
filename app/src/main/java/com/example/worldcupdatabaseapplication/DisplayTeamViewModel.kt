package com.example.worldcupdatabaseapplication

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.worldcupdatabaseapplication.AppDatabase.Companion.getInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisplayTeamViewModel(application: Application) : AndroidViewModel(application) {

    private val teamDao = getInstance(application).teamDao()
    private val teamRepository = TeamRepository(teamDao)

    val teams = teamRepository.getAllTeams()

    fun getTeamsSortedByName(): LiveData<List<Team>> {
        return try {
            teamRepository.getTeamsSortedByName()
        } catch (e: Exception) {
            Log.e(TAG, "Error sorting teams by name", e)
            MutableLiveData(emptyList())
        }
    }

    fun getTeamsSortedByContinent(): LiveData<List<Team>> {
        return teamRepository.getTeamsSortedByContinent()
    }

    fun getTeamsSortedByPoints(): LiveData<List<Team>> {
        return teamRepository.getTeamsSortedByPoints()
    }

    // Override onCleared method to clear the database when the ViewModel is destroyed
    override fun onCleared() {
        super.onCleared()

        // The viewModelScope is a CoroutineScope tied to the ViewModel's lifecycle.
        // It helps to manage coroutines in a structured way, ensuring they are canceled when the ViewModel is destroyed.
        viewModelScope.launch {
            // The withContext(Dispatchers.IO) function is used to change the coroutine context.
            // In this case, we are switching to the IO dispatcher, which is designed to handle tasks
            // that involve I/O operations, such as database queries and network requests.
            withContext(Dispatchers.IO) {
                teamRepository.deleteAllTeams()
            }
        }
    }

}




