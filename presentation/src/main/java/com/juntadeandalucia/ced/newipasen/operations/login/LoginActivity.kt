package com.juntadeandalucia.ced.newipasen.operations.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.juntadeandalucia.ced.newipasen.R
import org.koin.ext.getFullName

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(savedInstanceState == null )
            supportFragmentManager.beginTransaction()
                .replace(R.id.loginContainer, LoginFragment(),LoginFragment::class.getFullName())
                .commit()

    }
}
