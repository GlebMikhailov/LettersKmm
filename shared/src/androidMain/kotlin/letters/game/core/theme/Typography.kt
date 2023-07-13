package letters.game.core.theme

import letters.game.core.theme.custom.BodyTypography
import letters.game.core.theme.custom.ButtonTypography
import letters.game.core.theme.custom.CaptionTypography
import letters.game.core.theme.custom.CustomTypography
import letters.game.core.theme.custom.TitleTypography
import letters.game.figma.Typography

val AppTypography = CustomTypography(
    title = TitleTypography(
        boldLarge = Typography.titleBoldLarge,
        boldNormal = Typography.titleBoldNormal,
        boldSmall = Typography.titleBoldSmall,
        mediumSmall = Typography.titleMediumSmall,
        regularLarge = Typography.titleRegularLarge,
        boldVeryLarge = Typography.titleBoldVeryLarge
    ),
    body = BodyTypography(
        regularNormal = Typography.bodyRegularNormal,
        boldNormal = Typography.bodyBoldNormal,
        boldSmall = Typography.bodyBoldSmall,
        regularSmall = Typography.bodyRegularSmall,
    ),
    caption = CaptionTypography(
        regularLarge = Typography.captionRegularLarge,
        regularNormal = Typography.captionRegularNormal,
        boldSmall = Typography.captionBoldSmall,
        boldExtra = Typography.captionBoldExtra,
        regularSmall = Typography.captionRegularSmall,
        regularUltraSmall = Typography.captionRegularUltraSmall,
        crossPriceLarge = Typography.captionCrossPriceLarge,
        mediumLarge = Typography.captionMediumLarge,
    ),
    button = ButtonTypography(
        mediumNormal = Typography.buttonMediumNormal,
        mediumSmall = Typography.buttonMediumSmall,
        boldCaps = Typography.buttonBoldcaps,
    )
)
