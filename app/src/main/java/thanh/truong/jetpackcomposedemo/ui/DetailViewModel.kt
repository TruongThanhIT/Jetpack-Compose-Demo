package thanh.truong.jetpackcomposedemo.ui

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import thanh.truong.jetpackcomposedemo.ui.db.DemoDatabase
import thanh.truong.jetpackcomposedemo.ui.db.UserDao
import thanh.truong.jetpackcomposedemo.ui.db.UserEntity
import thanh.truong.jetpackcomposedemo.ui.model.Location
import thanh.truong.jetpackcomposedemo.ui.model.User
import thanh.truong.jetpackcomposedemo.ui.network.ApiClient
import java.util.*

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao: UserDao = DemoDatabase.getUserDao(application)

    fun getUserBy(id: String?): Flow<User?> {
        if (id.isNullOrBlank()) return emptyFlow()
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiClient.getDummyService().getUserById(userId = id)
            if (response.isSuccessful) {
                response.body()?.let { userDto ->
                    userDao.insertUsers(
                        UserEntity(
                            id = userDto.id ?: UUID.randomUUID().toString(),
                            firstName = userDto.firstName,
                            lastName = userDto.lastName,
                            title = userDto.title,
                            picture = userDto.picture,
                            gender = userDto.gender,
                            phone = userDto.phone,
                            email = userDto.email,
                            dateOfBirth = userDto.dateOfBirth,
                            country = userDto.location?.country,
                            state = userDto.location?.state,
                            street = userDto.location?.street,
                            city = userDto.location?.city,
                            registerDate = userDto.registerDate
                        )
                    )
                }
            }
        }
        return userDao.getUserById(id).map { userEntity ->
            if (userEntity == null) return@map null
            User(
                id = userEntity.id,
                firstName = userEntity.firstName,
                lastName = userEntity.lastName,
                title = userEntity.title,
                picture = userEntity.picture,
                gender = userEntity.gender,
                phone = userEntity.phone,
                email = userEntity.email,
                dateOfBirth = userEntity.dateOfBirth,
                location = Location(
                    street = userEntity.street,
                    city = userEntity.city,
                    state = userEntity.state,
                    country = userEntity.country
                ),
                registerDate = userEntity.registerDate
            )
        }.distinctUntilChanged()
    }
}