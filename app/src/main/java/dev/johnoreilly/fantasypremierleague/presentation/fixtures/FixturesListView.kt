package dev.johnoreilly.fantasypremierleague.presentation.fixtures

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import dev.johnoreilly.fantasypremierleague.presentation.FantasyPremierLeagueViewModel

@Composable
fun FixturesListView(
    fantasyPremierLeagueViewModel: FantasyPremierLeagueViewModel,
    onFixtureSelected: (fixtureId: Int) -> Unit
) {
    val pastFixturesState = fantasyPremierLeagueViewModel.fixturesList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Fantasy Premier League") })
        }) {
            Column {
                LazyColumn {
                    items(items = pastFixturesState.value, itemContent = { fixture ->
                        FixtureView(
                            fixture = fixture,
                            onFixtureSelected = onFixtureSelected
                        )
                    })
                }
            }
        }
}