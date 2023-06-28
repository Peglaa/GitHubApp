package hr.dice.damir_stipancic.githubapp.ui.search_repositories

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.ui.theme.SearchBackgroundColor
import hr.dice.damir_stipancic.githubapp.ui.theme.SearchHintColor
import hr.dice.damir_stipancic.githubapp.ui.theme.SearchTextColor

/**
 * A [TextField] with input validation, ideally used as a top app bar search field.
 *
 * @param [modifier] a [Modifier] for this component.
 * @param [value] a [String] representing the current or initial text.
 * @param [onValueChange] a callback that triggers whenever a new input is detected.
 * Passes a [String] representing the new input value.
 * @param [onSearchClicked] a callback that triggers when the user clicks the search keyboard button.
 * Passes a [String] that contains the search term.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppSearchBar(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    isError: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    onSearchClicked: (String) -> Unit,
    onBackArrowClicked: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(text = stringResource(id = R.string.search_repositories)) },
        leadingIcon = {
            IconButton(
                onClick = onBackArrowClicked
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = SearchHintColor
                )
            }
        },
        trailingIcon = {
            if (value.text.isNotEmpty()) {
                IconButton(onClick = {
                    onValueChange(TextFieldValue(""))
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            }
        },
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        id = R.string.input_error_message,
                        value.text.trim().length,
                        2
                    )
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearchClicked(value.text) }
        ),
        isError = isError,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = SearchBackgroundColor,
            focusedContainerColor = SearchBackgroundColor,
            unfocusedTextColor = SearchTextColor,
            focusedTextColor = SearchTextColor,
            unfocusedPlaceholderColor = SearchHintColor,
            focusedPlaceholderColor = SearchHintColor,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = SearchTextColor,
            focusedIndicatorColor = Color.Transparent,
            errorCursorColor = Color.Red,
            errorIndicatorColor = Color.Red,
            errorSupportingTextColor = Color.Red,
            errorTrailingIconColor = Color.Red
        )
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
