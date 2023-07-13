package letters.game.feautures.game.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class GameId(val value: Long) : Parcelable