package jaza.technical.assessment.ui.screens.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import jaza.technical.assessment.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserListUiState())
    val uiState: StateFlow<UserListUiState> = _uiState.asStateFlow()

    val userPagingFlow = userRepository.getUsers().cachedIn(viewModelScope)

    fun refreshUsers() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isRefreshing = true) }
                userRepository.refreshUsers()
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    fun handleError(error: Throwable) {
        _uiState.update {
            it.copy(errorMessage = error.message ?: "Unknown error occurred")
        }
    }

    fun clearError() = _uiState.update { it.copy(errorMessage = null) }
}

data class UserListUiState(
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
)