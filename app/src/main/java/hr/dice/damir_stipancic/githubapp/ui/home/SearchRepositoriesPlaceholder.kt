package hr.dice.damir_stipancic.githubapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.ui.theme.SearchBackgroundColor
import hr.dice.damir_stipancic.githubapp.ui.theme.SearchHintColor
import hr.dice.damir_stipancic.githubapp.ui.theme.SearchTextColor

/**
 * A filled [TextField] component used in [HomeScreen].
 * This component is disabled and serves as a clickable placeholder.
 *
 * @param [modifier] a [Modifier] for this [TextField]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRepositoriesPlaceholder(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small.copy(
            CornerSize(10.dp)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        TextField(
            modifier = modifier
                .clickable { onClick() }
                .shadow(1.dp),
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = stringResource(id = R.string.search_repositories))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = SearchHintColor
                )
            },
            enabled = false,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SearchBackgroundColor,
                focusedContainerColor = SearchBackgroundColor,
                unfocusedTextColor = SearchTextColor,
                focusedTextColor = SearchTextColor,
                unfocusedPlaceholderColor = SearchHintColor,
                focusedPlaceholderColor = SearchHintColor,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = MaterialTheme.shapes.small.copy(
                CornerSize(10.dp)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchRepositoriesTextField() {
    SearchRepositoriesPlaceholder(onClick = {})
}
