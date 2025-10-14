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

public class DeleteMovieDialog extends JDialog {
    
	private JTextField movie_titleField, directorField, priceField;
    private JButton deleteButton;
    
    public DeleteMovieDialog(MoviePanel moviePanel, DefaultTableModel tableModel, int rowIndex) {
        
    	setTitle("영화 삭제");
        setSize(300, 200);
        setLocationRelativeTo(moviePanel); 		// 부모에 맞게
        setLayout(new GridLayout(5, 2));	// row 5개, column 2개
        
        // Get current values
        int movie_id = (int) tableModel.getValueAt(rowIndex, 0);
        String movie_title = (String) tableModel.getValueAt(rowIndex, 1);  
        String director = (String) tableModel.getValueAt(rowIndex, 2);  
        
        // Fields
        movie_titleField = new JTextField(movie_title);
        directorField = new JTextField(director);
        deleteButton = new JButton("삭제");
        
        // Add components
        // 왼쪽 위부터 오른쪽 그리고 아래로 순차적으로 채워 진다.
        add(new JLabel("Movie Title:"));
        add(new JLabel(movie_title));
        add(new JLabel("Director:"));
        add(new JLabel(director));
        add(new JLabel());
        add(deleteButton);
        
        // Delete button action
        deleteButton.addActionListener( e -> {
        	int result = MovieDAO.deleteMovie(movie_id);
        	if (result > 0) {
            	JOptionPane.showMessageDialog(DeleteMovieDialog.this, "<" + movie_title + ">\n" + "영화가 삭제되었습니다.");
            	moviePanel.loadMovieData();
            
            	dispose();
            }
        });
    }
}
