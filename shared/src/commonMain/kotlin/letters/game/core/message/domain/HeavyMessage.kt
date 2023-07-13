package letters.game.core.message.domain

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import letters.game.MR

class HeavyMessage(
    val title: StringDesc,
    val description: StringDesc,
    val actionTitle: StringDesc,
    val action: (() -> Unit)? = null
) {
    companion object {
        val MOCK = HeavyMessage(
            title = "Title".desc(),
            description = "Description".desc(),
            actionTitle = "close".desc()
        )
    }
}