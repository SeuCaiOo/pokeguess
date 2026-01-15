package br.com.seucaio.pokeguess.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StartTimerUseCase {
    operator fun invoke(initialTime: Int, delayMillis: Long = 1000L): Flow<Int> {
        return flow {
            var currentTime = initialTime
            while (currentTime > 0) {
                delay(delayMillis)
                currentTime--
                emit(currentTime)
            }
        }
    }
}
