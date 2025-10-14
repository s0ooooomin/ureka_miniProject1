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

public class InsertCustomerDialog extends JDialog {
    
	private JTextField cust_nameField, cust_phoneField;
    private JButton insertButton, cancelButton;
    
    public InsertCustomerDialog(CustomerPanel customerPanel, DefaultTableModel tableModel) {
        setTitle("영화 등록");
        setSize(300, 200);
        setLocationRelativeTo(customerPanel); 		// 부모에 맞게
        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
        
        // Fields
        cust_nameField = new JTextField();
        cust_phoneField = new JTextField();
        insertButton = new JButton("추가하기");
        cancelButton = new JButton("취소");

        
        // Add components
        // 왼쪽 위부터 오른쪽 그리고 아래로 순차적으로 채워 진다.
        add(new JLabel("Customer name:"));
        add(cust_nameField);
        add(new JLabel("phone number:"));
        add(cust_phoneField);
        
        add(new JLabel());
        add(insertButton);
        add(new JLabel());
        add(cancelButton);
        
        // Add button action
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cust_name = cust_nameField.getText();
                String cust_phone = cust_phoneField.getText();
                
                int result = CustomerDAO.insertCustomer(cust_name, cust_phone);
                if (result > 0) {
                	JOptionPane.showMessageDialog(InsertCustomerDialog.this, "고객이 추가되었습니다.");
                	customerPanel.loadCustomerData();
                
                	dispose();
                }
            }
        });
        cancelButton.addActionListener(e -> dispose());
    }
}
