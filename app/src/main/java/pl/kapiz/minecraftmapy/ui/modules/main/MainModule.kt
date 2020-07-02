package pl.kapiz.minecraftmapy.ui.modules.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.kapiz.minecraftmapy.di.ViewModelKey
import pl.kapiz.minecraftmapy.ui.modules.map.MapFragment
import pl.kapiz.minecraftmapy.ui.modules.map.MapViewModel
import pl.kapiz.minecraftmapy.ui.modules.maps.MapsFragment
import pl.kapiz.minecraftmapy.ui.modules.maps.MapsViewModel
import pl.kapiz.minecraftmapy.ui.modules.more.MoreFragment
import pl.kapiz.minecraftmapy.ui.modules.more.MoreViewModel
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
    abstract fun contributeMapsFragment(): MapsFragment

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel::class)
    abstract fun bindMapsViewModel(viewModel: MapsViewModel): ViewModel

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
