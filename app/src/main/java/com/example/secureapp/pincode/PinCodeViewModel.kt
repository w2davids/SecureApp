package com.example.secureapp.pincode

import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secureapp.R
import kotlinx.coroutines.launch
import timber.log.Timber

class PinCodeViewModel : ViewModel() {

    companion object {

        const val MAX_FAILED_ATTEMPTS = 3
        const val PASSCODE = "3245"

        @BindingAdapter("visibility")
        @JvmStatic
        fun setVisibility(view: View, visible: Boolean) {
            view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        }
    }

    private val failedAttempts: ObservableField<Int> = ObservableField(0)
    private val onError: ObservableField<Boolean> = ObservableField(false)

    val passCodeTitle: ObservableField<Int> = ObservableField(R.string.new_passcode)
    val pinCode: MutableLiveData<String> = MutableLiveData<String>("")
    val onIncorrectPinCode: ObservableField<Boolean> = ObservableField(false)
    val onTooManyFailedAttempts: ObservableField<Boolean> = ObservableField(false)
    val enableOverlay: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)


    private fun verifyPinCode(pinCode: String?) {
        pinCode?.let {

            if (it == PASSCODE) {
                enableOverlay.value = false
                onError.set(false)
                passCodeTitle.set(R.string.enter_passcode)
            } else {
                onFailedAttempt()
            }
        }
    }

    fun resetAttempts() {
        failedAttempts.set(0)
        enableOverlay.value = false
        reset()
    }

    fun reset() {
        onError.set(false)
        onIncorrectPinCode.set(false)
        onTooManyFailedAttempts.set(false)
        passCodeTitle.set(R.string.enter_passcode)
    }

    fun onSubmit(actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            passCodeTitle.set(R.string.confirm_passcode)
            verifyPinCode(pinCode.value)
            true
        }
        return false
    }

    fun onFailedAttempt() {
        if (failedAttempts.get() == MAX_FAILED_ATTEMPTS - 1) {
            onTooManyFailedAttempts.set(true)
            viewModelScope.launch {
                lock()
            }
        } else {
            onIncorrectPinCode.set(true)
            failedAttempts.set(failedAttempts.get()?.plus(1))
        }
    }

    suspend fun lock() {
        enableOverlay.value = true
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Timber.d("=> Locked for: $millisUntilFinished millisec.")
            }

            override fun onFinish() {
                resetAttempts()
            }
        }.start()
    }
}