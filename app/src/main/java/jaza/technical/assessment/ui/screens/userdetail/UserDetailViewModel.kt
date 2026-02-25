package jaza.technical.assessment.ui.screens.userdetail
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jaza.technical.assessment.data.repository.UserRepository
import jaza.technical.assessment.domain.model.UserDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val username: String = checkNotNull(savedStateHandle["username"])

    private val _uiState = MutableStateFlow(UserDetailUiState(username = username))
    val uiState: StateFlow<UserDetailUiState> = _uiState.asStateFlow()

    init {
        loadUserDetail()
    }

    fun loadUserDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = userRepository.getUserDetail(username).last()

            _uiState.update {
                when {
                    result.isSuccess -> it.copy(
                        isLoading = false,
                        userDetail = result.getOrNull()?.toDomain()
                    )
                    else -> it.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message
                    )
                }
            }
        }
    }

    fun retry() =  loadUserDetail()
}

data class UserDetailUiState(
    val isLoading: Boolean = false,
    val userDetail: UserDetail? = null,
    val error: String? = null,
    val username: String
)