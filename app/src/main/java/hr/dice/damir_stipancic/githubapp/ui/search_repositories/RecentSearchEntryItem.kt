package hr.dice.damir_stipancic.githubapp.ui.search_repositories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.ui.theme.GitHubAppTheme
import hr.dice.damir_stipancic.githubapp.ui.theme.SearchHintColor

/**
 * An item representing a saved search entry.
 *
 * @param [modifier] a [Modifier] for this component
 * @param [name] a [String] argument representing the exact text that was used as a search parameter
 */
@Composable
fun RecentSearchEntryItem(
    modifier: Modifier = Modifier,
    name: String,
    onItemClicked: (String) -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(name) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(start = 16.dp),
            painter = painterResource(id = R.drawable.access_time),
            contentDescription = null,
            tint = SearchHintColor
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = name,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRecentSearchEntryItem() {
    GitHubAppTheme {
        RecentSearchEntryItem(
            name = "Preview",
            onItemClicked = {}
        )
    }
}
