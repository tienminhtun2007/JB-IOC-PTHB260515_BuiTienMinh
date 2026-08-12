package librarymanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowCardDAO {
    public List<BorrowCard> getAllBorrowCards() {
        List<BorrowCard> borrowCards = new ArrayList<>();
        String sql = "SELECT * FROM get_all_borrow_cards()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                borrowCards.add(mapResultSetToBorrowCard(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách: " + e.getMessage());
        }
        return borrowCards;
    }

    public void insertBorrowCard(BorrowCard card) {
        String sql = "CALL insert_borrower_card(?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
                 cstmt.setString(1, card.getBookTitle());
                 cstmt.setString(2, card.getBorrowerName());
                 cstmt.setTimestamp(3, Timestamp.valueOf(card.getBorrowedDate()));
                 cstmt.setTimestamp(4, Timestamp.valueOf(card.getReturnDeadline()));
                 cstmt.setInt(5, card.getQuantity());
                 cstmt.setString(6, card.getStatus());
                 cstmt.executeUpdate();
            System.out.println("Thêm phiếu mượn thành công");
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm mới: " + e.getMessage());
        }
    }

    public void updateBorrowCard(BorrowCard card) {
        String sql = "CALL update_borrow_card(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, card.getCardId());
            cstmt.setString(2, card.getBookTitle());
            cstmt.setString(3, card.getBorrowerName());
            cstmt.setTimestamp(4, Timestamp.valueOf(card.getBorrowedDate()));
            cstmt.setTimestamp(5, Timestamp.valueOf(card.getReturnDeadline()));
            cstmt.setInt(6, card.getQuantity());
            cstmt.setString(7, card.getStatus());
            cstmt.executeUpdate();
            System.out.println("Cập nhật thành công");
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    public void deleteBorrowCard(int cardId) {
        String sql = "CALL delete_borrow_card(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, cardId);
            cstmt.executeUpdate();
            System.out.println("Đã xoá thành công");
        } catch (SQLException e) {
            System.err.println("Lỗi khi xoá phiếu mượn: " + e.getMessage());
        }
    }

    public List<BorrowCard> searchBorrowCards(String functionName, String keyword) {
        List<BorrowCard> borrowCards = new ArrayList<>();
        String sql = "SELECT * FROM" + functionName + "(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, keyword);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    borrowCards.add(mapResultSetToBorrowCard(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm: " + e.getMessage());
        }
    return borrowCards;
    }

    private BorrowCard mapResultSetToBorrowCard(ResultSet rs) throws SQLException {
        BorrowCard card = new BorrowCard();
        card.setCardId(rs.getInt("card_id"));
        card.setBookTitle(rs.getString("book_title"));
        card.setBorrowerName(rs.getString("borrower_name"));
        card.setBorrowedDate(rs.getTimestamp("borrowed_date").toLocalDateTime());
        card.setReturnDeadline(rs.getTimestamp("return_deadline").toLocalDateTime());
        card.setQuantity(rs.getInt("quantity"));
        card.setStatus(rs.getString("status"));
        return card;
    }
}
