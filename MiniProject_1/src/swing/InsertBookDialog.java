package swing;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InsertBookDialog extends JDialog {
    
	private JTextField bookIdField, bookNameField, publisherField, priceField;
    private JButton insertButton;
    
    public InsertBookDialog(JFrame parent, DefaultTableModel tableModel) {
        setTitle("도서 등록");
        setSize(300, 200);
        setLocationRelativeTo(parent); 		// 부모에 맞게
        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
        
        // Fields
        bookIdField = new JTextField();
        bookNameField = new JTextField();
        publisherField = new JTextField();
        priceField = new JTextField();
        insertButton = new JButton("Add");
        
        // Add components
        // 왼쪽 위부터 오른쪽 그리고 아래로 순차적으로 채워 진다.
        add(new JLabel("Book ID:"));
        add(bookIdField);
        add(new JLabel("Book Name:"));
        add(bookNameField);
        add(new JLabel("Publisher:"));
        add(publisherField);
        add(new JLabel("Price:"));
        add(priceField);
        add(new JLabel());
        add(insertButton);
        
        // Add button action
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookId = bookIdField.getText();
                String bookName = bookNameField.getText();
                String publisher = publisherField.getText();
                String price = priceField.getText();
                
                tableModel.addRow(new Object[]{bookId, bookName, publisher, price});
                dispose();
            }
        });
    }
}
