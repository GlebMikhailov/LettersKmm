package letters.game.feautures.splash.data

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import letters.game.core.data.response.BaseResponse
import letters.game.feautures.splash.data.dto.DeviceRequest
import letters.game.feautures.splash.data.dto.DeviceResponse


interface DeviceApi {
    @POST("device")
    suspend fun createDevice(
        @Body deviceRequest: DeviceRequest
    ): BaseResponse<DeviceResponse>
}