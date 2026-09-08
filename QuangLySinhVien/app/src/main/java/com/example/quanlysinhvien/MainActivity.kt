package com.example.quanlysinhvien

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlysinhvien.ui.theme.QuanLySinhVienTheme
import java.text.Normalizer
import java.util.Locale

data class SinhVien(
    val maSinhVien: String,
    val hoVaTen: String,
    val tuoi: Int,
    val nganhHoc: String,
    val diemTrungBinh: Double
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuanLySinhVienTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UngDungQuanLySinhVien()
                }
            }
        }
    }
}

@Composable
fun UngDungQuanLySinhVien() {

    val danhSachSinhVien = remember {
        mutableStateListOf(
            SinhVien(
                maSinhVien = "23115053122143",
                hoVaTen = "Dương Công Tiến",
                tuoi = 21,
                nganhHoc = "Công nghệ thông tin",
                diemTrungBinh = 8.7
            ),
            SinhVien(
                maSinhVien = "23115053122144",
                hoVaTen = "Nguyễn Thị Minh Anh",
                tuoi = 20,
                nganhHoc = "Công nghệ thông tin",
                diemTrungBinh = 9.2
            ),
            SinhVien(
                maSinhVien = "23115053122145",
                hoVaTen = "Phan Việt Hoàng",
                tuoi = 22,
                nganhHoc = "Quản trị kinh doanh",
                diemTrungBinh = 7.6
            ),
            SinhVien(
                maSinhVien = "23115053122146",
                hoVaTen = "Lê Thị Thu Hà",
                tuoi = 23,
                nganhHoc = "Kế toán",
                diemTrungBinh = 8.3
            ),
            SinhVien(
                maSinhVien = "23115053122147",
                hoVaTen = "Phạm Xuân Hoàng Nam",
                tuoi = 19,
                nganhHoc = "Logistics",
                diemTrungBinh = 4.7
            )
        )
    }

    var danhSachDangHienThi by remember {
        mutableStateOf(danhSachSinhVien.toList())
    }

    var thongBao by remember {
        mutableStateOf("Chào mừng bạn đến với chương trình quản lý sinh viên!")
    }

    var hienThiHopThoaiThem by remember {
        mutableStateOf(false)
    }

    var hienThiHopThoaiTimKiem by remember {
        mutableStateOf(false)
    }

    var hienThiHopThoaiTinhTrungBinh by remember {
        mutableStateOf(false)
    }

    var hienThiHopThoaiTimTheoNganh by remember {
        mutableStateOf(false)
    }

    fun themSinhVien(sinhVien: SinhVien): String {
        val maDaTonTai = danhSachSinhVien.any {
            it.maSinhVien.equals(
                sinhVien.maSinhVien,
                ignoreCase = true
            )
        }

        if (maDaTonTai) {
            return "Mã sinh viên đã tồn tại!"
        }

        danhSachSinhVien.add(sinhVien)
        danhSachDangHienThi = danhSachSinhVien.toList()

        return "Đã thêm sinh viên ${sinhVien.hoVaTen} thành công!"
    }

    fun hienThiTatCaSinhVien() {
        danhSachDangHienThi = danhSachSinhVien.toList()
        thongBao =
            "Đang hiển thị tất cả ${danhSachSinhVien.size} sinh viên."
    }

    fun timKiemSinhVien(tuKhoa: String) {
        val tuKhoaChuanHoa = chuanHoaChuoi(tuKhoa)

        danhSachDangHienThi = danhSachSinhVien.filter {
            it.maSinhVien.contains(
                tuKhoa,
                ignoreCase = true
            ) || chuanHoaChuoi(it.hoVaTen).contains(tuKhoaChuanHoa)
        }

        thongBao = if (danhSachDangHienThi.isEmpty()) {
            "Không tìm thấy sinh viên phù hợp."
        } else {
            "Tìm thấy ${danhSachDangHienThi.size} sinh viên phù hợp."
        }
    }

    fun tinhDiemTrungBinhTheoNganh(nganh: String) {
        val ketQua = danhSachSinhVien.filter {
            chuanHoaChuoi(it.nganhHoc) == chuanHoaChuoi(nganh)
        }

        danhSachDangHienThi = ketQua

        thongBao = if (ketQua.isEmpty()) {
            "Không tìm thấy sinh viên thuộc ngành $nganh."
        } else {
            val diemTrungBinh = ketQua
                .map { it.diemTrungBinh }
                .average()

            "Điểm GPA trung bình của ngành ${ketQua.first().nganhHoc}: " +
                    "%.2f".format(diemTrungBinh)
        }
    }

    fun timSinhVienCoGpaCaoNhat() {
        val gpaCaoNhat = danhSachSinhVien.maxOfOrNull {
            it.diemTrungBinh
        }

        if (gpaCaoNhat == null) {
            danhSachDangHienThi = emptyList()
            thongBao = "Danh sách sinh viên đang trống."
            return
        }

        danhSachDangHienThi = danhSachSinhVien.filter {
            it.diemTrungBinh == gpaCaoNhat
        }

        thongBao = "Điểm GPA cao nhất là %.2f.".format(gpaCaoNhat)
    }

    fun xoaSinhVien(sinhVien: SinhVien) {
        danhSachSinhVien.remove(sinhVien)
        danhSachDangHienThi = danhSachSinhVien.toList()

        thongBao =
            "Đã xóa sinh viên ${sinhVien.maSinhVien} - ${sinhVien.hoVaTen}."
    }

    fun demSinhVienGpaTuTam() {
        val ketQua = danhSachSinhVien.filter {
            it.diemTrungBinh >= 8.0
        }

        danhSachDangHienThi = ketQua
        thongBao =
            "Có ${ketQua.size} sinh viên có GPA từ 8.0 trở lên."
    }

    fun demSinhVienGpaDuoiNam() {
        val ketQua = danhSachSinhVien.filter {
            it.diemTrungBinh < 5.0
        }

        danhSachDangHienThi = ketQua
        thongBao =
            "Có ${ketQua.size} sinh viên có GPA dưới 5.0."
    }

    fun timSinhVienLonTuoiNhat() {
        val tuoiLonNhat = danhSachSinhVien.maxOfOrNull {
            it.tuoi
        }

        if (tuoiLonNhat == null) {
            danhSachDangHienThi = emptyList()
            thongBao = "Danh sách sinh viên đang trống."
            return
        }

        danhSachDangHienThi = danhSachSinhVien.filter {
            it.tuoi == tuoiLonNhat
        }

        thongBao = "Sinh viên lớn tuổi nhất hiện nay là $tuoiLonNhat tuổi."
    }

    fun timSinhVienTrongKhoangGpa() {
        danhSachDangHienThi = danhSachSinhVien.filter {
            it.diemTrungBinh in 7.0..8.5
        }

        thongBao =
            "Tìm thấy ${danhSachDangHienThi.size} sinh viên có GPA từ 7.0 đến 8.5."
    }

    fun timSinhVienTheoNganh(nganh: String) {
        danhSachDangHienThi = danhSachSinhVien.filter {
            chuanHoaChuoi(it.nganhHoc) == chuanHoaChuoi(nganh)
        }

        thongBao = if (danhSachDangHienThi.isEmpty()) {
            "Không tìm thấy sinh viên thuộc ngành $nganh."
        } else {
            "Tìm thấy ${danhSachDangHienThi.size} sinh viên thuộc ngành $nganh."
        }
    }

    fun sapXepTheoGpaGiamDan() {
        danhSachDangHienThi = danhSachSinhVien.sortedByDescending {
            it.diemTrungBinh
        }

        thongBao = "Đã sắp xếp sinh viên theo GPA giảm dần."
    }

    fun hienThiBaSinhVienGpaCaoNhat() {
        danhSachDangHienThi = danhSachSinhVien
            .sortedByDescending { it.diemTrungBinh }
            .take(3)

        thongBao = "Đang hiển thị 3 sinh viên có GPA cao nhất."
    }

    fun sapXepTheoTuoi() {
        danhSachDangHienThi = danhSachSinhVien.sortedBy {
            it.tuoi
        }

        thongBao = "Đã sắp xếp sinh viên theo tuổi tăng dần."
    }

    fun sapXepTheoTen() {
        danhSachDangHienThi = danhSachSinhVien.sortedBy {
            chuanHoaChuoi(it.hoVaTen)
        }

        thongBao = "Đã sắp xếp sinh viên theo tên từ A đến Z."
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { khoangCachBenTrong ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(khoangCachBenTrong)
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "QUẢN LÝ SINH VIÊN",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Ứng dụng lập trình bằng Kotlin",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                TheThongBao(thongBao)
            }

            item {
                Text(
                    text = "CHỨC NĂNG CƠ BẢN",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                HangNutChucNang(
                    noiDungNutThuNhat = "1. Thêm sinh viên",
                    noiDungNutThuHai = "2. Hiển thị tất cả",
                    khiNhanNutThuNhat = {
                        hienThiHopThoaiThem = true
                    },
                    khiNhanNutThuHai = {
                        hienThiTatCaSinhVien()
                    }
                )
            }

            item {
                HangNutChucNang(
                    noiDungNutThuNhat = "3. Tìm sinh viên",
                    noiDungNutThuHai = "4. GPA theo ngành",
                    khiNhanNutThuNhat = {
                        hienThiHopThoaiTimKiem = true
                    },
                    khiNhanNutThuHai = {
                        hienThiHopThoaiTinhTrungBinh = true
                    }
                )
            }

            item {
                HangNutChucNang(
                    noiDungNutThuNhat = "5. GPA cao nhất",
                    noiDungNutThuHai = "6. Xóa sinh viên",
                    khiNhanNutThuNhat = {
                        timSinhVienCoGpaCaoNhat()
                    },
                    khiNhanNutThuHai = {
                        thongBao =
                            "Nhấn nút Xóa ở sinh viên mà bạn muốn xóa."
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "CHỨC NĂNG BỔ SUNG",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                HangNutChucNang(
                    noiDungNutThuNhat = "7. Đếm GPA ≥ 8.0",
                    noiDungNutThuHai = "8. Đếm GPA < 5.0",
                    khiNhanNutThuNhat = {
                        demSinhVienGpaTuTam()
                    },
                    khiNhanNutThuHai = {
                        demSinhVienGpaDuoiNam()
                    }
                )
            }

            item {
                HangNutChucNang(
                    noiDungNutThuNhat = "9. Lớn tuổi nhất",
                    noiDungNutThuHai = "10. GPA 7.0–8.5",
                    khiNhanNutThuNhat = {
                        timSinhVienLonTuoiNhat()
                    },
                    khiNhanNutThuHai = {
                        timSinhVienTrongKhoangGpa()
                    }
                )
            }

            item {
                HangNutChucNang(
                    noiDungNutThuNhat = "11. Tìm theo ngành",
                    noiDungNutThuHai = "12. Tìm theo tên",
                    khiNhanNutThuNhat = {
                        hienThiHopThoaiTimTheoNganh = true
                    },
                    khiNhanNutThuHai = {
                        hienThiHopThoaiTimKiem = true
                    }
                )
            }

            item {
                HangNutChucNang(
                    noiDungNutThuNhat = "13. Xếp GPA giảm",
                    noiDungNutThuHai = "14. Top 3 GPA",
                    khiNhanNutThuNhat = {
                        sapXepTheoGpaGiamDan()
                    },
                    khiNhanNutThuHai = {
                        hienThiBaSinhVienGpaCaoNhat()
                    }
                )
            }

            item {
                HangNutChucNang(
                    noiDungNutThuNhat = "15. Xếp theo tuổi",
                    noiDungNutThuHai = "16. Xếp theo tên",
                    khiNhanNutThuNhat = {
                        sapXepTheoTuoi()
                    },
                    khiNhanNutThuHai = {
                        sapXepTheoTen()
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "DANH SÁCH SINH VIÊN (${danhSachDangHienThi.size})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(
                items = danhSachDangHienThi,
                key = { sinhVien -> sinhVien.maSinhVien }
            ) { sinhVien ->
                TheSinhVien(
                    sinhVien = sinhVien,
                    khiNhanXoa = {
                        xoaSinhVien(sinhVien)
                    }
                )
            }
        }
    }

    if (hienThiHopThoaiThem) {
        HopThoaiThemSinhVien(
            khiDong = {
                hienThiHopThoaiThem = false
            },
            khiThem = { sinhVien ->
                thongBao = themSinhVien(sinhVien)
                hienThiHopThoaiThem = false
            }
        )
    }

    if (hienThiHopThoaiTimKiem) {
        HopThoaiNhapDuLieu(
            tieuDe = "Tìm kiếm sinh viên",
            nhanOThongTin = "Nhập mã hoặc một phần tên",
            khiDong = {
                hienThiHopThoaiTimKiem = false
            },
            khiXacNhan = { tuKhoa ->
                timKiemSinhVien(tuKhoa)
                hienThiHopThoaiTimKiem = false
            }
        )
    }

    if (hienThiHopThoaiTinhTrungBinh) {
        HopThoaiNhapDuLieu(
            tieuDe = "Tính GPA trung bình theo ngành",
            nhanOThongTin = "Nhập tên ngành",
            khiDong = {
                hienThiHopThoaiTinhTrungBinh = false
            },
            khiXacNhan = { nganh ->
                tinhDiemTrungBinhTheoNganh(nganh)
                hienThiHopThoaiTinhTrungBinh = false
            }
        )
    }

    if (hienThiHopThoaiTimTheoNganh) {
        HopThoaiNhapDuLieu(
            tieuDe = "Tìm sinh viên theo ngành",
            nhanOThongTin = "Nhập tên ngành",
            khiDong = {
                hienThiHopThoaiTimTheoNganh = false
            },
            khiXacNhan = { nganh ->
                timSinhVienTheoNganh(nganh)
                hienThiHopThoaiTimTheoNganh = false
            }
        )
    }
}

@Composable
fun HangNutChucNang(
    noiDungNutThuNhat: String,
    noiDungNutThuHai: String,
    khiNhanNutThuNhat: () -> Unit,
    khiNhanNutThuHai: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = khiNhanNutThuNhat,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                horizontal = 4.dp,
                vertical = 10.dp
            )
        ) {
            Text(
                text = noiDungNutThuNhat,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = khiNhanNutThuHai,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                horizontal = 4.dp,
                vertical = 10.dp
            )
        ) {
            Text(
                text = noiDungNutThuHai,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TheThongBao(thongBao: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Text(
            text = thongBao,
            modifier = Modifier.padding(12.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TheSinhVien(
    sinhVien: SinhVien,
    khiNhanXoa: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = sinhVien.hoVaTen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(text = "Mã sinh viên: ${sinhVien.maSinhVien}")
            Text(text = "Tuổi: ${sinhVien.tuoi}")
            Text(text = "Ngành học: ${sinhVien.nganhHoc}")

            Text(
                text = "Điểm GPA: %.2f".format(
                    sinhVien.diemTrungBinh
                ),
                fontWeight = FontWeight.Bold,
                color = layMauGpa(sinhVien.diemTrungBinh)
            )

            OutlinedButton(
                onClick = khiNhanXoa,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Xóa sinh viên")
            }
        }
    }
}

@Composable
fun HopThoaiThemSinhVien(
    khiDong: () -> Unit,
    khiThem: (SinhVien) -> Unit
) {
    var maSinhVien by remember {
        mutableStateOf("")
    }

    var hoVaTen by remember {
        mutableStateOf("")
    }

    var tuoi by remember {
        mutableStateOf("")
    }

    var nganhHoc by remember {
        mutableStateOf("")
    }

    var diemTrungBinh by remember {
        mutableStateOf("")
    }

    var thongBaoLoi by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = khiDong,
        title = {
            Text("Thêm sinh viên")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                OutlinedTextField(
                    value = maSinhVien,
                    onValueChange = {
                        maSinhVien = it
                    },
                    label = {
                        Text("Mã sinh viên")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = hoVaTen,
                    onValueChange = {
                        hoVaTen = it
                    },
                    label = {
                        Text("Họ và tên")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = tuoi,
                    onValueChange = {
                        tuoi = it
                    },
                    label = {
                        Text("Tuổi")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = nganhHoc,
                    onValueChange = {
                        nganhHoc = it
                    },
                    label = {
                        Text("Ngành học")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = diemTrungBinh,
                    onValueChange = {
                        diemTrungBinh = it
                    },
                    label = {
                        Text("Điểm GPA từ 0 đến 10")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (thongBaoLoi.isNotEmpty()) {
                    Text(
                        text = thongBaoLoi,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val tuoiHopLe = tuoi.toIntOrNull()

                    val gpaHopLe = diemTrungBinh
                        .replace(',', '.')
                        .toDoubleOrNull()

                    thongBaoLoi = when {
                        maSinhVien.isBlank() ->
                            "Mã sinh viên không được để trống."

                        hoVaTen.isBlank() ->
                            "Họ và tên không được để trống."

                        tuoiHopLe == null || tuoiHopLe !in 1..150 ->
                            "Tuổi phải là số từ 1 đến 150."

                        nganhHoc.isBlank() ->
                            "Ngành học không được để trống."

                        gpaHopLe == null || gpaHopLe !in 0.0..10.0 ->
                            "Điểm GPA phải nằm trong khoảng từ 0 đến 10."

                        else -> ""
                    }

                    if (thongBaoLoi.isEmpty()) {
                        khiThem(
                            SinhVien(
                                maSinhVien = maSinhVien.trim(),
                                hoVaTen = hoVaTen.trim(),
                                tuoi = tuoiHopLe!!,
                                nganhHoc = nganhHoc.trim(),
                                diemTrungBinh = gpaHopLe!!
                            )
                        )
                    }
                }
            ) {
                Text("Thêm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = khiDong
            ) {
                Text("Hủy")
            }
        }
    )
}

@Composable
fun HopThoaiNhapDuLieu(
    tieuDe: String,
    nhanOThongTin: String,
    khiDong: () -> Unit,
    khiXacNhan: (String) -> Unit
) {
    var duLieuNhap by remember {
        mutableStateOf("")
    }

    var hienThiLoi by remember {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = khiDong,
        title = {
            Text(tieuDe)
        },
        text = {
            Column {
                OutlinedTextField(
                    value = duLieuNhap,
                    onValueChange = {
                        duLieuNhap = it
                        hienThiLoi = false
                    },
                    label = {
                        Text(nhanOThongTin)
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (hienThiLoi) {
                    Text(
                        text = "Vui lòng không để trống thông tin.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (duLieuNhap.isBlank()) {
                        hienThiLoi = true
                    } else {
                        khiXacNhan(duLieuNhap.trim())
                    }
                }
            ) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            TextButton(
                onClick = khiDong
            ) {
                Text("Hủy")
            }
        }
    )
}

fun chuanHoaChuoi(chuoi: String): String {
    return Normalizer
        .normalize(chuoi.trim(), Normalizer.Form.NFD)
        .replace("\\p{M}+".toRegex(), "")
        .lowercase(Locale.getDefault())
}

fun layMauGpa(gpa: Double): Color {
    return when {
        gpa >= 8.0 -> Color(0xFF16803C)
        gpa >= 5.0 -> Color(0xFFE07B00)
        else -> Color(0xFFD32F2F)
    }
}