package com.juntadeandalucia.ced.newipasen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.juntadeandalucia.ced.newipasen.base.BackPressedListener
import com.juntadeandalucia.ced.newipasen.base.BaseActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

    }


    override fun onBackPressed() {
        val fragment: BackPressedListener? = getCurrentBackPressedListenerFragment()
        if (fragment == null) {
            super.onBackPressed()
        } else {
            fragment.onBackPressed()
        }
    }


    private fun getCurrentBackPressedListenerFragment(): BackPressedListener? {
        val fragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (fragment != null && fragment is NavHostFragment) {
            for (childFragment in fragment.childFragmentManager.fragments) {
                if (childFragment is BackPressedListener) {
                    return childFragment
                }
            }
        }

        return null
    }
}