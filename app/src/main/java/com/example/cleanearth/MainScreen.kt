// MainScreen.kt

package com.example.cleanearth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(
    onCheckRecycle: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CleanEarth",
                    color = Color(0xFF121717),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                // TODO: IconButton for menu (resource not added yet)
                /*
                IconButton(onClick = onNavigateToLogin) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "Menu",
                        tint = Color(0xFF121717)
                    )
                }
                */
            }

            // Hero Section (Background Image temporarily removed)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
            ) {
                // TODO: Background Image (bg_hero)
                /*
                Image(
                    painter = painterResource(id = R.drawable.bg_hero),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                */
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.1f),
                                    Color.Black.copy(alpha = 0.4f)
                                )
                            )
                        )
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .matchParentSize()
                        .padding(bottom = 200.dp)
                ) {
                    Text(
                        text = "재활용? 이젠 고민하지 마세요!",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1).sp,
                        lineHeight = 45.sp,
                        modifier = Modifier.padding(horizontal = 22.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "사진 한 장으로 재활용 가능 여부를 알 수 있어요.",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 21.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Check Recycle Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onCheckRecycle,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF17CFA1)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // TODO: Icon for recycle (resource not added)
                    /*
                    Icon(
                        painter = painterResource(id = R.drawable.ic_recycle),
                        contentDescription = "Check Recycle"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    */
                    Text(
                        text = "재활용 여부, 지금 확인!",
                        color = Color(0xFF121717),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Today's Reform Recommendation
            Text(
                text = "오늘의 리폼 추천",
                color = Color(0xFF121717),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Recommendations List (Images temporarily removed)
            Column(modifier = Modifier.fillMaxWidth()) {
                val recommendations = listOf(
                    "플라스틱의 새로운 쓰임!" to "물병이 화분이 된다면? 지금 아이디어 보기",
                    "유리병, 버리지 마세요" to "인테리어 소품으로 재탄생하는 법!",
                    "종이가방의 반전 매력" to "포장지부터 수납함까지, 다양하게 활용해보세요"
                )
                recommendations.forEach { (title, subtitle) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // TODO: Thumbnail Image
                        /*
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        */
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(
                                text = title,
                                color = Color(0xFF121717),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = subtitle,
                                color = Color(0xFF638780),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Navigation
            Divider(color = Color(0xFFF0F5F2), thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val navItems = listOf("Home", "History", "Profile")
                navItems.forEachIndexed { index, label ->
                    val selected = index == 0
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .height(54.dp)
                            .clip(RoundedCornerShape(27.dp))
                            .clickable {
                                when (label) {
                                    "Home" -> onNavigateToSignUp()
                                    "History" -> {}
                                    "Profile" -> onNavigateToLogin()
                                }
                            }
                    ) {
                        // TODO: Navigation Icon
                        /*
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = label,
                            tint = if (selected) Color(0xFF121717) else Color(0xFF638780),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        */
                        Text(
                            text = label,
                            color = if (selected) Color(0xFF121717) else Color(0xFF638780),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
