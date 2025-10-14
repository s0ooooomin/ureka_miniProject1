package ui.dialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import jdbc.dao.OrderDAO;
import ui.panel.OrderPanel;

public class DeleteOrderDialog extends JDialog {
    
	private JTextField movie_titleField, directorField, priceField;
    private JButton deleteButton;
    
    public DeleteOrderDialog(OrderPanel orderPanel, DefaultTableModel tableModel, int rowIndex) {
        
    	setTitle("대여기록 삭제");
        setSize(300, 200);
        setLocationRelativeTo(orderPanel); 		// 부모에 맞게
        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
        
        // Get current values
        int order_id = (int) tableModel.getValueAt(rowIndex, 0);
        
        deleteButton = new JButton("삭제");
        JOptionPane.showConfirmDialog(DeleteOrderDialog.this, "정말 삭제하시겠습니까?", "대여기록 삭제", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
        
        // Add components
        // 왼쪽 위부터 오른쪽 그리고 아래로 순차적으로 채워 진다.
        add(new JLabel());
        add(deleteButton);
        
        // Delete button action
        deleteButton.addActionListener( e -> {
        	int result = OrderDAO.deleteOrder(order_id);
        	if (result > 0) {
            	JOptionPane.showMessageDialog(DeleteOrderDialog.this, "대여 기록이 삭제되었습니다.");
            	orderPanel.loadOrderData();
            
            	dispose();
            }
        });
    }
}
