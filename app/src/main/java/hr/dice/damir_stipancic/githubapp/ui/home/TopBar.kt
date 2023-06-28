package hr.dice.damir_stipancic.githubapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import hr.dice.damir_stipancic.githubapp.R

/**
 * [TopAppBar] component used in [HomeScreen]. It is transparent and contains a single menu item
 * presented as 3 vertical dots. Clicking it offers one option, to clear recent search queries.
 *
 * @param [onClearMenuItemClicked] a callback when the user clicks the menu item
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onClearMenuItemClicked: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = "") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            DropdownMenu(
                modifier = Modifier.background(Color.White),
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        showMenu = false
                        onClearMenuItemClicked()
                    },
                    text = { Text(text = stringResource(id = R.string.clear_search_entries)) }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewTopBar() {
    TopBar(
        onClearMenuItemClicked = {}
    )
}
