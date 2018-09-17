package io.github.wulkanowy.ui.main.grade

import dagger.Module
import dagger.Provides
import io.github.wulkanowy.di.scopes.PerFragment
import io.github.wulkanowy.ui.base.BasePagerAdapter

@Module
class GradeModule {

    @PerFragment
    @Provides
    fun provideGradePagerAdapter(fragment: GradeFragment) = BasePagerAdapter(fragment.childFragmentManager)
}

