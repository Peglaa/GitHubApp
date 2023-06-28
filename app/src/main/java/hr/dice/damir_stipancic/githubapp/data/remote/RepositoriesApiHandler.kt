package hr.dice.damir_stipancic.githubapp.data.remote

import hr.dice.damir_stipancic.githubapp.ui.search_results.SearchResultsUiState
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

/**
 * Function responsible for performing the network API call and handling possible response states
 * @param [api] Suspend function block for calling the wanted API call function
 * @return [SearchResultsUiState] representing the state of the network API response
 */
suspend fun <T> callApi(api: suspend () -> Response<T>): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            api().let { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Resource.Success(data = body)
                } else {
                    Resource.Error(
                        code = response.code(),
                        message = response.message(),
                        isInternetException = false
                    )
                }
            }
        } catch (e: HttpException) {
            Resource.Error(
                code = e.code(),
                message = e.localizedMessage,
                isInternetException = false
            )
        } catch (e: IOException) {
            Resource.Error(
                code = null,
                message = e.localizedMessage,
                isInternetException = true
            )
        } catch (e: Exception) {
            println(e.localizedMessage)
            Resource.Error(
                code = null,
                message = e.localizedMessage,
                isInternetException = false
            )
        }
    }
}
