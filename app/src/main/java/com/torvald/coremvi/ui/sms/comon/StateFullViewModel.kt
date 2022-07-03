package com.torvald.coremvi.ui.sms.comon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


abstract class StateFullViewModel<S, E> : ViewModel() {

    private val mutex = Mutex()

    abstract val state: S

    abstract fun obtainEvent(event: E)

    private val _error = MutableSharedFlow<Error>()
    val error get() = _error.asSharedFlow()

    private val loading = MutableStateFlow(0)
    val isLoading get() = loading.asStateFlow().map { it != 0 }

    private var errorHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _error.emit(Error.Unknown(throwable.message.toString()))
            loading.decrement()
        }
    }

    protected fun networkCall(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(errorHandler) {
            loading.increment()
            block()
            loading.decrement()
        }
    }

    protected fun launchInScope(
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch() {
        block()
    }

    protected fun <T> Flow<T>.collectInScope(collector: suspend (T) -> Unit) {
        launchInScope { collect(collector) }
    }

    private suspend fun MutableStateFlow<Int>.increment() {
        mutex.withLock {
            emit(value + 1)
        }
    }

    protected fun emitError(result: Result.Failure) {
        launchInScope {
            _error.emit(result.error)
        }
    }

    private suspend fun MutableStateFlow<Int>.decrement() {
        mutex.withLock {
            emit(value - 1)
        }
    }
}