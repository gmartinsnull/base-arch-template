package com.gmartinsdev.base_arch_template.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.gmartinsdev.base_arch_template.R
import com.gmartinsdev.base_arch_template.data.model.User
import com.gmartinsdev.base_arch_template.ui.theme.MainTheme

/**
 *  composable class responsible for displaying a user item
 */
@Composable
fun UserItemScreen(
    userItem: User,
    onClick: (User) -> Unit
) {
    UserItem(userItem) {
        onClick.invoke(userItem)
    }
}

@Composable
fun UserItem(
    item: User,
    onClick: (User) -> Unit
) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(250.dp)
            .clip(shape = RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(
                model = R.drawable.image_not_available
//                error = painterResource(id = 0),
//                placeholder = painterResource(id = 0)
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(top = 14.dp, start = 10.dp)
        ) {
            Text(
                modifier = Modifier.width(200.dp),
                textAlign = TextAlign.Start,
                color = Color.White,
                text = item.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = item.username,
                color = Color.White,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Button(
            modifier = Modifier
                .width(120.dp)
                .padding(bottom = 5.dp)
                .align(Alignment.BottomCenter),
            onClick = { onClick.invoke(item) }
        ) {
            Text(
                color = Color.White,
                text = "button"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    MainTheme {
        UserItem(
            item = User(
                1,
                "aaa",
                "aaa1",
                "aaa@aaa.com"
            )
        ) {

        }
    }
}