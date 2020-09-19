package com.purewowstudio.socketiochat.ui.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.purewowstudio.socketiochat.R
import com.purewowstudio.socketiochat.ui.theme.SocketIOChatTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                SocketIOChatTheme {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "SocketIO Chat", style = MaterialTheme.typography.h1)
                        UsernameField()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel._state.observe(viewLifecycleOwner, {
            if (it is LoginViewState.Success)
                view?.findViewById<TextView>(R.id.message)?.text = it.message
        })
    }
}

@Composable
fun UsernameField() {

    val usernameField = remember { mutableStateOf("Test") }

    TextField(
        value = usernameField.value,
        onValueChange = {
            usernameField.value = it
        }
    )
}
