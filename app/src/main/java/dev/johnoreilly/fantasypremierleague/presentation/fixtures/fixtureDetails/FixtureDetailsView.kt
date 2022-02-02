package dev.johnoreilly.fantasypremierleague.presentation.fixtures.fixtureDetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.johnoreilly.common.domain.entities.GameFixture
import dev.johnoreilly.fantasypremierleague.presentation.fixtures.ClubInFixtureView
import dev.johnoreilly.fantasypremierleague.presentation.FantasyPremierLeagueViewModel

@Composable
fun FixtureDetailsView(fixture: GameFixture, popBackStack: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Fixture details")
                },
                navigationIcon = {
                    IconButton(onClick = { popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ClubInFixtureView(
                    fixture.homeTeam,
                    fixture.homeTeamPhotoUrl
                )
                Text(
                    text = "(${fixture.homeTeamScore})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = "vs",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Text(
                    text = "(${fixture.awayTeamScore})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                )
                ClubInFixtureView(
                    fixture.awayTeam,
                    fixture.awayTeamPhotoUrl
                )
            }

            fixture.localKickoffTime?.let { localKickoffTime ->
                val formattedTime = "%02d:%02d".format(localKickoffTime.hour, localKickoffTime.minute)
                PastFixtureStatView(statName = "Date", statValue = localKickoffTime.date.toString())
                PastFixtureStatView(statName = "Kick Off Time", statValue = formattedTime)
            }
        }
    }
}

@Composable
fun PastFixtureStatView(statName: String, statValue: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = statName,
                    fontWeight = FontWeight.Bold
                )
            }
            Column {
                Text(
                    text = statValue,
                    color = Color(0xFF3179EA),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Divider(thickness = 1.dp)
    }
}