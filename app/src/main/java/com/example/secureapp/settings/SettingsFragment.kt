package com.example.secureapp.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.secureapp.R
import com.example.secureapp.databinding.SettingsFragmentBinding
import com.example.secureapp.pincode.PinCodeFragment

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onPassCodeChecked.observe(viewLifecycleOwner, Observer {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, PinCodeFragment.newInstance())
                ?.commitNow()
        })
    }

}