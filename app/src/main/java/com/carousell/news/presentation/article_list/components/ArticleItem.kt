package com.carousell.news.presentation.article_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.carousell.news.domain.models.NewsArticle
import com.carousell.news.presentation.ui.theme.GrayText

@Composable
fun ArticleItem(
    article: NewsArticle,
    formattedTime: (Long) -> String
) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Column {
            AsyncImage(
                model = article.bannerUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.FillWidth
            )

            Text(
                article.title,
                modifier = Modifier.padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 4.dp
                ),
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                article.description,
                style = MaterialTheme.typography.body2,
                maxLines = 2,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                ),
                overflow = TextOverflow.Ellipsis
            )

            Text(
                formattedTime(article.timeCreated),
                style = MaterialTheme.typography.subtitle1.copy(color = GrayText),
                maxLines = 2,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 8.dp,
                    bottom = 12.dp
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        NewsArticle(
            id = "",
            title = "This is the title",
            description = "Foo",
            bannerUrl = "",
            timeCreated = 1,
            rank = 1
        )
    ) { "" }
}