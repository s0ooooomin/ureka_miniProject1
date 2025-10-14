package ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import jdbc.dao.MovieDAO;
import jdbc.dto.MovieDTO;
import ui.dialog.BorrowMovieDialog;
import ui.dialog.DeleteMovieDialog;
import ui.dialog.DetailMovieDialog;
import ui.dialog.InsertMovieDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import java.util.List;

public class MoviePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private MovieDAO movieDAO; // 실제 DAO 객체

    public MoviePanel() {
        MovieDAO movieDAO = new MovieDAO();
        setLayout(new BorderLayout());

        // 검색
        Dimension textFieldSize = new Dimension(400,30);
        JTextField searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);
        
        JButton searchButton = new JButton("검색");
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("영화 검색"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);
        
        // 테이블 모델 및 테이블 생성
        String[] columnNames = {"영화ID", "영화제목", "감독", "일일대여비"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        
        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton insertButton = new JButton("추가");
        JButton detailButton = new JButton("가격 수정");        
        JButton deleteButton = new JButton("삭제");
        JButton borrowButton = new JButton("대여하기");
        buttonPanel.add(insertButton);
        buttonPanel.add(detailButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(borrowButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        
        // 데이터 로드
        loadMovieData();

        // --- 이벤트 리스너 ---

        // 0. search
        searchButton.addActionListener( e -> {
        	String searchWord = searchWordField.getText();
        	if ( !searchWord.isBlank() ) {
        		loadMovieData(searchWord);
        	} else {
        		JOptionPane.showMessageDialog(this, "검색어를 입력하세요");
        		loadMovieData();
        	}
        	
        });
        
        
        // 1. insert
        insertButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("Movie insert");
				InsertMovieDialog insertMovieDialog = new InsertMovieDialog(MoviePanel.this, model); // 익명객체라 this 쓰면 익명객체 나옴
				insertMovieDialog.setVisible(true);
			
			}
			
		});
        
        // 2. detail - update
        detailButton.addActionListener(e -> {
			System.out.println("Movie detail"); 
			int selectedRow = table.getSelectedRow(); // 선택된 현재 row
			if (selectedRow >= 0) { // 정상
				DetailMovieDialog detailMovieDialog = new DetailMovieDialog(this, model, selectedRow); // 람다식이라 바로 this ㄱㅊ
				detailMovieDialog.setVisible(true);
			} else {	// 선택 안 된 거
				JOptionPane.showMessageDialog(this, "영화를 선택하세요"); // allert 비슷한 거
				
			}
		});        
        // 3. delete
        deleteButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("delete Movie");
				int selectedRow = table.getSelectedRow(); // 선택된 현재 row
				if (selectedRow >= 0) { // 정상
					DeleteMovieDialog deleteMovieDialog = new DeleteMovieDialog(MoviePanel.this, model, selectedRow); // 익명객체라 this 쓰면 익명객체 나옴 -> MoviePanel.this써야 현재 화면을 가리킴
					deleteMovieDialog.setVisible(true);				
				} else {	// 선택 안 된 거
					JOptionPane.showMessageDialog(MoviePanel.this, "영화를 선택하세요"); // allert 비슷한 거
					
				}
				
			}
		});
        
        
        // 4. borrow
        borrowButton.addActionListener( e -> {
		
			System.out.println("borrow Movie");
			int selectedRow = table.getSelectedRow(); // 선택된 현재 row
			if (selectedRow >= 0) { // 정상
				
				BorrowMovieDialog borrowMovieDialog = new BorrowMovieDialog(this, model, selectedRow); 
				borrowMovieDialog.setVisible(true);				
			} else {	// 선택 안 된 거
				JOptionPane.showMessageDialog(MoviePanel.this, "대여할 영화를 선택하세요"); // allert 비슷한 거
				
			}
				
			
		});
        
        
    }

    // 테이블 데이터를 DB에서 불러오는 메서드
    public void loadMovieData() {
         model.setRowCount(0); // 기존 데이터 삭제
         List<MovieDTO> movies = movieDAO.listMovie();
         for (MovieDTO dto : movies) {
             model.addRow(new Object[]{ 
            		 dto.getMovie_id(),
            		 dto.getMovie_title(),
            		 dto.getDirector(),
            		 dto.getPrice()
             });
         }
        System.out.println("영화 목록 데이터를 새로고침합니다."); // (실제 DB 연동 코드 필요)
    }
    // 검색어 입력 시
    public void loadMovieData(String searchWord) {
    	model.setRowCount(0); // 기존 데이터 삭제
    	List<MovieDTO> movies = movieDAO.listMovie(searchWord);
    	for (MovieDTO dto : movies) {
    		model.addRow(new Object[]{ 
    				dto.getMovie_id(),
    				dto.getMovie_title(),
    				dto.getDirector(),
    				dto.getPrice()
    		});
    	}
    	System.out.println("영화 목록 데이터를 새로고침합니다."); // (실제 DB 연동 코드 필요)
    }
}
