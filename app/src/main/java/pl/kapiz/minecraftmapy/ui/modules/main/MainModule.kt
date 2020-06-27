package pl.kapiz.minecraftmapy.ui.modules.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.kapiz.minecraftmapy.di.ViewModelKey
import pl.kapiz.minecraftmapy.ui.modules.home.HomeFragment
import pl.kapiz.minecraftmapy.ui.modules.home.HomeViewModel
import pl.kapiz.minecraftmapy.ui.modules.more.MoreFragment
import pl.kapiz.minecraftmapy.ui.modules.more.MoreViewModel
import pl.kapiz.minecraftmapy.ui.modules.search.SearchFragment
import pl.kapiz.minecraftmapy.ui.modules.search.SearchViewModel

@Suppress("unused")
@Module
internal abstract class MainModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeMoreFragment(): MoreFragment

    @Binds
    @IntoMap
    @ViewModelKey(MoreViewModel::class)
    internal abstract fun bindMoreViewModel(viewModel: MoreViewModel): ViewModel
}
