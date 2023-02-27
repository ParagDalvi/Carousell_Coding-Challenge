package com.carousell.news.presentation.article_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carousell.news.R
import com.carousell.news.presentation.article_list.components.ArticleItem
import com.carousell.news.presentation.ui.theme.BackgroundColor
import com.carousell.news.util.UiState

@Composable
fun ArticleListScreen(
    viewModel: ArticleListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    IconButton(
                        onClick = { showMenu = !showMenu }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                viewModel.sortList(false)
                                showMenu = false
                            }
                        ) {
                            Text(stringResource(R.string.recent))
                        }
                        DropdownMenuItem(
                            onClick = {
                                viewModel.sortList(true)
                                showMenu = false
                            }
                        ) {
                            Text(stringResource(R.string.popular))
                        }
                    }
                },
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        modifier = Modifier.padding(start = 36.dp),
                        style = TextStyle(color = Color.White, fontSize = 16.sp)
                    )
                }
            )
        },
        content = { _ ->
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                ) {
                    if (state?.data != null)
                        items(state.data) {
                            ArticleItem(
                                article = it,
                                formattedTime = viewModel::getFormattedDate
                            )
                        }
                }
                if (state is UiState.Error) {
                    Text(
                        text = state.message ?: "Something went wrong",
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                if (state is UiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        },
        backgroundColor = BackgroundColor
    )
}