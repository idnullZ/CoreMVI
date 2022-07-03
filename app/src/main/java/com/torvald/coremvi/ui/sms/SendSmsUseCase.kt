package com.torvald.coremvi.ui.sms

import com.torvald.coremvi.ui.sms.comon.BaseResponse
import com.torvald.coremvi.ui.sms.comon.Error
import com.torvald.coremvi.ui.sms.comon.Result
import kotlinx.coroutines.delay
import javax.inject.Inject

class SendSmsUseCase @Inject constructor() {
    suspend operator fun invoke(sms: String): Result<BaseResponse> {
        delay(2000)
        return if (sms.toInt() % 2 == 0) {
            Result.Success(BaseResponse())
        } else {
            Result.Failure(Error.Unknown("error"))
        }
    }
}