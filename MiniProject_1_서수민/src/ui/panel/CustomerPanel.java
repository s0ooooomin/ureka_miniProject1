package ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import jdbc.dao.CustomerDAO;
import jdbc.dto.CustomerDTO;
import ui.dialog.DeleteCustomerDialog;
import ui.dialog.DetailCustomerDialog;
import ui.dialog.InsertCustomerDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import java.util.List;

public class CustomerPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private CustomerDAO customerDAO; // 실제 DAO 객체

    public CustomerPanel() {
        CustomerDAO customerDAO = new CustomerDAO();
        setLayout(new BorderLayout());

        // 테이블 모델 및 테이블 생성
        String[] columnNames = {"고객ID", "고객명", "전화번호"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton insertButton = new JButton("추가");
        JButton detailButton = new JButton("수정");        
        JButton deleteButton = new JButton("삭제");
        buttonPanel.add(insertButton);
        buttonPanel.add(detailButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        
        // 데이터 로드
        loadCustomerData();

        // --- 이벤트 리스너 ---
        
        // 1. insert
        insertButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("Customer insert");
				InsertCustomerDialog insertCustomerDialog = new InsertCustomerDialog(CustomerPanel.this, model); // 익명객체라 this 쓰면 익명객체 나옴
				insertCustomerDialog.setVisible(true);
			
			}
			
		});
        
        // 2. detail - update
        detailButton.addActionListener(e -> {
			System.out.println("Customer detail"); 
			int selectedRow = table.getSelectedRow(); // 선택된 현재 row
			if (selectedRow >= 0) { // 정상
				DetailCustomerDialog detailCustomerDialog = new DetailCustomerDialog(this, model, selectedRow); // 람다식이라 바로 this ㄱㅊ
				detailCustomerDialog.setVisible(true);
			} else {	// 선택 안 된 거
				JOptionPane.showMessageDialog(this, "고객을 선택하세요"); // allert 비슷한 거
				
			}
		});        
        // 3. delete
        deleteButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("delete Movie");
				int selectedRow = table.getSelectedRow(); // 선택된 현재 row
				if (selectedRow >= 0) { // 정상
					DeleteCustomerDialog deleteCustomerDialog = new DeleteCustomerDialog(CustomerPanel.this, model, selectedRow); // 익명객체라 this 쓰면 익명객체 나옴 -> CustomerPanel.this써야 현재 화면을 가리킴
					deleteCustomerDialog.setVisible(true);				
				} else {	// 선택 안 된 거
					JOptionPane.showMessageDialog(CustomerPanel.this, "고객을 선택하세요"); // allert 비슷한 거
					
				}
				
			}
		});
        
        
        
    }

    // 테이블 데이터를 DB에서 불러오는 메서드 (list)
    public void loadCustomerData() {
         model.setRowCount(0); // 기존 데이터 삭제
         List<CustomerDTO> customers = customerDAO.listCustomer();
         for (CustomerDTO dto : customers) {
             model.addRow(new Object[]{ 
            		 dto.getCust_id(),
            		 dto.getCust_name(),
            		 dto.getCust_phone()
             });
         }
        System.out.println("고객 목록 데이터를 새로고침합니다.");
    }
}
