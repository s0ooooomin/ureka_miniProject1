package ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import jdbc.dao.OrderDAO;
import jdbc.dto.OrderDTO;
import ui.dialog.DeleteMovieDialog;
import ui.dialog.DeleteOrderDialog;
import ui.dialog.DetailMovieDialog;
//import ui.dialog.DetailOrderDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
// OrderDAO와 같은 DAO 클래스들을 import 해야 합니다.
import java.util.List;

public class OrderPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private OrderDAO orderDAO; // 실제 DAO 객체

    public OrderPanel() {
        OrderDAO orderDAO = new OrderDAO();
        setLayout(new BorderLayout());

        // 테이블 모델 및 테이블 생성
        String[] columnNames = {"주문ID", "영화제목", "고객명", "전화번호", "대여일"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton returnButton = new JButton("반납 처리");
        JButton updateButton = new JButton("날짜 수정");
        JButton deleteButton = new JButton("주문 삭제");
        buttonPanel.add(returnButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 데이터 로드
        loadOrderData();

        // --- 이벤트 리스너 ---
        
        // 2-1: 반납 처리
        returnButton.addActionListener(e -> {
			System.out.println("return"); 

            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) { // 정상
            	int orderId = (int) model.getValueAt(selectedRow, 0);
                
                try {
                    // 1. DAO를 통해 반납 처리 및 가격 계산
                    int price = orderDAO.ReturnProcess(orderId);

                    // 2. 사용자에게 결제/취소 확인
                    String message = "반납 요금은 " + price + "원 입니다. 결제하시겠습니까?";
                    int choice = JOptionPane.showConfirmDialog(this, message, "결제 확인", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        orderDAO.commit();
                        JOptionPane.showMessageDialog(this, "반납 처리가 완료되었습니다.");
                    } else {
                        orderDAO.rollback();
                        JOptionPane.showMessageDialog(this, "반납이 취소되었습니다.");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "반납 처리 중 오류 발생: " + ex.getMessage());
                    orderDAO.rollback(); // 예외 발생 시 롤백
                } finally {
                    loadOrderData(); // 테이블 새로고침
                }
            }

            
        });
        
//        // detail-update
//        // 2. detail - update
//        updateButton.addActionListener(e -> {
//			System.out.println("Order detail"); 
//			int selectedRow = table.getSelectedRow(); // 선택된 현재 row
//			if (selectedRow >= 0) { // 정상
//				DetailOrderDialog detailOrderDialog = new DetailOrderDialog(this, model, selectedRow); // 람다식이라 바로 this ㄱㅊ
//				detailOrderDialog.setVisible(true);
//			} else {	// 선택 안 된 거
//				JOptionPane.showMessageDialog(this, "대여기록을 선택하세요"); // allert 비슷한 거
//				
//			}
//		}); 
        
        // delete
        deleteButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("delete Order");
				int selectedRow = table.getSelectedRow(); // 선택된 현재 row
				if (selectedRow >= 0) { // 정상
					DeleteOrderDialog deleteOrderDialog = new DeleteOrderDialog(OrderPanel.this, model, selectedRow); // 익명객체라 this 쓰면 익명객체 나옴 -> MoviePanel.this써야 현재 화면을 가리킴
					deleteOrderDialog.setVisible(true);				
				} else {	// 선택 안 된 거
					JOptionPane.showMessageDialog(OrderPanel.this, "영화를 선택하세요"); // allert 비슷한 거
					
				}
				
			}
		});
    }

    // 테이블 데이터를 DB에서 불러오는 메서드
    public void loadOrderData() {
         model.setRowCount(0); // 기존 데이터 삭제
         List<OrderDTO> orders = orderDAO.listOrder();
         for (OrderDTO dto : orders) {
             model.addRow(new Object[]{ 
            		 dto.getOrder_id(),
            		 dto.getMovie_name(),
            		 dto.getCust_name(),
            		 dto.getCust_phone(),
            		 dto.getBorrowdate()
             });
         }
        System.out.println("대여 목록 데이터를 새로고침합니다.");
    }
}
