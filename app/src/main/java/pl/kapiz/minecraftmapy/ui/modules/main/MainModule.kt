package pl.kapiz.minecraftmapy.ui.modules.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.kapiz.minecraftmapy.di.ViewModelKey
import pl.kapiz.minecraftmapy.ui.modules.discover.DiscoverFragment
import pl.kapiz.minecraftmapy.ui.modules.discover.DiscoverViewModel
import pl.kapiz.minecraftmapy.ui.modules.map.MapFragment
import pl.kapiz.minecraftmapy.ui.modules.map.MapViewModel
import pl.kapiz.minecraftmapy.ui.modules.more.MoreFragment
import pl.kapiz.minecraftmapy.ui.modules.more.MoreViewModel
import pl.kapiz.minecraftmapy.ui.modules.newest.NewestFragment
import pl.kapiz.minecraftmapy.ui.modules.newest.NewestViewModel
import pl.kapiz.minecraftmapy.ui.modules.search.SearchFragment
import pl.kapiz.minecraftmapy.ui.modules.search.SearchViewModel
import pl.kapiz.minecraftmapy.ui.modules.user.UserFragment
import pl.kapiz.minecraftmapy.ui.modules.user.UserViewModel

@Suppress("unused")
@Module
internal abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeNewestFragment(): NewestFragment

    @Binds
    @IntoMap
    @ViewModelKey(NewestViewModel::class)
    abstract fun bindNewestViewModel(viewModel: NewestViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeDiscoverFragment(): DiscoverFragment

    @Binds
    @IntoMap
    @ViewModelKey(DiscoverViewModel::class)
    abstract fun bindDiscoverViewModel(viewModel: DiscoverViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeMoreFragment(): MoreFragment

    @Binds
    @IntoMap
    @ViewModelKey(MoreViewModel::class)
    abstract fun bindMoreViewModel(viewModel: MoreViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapFragment

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(viewModel: MapViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel
}
