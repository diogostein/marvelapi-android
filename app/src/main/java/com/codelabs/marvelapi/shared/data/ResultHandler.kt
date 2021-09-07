package com.codelabs.marvelapi.shared.data

import arrow.core.Either
import com.codelabs.marvelapi.core.api.MarvelApiErrorParser
import com.codelabs.marvelapi.core.errors.CustomException
import com.codelabs.marvelapi.core.errors.Failure
import retrofit2.HttpException

object ResultHandler {

    suspend fun <T> onRepository(callback: suspend () -> Either.Right<T>): Either<Failure, T> {
        return try {
           callback.invoke()
        } catch (e: CustomException.Server) {
            Either.Left(e.apiError.getFailureType())
        } catch (e: CustomException) {
            Either.Left(Failure(e.message))
        }
    }

    suspend fun <T> onRemoteDataSource(callback: suspend () -> T): T {
        return try {
            callback.invoke()
        } catch (e: HttpException) {
            throw CustomException.Server(MarvelApiErrorParser.parse(e.response()!!))
        } catch (e: Exception) {
            throw CustomException(message = e.message)
        }
    }

}