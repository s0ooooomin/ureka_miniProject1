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

import jdbc.dao.MovieDAO;
import ui.panel.MoviePanel;

public class BorrowMovieDialog extends JDialog {
    
	private JTextField cust_nameField, cust_phoneField;
    private JButton insertButton, cancelButton;
    
    public BorrowMovieDialog(MoviePanel moviePanel, DefaultTableModel tableModel, int rowIndex) {
        setTitle("대여 등록");
        setSize(300, 200);
        setLocationRelativeTo(moviePanel); 		// 부모에 맞게
        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
        
        // values 불러오기
        int movie_id = (int) tableModel.getValueAt(rowIndex, 0);
        String movie_title = (String) tableModel.getValueAt(rowIndex, 1);
        
        
        // Fields
        cust_nameField = new JTextField();
        cust_phoneField = new JTextField();
        insertButton = new JButton("대여하기");
        cancelButton = new JButton("취소");

        
        // Add components
        // 왼쪽 위부터 오른쪽 그리고 아래로 순차적으로 채워 진다.
        add(new JLabel("이름: "));
        add(cust_nameField);
        add(new JLabel("전화번호:"));
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
            	int movie_id = (int) tableModel.getValueAt(rowIndex, 0);
            	String movie_title = (String) tableModel.getValueAt(rowIndex, 1);
				
//				String cust_name = JOptionPane.showInputDialog(this, "이름을 입력하세요 : ");
				if (cust_name == null) JOptionPane.showMessageDialog(BorrowMovieDialog.this, "이름를 입력하시오");
				
//				String cust_phone = JOptionPane.showInputDialog(this, "전화번호를 입력하세요 (예시:010-0000-0000) \n: ");
				if (cust_phone == null) JOptionPane.showMessageDialog(BorrowMovieDialog.this, "전화번호를 입력하시오");
				
				int success = MovieDAO.borrowMovie(cust_name, cust_phone, movie_id);
				
				if (success >= 0) {
					JOptionPane.showMessageDialog(BorrowMovieDialog.this, "<"+movie_title+">\n대여되었습니다.");
					dispose();
				} else {
					JOptionPane.showMessageDialog(BorrowMovieDialog.this, "대여 처리 중 오류가 발생했습니다.");
				}
            	
            }
        });
        cancelButton.addActionListener(e -> dispose());
    }
}
