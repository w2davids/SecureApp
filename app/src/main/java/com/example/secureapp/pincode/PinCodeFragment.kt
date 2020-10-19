package com.example.secureapp.pincode

import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.secureapp.R
import com.example.secureapp.databinding.PinCodeFragmentBinding
import com.example.secureapp.lockscreen.LockScreenUtil

class PinCodeFragment : Fragment() {

    companion object {
        fun newInstance() = PinCodeFragment()
    }

    private lateinit var viewModel: PinCodeViewModel
    private lateinit var binding: PinCodeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pin_code_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PinCodeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.enableOverlay.observe(viewLifecycleOwner, Observer {
            if (it) {
                LockScreenUtil.lock(activity)
                Toast.makeText(
                    activity,
                    "SCREEN LOCKED - INPUT DISABLED - ${PinCodeViewModel.PASSCODE}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                LockScreenUtil.unlock()
                Toast.makeText(activity, "SCREEN UNLOCKED", Toast.LENGTH_SHORT).show()
            }
        })

        binding.pos1.apply {

            doAfterTextChanged {
                goNext(it, 2)
            }

            setOnKeyListener { _, keyCode, _ ->
                when (keyCode) {
                    KeyEvent.KEYCODE_DEL -> goPrevious(1)
                }
                false
            }
        }

        binding.pos2.apply {

            doAfterTextChanged {
                goNext(it, 3)
            }

            setOnKeyListener { _, keyCode, _ ->
                when (keyCode) {
                    KeyEvent.KEYCODE_DEL -> goPrevious(1)
                }
                false
            }
        }

        binding.pos3.apply {

            doAfterTextChanged {
                goNext(it, 4)
            }

            setOnKeyListener { _, keyCode, _ ->
                when (keyCode) {
                    KeyEvent.KEYCODE_DEL -> goPrevious(2)
                }
                false
            }
        }

        binding.pos4.apply {

            doAfterTextChanged {
                goNext(it, 4)
            }

            setOnKeyListener { _, keyCode, _ ->
                when (keyCode) {
                    KeyEvent.KEYCODE_DEL -> {
                        viewModel.reset()
                        goPrevious(3)
                    }
                }
                false
            }

            setOnEditorActionListener { _, actionId, _ ->
                viewModel.onSubmit(actionId)
            }
        }
    }

    private fun EditText.goNext(it: Editable?, pos: Int) {
        if (it?.length == 0) {
            background = activity?.getDrawable(R.drawable.pin_code_cell_background)
        } else {
            background = activity?.getDrawable(R.drawable.pin_code_cell_full)
            viewModel.pinCode.value += text
            goTo(pos)
        }
    }

    private fun goPrevious(pos: Int) {
        viewModel.pinCode.value = viewModel.pinCode.value?.dropLast(1)
        goTo(pos)
    }

    private fun goTo(pos: Int) {
        when (pos) {
            1 -> binding.pos1.requestFocus()
            2 -> binding.pos2.requestFocus()
            3 -> binding.pos3.requestFocus()
            4 -> binding.pos4.requestFocus()
        }
    }

}