package swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// swing 관련 (앱 ? ui)
public class BookManager extends JFrame{	// 어플리케이션 실행 시 나오는 창

	private JTable table;	// grid 형태의 테이블 UI
	private DefaultTableModel tableModel;	// JTable에서 다룰 데이터
	private JButton insertButton, detailButton, deleteButton; // Button UI
		
	private static final long serialVersionUID = 1L;
	
	// 기본 생성자
	// UI 등 초기화 수행
	public BookManager() {
		// window : JFrame 메소드들
		setTitle("도서 관리"); 
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창의 닫기 버튼 -> 종료
		
		// table
		tableModel = new DefaultTableModel( new Object[] {"Book ID", "Bookname", "Publisher", "Price"}, 0 ); // 컬럼명들
		table = new JTable(tableModel); // UI와 Data 연결 -> 화면에 표시할 table 만들기
		
		// button
		insertButton = new JButton("등록");
		detailButton = new JButton("상세");
		deleteButton = new JButton("삭제");
		
		// button panel // panel : 화면 구성 컴포넌트? 컨테이너?
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(insertButton);
		buttonPanel.add(detailButton);
		buttonPanel.add(deleteButton);
		
		// layout
		setLayout(new BorderLayout());//동서남북+center
		add(new JScrollPane(table), BorderLayout.CENTER); // 중앙에 scroll 기능을 가진 table
		add(buttonPanel, BorderLayout.SOUTH); // 하단에 button
		
		// button - click event handler
		insertButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("insert");
				InsertBookDialog insertBookDialog = new InsertBookDialog(BookManager.this, tableModel); // 익명객체라 this 쓰면 익명객체 나옴
				insertBookDialog.setVisible(true);
			
			}
			
		});
		
		detailButton.addActionListener(e -> {
			System.out.println("detail"); 
			int selectedRow = table.getSelectedRow(); // 선택된 현재 row
			if (selectedRow >= 0) { // 정상
				DetailBookDialog detailBookDialog = new DetailBookDialog(this, tableModel, selectedRow); // 람다식이라 바로 this ㄱㅊ
				detailBookDialog.setVisible(true);
			} else {	// 선택 안 된 거
				JOptionPane.showMessageDialog(this, "도서를 선택하세요"); // allert 비슷한 거
				
			}
		});
		
//		deleteButton.addActionListener( new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("delete");
//				int selectedRow = table.getSelectedRow(); // 선택된 현재 row
//				if (selectedRow >= 0) { // 정상
//					DeleteBookDialog deleteBookDialog = new DeleteBookDialog(this, tableModel, selectedRow); // 익명객체라 this 쓰면 익명객체 나옴
//					deleteBookDialog.setVisible(true);				
//				} else {	// 선택 안 된 거
//					JOptionPane.showMessageDialog(this, "도서를 선택하세요"); // allert 비슷한 거
//					
//				}
//				
//			}
//		});
		
		
		
	}
	
	
	
	

}
