package ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import jdbc.dao.OrderDAO;
import jdbc.dao.SaleDAO;
import jdbc.dto.OrderDTO;
import jdbc.dto.SaleDTO;

import java.awt.*;
import java.sql.SQLException;
// OrderDAO와 같은 DAO 클래스들을 import 해야 합니다.
import java.util.List;

public class SalePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private SaleDAO saleDAO; // 실제 DAO 객체

    public SalePanel() {
    	SaleDAO saleDAO = new SaleDAO();
        setLayout(new BorderLayout());

        // 테이블 모델 및 테이블 생성
        String[] columnNames = {"고객명", "영화제목", "금액", "반납일"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        
        // 데이터 로드
        loadSalesData();

    }

    // 테이블 데이터를 DB에서 불러오는 메서드
    public void loadSalesData() {
         model.setRowCount(0); // 기존 데이터 삭제
         List<SaleDTO> sales = SaleDAO.listSale();
         for (SaleDTO dto : sales) {
             model.addRow(new Object[]{ 
            		dto.getSale_id(),
     				dto.getMovie_id(),
     				dto.getSaleprice(),
     				dto.getReturndate()
             });
         }
        System.out.println("판매 목록 데이터를 새로고침합니다.");
    }
}
