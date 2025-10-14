package swing;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DeleteBookDialog extends JDialog {
    
	private JTextField bookIdField, bookNameField, publisherField, priceField;
    private JButton deleteButton;
    
    public DeleteBookDialog(JFrame parent, DefaultTableModel tableModel, int rowIndex) {
        
    	setTitle("도서 삭제");
        setSize(300, 200);
        setLocationRelativeTo(parent); // 부모에 맞게
        setLayout(new GridLayout(5, 2));
        
        // Get current values
        String bookName = (String) tableModel.getValueAt(rowIndex, 1);        
        // Fields
        bookNameField = new JTextField(bookName);

        deleteButton = new JButton("삭제");
        
        // Add components

        add(new JLabel("Book Name:"));
        add(bookNameField);
        add(new JLabel());
        add(deleteButton);
        
        // Save button action
        // using lambda
        deleteButton.addActionListener( e -> {
        	tableModel.fireTableRowsDeleted(rowIndex, rowIndex);
            dispose();
        });
    }
}
