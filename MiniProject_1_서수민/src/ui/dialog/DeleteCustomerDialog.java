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

import jdbc.dao.CustomerDAO;
import ui.panel.CustomerPanel;

public class DeleteCustomerDialog extends JDialog {
    
	private JTextField movie_titleField, directorField, priceField;
    private JButton deleteButton;
    
    public DeleteCustomerDialog(CustomerPanel customerPanel, DefaultTableModel tableModel, int rowIndex) {
        
    	setTitle("고객 삭제");
        setSize(300, 200);
        setLocationRelativeTo(customerPanel); 		// 부모에 맞게
        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
        
        // Get current values
        int cust_id = (int) tableModel.getValueAt(rowIndex, 0);
        String cust_name = (String) tableModel.getValueAt(rowIndex, 1);  
        
        // Fields
        deleteButton = new JButton("삭제");
        
        // Add components
        // 왼쪽 위부터 오른쪽 그리고 아래로 순차적으로 채워 진다.
        add(new JLabel("고객명 "));
        add(new JLabel(cust_name));
        add(new JLabel());
        add(deleteButton);
        
        // Delete button action
        deleteButton.addActionListener( e -> {
        	int result = CustomerDAO.deleteCustomer(cust_id);
        	if (result > 0) {
            	JOptionPane.showMessageDialog(DeleteCustomerDialog.this, cust_name + "고객의 정보가\n삭제되었습니다.");
            	customerPanel.loadCustomerData();
            
            	dispose();
            }
        });
    }
}
