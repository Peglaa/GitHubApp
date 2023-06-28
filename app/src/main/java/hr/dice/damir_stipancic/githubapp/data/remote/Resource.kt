package hr.dice.damir_stipancic.githubapp.data.remote

/**
 * A sealed class representing different states of an API network response.
 *
 * Use [Success] to signify a successful API response that returns some data
 *
 * Use [Error] to signify a failed API response with a corresponding message and/or throwable Exception
 *
 */
sealed class Resource<T> {
    /**
     * Data class representing a successful API response
     *
     * @param [data] generic type representing the response data
     */
    data class Success<T>(val data: T?) : Resource<T>()

    /**
     * Data class representing a failed API response
     *
     * @param [code] error HTTP code
     * @param [message] a message explaining the error
     */
    data class Error<T>(
        val code: Int? = null,
        val message: String? = null,
        val isInternetException: Boolean
    ) : Resource<T>()
}
