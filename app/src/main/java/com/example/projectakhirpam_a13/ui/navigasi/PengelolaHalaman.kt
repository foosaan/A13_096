package com.example.projectakhirpam_a13.ui.navigasi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.loket.ui.view.peserta.DestinasiHomePeserta
import com.example.loket.ui.view.peserta.HomeScreenPeserta
import com.example.projectakhirpam_a13.ui.view.event.DestinasiDetailEvent
import com.example.projectakhirpam_a13.ui.view.event.DestinasiEntryEvent
import com.example.projectakhirpam_a13.ui.view.event.DestinasiHome
import com.example.projectakhirpam_a13.ui.view.event.DestinasiUpdateEvent
import com.example.projectakhirpam_a13.ui.view.event.DetailEventView
import com.example.projectakhirpam_a13.ui.view.event.EventInsertView
import com.example.projectakhirpam_a13.ui.view.event.EventUpdateView
import com.example.projectakhirpam_a13.ui.view.event.HomeScreenEvent
import com.example.projectakhirpam_a13.ui.view.peserta.DestinasiDetail
import com.example.projectakhirpam_a13.ui.view.peserta.DestinasiEntryPeserta
import com.example.projectakhirpam_a13.ui.view.peserta.DestinasiUpdate
import com.example.projectakhirpam_a13.ui.view.peserta.PesertaDetailView
import com.example.projectakhirpam_a13.ui.view.peserta.PesertaEntryScreen
import com.example.projectakhirpam_a13.ui.view.peserta.PesertaUpdateView
import com.example.projectakhirpam_a13.ui.view.tiket.DestinasiHomeTkt
import com.example.projectakhirpam_a13.ui.view.tiket.HomeScreenTkt

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // Home Event
        composable(DestinasiHome.route) {
            HomeScreenEvent(
                navigateToEventEntry = { navController.navigate(DestinasiEntryEvent.route) },
                onDetailClick = { idEvent ->
                    navController.navigate("${DestinasiDetailEvent.route}/$idEvent")
                },
                navigateToEvent = { navController.navigate(DestinasiHome.route) },
                navigateToTiket = { },
                navigateToTransaksi = { },
                navigateToPengguna = { navController.navigate(DestinasiHomePeserta.route) },
                navigateToUpdateEvent = { idEvent ->
                    navController.navigate("${DestinasiUpdateEvent.route}/$idEvent")
                }
            )
        }

        // Entry Event
        composable(DestinasiEntryEvent.route) {
            EventInsertView(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Event
        composable(
            DestinasiDetailEvent.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailEvent.idEvent) { type = NavType.IntType })
        ) { backStackEntry ->
            val idEvent = backStackEntry.arguments?.getInt(DestinasiDetailEvent.idEvent) ?: -1
            DetailEventView(
                idEvent = idEvent,
                navigateBack = { navController.popBackStack() },
                onEditClick = { idEvent ->
                    navController.navigate("${DestinasiUpdateEvent.route}/$idEvent")
                }
            )
        }

        // Update Event
        composable(
            DestinasiUpdateEvent.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdateEvent.idEvent) { type = NavType.IntType })
        ) { backStackEntry ->
            val idEvent = backStackEntry.arguments?.getInt(DestinasiUpdateEvent.idEvent)
            idEvent?.let {
                EventUpdateView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }


        composable(DestinasiHomePeserta.route) {
            HomeScreenPeserta(
                navigateToPesertaEntry = { navController.navigate(DestinasiEntryPeserta.route) },
                onDetailClick = { idPeserta ->
                    navController.navigate("${DestinasiDetail.route}/$idPeserta")
                },
                navigateToEvent = { navController.navigate(DestinasiHome.route) },
                navigateToTiket = {},
                navigateToTransaksi = { },
                navigateToPengguna = { navController.navigate(DestinasiHomePeserta.route)},
                navigateToUpdateEvent = { idPeserta ->
                    navController.navigate("${DestinasiUpdateEvent.route}/$idPeserta")
                }
            )
        }


        composable(DestinasiEntryPeserta.route) {
            PesertaEntryScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            DestinasiDetail.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetail.idpeserta) { type = NavType.IntType })
        ) { backStackEntry ->
            val idTerapis = backStackEntry.arguments?.getInt(DestinasiDetail.idpeserta) ?: -1
            PesertaDetailView(
                idPeserta = idTerapis,
                navigateBack = { navController.popBackStack() },
                onEditClick = { idTerapis ->
                    navController.navigate("${DestinasiDetail.route}/$idTerapis")
                }
            )
        }

        // Update Terapis
        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdate.idpeserta) { type = NavType.IntType })
        ) { backStackEntry ->
            val idPeserta = backStackEntry.arguments?.getInt(DestinasiUpdate.idpeserta)
            idPeserta?.let {
                PesertaUpdateView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
    }
}

