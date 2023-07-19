package letters.game.core.theme

import letters.game.core.theme.custom.BackgroundColors
import letters.game.core.theme.custom.ButtonColors
import letters.game.core.theme.custom.CustomColors
import letters.game.core.theme.custom.GradientColor
import letters.game.core.theme.custom.IconColor
import letters.game.core.theme.custom.PrimaryColor
import letters.game.core.theme.custom.TextColors
import letters.game.figma.Colors
import letters.game.figma.*

val LightAppColors = CustomColors(
    isLight = true,

    background = BackgroundColors(
        screen = { Colors.screen() },
        invalid = { Colors.invalidLetter() },
        screenSunset = GradientColor(
            start = { Colors.screenStart() },
            end = { Colors.screenEnd() }
        ),
        letterBackground = { Colors.letterBackground() }
    ),

    button = ButtonColors(
        primary = PrimaryColor(
            default = GradientColor(
                start = { Colors.buttonStart() },
                end = { Colors.buttonEnd() }
            ),
            pressed = GradientColor(
                start = { Colors.buttonPrimaryPressed() },
                end = { Colors.buttonPrimaryPressed() }
            ),
            disabled = { Colors.buttonDisabled() },
            link = { Colors.buttonLink() },
            shadowColor = { Colors.buttonPrimaryShadow() },
            error = GradientColor(
                start = { Colors.errorStart() },
                end = { Colors.errorEnd() }
            ),
            success = { Colors.success() }
        ),
        purple = GradientColor(
            start = { Colors.purpleStart() },
            end = { Colors.purpleEnd() }
        )
    ),

    text = TextColors(
        primary = PrimaryColor(
            default = GradientColor(
                start = { Colors.textPrimaryDefault() },
                end = { Colors.textPrimaryDefault() }
            ),
            pressed = GradientColor(
                start = { Colors.textPrimaryPressed() },
                end = { Colors.textPrimaryPressed() }
            ),
            disabled = { Colors.textPrimaryDisabled() },
            link = { Colors.textPrimaryLink() },
            shadowColor = { Colors.textPrimaryShadow() },
            error = GradientColor(
                start = { Colors.errorStart() },
                end = { Colors.errorEnd() }
            ),
            success = { Colors.success() },
        ),
        onError = { Colors.screen() },
        onSuccess = { Colors.screen() },
        backgroundInvert = { Colors.screen() }
    ),

    icon = IconColor(
        primary = { Colors.iconPrimary() }
    )
)

val DarkAppColors = LightAppColors // not implemented
