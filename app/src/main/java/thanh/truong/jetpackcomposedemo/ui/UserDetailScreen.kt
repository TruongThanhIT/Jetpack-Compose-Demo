package thanh.truong.jetpackcomposedemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import thanh.truong.jetpackcomposedemo.R
import thanh.truong.jetpackcomposedemo.ui.model.Location
import thanh.truong.jetpackcomposedemo.ui.model.User
import thanh.truong.jetpackcomposedemo.ui.theme.orangeRed

@Composable
fun UserDetailScreen(
    userId: String?,
    detailViewModel: DetailViewModel = viewModel(),
    onClose: () -> Unit = {}
) {
    val user = detailViewModel.getUserBy(userId).collectAsState(initial = null)
    Scaffold(
        topBar = {
            UserDetailTopBar(onClose = onClose, title = "Customer information")
        },
        content = {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                UserDetailInfoCard(
                    user = user.value, modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                UserContactInfo(user = user.value, modifier = Modifier.fillMaxWidth())
            }
        }
    )
}

@Composable
fun UserContactInfo(user: User?, modifier: Modifier) {
    Text(
        text = "Contact information",
        color = Color.LightGray,
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 14.sp,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(12.dp))
    Card(
        backgroundColor = Color.White,
        elevation = 4.dp,
        shape = CircleShape.copy(CornerSize(16.dp)),
        modifier = modifier.wrapContentHeight()
    ) {
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(
                    text = "Phone number",
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                Text(
                    text = user?.phone ?: "-",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Divider(color = Color.LightGray, thickness = 1.dp)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(
                    text = "Email",
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                Text(
                    text = user?.email ?: "-",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            user?.location?.let { location ->
                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Stays at",
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${location.street}, ${location.city}, ${location.state}, ${location.country}",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
fun UserDetailInfoCard(user: User?, modifier: Modifier) {
    Card(
        backgroundColor = orangeRed,
        elevation = 4.dp,
        shape = CircleShape.copy(CornerSize(16.dp)),
        modifier = modifier
    ) {
        ConstraintLayout {
            val (avatar, nameText, genderImage, dateOfBirthText) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = user?.picture,
                    builder = {
                        placeholder(R.drawable.ic_jet_pack)
                        error(R.drawable.ic_jet_pack)
                        transformations(CircleCropTransformation())
                        memoryCacheKey("user${user?.id}")
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(48.dp)
                    .aspectRatio(1f)
                    .constrainAs(avatar) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                    }
            )
            Text(
                text = "${user?.title.orEmpty()}. ${user?.firstName.orEmpty()} ${user?.lastName.orEmpty()}".trim(),
                color = Color.White,
                modifier = Modifier
                    .constrainAs(nameText) {
                        start.linkTo(avatar.end, 8.dp)
                        end.linkTo(parent.end, 16.dp)
                        centerVerticallyTo(avatar)
                        width = Dimension.fillToConstraints
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "DOB: " + user?.dateOfBirth?.substringBefore("T").orEmpty(),
                color = Color.White,
                modifier = Modifier
                    .constrainAs(dateOfBirthText) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(avatar.bottom, 12.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                        width = Dimension.wrapContent
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                textAlign = TextAlign.Left
            )
            if (user?.gender != null) {
                Icon(
                    tint = Color.White,
                    imageVector = if (user.gender == "female") Icons.Outlined.Female else Icons.Outlined.Male,
                    contentDescription = null,
                    modifier = Modifier.constrainAs(genderImage) {
                        end.linkTo(parent.end, 16.dp)
                        centerVerticallyTo(parent)
                    }
                )
            }
        }
    }
}

@Composable
fun UserDetailTopBar(onClose: () -> Unit, title: String) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier.fillMaxWidth(),
        content = {
            IconButton(onClick = onClose, modifier = Modifier.padding(8.dp)) {
                Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
            }
            Text(
                text = title,
                color = Color.Black,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        })
}

@Preview
@Composable
fun UserDetailPreview() {
    val user = User(
        title = "Mr",
        lastName = "Nguyen",
        firstName = "Hung",
        gender = "male",
        email = "v.hungnpk@cloudhms.net",
        dateOfBirth = "01/01/1994",
        phone = "0912303002",
        location = Location(
            street = "188 Vo Thi Sau",
            city = "District 1",
            state = "Ho Chi Minh city",
            country = "Vietnam"
        )
    )
    Scaffold(
        topBar = {
            UserDetailTopBar(onClose = { }, title = "Customer information")
        },
        content = {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                UserDetailInfoCard(
                    user = user,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                UserContactInfo(user = user, modifier = Modifier.fillMaxWidth())
            }
        }
    )
}
