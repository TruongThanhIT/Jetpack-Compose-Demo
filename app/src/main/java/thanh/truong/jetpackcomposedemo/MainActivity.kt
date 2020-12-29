package thanh.truong.jetpackcomposedemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import thanh.truong.jetpackcomposedemo.ui.JetpackComposeDemoTheme
import thanh.truong.jetpackcomposedemo.ui.shapes
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
//                MyScreenContent()
//                LayoutsCodeLab()
                App()
//                MyComposeList(
//                    modifier = Modifier.fillMaxWidth().background(color = Color.White),
//                    itemViewStates = listOf(
//                        ItemViewState("Thanh Mia"),
//                        ItemViewState("Nam Heo"),
//                        ItemViewState("Nam Beo")
//                    ),
//                    itemClickedCallback = {
//                        Log.d("Thanh", "MyListPreview")
//                    }
//                )
            }
        }
    }
}

//region Basic
@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetpackComposeDemoTheme {
        Surface(color = Color.Green) {
            content()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = Color.Yellow, shape = shapes.large) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.padding(24.dp),
            style = MaterialTheme.typography.body1
            // we have 2 ways to using typographies. The first is using typographies which we defined the second is using MaterialTheme.typography
        )
    }
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(backgroundColor = if (count > 5) Color.Green else Color.White)
    ) {
        Text(text = "I've been clicked $count times")
    }
}

@Composable
fun MyScreenContent(names: List<String> = listOf("Thanh", "Mia there")) {
    val counterState = remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxHeight()) {
        Column(modifier = Modifier.weight(1f)) {
            for (name in names) {
                Greeting(name)
                Divider(color = Color.Black)
            }
        }
        Counter(
            count = counterState.value,
            updateCount = { newCount -> counterState.value = newCount })
    }
}

//@Preview(showBackground = true, name = "Text View")
//@Composable
//fun DefaultPreview() {
//    MyApp {
//        MyScreenContent()
//    }
//}
//endregion Basic

//region Layouts
@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = { /*Ignoring onclick */ })
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.preferredSize(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }
        Column(
            modifier = Modifier.padding(start = 8.dp).align(Alignment.CenterVertically)
        ) {
            Text("Thanh Mia", fontWeight = FontWeight.Bold)
            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

//@Preview
//@Composable
//fun PhotographerCardPreview() {
//    JetpackComposeDemoTheme {
//        PhotographerCard()
//    }
//}
//endregion Layouts

//region Material Components
@Composable
fun LayoutsCodeLab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { /*do some thing*/ }) {
                        Icon(Icons.Filled.Favorite)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    ScrollableRow(
        modifier = modifier
//            .background(color = Color.LightGray, shape = RectangleShape)
//            .padding(16.dp)
//            .size(200.dp)
    ) {
        StaggeredGrid {
            for (topic in topics) {
                Chip(text = topic, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

//@Preview
//@Composable
//fun LayoutsCodeLabPreview() {
//    JetpackComposeDemoTheme {
//        LayoutsCodeLab()
//    }
//}
//endregion Material Components

//region Custom Layouts
fun Modifier.firstBaseLineToTop(
    firstBaseLineToTop: Dp
) = Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseLine = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaseLineToTop.toIntPx() - firstBaseLine
    val height = placeable.height + placeableY
    layout(width = placeable.width, height = height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}

//@Preview
//@Composable
//fun TextWithPaddingToBaselinePreview() {
//    JetpackComposeDemoTheme {
//        Text(text = "Hi there!", modifier = Modifier.firstBaseLineToTop(32.dp))
//    }
//}
//
//@Preview
//@Composable
//fun TextWithNormalPaddingPreview() {
//    JetpackComposeDemoTheme {
//        Text(text = "Hi there!", modifier = Modifier.padding(top = 32.dp))
//    }
//}

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    children: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = children
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }

        // Track the y co-ord we have placed children up to
        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun MyOwnColumnOld(
    modifier: Modifier = Modifier,
    children: @Composable () -> Unit
) {
    MultiMeasureLayout(
        modifier = modifier,
        children = children
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }

        // Track the y co-ord we have placed children up to
        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}
//endregion Custom Layouts

//region Complex Custom Layouts
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    children: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = children
    ) { measurables, constraints ->
        // Keep track of the width of each row
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of the max height of each row
        val rowMaxHeights = IntArray(rows) { 0 }

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.mapIndexed { index, measurable ->
            // Measure each child
            val placeable = measurable.measure(constraints)
            // Track the width and max height of each row
            val row = index.rem(rows)
            rowWidths[row] = rowWidths[row] + placeable.width.absoluteValue
            rowMaxHeights[row] = kotlin.math.max(rowMaxHeights[row], placeable.height.absoluteValue)
            placeable
        }
        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()?.coerceIn(
            constraints.minWidth.rangeTo(constraints.maxWidth)
        ) ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowMaxHeights.sumBy { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowMaxHeights[i - 1]
        }

        // Set the size of the parent layout
        layout(width = width, height = height) {
            // x cord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index.rem(rows)
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.preferredSize(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.preferredWidth(4.dp))
            Text(text = text)
        }
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

//@Preview
//@Composable
//fun ChipPreview() {
//    JetpackComposeDemoTheme {
//        Chip(text = "Hi there")
//    }
//}
//endregion Complex Custom Layouts

//region Layout modifiers under the hood
// can set background for scrollable
//            .background(color = Color.LightGray, shape = RectangleShape)
//            .padding(16.dp)
//            .size(200.dp)
//endregion Layout modifiers under the hood

//region Constraint Layout
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (button1, button2, text) = createRefs()
        Button(
            onClick = { /*do something*/ },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button 1")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text(text = "Text", modifier = Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            // Centers Text horizontally in the ConstraintLayout
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)
        Button(onClick = { /*do something*/ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(barrier)
            }) {
            Text(text = "Button 2")
        }
    }
}

//@Preview
//@Composable
//fun ConstraintLayoutContentPreview() {
//    JetpackComposeDemoTheme {
//        ConstraintLayoutContent()
//    }
//}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            text = "Hi there text",
            modifier = Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent.atLeast(100.dp)
            }
        )
    }
}

