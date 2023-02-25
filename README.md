# G8Shop
Subject project of mobile software development group 8 - HCMUS.
<br/>
Lecturer: Doc. Trương Toàn Thịnh

### Team member:
* Triệu Quốc Thái - 20120370
* Trần Văn Thật - 20120376
* Hồ Duy Bảo - 20120433
* Trương Văn Hào - 20120471
* Dương Minh Hiếu - 20120473

## Lý do ra đời
Xuất phát từ vấn đề nhu cầu mua sắm ngày càng tăng trong kỷ nguyên Internet được phổ
biến rất rộng rãi hiện nay. Thay vì, phải ra các cửa hàng, chợ, … người dùng có thể ở nhà và
tha hồ lựa chọn các sản phẩm mình thích theo nhu cầu, thanh toán tiện dụng, mà đôi khi còn
có giá hời hơn rất nhiều so với bên ngoài cửa hàng.

Đặc biệt, hiện nay trên các sàn thương mại nhu cầu người dùng tăng rất cao về mua sắm
online nhưng đôi khi sản phẩm họ nhận về lại không chất lượng như họ kỳ vọng khi xem thông
tin sản phẩm. Vì thế, G8 Shop đã ra đời vừa giải quyết được vấn đề nhu cầu cực lớn về mua
sắm của con người, mà còn mang lại sự an toàn về sản phẩm họ sẽ nhận được.
<br/>
## Các phần mềm có chức năng tương tự
Hiện nay có rất nhiều App thương mại điện tử rất nổi tiếng trên thị trường như: Shopee,
Lazada, … Mỗi app sẽ có những chức năng chính nổi bật sau:
<br/>
 ### Shopee
* Người bán: Có thể thiết lập shop, cài đặt thông báo, thiết lập vận chuyển, cài
đặt chat, chọn ngôn ngữ, hiển thị địa chỉ lấy hàng, hiển thị hồ sơ shop, quản lí
sản phẩm đơn hàng (thêm, sửa, xóa, tình trạng), ...
* Người mua: Đăng ký, đăng nhập, có thể tìm kiếm sản phẩm, lọc sản phẩm,
thêm vào giỏ hàng, hiển thị thông tin sản phẩm, chat với shop, thông báo, cài
đặt profile, tình trạng đơn mua, ví ShopeePay, voucher, thanh toán, đánh giá,
nhận xét, chia sẻ …

=> App khá hoàn thiện và đẹp, nhưng hay xảy ra tình trạng chồng chất dữ liệu dẫn đến lag cho
người sử dụng Android. Việc ai cũng có thể thiết lập shop và bán hàng dẫn đến không kiểm
soát được chất lượng, gây nhiều vấn đề phá giá.
<br/>

### Lazada
* Người bán: Có thể thiết lập shop, cài đặt thông báo, thiết lập vận chuyển, cài
đặt chat, chọn ngôn ngữ, hiển thị địa chỉ lấy hàng, hiển thị hồ sơ shop, quản lí
sản phẩm đơn hàng (thêm, sửa, xóa, tình trạng), ...
* Người mua: Đăng ký, đăng nhập, nhận thông báo ưu đãi, trò chuyện với nhà
bán hàng, cá nhân hóa trải nghiệm với sản phẩm, tìm kiếm với nhiều bộ lọc nâng
cao, đánh giá, nhận xét, theo dõi đơn hàng, dễ dàng chia sẻ,

=> App mượt mà, nhưng giao diện và các chức năng bố trí khó nhận thấy hơn shopee, dẫn đến
trải nghiệm không tốt cho người mới sử dụng.
<br/>

## Điểm khác biệt của chương trình
Nhóm phát triển nhóm dựa trên những chức năng đã có của các app trên, nhưng có những
điểm khách biệt như sau:
<br/>
* Dự án được chia làm 2 app: G8Shop và G8Shop Admin => dễ dàng quản lý
* Sử dụng cho một shop nhất định (admin) => chất lượng sản phẩm được kiểm kê và an
toàn hơn.
* Thanh toán qua QR code Ngân hàng, Momo.
* Bảo mật tốt (mật khẩu được mã hóa trước khi được đưa lên database).
* Sử dụng nhữ kĩ thuật đặc biệt: đăng nhập bằng facebook, mã hóa mật khẩu bằng
SHA, …
• Giao diện: Được đầu tư thiết riêng từ công cụ Figma (tại đây).
<br/>
## Thông tin cần thiết để thực thi chương trình
Dự án G8 Shop được chia thành 2 app
* App G8 Shop: Dành cho khách hàng, tại đây khách hàng có thể thoải thích mua sắm,
chat, xem sản phẩm, …
* App G8Shop Admin: App này dành cho Admin những người trực tiếp quản lí sản
phẩm, mã giảm giá, tài khoản khách hàng, …

## Cách cài đặt: 
* Cài đặt bằng file *.apk hoặc chạy trực tiếp trên máy ảo Android Studio
* Chuẩn bị môi trường: Dùng điện thoại chạy hệ điều hành Android
* Các tài khoản login vào hệ thống: Có thể tạo tài khoản khách hàng trên app (facebook,
tự tạo), tài khoản admin được cung cấp sẵn.
* Tên CSDL: FireBase (Firestore, Storage, Messaging), SQLite.
* Ngoài ra: Cần cấp quyền truy cập vào bộ nhớ cho ứng dụng, cần có kết nối internet
