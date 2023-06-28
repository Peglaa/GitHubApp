package hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A composable that represent a single detail item to be shown in a column. It can show one of 3
 * items: item with an image([ImageDetailContent]), an icon([IconDetailContent]) or an item with only text
 * ([TextDetailContent]).
 *
 * @param [modifier] a [Modifier] for this component
 * @param [detail] a [Detail] object that holds data for this specific item
 */
@Composable
fun RepositoryDetailsItem(
    modifier: Modifier = Modifier,
    detail: Detail
) {
    when (detail) {
        is Detail.ImageDetail -> {
            ImageDetailContent(
                modifier = modifier,
                detail = detail
            )
        }
        is Detail.TextDetail -> {
            TextDetailContent(
                modifier = modifier,
                detail = detail
            )
        }
        else -> {
            IconDetailContent(
                modifier = modifier,
                detail = detail as Detail.IconDetail
            )
        }
    }
}
