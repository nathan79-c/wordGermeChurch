package com.example.wordgermechurch.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordgermechurch.data.Item


@Composable
fun HomeScreen(
    onUpdateScreen: () -> Unit,
    uiState: HomeUistate,
    onLikeClicked: (Item) -> Unit ,
    navigateToItemModfiy: () -> Unit// Ajout de cette ligne
) {
    CardItems(
        onClick = { onUpdateScreen() },
        uiState = uiState,
        onLikeClicked = onLikeClicked ,
        navigateToItemModfiy = navigateToItemModfiy , // Ajout de cette ligne
    )
}

@Composable
fun CardItems(
    onClick: () -> Unit,
    uiState: HomeUistate,
    onLikeClicked: (Item) -> Unit,
    navigateToItemModfiy: () -> Unit,
    modifier: Modifier = Modifier
) {
    val image: Painter = painterResource(id = uiState.currentImage)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Background Image
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Card content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(36.dp)
                ) {
                    Spacer(modifier = Modifier.padding(12.dp))

                    uiState.currentVerset?.let {
                        Text(
                            text = it.content,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        uiState.currentVerset?.let {
                            Text(
                                text = it.description,
                                textAlign = TextAlign.End,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = onClick,
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Next Text",
                                tint = Color.Gray
                            )
                        }
                        IconButton(
                            onClick = {
                                uiState.currentVerset?.let { onLikeClicked(it) }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = if ((uiState.currentVerset?.liked ?: 0) > 0) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Like",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }


        // Floating Action Button
        FloatingActionButton(
            onClick = { /* Action à définir */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Create, contentDescription = "Add")
        }
    }
}






@Preview(showBackground = true)
@Composable
fun ScreenPreview(){
   //CardItems({},ui)
}
