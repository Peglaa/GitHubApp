package hr.dice.damir_stipancic.githubapp.ui.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.ui.theme.DarkGrey

/**
 * A composable that is shown if there are any errors when fetching results. It shows a general
 * error message or a specific message when there is no internet connection and it contains a
 * [Button] so the user can retry the search
 *
 * @param [modifier] a [Modifier] for this component
 * @param [isInternetException] a [Boolean] to help differentiate if the error is an internet
 * connection error or not
 * @param [onTryAgainClicked] a callback that is invoked when the user clicks the try again button
 */
@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    isInternetException: Boolean,
    onTryAgainClicked: () -> Unit
) {
    val errorText = stringResource(
        if (isInternetException) {
            R.string.no_internet_connection
        } else {
            R.string.error
        }
    )

    val errorIcon = painterResource(
        if (isInternetException) {
            R.drawable.wifi_off
        } else {
            R.drawable.error_outlined
        }
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(0.6f),
            painter = errorIcon,
            contentDescription = null,
            tint = DarkGrey
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.unexpected_error),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = errorText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkGrey
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = onTryAgainClicked
        ) {
            Text(
                text = stringResource(id = R.string.try_again).uppercase()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorContentNoInternet() {
    ErrorContent(
        isInternetException = true,
        onTryAgainClicked = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorContent() {
    ErrorContent(
        isInternetException = false,
        onTryAgainClicked = { }
    )
}
