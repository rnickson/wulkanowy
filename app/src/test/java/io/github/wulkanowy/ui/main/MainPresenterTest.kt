package io.github.wulkanowy.ui.main

import io.github.wulkanowy.TestSchedulers
import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.repositories.StudentRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock
    lateinit var repository: StudentRepository

    @Mock
    lateinit var errorHandler: ErrorHandler

    @Mock
    lateinit var mainView: MainView

    private lateinit var presenter: MainPresenter

    @Before
    fun initPresenter() {
        MockitoAnnotations.initMocks(this)
        clearInvocations(mainView)

        doReturn(Completable.complete()).`when`(repository).initLastStudentSession() //TODO Temporary solution

        presenter = MainPresenter(errorHandler, repository, TestSchedulers())
        presenter.attachView(mainView)
    }

    @Test
    fun initMenuTest() {
        verify(mainView).initBottomNav()
        verify(mainView).initFragmentController()
    }

    @Test
    fun onTabSelectedTest() {
        presenter.onTabSelected(1)
        verify(mainView).switchMenuFragment(1)
    }

    @Test
    fun onMenuFragmentChangeTest() {
        doReturn(mapOf(1 to "Test")).`when`(mainView).mapOfTitles()
        presenter.onMenuFragmentChange(1)
        verify(mainView).setViewTitle("Test")
    }

    @Test
    fun onMenuFragmentChangeDefaultTest() {
        doReturn(emptyMap<Int, String>()).`when`(mainView).mapOfTitles()
        doReturn("Default").`when`(mainView).defaultTitle()
        presenter.onMenuFragmentChange(2)
        verify(mainView).setViewTitle("Default")
    }
}

