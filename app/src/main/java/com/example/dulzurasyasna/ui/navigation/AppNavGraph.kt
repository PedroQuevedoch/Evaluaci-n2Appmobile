package com.example.dulzurasyasna.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dulzurasyasna.data.InMemoryProductRepository
import com.example.dulzurasyasna.ui.CartViewModel
import com.example.dulzurasyasna.ui.screens.AboutScreen
import com.example.dulzurasyasna.ui.screens.CartScreen
import com.example.dulzurasyasna.ui.screens.CheckoutScreen
import com.example.dulzurasyasna.ui.screens.DetailScreen
import com.example.dulzurasyasna.ui.screens.HomeScreen
import com.example.dulzurasyasna.ui.screens.LoginScreen
import com.example.dulzurasyasna.ui.screens.SignUpScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel,
) {
    val navController = rememberNavController()
    val repo = InMemoryProductRepository()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOGIN,
        modifier = modifier
    ) {
        composable(NavRoutes.LOGIN) {
            LoginScreen(
                onLoginClick = { navController.navigate(NavRoutes.HOME) },
                onSignUpClick = { navController.navigate(NavRoutes.SIGNUP) }
            )
        }
        composable(
            route = NavRoutes.SIGNUP,
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                    animationSpec = tween(300),
                    targetOffsetX = { -it }
                )
            }
        ) {
            SignUpScreen(
                onSignUpClick = { navController.navigate(NavRoutes.HOME) },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = NavRoutes.HOME,
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                    animationSpec = tween(300),
                    targetOffsetX = { -it }
                )
            }
        ) {
            HomeScreen(
                products = repo.getProducts(),
                onProductClick = { productId -> navController.navigate(NavRoutes.detail(productId)) },
                onCartClick = { navController.navigate(NavRoutes.CART) },
                onAboutClick = { navController.navigate(NavRoutes.ABOUT) }
            )
        }
        composable(
            route = NavRoutes.DETAIL,
            arguments = listOf(navArgument("productId") { type = NavType.StringType }),
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                    animationSpec = tween(300),
                    targetOffsetX = { -it }
                )
            }
        ) {
            val productId = it.arguments?.getString("productId") ?: return@composable
            val product = repo.getProductById(productId)
            if (product != null) {
                DetailScreen(
                        product = product,
                        onBack = { navController.popBackStack() },
                        onAddToCart = { numberOfPeople ->
                            cartViewModel.addToCart(product, numberOfPeople)
                            navController.navigate(NavRoutes.CART)
                        }
                    )
            } else {
                navController.popBackStack()
            }
        }
        composable(
            route = NavRoutes.CART,
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                    animationSpec = tween(300),
                    targetOffsetX = { -it }
                )
            }
        ) {
            CartScreen(
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onCheckout = { navController.navigate(NavRoutes.CHECKOUT) }
            )
        }
        composable(
            route = NavRoutes.CHECKOUT,
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                    animationSpec = tween(300),
                    targetOffsetX = { -it }
                )
            }
        ) {
            CheckoutScreen(
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onConfirm = {
                    cartViewModel.clearCart()
                    navController.popBackStack(NavRoutes.HOME, inclusive = false)
                }
            )
        }
        composable(
            route = NavRoutes.ABOUT,
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                    animationSpec = tween(300),
                    targetOffsetX = { -it }
                )
            }
        ) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}


