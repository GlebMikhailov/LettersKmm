package letters.game.core.theme.custom

import androidx.compose.material.Colors
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import letters.game.core.theme.custom.CustomColors
import letters.game.core.theme.custom.CustomTypography

@Composable
fun CustomColors.toMaterialColors(): Colors {
    return Colors(
        isLight = isLight,
        primary = button.primary.default.start(),
        primaryVariant =  button.primary.default.start(),
        secondary = icon.primary(),
        secondaryVariant = icon.primary(),
        background = background.screen(),
        surface = background.screen(),
        error = text.primary.default.start(),
        onPrimary = text.primary.default.start(),
        onSecondary = text.primary.default.start(),
        onBackground = text.primary.default.start(),
        onSurface = text.primary.default.start(),
        onError = text.primary.default.start()
    )
}

fun CustomTypography.toMaterialTypography(): Typography {
    return Typography(
        h1 = title.boldLarge,
        h2 = title.boldNormal,
        h3 = title.boldSmall,
        h4 = title.mediumSmall,
        h5 = title.regularLarge,
        subtitle1 = body.regularNormal,
        subtitle2 = body.regularSmall,
        button = button.mediumNormal,
        caption = caption.regularLarge,
        overline = caption.regularNormal
    )
}
