package letters.game.figma

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import words.letters.alphabet.game.R

object Colors

@Composable
@ReadOnlyComposable
fun Colors.screen(): Color = colorResource(id = R.color.light_mode_screen)

@Composable
@ReadOnlyComposable
fun Colors.buttonStart(): Color = colorResource(id = R.color.light_mode_button_start)

@Composable
@ReadOnlyComposable
fun Colors.buttonEnd(): Color = colorResource(id = R.color.light_mode_button_end)


@Composable
@ReadOnlyComposable
fun Colors.buttonDisabled(): Color = colorResource(id = R.color.light_mode_button_disabled)

@Composable
@ReadOnlyComposable
fun Colors.textPrimaryDefault(): Color = colorResource(id = R.color.light_mode_text_primary_default)

@Composable
@ReadOnlyComposable
fun Colors.textPrimaryDisabled(): Color =
    colorResource(id = R.color.light_mode_text_primary_disabled)

@Composable
@ReadOnlyComposable
fun Colors.textPrimaryPressed(): Color = colorResource(id = R.color.light_mode_text_primary_pressed)

@Composable
@ReadOnlyComposable
fun Colors.textPrimaryLink(): Color = colorResource(id = R.color.light_mode_text_primary_link)

@Composable
@ReadOnlyComposable
fun Colors.buttonLink(): Color = colorResource(id = R.color.light_mode_button_link)

@Composable
@ReadOnlyComposable
fun Colors.iconPrimary(): Color = colorResource(id = R.color.light_mode_icon_primary)

@Composable
@ReadOnlyComposable
fun Colors.buttonPrimaryPressed(): Color = colorResource(id = R.color.light_mode_button_pressed)

@Composable
@ReadOnlyComposable
fun Colors.buttonPrimaryShadow(): Color = colorResource(id = R.color.light_mode_button_shadow)

@Composable
@ReadOnlyComposable
fun Colors.textPrimaryShadow(): Color = colorResource(id = R.color.light_mode_text_primary_shadow)