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
        screen = { Colors.screen() }
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
            link = { Colors.buttonLink() },
            shadowColor = { Colors.buttonPrimaryShadow() },
            disabled = { Colors.buttonDisabled() }
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
            link = { Colors.textPrimaryLink() },
            shadowColor = { Colors.textPrimaryShadow() },
            disabled = { Colors.textPrimaryDisabled() }
        )
    ),

    icon = IconColor(
        primary = { Colors.iconPrimary() }
    )
)

val DarkAppColors = LightAppColors // not implemented
