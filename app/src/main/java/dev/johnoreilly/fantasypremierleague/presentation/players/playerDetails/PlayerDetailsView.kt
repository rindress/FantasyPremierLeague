package dev.johnoreilly.fantasypremierleague.presentation.players.playerDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import coil.compose.rememberImagePainter
import dev.johnoreilly.common.domain.entities.Player


@Composable
fun PlayerDetailsView(player: Player, popBackStack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Player details")
                },
                navigationIcon = {
                    IconButton(onClick = { popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                    painter = rememberImagePainter(player.photoUrl),
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally),
                    contentDescription = player.name
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "INFO",
                        fontWeight = FontWeight.Bold
                    )
                }
                PlayerStatView("Team", player.team)
                PlayerStatView("CurrentPrice", player.currentPrice.toString())
                PlayerStatView("Points", player.points.toString())
                PlayerStatView("Goals Scored", player.goalsScored.toString())
                PlayerStatView("Assists", player.assists.toString())
            }
        }
}

@Composable
fun PlayerStatView(statName: String, statValue: String) {
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