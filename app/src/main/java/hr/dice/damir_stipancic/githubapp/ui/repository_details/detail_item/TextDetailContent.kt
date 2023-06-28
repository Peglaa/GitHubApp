package hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.ui.theme.DarkGrey

/**
 * A composable representing an item that contains only text.
 *
 * @param [modifier] a [Modifier] for this component
 * @param [detail] a [Detail.TextDetail] object that contains information to be displayed
 */
@Composable
fun TextDetailContent(
    modifier: Modifier = Modifier,
    detail: Detail.TextDetail
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = detail.title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGrey
        )
        Text(
            text = detail.text,
            color = DarkGrey
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewTextDetailContent() {
    TextDetailContent(
        detail = Detail.TextDetail(
            title = R.string.description_title,
            text = "Repository description"
        )
    )
}
