package io.github.wulkanowy.ui.splash

import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.repositories.SessionRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SplashPresenterTest {

    @Mock
    lateinit var splashView: SplashView

    @Mock
    lateinit var sessionRepository: SessionRepository

    @Mock
    lateinit var errorHandler: ErrorHandler

    private lateinit var presenter: SplashPresenter

    @Before
    fun initPresenter() {
        MockitoAnnotations.initMocks(this)
        presenter = SplashPresenter(sessionRepository, errorHandler)
    }

    @Test
    fun testOpenLoginView() {
        doReturn(false).`when`(sessionRepository).isSessionSaved
        presenter.attachView(splashView)
        verify(splashView).openLoginView()
    }

    @Test
    fun testMainMainView() {
        doReturn(true).`when`(sessionRepository).isSessionSaved
        presenter.attachView(splashView)
        verify(splashView).openMainView()
    }
}