package com.juntadeandalucia.ced.newipasen.operations.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.juntadeandalucia.ced.newipasen.BuildConfig
import com.juntadeandalucia.ced.newipasen.R
import com.juntadeandalucia.ced.newipasen.extension.hide
import com.juntadeandalucia.ced.newipasen.extension.show
import com.juntadeandalucia.ced.newipasen.extension.toast
import com.juntadeandalucia.ced.newipasen.operations.Login.LoginFragmentDirections
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val loginViewModel by viewModel <LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
    }


    private fun initObservers() {

        loginViewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoginViewState.Login -> {
                    view?.let { v ->
                        Navigation.findNavController(v)
                            .navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment("Bienvenido")) }
                }

                is LoginViewState.Loading -> {
                    tvBtnLogin.hide()
                    pbBtnLogin.show()
                }

                is LoginViewState.Error -> {
                    it.error.toast(context)
                }
            }
        })
    }


    private fun initListeners() {

        btnLogin.setOnClickListener {
            loginViewModel.doLogin(
                tilUsername.editText?.text.toString(),
                tilUserPass.editText?.text.toString(),
                BuildConfig.VERSION_NAME)
        }
    }

}
