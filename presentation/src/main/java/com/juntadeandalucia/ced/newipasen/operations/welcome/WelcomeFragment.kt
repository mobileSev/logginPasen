package com.juntadeandalucia.ced.newipasen.operations.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juntadeandalucia.ced.newipasen.R


class WelcomeFragment : Fragment() {

    //val args: WelcomeFragment by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //TODO message argument not recognized
        //tvWelcome.text = args.message
    }
}
