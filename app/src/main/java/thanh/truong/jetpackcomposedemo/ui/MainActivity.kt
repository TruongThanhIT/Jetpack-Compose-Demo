package thanh.truong.jetpackcomposedemo.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.launch
import thanh.truong.jetpackcomposedemo.R
import thanh.truong.jetpackcomposedemo.ui.model.User
import thanh.truong.jetpackcomposedemo.ui.navigation.DemoNavHost
import thanh.truong.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import thanh.truong.jetpackcomposedemo.ui.theme.pink500

class MainActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

//region Basic
@ExperimentalMaterialApi
@Composable
fun MyApp() {
    val navController = rememberNavController()
    JetpackComposeDemoTheme {
        DemoNavHost(navController = navController, modifier = Modifier.fillMaxWidth())
    }
}

@ExperimentalMaterialApi
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel(), onUserClick: (String?) -> Unit) {
    val users: List<User> by mainViewModel.userListLive.observeAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    "Users",
                    modifier = Modifier.padding(start = 10.dp),
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        },
        content = {
            UsersList(
                modifier = Modifier.fillMaxWidth(),
                users = users,
                itemClickedCallback = { id ->
                    onUserClick.invoke(id)
                })
        }
    )
}
//endregion Basic

//region Recyclerview

@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UsersList(
    modifier: Modifier = Modifier,
    users: List<User>,
    itemClickedCallback: (String?) -> Unit
) {
    // Use LazyRow when making horizontal lists
    Box(modifier) {
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 50.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(users) { user ->
                UserItem(
                    user = user,
                    onItemClicked = itemClickedCallback
                )
            }
        }
        // uses derivedStateOf() to minimize unnecessary compositions.
        val showButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                shape = RoundedCornerShape(50),
                backgroundColor = Color(0xFF43302E),
                modifier = Modifier.padding(all = 16.dp)
            )
            {
                Icon(Icons.Filled.ArrowUpward, "", tint = White)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun UserItem(
    user: User,
    onItemClicked: (String?) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 10.dp,
        onClick = {
            onItemClicked(user.id)
        }
    ) {
        ConstraintLayout {
            val (image, divider, text, icon) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = user.picture,
                    builder = {
                        placeholder(R.drawable.ic_jet_pack)
                        error(R.drawable.ic_jet_pack)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            )
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(divider) {
                        centerVerticallyTo(image)
                        start.linkTo(image.end)
                    }
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            ) {
                Divider(
                    color = Gray,
                    thickness = 30.dp,
                    modifier = Modifier.width(1.dp)
                )
            }
            val title = user.title?.lowercase()?.replaceFirstChar { it.uppercaseChar() }
            Text(
                text = "$title ${user.firstName} ${user.lastName}",
                color = pink500,
                modifier = Modifier
                    .constrainAs(text) {
                        centerVerticallyTo(parent)
                        start.linkTo(divider.end)
                        end.linkTo(icon.start)
                        width = Dimension.fillToConstraints
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 13.sp,
                textAlign = TextAlign.Left
            )
            Icon(
                Icons.Rounded.ChevronRight,
                contentDescription = null,
                modifier = Modifier.constrainAs(icon) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end, margin = 8.dp)
                })
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun MyListPreview() {
    JetpackComposeDemoTheme {
        UserItem(user = User(firstName = "In the ConstraintLayout example, constraints are specified inline, with a modifier in the composable they're applied")) {

        }
    }
}
//endregion Recyclerview
