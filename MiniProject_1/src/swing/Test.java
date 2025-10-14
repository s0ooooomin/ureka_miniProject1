package swing;

import javax.swing.SwingUtilities;

// main메소드
public class Test {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater( () -> {
			new BookManager().setVisible(true); // BookManager 객체를 화면에 보이게
		});
	}

}
