package io.github.wulkanowy.ui.main.grade

import dagger.Module
import dagger.Provides
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.wulkanowy.di.scopes.PerFragment

@Module
class GradeModule {

    @PerFragment
    @Provides
    fun provideGradeAdapter() = FlexibleAdapter<GradeHeader>(null)

}

