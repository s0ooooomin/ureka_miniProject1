//package ui.dialog;
//
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Date;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JTextField;
//import javax.swing.table.DefaultTableModel;
//
//import jdbc.dao.OrderDAO;
//import ui.panel.OrderPanel;
//
//public class DetailOrderDialog extends JDialog {
//    
//	private JTextField order_idField, cust_nameField, movie_nameField, borrowdateField, returndateField;
//    private JButton saveButton, cancelButton;
//    
//    public DetailOrderDialog(OrderPanel orderPanel, DefaultTableModel tableModel, int rowIndex) {
//        
//    	setTitle("대여 정보");
//        setSize(300, 200);
//        setLocationRelativeTo(orderPanel); 		// 부모에 맞게
//        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
//        
//        // values 불러오기
//        int order_id = (int) tableModel.getValueAt(rowIndex, 0);
//        String cust_name = (String) tableModel.getValueAt(rowIndex, 1);
//        String movie_name = (String) tableModel.getValueAt(rowIndex, 2);
//        
//        DateTimeFormatter format = new DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        String borrowdate = (String) tableModel.getValueAt(rowIndex, 3);
//        String returndate = (String) tableModel.getValueAt(rowIndex, 4);
//		
//        // Fields
//        order_idField = new JTextField(order_id);
//        cust_nameField = new JTextField(cust_name);
//        movie_nameField = new JTextField(movie_name);
//        borrowdateField = new JTextField(borrowdate);
//        returndateField = new JTextField(returndate);
//        
//        saveButton = new JButton("수정하기");
//        cancelButton = new JButton("취소");
//
//        
//        // Add components
//        // 왼쪽 위부터 오른쪽 그리고 아래로 순차적으로 채워 진다.
//        add(new JLabel("주문 번호:"));
//        add(order_idField);
//        add(new JLabel("고객명:"));
//        add(cust_nameField);
//        add(new JLabel("영화 제목:"));
//        add(movie_nameField);
//        add(new JLabel("borrowdate:"));
//        add(borrowdateField);
//        add(new JLabel("returndate:"));
//        add(returndateField);
//        
//        add(new JLabel());
//        add(saveButton);
//        add(new JLabel());
//        add(cancelButton);
//        
//        // Add button action
//        saveButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                LocalDate borrowdate = LocalDate.parse(borrowdateField.getText(), format);
//                LocalDate returndate = LocalDate.parse(returndateField.getText(), format);
//                
//                int result = OrderDAO.updateOrder(order_id, borrowdate, returndate);
//                if (result > 0) {
//                	JOptionPane.showMessageDialog(DetailOrderDialog.this, "날짜가 수정되었습니다.");
//                	orderPanel.loadOrderData();
//                
//                	dispose();
//                }
//            }
//        });
//        cancelButton.addActionListener(e -> dispose());
//    }
//}
