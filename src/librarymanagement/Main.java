package librarymanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final BorrowCardDAO borrowCardDAO = new BorrowCardDAO();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
    public static void main(String[] args) {
        int choice = 0;
        do {
            System.out.println("=============== LIBRARY MANAGEMENT ================");
            System.out.println("1. Danh sách tất cả phiếu mượn");
            System.out.println("2. Thêm mới phiếu mượn");
            System.out.println("3. Cập nhật thông tin phiếu mượn");
            System.out.println("4. Xoá phiếu mượn");
            System.out.println("5. Tìm kiếm phiếu mượn theo tên độc giả");
            System.out.println("6. Tìm kiếm phiếu mượn theo tên sách");
            System.out.println("7. Thoát");
            System.out.print("Chọn chức năng (1-7): ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập 1 số từ 1 đến 7");
                continue;
            }
            switch (choice) {
                case 1:
                    displayAllCards();
                    break;
                case 2:
                    insertBorrowCard();
                    break;
                case 3:
                    updateCard();
                    break;
                case 4:
                    deleteCard();
                    break;
                case 5:
                    searchCards("search_by_borrower_name", "tên độc giả");
                    break;
                case 6:
                    searchCards("get_by_book_title", "tên sách");
                    break;
                case 7:
                    System.out.println("Chương trình đã thoát thành công");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 7);
    }

    private static void displayAllCards() {
        System.out.println("=============== DANH SÁCH PHIẾU MƯỢN ================");
        List<BorrowCard> list = borrowCardDAO.getAllBorrowCards();
        if (list.isEmpty()) {
            System.out.println("Chưa có phiếu mượn nào");
        } else {
            list.forEach(card -> System.out.println(card.toString()));
        }
    }

    private static void insertBorrowCard() {
        BorrowCard borrowCard = new BorrowCard();
        System.out.println("====== NHẬP THÔNG TIN PHIẾU MƯỢN ======");
        System.out.print("Nhập tên sách: ");
        borrowCard.setBookTitle(sc.nextLine());

        System.out.print("Nhập tên độc giả: ");
        borrowCard.setBorrowerName(sc.nextLine());

        System.out.print("Nhập ngày mượn: ");
        borrowCard.setBorrowedDate(LocalDate.parse(sc.nextLine(), dateFormatter).atStartOfDay());
        System.out.print("Nhập ngày trả: ");
        borrowCard.setReturnDeadline(LocalDate.parse(sc.nextLine(), dateFormatter).atStartOfDay());
        System.out.print("Nhập số lượng mượn: ");
        borrowCard.setQuantity(Integer.parseInt(sc.nextLine()));
        System.out.print("Nhập trạng thái; (Borrowing, Returned, Overdue) ");
        borrowCard.setStatus(sc.nextLine());
        borrowCardDAO.insertBorrowCard(borrowCard);
    }

    private static void updateCard() {
        System.out.print("Nhập mã phiếu mượn cần sửa: ");
        int cardId = Integer.parseInt(sc.nextLine());
        BorrowCard borrowCard = new BorrowCard();
        borrowCard.setCardId(cardId);

        System.out.println("====== CẬP NHẬT PHIẾU MƯỢN ======");
        System.out.print("Nhập tên sách mới: ");
        borrowCard.setBookTitle(sc.nextLine());

        System.out.print("Nhập tên độc giả mới: ");
        borrowCard.setBorrowerName(sc.nextLine());

        System.out.print("Nhập ngày mượn mới: ");
        borrowCard.setBorrowedDate(LocalDate.parse(sc.nextLine(), dateFormatter).atStartOfDay());
        System.out.print("Nhập ngày trả mới: ");
        borrowCard.setReturnDeadline(LocalDate.parse(sc.nextLine(), dateFormatter).atStartOfDay());
        System.out.print("Nhập số lượng mượn mới: ");
        borrowCard.setQuantity(Integer.parseInt(sc.nextLine()));
        System.out.print("Nhập trạng thái mới; (Borrowing, Returned, Overdue) ");
        borrowCard.setStatus(sc.nextLine());
        borrowCardDAO.updateBorrowCard(borrowCard);
    }

    private static void deleteCard() {
        System.out.println("Nhập mã phiếu mượn cần xoá: ");
        int cardId = Integer.parseInt(sc.nextLine());

        System.out.println("Xác nhận xoá phiếu mượn (Y/N): ");
        if (sc.nextLine().equalsIgnoreCase("Y")) {
            borrowCardDAO.deleteBorrowCard(cardId);
        } else {
            System.out.println("Hoàn tác xoá phiếu mượn");
        }
    }

    private static void searchCards(String functionName,String keyword) {
        System.out.print("Nhập" + keyword + "cần tìm:  ");
        String key = sc.nextLine();
        List<BorrowCard> list = borrowCardDAO.searchBorrowCards(functionName, key);
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy phiếu mượn");
        } else {
            list.forEach(card -> System.out.println(card.toString()));
        }
    }
}
