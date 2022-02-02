package dev.johnoreilly.fantasypremierleague

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import dev.johnoreilly.fantasypremierleague.presentation.Screen
import dev.johnoreilly.fantasypremierleague.presentation.bottomNavigationItems
import dev.johnoreilly.fantasypremierleague.presentation.fixtures.FixturesListView
import dev.johnoreilly.fantasypremierleague.presentation.fixtures.fixtureDetails.FixtureDetailsView
import dev.johnoreilly.fantasypremierleague.presentation.global.FantasyPremierLeagueTheme
import dev.johnoreilly.fantasypremierleague.presentation.players.PlayerListView
import dev.johnoreilly.fantasypremierleague.presentation.FantasyPremierLeagueViewModel
import dev.johnoreilly.fantasypremierleague.presentation.players.playerDetails.PlayerDetailsView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val fantasyPremierLeagueViewModel: FantasyPremierLeagueViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainLayout(fantasyPremierLeagueViewModel)
        }
    }
}

@Composable
fun MainLayout(fantasyPremierLeagueViewModel: FantasyPremierLeagueViewModel) {
    val navController = rememberNavController()

    FantasyPremierLeagueTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    bottomNavigationItems.forEach { bottomNavigationitem ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    bottomNavigationitem.icon,
                                    contentDescription = bottomNavigationitem.iconContentDescription
                                )
                            },
                            selected = currentRoute == bottomNavigationitem.route,
                            onClick = {
                                navController.navigate(bottomNavigationitem.route) {
                                    popUpTo(navController.graph.id)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        ) {
            NavHost(navController, startDestination = Screen.PlayerListScreen.title) {
                composable(Screen.PlayerListScreen.title) {
                    PlayerListView(
                        fantasyPremierLeagueViewModel = fantasyPremierLeagueViewModel,
                        onPlayerSelected = { playerId ->
                            navController.navigate(Screen.PlayerDetailsScreen.title + "/${playerId}")
                        }
                    )
                }
                composable(
                    Screen.PlayerDetailsScreen.title + "/{playerId}",
                    arguments = listOf(navArgument("playerId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    val playerId: Int? = navBackStackEntry.arguments?.getInt("playerId")
                    playerId?.let {
                        val player = fantasyPremierLeagueViewModel.getPlayer(playerId)
                        player?.let {
                            PlayerDetailsView(player, popBackStack = { navController.popBackStack() })
                        }
                    }
                }
                composable(Screen.FixtureListScreen.title) {
                    FixturesListView(
                        fantasyPremierLeagueViewModel = fantasyPremierLeagueViewModel,
                        onFixtureSelected = { fixtureId ->
                            navController.navigate(Screen.FixtureDetailsScreen.title + "/${fixtureId}")
                        }
                    )
                }
                composable(
                    Screen.FixtureDetailsScreen.title + "/{fixtureId}",
                    arguments = listOf(navArgument("fixtureId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    val fixtureId: Int? = navBackStackEntry.arguments?.getInt("fixtureId")
                    fixtureId?.let {
                        val fixture = fantasyPremierLeagueViewModel.getFixture(fixtureId)
                        fixture?.let {
                            FixtureDetailsView(fixture, popBackStack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}