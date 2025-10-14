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

public class DetailMovieDialog extends JDialog {
    
	private JTextField movie_titleField, directorField, priceField;
    private JButton saveButton, cancelButton;
    
    public DetailMovieDialog(MoviePanel moviePanel, DefaultTableModel tableModel, int rowIndex) {
        
    	setTitle("영화 상세");
        setSize(300, 200);
        setLocationRelativeTo(moviePanel); 		// 부모에 맞게
        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
        
        // values 불러오기
        int movie_id = (int) tableModel.getValueAt(rowIndex, 0);
        String movie_title = (String) tableModel.getValueAt(rowIndex, 1);
        String director = (String) tableModel.getValueAt(rowIndex, 2);
        int price = (int) tableModel.getValueAt(rowIndex, 3);
        
        // Fields
        movie_titleField = new JTextField(movie_title);
        directorField = new JTextField(director);
        priceField = new JTextField(price);
        
        saveButton = new JButton("추가하기");
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
        add(saveButton);
        add(new JLabel());
        add(cancelButton);
        
        // Add button action
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int price = Integer.parseInt(priceField.getText());
                
                int result = MovieDAO.updateMovie(movie_id, price);
                if (result > 0) {
                	JOptionPane.showMessageDialog(DetailMovieDialog.this, "<" + movie_title + ">\n"+ " 가격이 수정되었습니다.");
                	moviePanel.loadMovieData();
                
                	dispose();
                }
            }
        });
        cancelButton.addActionListener(e -> dispose());
    }
}
