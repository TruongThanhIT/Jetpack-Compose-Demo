package thanh.truong.jetpackcomposedemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import thanh.truong.jetpackcomposedemo.ui.model.User

class MainViewModel : ViewModel() {

    val userListLive: LiveData<List<User>>
        get() = _userListLive
    private val _userListLive = MutableLiveData<List<User>>()

    init {
        getFakeData()
    }

    private fun getFakeData() {
        viewModelScope.launch(Dispatchers.Default) {
            val testList = arrayListOf<User>()
            repeat(20) {
                testList += User(
                    id = "$it",
                    firstName = "FirstName $it",
                    lastName = "Last $it",
                    title = "ms",
                    picture = "https://www.seekpng.com/png/detail/9-94457_vector-black-and-white-library-dogs-png-seafood.png"
                )
            }
            _userListLive.postValue(testList)
        }
    }
}