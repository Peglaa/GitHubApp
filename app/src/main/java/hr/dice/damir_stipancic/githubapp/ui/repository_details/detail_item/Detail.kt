package hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Sealed class used to represent different types of items that display different types of details
 * about a repository.
 */
sealed class Detail {
    /**
     * This class represent a detail UI item that contains an image alongside its title and content
     *
     * @param [title] a [StringRes] of the title
     * @param [imageUrl] a [String] containing the url to the image
     * @param [text] a [String] containing the text or information specific to this item
     */
    data class ImageDetail(
        @StringRes val title: Int,
        val imageUrl: String,
        val text: String
    ) : Detail()

    /**
     * This class represent a detail UI item that contains just a title and content
     *
     * @param [title] a [StringRes] of the title
     * @param [text] a [String] containing the text or information specific to this item
     */
    data class TextDetail(
        @StringRes val title: Int,
        val text: String
    ) : Detail()

    /**
     * This class represent a detail UI item that contains an icon alongside its title and content.
     * This is also a sealed class that contains 2 classes that inherit from it. One takes a raw
     * [String] as its content and the other takes a [StringRes] id.
     *
     * @param [title] a [StringRes] of the title
     * @param [iconId] a [DrawableRes] id of the icon
     */
    sealed class IconDetail(
        @StringRes val title: Int,
        @DrawableRes val iconId: Int
    ) : Detail() {
        /**
         * This class takes in a raw [String] as its content.
         *
         * @param [stringTitle] a [StringRes] id for the title
         * @param [stringIconId] a [DrawableRes] id for the icon
         * @param [value] a [String] representation of the content to be shown
         */
        data class IconDetailString(
            @StringRes val stringTitle: Int,
            @DrawableRes val stringIconId: Int,
            val value: String
        ) : IconDetail(
            title = stringTitle,
            iconId = stringIconId
        )

        /**
         * This class takes in a [StringRes] as its content.
         *
         * @param [stringResTitle] a [StringRes] id for the title
         * @param [stringResIconId] a [DrawableRes] id for the icon
         * @param [value] a [StringRes] id of the content to be shown
         */
        data class IconDetailStringRes(
            @StringRes val stringResTitle: Int,
            @DrawableRes val stringResIconId: Int,
            @StringRes val value: Int
        ) : IconDetail(
            title = stringResTitle,
            iconId = stringResIconId
        )
    }
}
