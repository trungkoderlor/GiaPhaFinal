

create table ThanhVien(
	idThanhvien char(5) primary key,
	tenTV nchar(50),
	namsinh tinyint,
	gioitinh nchar(10),
	anh char(30),
	idbo char(5),
	idme char(5),
	idvc char(5)
)
 
Select * from ThanhVien
ALTER TABLE ThanhVien ADD
CONSTRAINT fkey_self
    FOREIGN KEY (idbo)
    REFERENCES ThanhVien(idThanhvien)
ALTER TABLE ThanhVien DROP COLUMN idgp
ALTER TABLE ThanhVien ADD idbo char(5)
ALTER TABLE GiaPha  ALTER COLUMN  TenGP  nchar(15)
ALTER TABLE ThanhVien  ALTER COLUMN  tenme  nchar(20)
INSERT INTO ThanhVien values('tv09',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)

ALTER TABLE ThanhVien
ALTER COLUMN anh text 
ALTER TABLE ThanhVien
ADD FOREIGN KEY (idbo) REFERENCES ThanhVien(idThanhvien);
ALTER TABLE ThanhVien ADD
CONSTRAINT primarykey PRIMARY KEY (IdThanhVien)
CREATE OR ALTER PROCEDURE GenerateIDThanhVien
    @newID CHAR(5) OUTPUT
AS
BEGIN
    DECLARE @lastID CHAR(5);
    DECLARE @lastIDAsInt INT;

    -- Lấy giá trị cuối cùng của cột idThanhvien
    SELECT @lastID = MAX(idThanhvien)
    FROM ThanhVien;

    -- Nếu không có giá trị cuối cùng, bắt đầu từ '00001'
    IF @lastID IS NULL
    BEGIN
        SET @newID = '00001';
    END
    ELSE
    BEGIN
        -- Thử chuyển đổi @lastID sang INT một cách an toàn
        SET @lastIDAsInt = TRY_CONVERT(INT, @lastID);
        
        -- Kiểm tra nếu chuyển đổi thành công
        IF @lastIDAsInt IS NOT NULL
        BEGIN
            -- Tăng giá trị cuối cùng lên 1 và tạo mã mới dạng chuỗi 5 ký tự
            SET @newID = RIGHT('00000' + CAST(@lastIDAsInt + 1 AS VARCHAR), 5);
        END
        ELSE
        BEGIN
            -- Nếu chuyển đổi không thành công, bắt đầu từ '00001'
            SET @newID = '00001';
        END
    END
END;

DECLARE @newID CHAR(5);

-- Gọi thủ tục để lấy mã idThanhvien mới
EXEC GenerateIDThanhVien @newID OUTPUT;

-- Thêm bản ghi mới vào bảng ThanhVien với mã idThanhvien mới
INSERT INTO ThanhVien (idThanhvien, tenTV, namsinh, gioitinh, anh, idbo, idme, idvc)
VALUES (@newID, 'Nguyen Van A', 1995, 'Nam', 'anh.jpg', '00002', '00003', '00004');
DECLARE @newID CHAR(5);

-- Gọi thủ tục để lấy mã idThanhvien mới
EXEC GenerateIDThanhVien @newID OUTPUT;

-- Thêm bản ghi mới vào bảng ThanhVien với mã idThanhvien mới
INSERT INTO ThanhVien (idThanhvien, tenTV, namsinh, gioitinh, anh, idbo, idme, idvc) VALUES (@newID, 'Nguyen Van A', 1995, 'Nam', 'anh.jpg', 'tv002', 'tv003', 'tv004');
select * from ThanhVien