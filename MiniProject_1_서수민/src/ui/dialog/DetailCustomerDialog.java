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

public class DetailCustomerDialog extends JDialog {
    
	private JTextField cust_nameField, cust_phoneField;
    private JButton saveButton, cancelButton;
    
    public DetailCustomerDialog(CustomerPanel customerPanel, DefaultTableModel tableModel, int rowIndex) {
        
    	setTitle("고객 상세");
        setSize(300, 200);
        setLocationRelativeTo(customerPanel); 		// 부모에 맞게
        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
        
        // values 불러오기
        int cust_id = (int) tableModel.getValueAt(rowIndex, 0);
        String cust_name = (String) tableModel.getValueAt(rowIndex, 1);
        String cust_phone = (String) tableModel.getValueAt(rowIndex, 2);
        
        // Fields - 입력은 phone만 가능
        cust_phoneField = new JTextField(cust_phone);
        
        saveButton = new JButton("추가하기");
        cancelButton = new JButton("취소");

        
        // Add components
        // phone 만 수정가능
        add(new JLabel("Customer name:"));
        add(new JLabel(cust_name));
        add(new JLabel("phone number:"));
        add(cust_phoneField);
        
        add(new JLabel());
        add(saveButton);
        add(new JLabel());
        add(cancelButton);
        
        // Add button action
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cust_phone = cust_phoneField.getText();
                
                int result = CustomerDAO.updateCustomer(cust_id, cust_phone);
                if (result > 0) {
                	JOptionPane.showMessageDialog(DetailCustomerDialog.this, cust_name + " 고객님\n"+ " 전화번호가 수정되었습니다.");
                	customerPanel.loadCustomerData();
                
                	dispose();
                }
            }
        });
        cancelButton.addActionListener(e -> dispose());
    }
}
