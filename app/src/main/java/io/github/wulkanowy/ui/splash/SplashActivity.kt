package io.github.wulkanowy.ui.splash

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import io.github.wulkanowy.ui.base.BaseActivity
import io.github.wulkanowy.ui.login.LoginActivity
import io.github.wulkanowy.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showMessage(text: String) {
        Toast.makeText(applicationContext, text, LENGTH_LONG).show()
    }

    override fun finishApp() {
        finish()
    }

    override fun openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this))
        finish()
    }

    override fun openMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}
