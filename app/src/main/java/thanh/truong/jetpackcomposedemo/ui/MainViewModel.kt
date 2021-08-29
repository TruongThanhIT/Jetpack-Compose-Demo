package thanh.truong.jetpackcomposedemo.ui

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import thanh.truong.jetpackcomposedemo.ui.db.DemoDatabase
import thanh.truong.jetpackcomposedemo.ui.db.UserDao
import thanh.truong.jetpackcomposedemo.ui.db.UserEntity
import thanh.truong.jetpackcomposedemo.ui.model.User
import thanh.truong.jetpackcomposedemo.ui.network.ApiClient
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val userListLive: LiveData<List<User>>
        get() = _userListLive
    private val _userListLive = MutableLiveData<List<User>>()
    private val userDao: UserDao = DemoDatabase.getUserDao(application)

    init {
        getUserList()
    }

    private fun getUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiClient.getDummyService().getUsers()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    val userEntities = it.map { userDto ->
                        UserEntity(
                            id = userDto.id ?: UUID.randomUUID().toString(),
                            firstName = userDto.firstName,
                            lastName = userDto.lastName,
                            title = userDto.title,
                            picture = userDto.picture
                        )
                    }
                    userDao.insertUsers(*userEntities.toTypedArray())
                }
            }
            val users = userDao.getAllUsers().map { userDto ->
                User(
                    id = userDto.id,
                    firstName = userDto.firstName,
                    lastName = userDto.lastName,
                    title = userDto.title,
                    picture = userDto.picture
                )
            }
            _userListLive.postValue(users)
        }
    }
}