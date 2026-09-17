package com.example.studentcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SinhVien(
    val hoTen: String,
    val maSinhVien: String,
    val truong: String,
    val lop: String,
    val nganh: String,
    val anhDaiDien: Int? = null
)

private val sinhVien = SinhVien(
    hoTen = "Dương Công Tiến",
    maSinhVien = "23115053122143",
    truong = "Trường Đại học Sư phạm Kỹ thuật",
    lop = "23T1",
    nganh = "Công Nghệ Thông Tin",
    anhDaiDien = null
)

private val xanhDam = Color(0xFF163A67)
private val xanhNhat = Color(0xFFEAF1FA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UngDungTheSinhVien()
        }
    }
}

@Composable
fun UngDungTheSinhVien() {
    MaterialTheme(
        colorScheme = lightColorScheme(primary = xanhDam)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF2F5FA)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                TheSinhVien(sinhVien)
            }
        }
    }
}

@Composable
fun TheSinhVien(thongTin: SinhVien) {
    Card(
        modifier = Modifier
            .widthIn(max = 420.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(xanhDam, Color(0xFF2862A0))
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "THẺ SINH VIÊN",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = thongTin.truong,
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val anh = thongTin.anhDaiDien

            if (anh != null) {
                Image(
                    painter = painterResource(id = anh),
                    contentDescription = "Ảnh của ${thongTin.hoTen}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(104.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(104.dp)
                        .background(xanhNhat, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = thongTin.hoTen
                            .split(" ")
                            .filter { it.isNotBlank() }
                            .take(3)
                            .joinToString("") {
                                it.take(1).uppercase()
                            },
                        color = xanhDam,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = thongTin.hoTen,
                color = xanhDam,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = xanhNhat,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "MÃ SỐ SINH VIÊN",
                        color = xanhDam,
                        fontSize = 12.sp
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = thongTin.maSinhVien,
                        color = xanhDam,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            DongThongTin("Lớp", thongTin.lop)

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = xanhNhat)
            Spacer(Modifier.height(16.dp))

            DongThongTin("Ngành học", thongTin.nganh)
        }
    }
}

@Composable
fun DongThongTin(nhan: String, noiDung: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = nhan,
            color = Color(0xFF526174),
            fontSize = 13.sp
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = noiDung,
            color = Color(0xFF182A41),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun XemTruocTheSinhVien() {
    UngDungTheSinhVien()
}