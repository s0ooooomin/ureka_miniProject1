package ui;

import javax.swing.*;

import ui.panel.CustomerPanel;
import ui.panel.MoviePanel;
import ui.panel.OrderPanel;
import ui.panel.SalePanel;

import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private MoviePanel moviePanel;
    private OrderPanel rentalPanel;
    private CustomerPanel customerPanel;
    private SalePanel salePanel;

    public MainFrame() {
        setTitle("DVD 대여 관리 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 카드 레이아웃 패널 설정
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 각 기능별 패널 생성
        // RentalPanel이 MoviePanel의 대여 기능 실행 후 새로고침 되어야 하므로 참조를 넘겨줍니다... ?
        rentalPanel = new OrderPanel();
        moviePanel = new MoviePanel();
        customerPanel = new CustomerPanel();
        salePanel = new SalePanel();

        // 카드 패널에 각 화면 추가
        cardPanel.add(rentalPanel, "ORDERS");
        cardPanel.add(moviePanel, "MOVIES");
        cardPanel.add(customerPanel, "CUSTOMERS");
        cardPanel.add(salePanel, "SALES");

        add(cardPanel, BorderLayout.CENTER);

        // 메뉴바 설정
        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu managementMenu = new JMenu("관리");
        JMenuItem rentalItem = new JMenuItem("대여 목록");
        JMenuItem movieItem = new JMenuItem("영화 목록");
        JMenuItem customerItem = new JMenuItem("고객 목록");
        JMenuItem saleItem = new JMenuItem("판매 목록");

        managementMenu.add(rentalItem);
        managementMenu.add(movieItem);
        managementMenu.add(customerItem);
        managementMenu.add(saleItem);

        menuBar.add(managementMenu);
        setJMenuBar(menuBar);

        // 메뉴 아이템 클릭 리스너
        rentalItem.addActionListener(e -> {
            rentalPanel.loadOrderData(); // 화면 보이기 전에 데이터 새로고침
            cardLayout.show(cardPanel, "ORDERS");
        });
        movieItem.addActionListener(e -> {
            moviePanel.loadMovieData();
            cardLayout.show(cardPanel, "MOVIES");
        });
        customerItem.addActionListener(e -> {
            customerPanel.loadCustomerData();
            cardLayout.show(cardPanel, "CUSTOMERS");
        });
        saleItem.addActionListener(e -> {
            salePanel.loadSalesData();
            cardLayout.show(cardPanel, "SALES");
        });
    }
}