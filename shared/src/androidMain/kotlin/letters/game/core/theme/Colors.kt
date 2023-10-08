package letters.game.core.theme

import letters.game.core.theme.custom.BackgroundColors
import letters.game.core.theme.custom.ButtonColors
import letters.game.core.theme.custom.CustomColors
import letters.game.core.theme.custom.GradientColor
import letters.game.core.theme.custom.IconColor
import letters.game.core.theme.custom.PrimaryColor
import letters.game.core.theme.custom.TextColors
import letters.game.figma.Colors
import letters.game.figma.buttonDisabled
import letters.game.figma.buttonEnd
import letters.game.figma.buttonLink
import letters.game.figma.buttonPrimaryShadow
import letters.game.figma.buttonSecondaryBackground
import letters.game.figma.buttonSecondaryDisabled
import letters.game.figma.buttonSecondaryShadow
import letters.game.figma.buttonStart
import letters.game.figma.errorEnd
import letters.game.figma.errorStart
import letters.game.figma.iconPrimary
import letters.game.figma.invalidLetter
import letters.game.figma.letterBackground
import letters.game.figma.purpleEnd
import letters.game.figma.purpleStart
import letters.game.figma.screen
import letters.game.figma.screenEnd
import letters.game.figma.screenStart
import letters.game.figma.success
import letters.game.figma.textPrimaryDefault
import letters.game.figma.textPrimaryDisabled
import letters.game.figma.textPrimaryLink
import letters.game.figma.textPrimaryShadow

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
        ),
        secondaryColor = PrimaryColor(
            default = GradientColor(
                start = { Colors.buttonSecondaryBackground() },
                end = { Colors.buttonSecondaryBackground() }
            ),
            disabled = { Colors.buttonSecondaryDisabled() },
            link = { Colors.buttonLink() },
            shadowColor = { Colors.buttonSecondaryShadow() },
            error = GradientColor(
                start = { Colors.errorStart() },
                end = { Colors.errorEnd() }
            ),
            success = { Colors.success() }
        )
    ),

    text = TextColors(
        primary = PrimaryColor(
            default = GradientColor(
                start = { Colors.textPrimaryDefault() },
                end = { Colors.textPrimaryDefault() }
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
