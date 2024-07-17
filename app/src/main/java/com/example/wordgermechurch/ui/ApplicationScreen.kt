package com.example.wordgermechurch.ui


//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wordgermechurch.R
import com.example.wordgermechurch.ui.create.ItemEditDestination
import com.example.wordgermechurch.ui.create.ItemEditScreen
import com.example.wordgermechurch.ui.create.SimpleOutlinedTextFieldSample
import com.example.wordgermechurch.ui.home.HomeScreen
import com.example.wordgermechurch.ui.home.HommeViewModel
import com.example.wordgermechurch.ui.listng.FavoriteScreen
import com.example.wordgermechurch.ui.listng.ListCard
import kotlinx.coroutines.launch


enum class ApplicationScreen(@StringRes val title: Int, val icon: ImageVector) {
    Home(R.string.home_button, Icons.Default.Home),
    Create(R.string.create_button, Icons.Default.Add),
    Favorites(R.string.favorites_button, Icons.Default.Favorite),
    All(R.string.punch_button, Icons.Default.MoreVert)
}




// TODO: AppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack :Boolean,
    navigateUp:()->Unit,
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreenTitle)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        modifier =Modifier.padding(bottom = 56.dp)
    ) {
        val coroutineScope = rememberCoroutineScope()
        ApplicationScreen.entries.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = stringResource(id = screen.title)) },
                label = { Text(text = stringResource(id = screen.title)) },
                selected = false, // Gérer la sélection de l'élément actif
                onClick = {
                    coroutineScope.launch {
                        navController.navigate(screen.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ApplicationScreen(
    navController: NavHostController = rememberNavController()
){


    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ApplicationScreen.valueOf(
            backStackEntry?.destination?.route ?:ApplicationScreen.Favorites.name
    )

        Scaffold(
                topBar = {
                    ApplicationAppBar(
                        currentScreenTitle = currentScreen.title,
                        canNavigateBack = navController.previousBackStackEntry !=null,
                        navigateUp = {  navController.navigateUp()}

                    )
                },
            bottomBar = { BottomNavigationBar(navController) }
        ) {innerPadding ->
            NavHost(
                navController = navController,
                startDestination = ApplicationScreen.Home.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(ApplicationScreen.Home.name) {
                    val viewModel: HommeViewModel = viewModel(factory = AppViewModelProvider.Factory)
                    val uiState by viewModel.uiState.collectAsState()
                    HomeScreen(
                        onUpdateScreen = { viewModel.getItem() },
                        uiState = uiState,
                        onLikeClicked = { item -> viewModel.incrementLiked(item) },
                        navigateToItemModfiy={}
                    )
                }
                composable(ApplicationScreen.Create.name) {
                    SimpleOutlinedTextFieldSample(
                        navigateHome = { navController.navigate(ApplicationScreen.Home.name) }
                    )/* Contenu de l'écran Create */
                }
                composable(ApplicationScreen.Favorites.name) {
                    FavoriteScreen() /* Contenu de l'écran Favorites */
                }
                composable(ApplicationScreen.All.name) {
                    ListCard() /* Contenu de l'écran Punch */
                }
                composable(
                    route = ItemEditDestination.routeWithArgs,
                    arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                        type = NavType.IntType
                    })
                ) {
                    ItemEditScreen(
                        navigateBack = { navController.popBackStack() },
                        onNavigateUp = { navController.navigateUp() }
                    )
                }

            }


        }
}


@Preview
@Composable
fun PreviewApp(){
    ApplicationScreen()
}