//@Preview
//@Composable
//fun LargeConstraintLayoutPreview() {
//    JetpackComposeDemoTheme {
//        LargeConstraintLayout()
//    }
//}

@Composable
fun DecoupledConstraintLayout() {
    WithConstraints {
        val constraint =
            if (maxWidth < maxHeight) decoupledConstraints(margin = 16.dp) // Portrait constraints
            else decoupledConstraints(margin = 32.dp) // Landscape constraints

        ConstraintLayout(constraint) {
            Button(
                onClick = { /*do something*/ },
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "Button")
            }

            Text(text = "Text", modifier = Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp) = ConstraintSet {
    val button = createRefFor("button")
    val text = createRefFor("text")
    constrain(button) {
        top.linkTo(parent.top, margin = margin)
    }
    constrain(text) {
        top.linkTo(button.bottom, margin = margin)
    }
}

//@Preview
//@Composable
//fun DecoupledConstraintLayoutPreview() {
//    JetpackComposeDemoTheme {
//        DecoupledConstraintLayout()
//    }
//}
//endregion Constraint Layout

//region [Experimental API] Intrinsics
@ExperimentalLayout
@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
    Row(modifier = modifier.preferredHeight(IntrinsicSize.Min)) {
        Text(
            modifier = Modifier.weight(1f).padding(start = 4.dp).wrapContentWidth(Alignment.Start),
            text = text1
        )
        Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().preferredWidth(1.dp))
        Text(
            modifier = Modifier.weight(1f).padding(end = 4.dp).wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}

//@ExperimentalLayout
//@Preview
//@Composable
//fun TwoTextsPreview() {
//    JetpackComposeDemoTheme {
//        Surface {
//            TwoTexts(text1 = "Hi", text2 = "There")
//        }
//    }
//}
//endregion [Experimental API] Intrinsics


//region Recyclerview
data class ItemViewState(
    val text: String
)

@Composable
fun MyComposeList(
    modifier: Modifier = Modifier,
    itemViewStates: List<ItemViewState>,
    itemClickedCallback: () -> Unit
) {
    // Use LazyRow when making horizontal lists
    LazyColumn(modifier = modifier) {
        items(itemViewStates) {
//            MyListItem(itemViewState = it)
            MyClickListItem(
                itemViewState = it,
                itemClickedCallback = itemClickedCallback
            )
        }

    }
}

@Composable
fun MyListItem(itemViewState: ItemViewState) {
    Text(text = itemViewState.text, modifier = Modifier.padding(16.dp))
}

@Composable
fun MyClickListItem(
    itemViewState: ItemViewState,
    itemClickedCallback: () -> Unit,
) {
    Button(onClick = { itemClickedCallback() }) {
        Text(text = itemViewState.text)
    }
}

@Preview
@Composable
fun MyListPreview() {
    JetpackComposeDemoTheme {
        MyComposeList(
            modifier = Modifier.fillMaxWidth().background(color = Color.White),
            itemViewStates = listOf(
                ItemViewState("Thanh Mia"),
                ItemViewState("Nam Heo"),
                ItemViewState("Nam béo")
            ),
            itemClickedCallback = {
                Log.d("Thanh", "MyListPreview")
            }
        )
    }
}

// multiple view type
sealed class ItemViewStateMultiType
data class ItemTypeOne(val text: String) : ItemViewStateMultiType()
data class ItemTypeTwo(
    val text: String,
    val description: String,
) : ItemViewStateMultiType()

@Composable
fun MyMultipleItemList(
    modifier: Modifier = Modifier,
    itemViewStates: List<ItemViewStateMultiType>,
) {
    LazyColumn(
        modifier = modifier.background(color = Color.White),
    ) {
        items(itemViewStates) { viewState ->
            when (viewState) {
                is ItemTypeOne -> ItemOne(viewState)
                is ItemTypeTwo -> ItemTwo(viewState)
            }
        }

    }
}

@Composable
fun ItemOne(itemTypeOne: ItemTypeOne) {
    Text(text = itemTypeOne.text, fontSize = 24.sp, color = colorResource(id = R.color.purple_200))
}

@Composable
fun ItemTwo(itemTypeTwo: ItemTypeTwo) {
    Column {
        Text(text = itemTypeTwo.text, fontSize = 20.sp)
        Text(text = itemTypeTwo.description, fontSize = 16.sp, color = colorResource(id = R.color.purple_700))
    }
}

@Preview
@Composable
fun MyMultipleItemListPreview() {
    JetpackComposeDemoTheme {
        MyMultipleItemList(
            itemViewStates = listOf(
                ItemTypeOne("Jetpack compose"),
                ItemTypeOne("Learn once, write everywhere!"),
                ItemTypeTwo("Tân Phạm", "Đùng một cái là xong"),
                ItemTypeTwo("Nam Heo", "Hơi béo"),
                ItemTypeTwo("Nam béo", "Hơi bê")
            )
        )
    }
}

//endregion Recyclerview
//region Demo
@Composable
fun TeamItem(placardTitle: String, team: String, score: Int, img: Int, onBtnClick: (Int) -> Unit) {
    val imgResource = imageResource(img)

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = placardTitle, fontSize = 20.sp)
        Spacer(Modifier.preferredHeight(16.dp))

        Image(imgResource, modifier = Modifier.preferredHeight(100.dp).preferredWidth(100.dp))
        Spacer(Modifier.preferredHeight(16.dp))

        Text(text = team, fontSize = 30.sp)
        Spacer(Modifier.preferredHeight(16.dp))

        Text(text = "$score", fontSize = 60.sp)
        Spacer(Modifier.preferredHeight(16.dp))

        Button(
            onClick = { onBtnClick(score + 3) }, shape = CircleShape
        ) {
            Text("+ 3 points", fontSize = 18.sp)
        }
        Spacer(Modifier.preferredHeight(16.dp))

        Button(onClick = { onBtnClick(score + 2) }, shape = CircleShape) {
            Text("+ 2 points", fontSize = 18.sp)
        }
        Spacer(Modifier.preferredHeight(16.dp))

        Button(
            onClick = { onBtnClick(score + 1) },
            shape = CircleShape
        ) { Text("Free Throw", fontSize = 18.sp) }
    }
}

