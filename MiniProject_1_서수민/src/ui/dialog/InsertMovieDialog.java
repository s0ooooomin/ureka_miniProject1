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

public class InsertMovieDialog extends JDialog {
    
	private JTextField movie_titleField, directorField, priceField;
    private JButton insertButton, cancelButton;
    
    public InsertMovieDialog(MoviePanel moviePanel, DefaultTableModel tableModel) {
        setTitle("영화 등록");
        setSize(300, 200);
        setLocationRelativeTo(moviePanel); 		// 부모에 맞게
        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
        
        // Fields
        movie_titleField = new JTextField();
        directorField = new JTextField();
        priceField = new JTextField();
        insertButton = new JButton("추가하기");
        cancelButton = new JButton("취소");

        
        // Add components
        // 왼쪽 위부터 오른쪽 그리고 아래로 순차적으로 채워 진다.
        add(new JLabel("Movie Title:"));
        add(movie_titleField);
        add(new JLabel("Director:"));
        add(directorField);
        add(new JLabel("Price:"));
        add(priceField);
        add(new JLabel());
        add(insertButton);
        add(new JLabel());
        add(cancelButton);
        
        // Add button action
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String movie_title = movie_titleField.getText();
                String director = directorField.getText();
                int price = Integer.parseInt(priceField.getText());
                
                int result = MovieDAO.insertMovie(movie_title, director, price);
                if (result > 0) {
                	JOptionPane.showMessageDialog(InsertMovieDialog.this, "영화가 추가되었습니다.");
                	moviePanel.loadMovieData();
                
                	dispose();
                }
            }
        });
        cancelButton.addActionListener(e -> dispose());
    }
}
