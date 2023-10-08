package letters.game.core.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import letters.game.core.theme.custom.CustomTheme
import letters.game.core.utils.LoadableState

/**
 * Displays Replica state ([LoadableState]) with swipe-to-refresh functionality.
 *
 * Note: a value of refreshing in [content] is true only when data is refreshing and swipe gesture didn't occur.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Any> SwipeRefreshLceWidget(
    state: LoadableState<T>,
    onRefresh: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    pullRefreshState: @Composable BoxScope.(state: PullRefreshState) -> Unit = { s ->
        PullRefreshIndicator(
            state = s,
            refreshing = state.loading,
            contentColor = CustomTheme.colors.text.onSuccess(),
            modifier = Modifier.align(Alignment.TopCenter)
        )
    },
    content: @Composable (data: T, refreshing: Boolean) -> Unit
) {
    LceWidget(
        state = state,
        onRetryClick = onRetryClick,
        modifier = modifier
    ) { data, refreshing ->
        var swipeGestureOccurred by remember { mutableStateOf(false) }

        LaunchedEffect(refreshing) {
            if (!refreshing) swipeGestureOccurred = false
        }

        val swipeRefreshState =
            rememberPullRefreshState(swipeGestureOccurred && refreshing, onRefresh = {
                swipeGestureOccurred = true
                onRefresh()
            })

        Box(contentModifier.pullRefresh(swipeRefreshState)) {
            content(data, refreshing && !swipeGestureOccurred)
            pullRefreshState(swipeRefreshState)
        }
    }
}