package hr.dice.damir_stipancic.githubapp.ui.repository_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.dice.damir_stipancic.githubapp.R
import hr.dice.damir_stipancic.githubapp.data.remote.repository_models.GithubRepository
import hr.dice.damir_stipancic.githubapp.ui.destinations.RepositoryOwnerScreenDestination
import hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item.Detail
import hr.dice.damir_stipancic.githubapp.ui.repository_details.detail_item.RepositoryDetailsItem
import hr.dice.damir_stipancic.githubapp.ui.theme.DarkGrey
import hr.dice.damir_stipancic.githubapp.ui.theme.VeryLightGray
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * An entire screen component that displays [RepositoryDetailsContent].
 *
 * @param [githubRepository] a [GithubRepository] object containing data to be displayed
 */
@Destination
@Composable
fun RepositoryDetailsScreen(
    navigator: DestinationsNavigator,
    githubRepository: GithubRepository
) {
    val repositoryDetailsViewModel =
        koinViewModel<RepositoryDetailsViewModel> { parametersOf(githubRepository) }
    val repositoryDetailsUiState =
        repositoryDetailsViewModel.repositoryDetailsUiState.collectAsStateWithLifecycle()
    RepositoryDetailsContent(
        repositoryDetailsUiState = repositoryDetailsUiState.value,
        onNavigationIconClicked = { navigator.navigateUp() },
        onOwnerButtonClicked = { destinationUrl ->
            navigator.navigate(
                RepositoryOwnerScreenDestination(
                    destinationUrl = destinationUrl,
                    ownerName = repositoryDetailsUiState.value.owner
                )
            )
        }
    )
}

/**
 * Content composable for [RepositoryDetailsScreen]. Contains a [Scaffold] with a top bar that
 * displays the name of the repository as its title and a back navigation arrow, and it contains
 * details about the repository like its owner, description, number of open issues etc.
 *
 * @param [repositoryDetailsUiState] a [RepositoryDetailsUiState] object containing the current
 * state of the screen
 * @param [onNavigationIconClicked] a callback that gets invoked when the user clicks the back
 * navigation icon in the toolbar
 * @param [onOwnerButtonClicked] a callback that gets invoked when the user clicks one of the
 * buttons to open repository owner details(profile or repositories)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryDetailsContent(
    repositoryDetailsUiState: RepositoryDetailsUiState,
    onNavigationIconClicked: () -> Unit,
    onOwnerButtonClicked: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = repositoryDetailsUiState.title,
                        color = DarkGrey,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigationIconClicked
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.nav_back_icon_content_desc
                            ),
                            tint = DarkGrey
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VeryLightGray
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    items = repositoryDetailsUiState.details
                ) {
                    RepositoryDetailsItem(detail = it)
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGrey
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onOwnerButtonClicked(
                            repositoryDetailsUiState.ownerRepositoriesUrl
                        )
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.open_owner_repositories).uppercase()
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGrey
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onOwnerButtonClicked(
                            repositoryDetailsUiState.profileUrl
                        )
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.open_owner_profile).uppercase()
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewRepositoryDetailsContent() {
    val details = listOf(
        Detail.ImageDetail(
            title = R.string.owner_title,
            imageUrl = "",
            text = "Repository owner"
        ),
        Detail.TextDetail(
            title = R.string.description_title,
            text = "Repository description"
        ),
        Detail.IconDetail.IconDetailString(
            stringTitle = R.string.opened_issues_title,
            stringIconId = R.drawable.issues,
            value = "1000"
        ),
        Detail.IconDetail.IconDetailString(
            stringTitle = R.string.watchers_title,
            stringIconId = R.drawable.watchers,
            value = "500"
        ),
        Detail.IconDetail.IconDetailString(
            stringTitle = R.string.stars_title,
            stringIconId = R.drawable.star_border,
            value = "555"
        ),
        Detail.IconDetail.IconDetailStringRes(
            stringResTitle = R.string.private_title,
            stringResIconId = R.drawable.lock_open,
            value = R.string.private_no
        )
    )
    RepositoryDetailsContent(
        repositoryDetailsUiState = RepositoryDetailsUiState(
            title = "Repository",
            owner = "Repository owner",
            profileUrl = "",
            ownerRepositoriesUrl = "",
            isPrivate = false,
            details = details
        ),
        onNavigationIconClicked = { },
        onOwnerButtonClicked = { }
    )
}
