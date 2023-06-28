package hr.dice.damir_stipancic.githubapp.ui.repository_owner

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.dice.damir_stipancic.githubapp.ui.theme.DarkGrey
import hr.dice.damir_stipancic.githubapp.ui.theme.VeryLightGray

/**
 * An entire screen component that display [RepositoryOwnerContent]
 *
 * @param [ownerName] a [String] representation of the repository owners name
 * @param [destinationUrl] a [String] representation of a url to be shown in the web view
 */
@Destination
@Composable
fun RepositoryOwnerScreen(
    navigator: DestinationsNavigator,
    ownerName: String,
    destinationUrl: String
) {
    RepositoryOwnerContent(
        ownerName = ownerName,
        destinationUrl = destinationUrl,
        onNavigationIconClicked = { navigator.navigateUp() }
    )
}

/**
 * Content composable for [RepositoryOwnerScreen]. It contains a [TopAppBar] with a back nav
 * arrow and a [WebView] that fills the entire screen.
 *
 * @param [ownerName] a [String] representation of the repository owners name
 * @param [destinationUrl] a [String] representation of a url to be shown in the web view
 * @param [onNavigationIconClicked] a callback that gets invoked when the user clicks the back arrow
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryOwnerContent(
    ownerName: String,
    destinationUrl: String,
    modifier: Modifier = Modifier,
    onNavigationIconClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = ownerName)
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigationIconClicked
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = DarkGrey
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VeryLightGray
                )
            )
        }
    ) { paddingValues ->
        AndroidView(
            modifier = modifier.padding(paddingValues),
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(destinationUrl)
                }
            },
            update = {
                it.loadUrl(destinationUrl)
            }
        )
    }
}

@Composable
@Preview
private fun PreviewRepositoryOwnerContent() {
    RepositoryOwnerContent(
        ownerName = "Repository owner",
        destinationUrl = "",
        onNavigationIconClicked = { }
    )
}
