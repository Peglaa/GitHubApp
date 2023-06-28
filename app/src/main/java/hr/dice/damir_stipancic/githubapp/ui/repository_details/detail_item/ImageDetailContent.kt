package hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.ui.theme.DarkGrey

/**
 * A composable representing an item that contains text with an image.
 *
 * @param [modifier] a [Modifier] for this component
 * @param [detail] a [Detail.ImageDetail] object that contains information to be displayed
 */
@Composable
fun ImageDetailContent(
    modifier: Modifier = Modifier,
    detail: Detail.ImageDetail
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = detail.title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGrey
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(1.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                AsyncImage(
                    modifier = Modifier.size(64.dp),
                    model = detail.imageUrl,
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = stringResource(id = R.string.repository_owner_avatar)
                )
            }
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = detail.text,
                color = DarkGrey
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewImageDetailContent() {
    ImageDetailContent(
        detail = Detail.ImageDetail(
            title = R.string.owner_title,
            imageUrl = "",
            text = "Repository Owner"
        )
    )
}
