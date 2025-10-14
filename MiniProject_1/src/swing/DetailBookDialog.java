package swing;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DetailBookDialog extends JDialog {
    
	private JTextField bookIdField, bookNameField, publisherField, priceField;
    private JButton saveButton;
    
    public DetailBookDialog(JFrame parent, DefaultTableModel tableModel, int rowIndex) {
        
    	setTitle("도서 상세");
        setSize(300, 200);
        setLocationRelativeTo(parent); // 부모에 맞게
        setLayout(new GridLayout(5, 2));
        
        // Get current values
        String bookId = (String) tableModel.getValueAt(rowIndex, 0);
        String bookName = (String) tableModel.getValueAt(rowIndex, 1);
        String publisher = (String) tableModel.getValueAt(rowIndex, 2);
        String price = (String) tableModel.getValueAt(rowIndex, 3);
        
        // Fields
        bookIdField = new JTextField(bookId);
        bookNameField = new JTextField(bookName);
        publisherField = new JTextField(publisher);
        priceField = new JTextField(price);
        saveButton = new JButton("저장");
        
        // Add components
        add(new JLabel("Book ID:"));
        add(bookIdField);
        add(new JLabel("Book Name:"));
        add(bookNameField);
        add(new JLabel("Publisher:"));
        add(publisherField);
        add(new JLabel("Price:"));
        add(priceField);
        add(new JLabel());
        add(saveButton);
        
        // Save button action
        // using lambda
        saveButton.addActionListener( e -> {
            tableModel.setValueAt(bookIdField.getText(), rowIndex, 0);
            tableModel.setValueAt(bookNameField.getText(), rowIndex, 1);
            tableModel.setValueAt(publisherField.getText(), rowIndex, 2);
            tableModel.setValueAt(priceField.getText(), rowIndex, 3);
            dispose();
        });
    }
}
