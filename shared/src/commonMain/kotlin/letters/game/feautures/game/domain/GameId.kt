package letters.game.feautures.game.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import letters.game.feautures.splash.domain.Device

@Parcelize
data class GameId(val value: Long) : Parcelable

@Parcelize
data class Game(
    val id: GameId,
    val word: Word,
    val attempts: List<Attempt>,
    val field: Field
) : Parcelable

@Parcelize
data class Field(
    val width: Int,
    val height: Int
) : Parcelable

@Parcelize
data class AttemptId(
    val value: Long
) : Parcelable

@Parcelize
data class Attempt(
    val id: AttemptId,
    val word: Word,
    val isSuccess: Boolean,
    val index: Int
) : Parcelable

@Parcelize
data class WordId(val value: Long) : Parcelable

@Parcelize
data class Word(
    val id: WordId,
    val name: String,
    val language: Language
) : Parcelable

@Parcelize
data class LanguageId(
    val value: Int
) : Parcelable

@Parcelize
data class Language(
    val id: LanguageId,
    val name: String,
    val nativeName: String,
    val isoCode2: String,
    val isoCode3: String,
    val letters: List<String>
) : Parcelable