package letters.game.core.message.domain

import dev.icerock.moko.resources.desc.StringDesc

data class Message(
    val text: StringDesc,
    val actionTitle: StringDesc? = null,
    val action: (() -> Unit)? = null
)