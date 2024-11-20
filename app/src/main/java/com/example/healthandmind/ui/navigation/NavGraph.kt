package com.example.healthandmind.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.healthandmind.domain.usecases.LoginHandlerUseCase
import com.example.healthandmind.ui.components.BufferingScreen
import com.example.healthandmind.ui.components.BufferingScreenDestination
import com.example.healthandmind.ui.screen.Dashboard
import com.example.healthandmind.ui.screen.DashboardDestination
import com.example.healthandmind.ui.screen.DashboardViewModel
import com.example.healthandmind.ui.screen.Diary
import com.example.healthandmind.ui.screen.DiaryDestination
import com.example.healthandmind.ui.screen.DiarySelectionDetail
import com.example.healthandmind.ui.screen.DiarySelectionDetailDestination
import com.example.healthandmind.ui.screen.DiarySelectionDetailViewModel
import com.example.healthandmind.ui.screen.DiaryViewModel
import com.example.healthandmind.ui.screen.FoodDetail
import com.example.healthandmind.ui.screen.FoodDetailViewModel
import com.example.healthandmind.ui.screen.Login
import com.example.healthandmind.ui.screen.Login2
import com.example.healthandmind.ui.screen.Login2Destination
import com.example.healthandmind.ui.screen.Login2ViewModel
import com.example.healthandmind.ui.screen.LoginDestination
import com.example.healthandmind.ui.screen.LoginViewModel
import com.example.healthandmind.ui.screen.PersonalArea
import com.example.healthandmind.ui.screen.PersonalAreaDestination
import com.example.healthandmind.ui.screen.PersonalAreaIDetailViewModel
import com.example.healthandmind.ui.screen.PersonalAreaItemDetail
import com.example.healthandmind.ui.screen.PersonalAreaItemDetailDestination
import com.example.healthandmind.ui.screen.PersonalAreaViewModel
import com.example.healthandmind.ui.screen.TrainingDetailScreen
import com.example.healthandmind.ui.screen.TrainingDetailScreenDestination
import com.example.healthandmind.ui.screen.TrainingDetailViewModel
import com.example.healthandmind.ui.screen.TrainingScreen
import com.example.healthandmind.ui.screen.TrainingScreenDestination
import com.example.healthandmind.ui.screen.TrainingScreenViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: NavViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = uiState.value.startDestination,
        modifier = modifier,
        enterTransition = {
            scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            scaleOut(
                targetScale = 0.8f,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {

        composable(route = BufferingScreenDestination.route) {
            BufferingScreen()
        }
        // Login
        composable(route = LoginDestination.route) {
            Login(
                viewModel = hiltViewModel<LoginViewModel>(),
                onContinueClick = {
                    navController.navigate(Login2Destination.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        //Login2
        composable(
            route = Login2Destination.route ,
        ) {
            Login2(
                viewModel = hiltViewModel<Login2ViewModel>(),
                onConfirmClick = {
                    navController.navigate(DashboardDestination.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        // Diary
        composable(route = DiaryDestination.route) {
            Diary(
                viewModel = hiltViewModel<DiaryViewModel>(),
                navigateToTrainingScreen = {
                    navController.navigate(TrainingScreenDestination.route)
                },
                navigateToDiarySelection = {
                    navController.navigate(DiarySelectionDetailDestination.route)
                },
                onSearchClicked = {},
                onPersonClicked = {
                    navController.navigate(PersonalAreaDestination.route)
                },
                onHomeClicked = {
                    navController.navigate(DashboardDestination.route)
                }
            )
        }
        // TrainingScreen
        composable(route = TrainingScreenDestination.route) {
            TrainingScreen(
                viewModel = hiltViewModel<TrainingScreenViewModel>(),
                onButtonClicked = {
                    navController.navigate(TrainingDetailScreenDestination.route)
                },
                onBackNav = {
                    navController.popBackStack()
                }
            )
        }
        // TrainingDetailScreen
        composable(route = TrainingDetailScreenDestination.route) {
            TrainingDetailScreen(
                viewModel = hiltViewModel<TrainingDetailViewModel>(),
                onSaveClicked = {
                    navController.popBackStack()
                }
            )
        }
        // DiarySelectionDetail
        composable(route = DiarySelectionDetailDestination.route) {
            DiarySelectionDetail(
                viewModel = hiltViewModel<DiarySelectionDetailViewModel>(),
                navigateToFoodDetail = {
                    navController.navigate(FoodDetail.route)
                },
                onBackNav = {
                    navController.popBackStack()
                }
            )
        }
        // FoodDetail
        composable(route = FoodDetail.route) {
            FoodDetail(
                viewModel = hiltViewModel<FoodDetailViewModel>(),
                onBackNav = {
                    navController.popBackStack()
                }
            )
        }

        // Dashboard
        composable(
            route = DashboardDestination.route,
            ) {
            Dashboard(
                onSearchClicked = {
                    navController.navigate(DiaryDestination.route)
                },
                onPersonClicked = {
                    navController.navigate(PersonalAreaDestination.route)
                },
                onHomeClicked = {},
                onTrainingClicked = {
                    navController.navigate(TrainingScreenDestination.route)
                },
                viewModel = hiltViewModel<DashboardViewModel>()
            )

            // TrainingScreen
            composable(route = TrainingScreenDestination.route) {
                TrainingScreen(
                    viewModel = hiltViewModel<TrainingScreenViewModel>(),
                    onButtonClicked = {
                        navController.navigate(TrainingDetailScreenDestination.route)
                    },
                    onBackNav = {
                        navController.popBackStack()
                    }
                )
            }
            // TrainingDetailScreen
            composable(route = TrainingDetailScreenDestination.route) {
                TrainingDetailScreen(
                    viewModel = hiltViewModel<TrainingDetailViewModel>(),
                    onSaveClicked = {
                        navController.popBackStack()
                    }
                )
            }

        }
        // PersonalArea
        composable(route = PersonalAreaDestination.route) {
            PersonalArea(
                viewModel = hiltViewModel<PersonalAreaViewModel>(),
                onHomeClicked = {
                    navController.navigate(DashboardDestination.route)
                },
                onSearchClicked = {
                    navController.navigate(DiaryDestination.route)
                },
                onPersonClicked = {
                    navController.navigate(PersonalAreaDestination.route)
                },
                onPersonalAreaClicked = {
                    navController.navigate(PersonalAreaItemDetailDestination.route)
                }
            )
        }
        // PersonalAreaItemDetail
        composable(route = PersonalAreaItemDetailDestination.route) {
            PersonalAreaItemDetail(
                viewModel = hiltViewModel<PersonalAreaIDetailViewModel>(),
                onBackNav = {
                    navController.popBackStack()
                }
            )
        }
    }
}