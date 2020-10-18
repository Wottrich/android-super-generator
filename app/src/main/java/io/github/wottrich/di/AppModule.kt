package io.github.wottrich.di

import io.github.wottrich.viewModel.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 18/10/2020
 *
 * Copyright © 2020 SuperGenerator. All rights reserved.
 *
 */

val viewModelModule = module {
    viewModel { OverviewViewModel() }
}

object AppModule {

    val modules = listOf(viewModelModule)

}