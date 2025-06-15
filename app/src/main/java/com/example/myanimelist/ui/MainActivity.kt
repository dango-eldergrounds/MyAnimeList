package com.example.myanimelist.ui

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myanimelist.R
import com.example.myanimelist.data.remote.ApiResponse
import com.example.myanimelist.databinding.ActivityMainBinding
import com.example.myanimelist.ui.viewmodel.AnimeViewModel
import com.example.myanimelist.ui.viewmodel.CharacterViewModel
import com.example.myanimelist.ui.viewmodel.MangaViewModel
import com.example.myanimelist.ui.viewmodel.PeopleViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val animeViewModel: AnimeViewModel by viewModels()
    private val mangaViewModel: MangaViewModel by viewModels()
    private val characterViewModel: CharacterViewModel by viewModels()
    private val peopleViewModel: PeopleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // The top app bar is set up using the toolbar from ActivityMainBinding.
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // The top app bar is already configured with setupActionBarWithNavController.
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_top10, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.nav_detail -> {
                    val id = arguments?.getInt("malId") ?: 0
                    val type = arguments?.getString("mediaType") ?: "Media"
                    if (type == "anime") {
//                        animeViewModel.getAnimeById(id)
                        collectSelectedAnime()
                    } else if (type == "manga") {
                        mangaViewModel.getMangaById(id)
                        collectSelectedManga()
                    } else if (type == "character") {
                        characterViewModel.getCharacterById(id)
                        collectSelectedCharacter()
                    } else if (type == "people") {
                        peopleViewModel.getPeopleById(id)
                        collectSelectedPeople()
                    }
                }

                R.id.nav_top10 -> supportActionBar?.title = "Top 10"
            }
        }
    }

    fun collectSelectedCharacter() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterViewModel.selectedCharacter.collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            supportActionBar?.title = response.data.name
                        }

                        is ApiResponse.Error -> {}
                        is ApiResponse.Loading -> {}
                    }
                }
            }
        }
    }

    fun collectSelectedPeople() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                peopleViewModel.selectedPeople.collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            supportActionBar?.title = response.data.name
                        }

                        is ApiResponse.Error -> {}
                        is ApiResponse.Loading -> {}
                    }
                }
            }
        }
    }

    fun collectSelectedAnime() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                animeViewModel.selectedAnime.collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            val title = response.data.anime.title
                            supportActionBar?.title = title
                        }

                        is ApiResponse.Error -> {}
                        is ApiResponse.Loading -> {}
                    }
                }
            }
        }
    }

    fun collectSelectedManga() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mangaViewModel.selectedManga.collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            val title = response.data.title
                            supportActionBar?.title = title
                        }
                        is ApiResponse.Error -> {}
                        is ApiResponse.Loading -> {}
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}