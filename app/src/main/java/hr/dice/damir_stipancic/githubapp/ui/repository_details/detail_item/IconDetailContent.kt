package hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.ui.theme.DarkGrey

/**
 * A composable representing an item that contains text with an icon.
 *
 * @param [modifier] a [Modifier] for this component
 * @param [detail] a [Detail.IconDetail] object that contains information to be displayed
 */
@Composable
fun IconDetailContent(
    modifier: Modifier = Modifier,
    detail: Detail.IconDetail
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = detail.title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGrey
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = detail.iconId),
                contentDescription = null,
                tint = DarkGrey
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = when (detail) {
                    is Detail.IconDetail.IconDetailString ->
                        detail.value

                    is Detail.IconDetail.IconDetailStringRes ->
                        stringResource(id = detail.value)
                },
                color = DarkGrey
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewIconDetailContent() {
    IconDetailContent(
        detail = Detail.IconDetail.IconDetailString(
            stringTitle = R.string.stars_title,
            stringIconId = R.drawable.star_border,
            value = "1000"
        )
    )
}
