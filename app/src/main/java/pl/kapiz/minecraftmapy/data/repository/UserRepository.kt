package pl.kapiz.minecraftmapy.data.repository

import pl.kapiz.minecraftmapy.data.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val api: ApiService) {

    suspend fun getUser(username: String) = api.getUser(username)
}