@Composable
fun Home() {

    val team = remember {
        mutableStateOf(
            Team(
                "GOOGLE",
                0,
                R.drawable.ic_jet_pack,
                "APPLE",
                0,
                R.drawable.ic_swift_ui
            )
        )
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.TopCenter)
        ) {

            TeamItem(
                placardTitle = "Compose",
                team = team.value.homeTeam,
                score = team.value.homeScore,
                img = team.value.homeLogo,
                onBtnClick = { newScore ->
                    team.value = team.value.copy(homeScore = newScore)
                })

            TeamItem(
                placardTitle = "Swift UI",
                team = team.value.guestTeam,
                score = team.value.guestScore,
                img = team.value.guestLogo,
                onBtnClick = { newScore ->
                    team.value = team.value.copy(guestScore = newScore)
                })
        }
        Button(
            onClick = {
                team.value = team.value.copy(homeScore = 0)
                team.value = team.value.copy(guestScore = 0)
            },
            shape = CircleShape,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Clean",
                fontSize = 18.sp,
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}
@Composable
fun App() {
    val context = AmbientContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Jetpack compose vs Swift UI", color = Color.White)
                },
                actions = {
                    IconButton(onClick = {
                        openMyActivity(context)
                    }) {
                        Icon(Icons.Filled.Favorite)
                    }
                }
            )
        }
    ) {
        Home()
    }
}

fun openMyActivity(context: Context) {
    startActivity(
        context,
        Intent(context, MyActivity::class.java),
        Bundle()
    )
}

@Preview
@Composable
fun AppPreview() {
    JetpackComposeDemoTheme {
        App()
    }
}
//endregion Demo
