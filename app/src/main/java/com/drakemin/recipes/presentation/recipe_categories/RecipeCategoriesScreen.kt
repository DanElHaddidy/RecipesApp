package com.drakemin.recipes.presentation.recipe_categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drakemin.recipes.R
import com.drakemin.recipes.presentation.destinations.RecipesScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
@RootNavGraph(start = true)
fun CuisineCategoriesScreen(
    navigator: DestinationsNavigator
) {
    val categories = stringArrayResource(id = R.array.recipe_categories)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row()
        {

            Text(
                text = stringResource(id = R.string.recipe_categories),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp, vertical = 20.dp)
            )
        }
        Divider(thickness = 3.dp)
        LazyColumn()
        {

            items(categories.size) { i ->
                val category = categories[i]

                Text(
                    text = category,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable {
                            navigator.navigate(RecipesScreenDestination(category))

                        }
                        .padding(vertical = 16.dp, horizontal = 10.dp)
                )

                if (i < categories.size) {
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }


        }
    }

}