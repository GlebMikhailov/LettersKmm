package letters.game.core.utils

import dev.icerock.moko.resources.desc.Raw
import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.kmm_form_validation.validation.control.ValidationResult

fun String.checkFieldModification(value: String): ValidationResult {
    return if (isNotEmpty()) {
        if (value.isNotEmpty()) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(StringDesc.Raw(""))
        }
    } else {
        ValidationResult.Valid
    }
}
