package com.gmartinsdev.base_arch_template.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.gmartinsdev.base_arch_template.data.model.User
import com.gmartinsdev.base_arch_template.ui.components.ErrorMessageScreen
import com.gmartinsdev.base_arch_template.ui.components.SearchViewComponent
import com.gmartinsdev.base_arch_template.ui.theme.MainTheme

/**
 *  composable class responsible for displaying a list of users
 */
@Composable
fun UsersHomeScreen() {
    val context = LocalContext.current
    val vm: UserViewModel = viewModel()
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = { }
    ) { innerPadding ->
        UsersHome(
            modifier = Modifier.padding(innerPadding),
            state = state,
            onClick = {
                Toast.makeText(context, "${it.name} clicked", Toast.LENGTH_LONG).show()
            },
            onSearch = {
                vm.fetchData(it)
            },
            onRefresh = {
                vm.fetchData("")
            }
        )
    }
}

@Composable
fun UsersHome(
    modifier: Modifier = Modifier,
    state: UiState,
    onClick: (User) -> Unit,
    onSearch: (String) -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchViewComponent(
            modifier = modifier,
            onSearch = { onSearch.invoke(it) }
        )
        when (state) {
            is UiState.Loading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Loaded -> {
                if (state.data.isNotEmpty()) {
                    LazyVerticalGrid(
                        modifier = modifier
                            .widthIn(0.dp, 800.dp)
                            .padding(
                                top = 10.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 10.dp
                            ),
                        columns = GridCells.Adaptive(120.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)

                    ) {
                        items(state.data) { user ->
                            UserItemScreen(
                                userItem = user,
                                onClick = { onClick.invoke(user) }
                            )
                        }
                    }
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorMessageScreen(
                        message = state.errorMessage,
                        buttonEnabled = true
                    ) {
                        onRefresh.invoke()
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@PreviewScreenSizes
@PreviewLightDark
@Composable
fun UsersHomePreview() {
    MainTheme {
        UsersHome(
            state = UiState.Loaded(
                listOf(
                    User(
                        1,
                        "aaa",
                        "aaa1",
                        "aaa@aaa.com"
                    ),
                    User(
                        2,
                        "aaa",
                        "aaa2",
                        "aaa@aaa.com"
                    ),
                    User(
                        3,
                        "aaa",
                        "aaa3",
                        "aaa@aaa.com"
                    ),
                    User(
                        4,
                        "aaa",
                        "aaa4",
                        "aaa@aaa.com"
                    ),
                )
            ),
            onSearch = {},
            onRefresh = {},
            onClick = {}
        )
    }
}