package hr.dice.damir_stipancic.githubapp.ui.home

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import hr.dice.damir_stipancic.githubapp.R

/**
 * [AlertDialog] component used in [HomeScreen].
 *
 * @param [modifier] a [Modifier] for this component
 * @param [onDismiss] a callback when the user clicks the dismiss button
 * @param [onClearConfirmClicked] a callback when the user clicks the confirmation button
 */
@Composable
fun SearchEntriesAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onClearConfirmClicked: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.search_entries_alert_title),
                fontWeight = FontWeight.Bold
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    onClearConfirmClicked()
                }
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        containerColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchEntriesAlertDialog() {
    SearchEntriesAlertDialog(
        onDismiss = {},
        onClearConfirmClicked = {}
    )
}
