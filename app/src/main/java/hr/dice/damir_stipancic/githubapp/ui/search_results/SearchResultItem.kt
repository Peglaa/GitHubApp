package hr.dice.damir_stipancic.githubapp.ui.search_results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.dice.damir_stipancic.githubapp.R

/**
 * A composable representing a single search result item. It is simply a [Card] that displays
 * the name of the repository, it's owner, a short description and the amount of stars.
 *
 * @param [modifier] a [Modifier] for this composable
 * @param [title] a [String] parameter containing the title of the repository
 * @param [repositoryOwner] a [String] parameter containing the name of the owner
 * @param [description] a [String] parameter containing the repository description
 * @param [starsCount] a [Int] parameter containing the number of stars
 * @param [onItemClicked] a callback that is invoked when the user clicks on the card
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    title: String,
    repositoryOwner: String,
    description: String,
    starsCount: String,
    onItemClicked: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(1.dp),
        onClick = onItemClicked
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "$repositoryOwner/$title"
            )
            Text(
                text = description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                fontSize = 12.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star_border),
                    contentDescription = null,
                    tint = Color.Gray
                )

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = starsCount,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRepositoryItem() {
    SearchResultItem(
        modifier = Modifier,
        title = "repository",
        repositoryOwner = "Repository",
        description = "This repository has a very long description. The reason for this is so " +
            "the preview can showcase what happens to the text if its too long. That's it.",
        starsCount = "39K",
        onItemClicked = { }
    )
}
