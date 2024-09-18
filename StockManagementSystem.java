import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class StockManagementSystem extends JFrame {

    private Map<String, Integer> stockData;

    public StockManagementSystem() {
        stockData = new HashMap<>();

        JButton addButton = new JButton("Add Stock");
        JButton sellButton = new JButton("Sell Stock");
        JButton viewButton = new JButton("View Stock");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = JOptionPane.showInputDialog("Enter item name:");
                if (itemName != null && !itemName.isEmpty()) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
                    addStock(itemName, quantity);
                    JOptionPane.showMessageDialog(null, quantity + " units of " + itemName + " added to stock.");
                }
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = JOptionPane.showInputDialog("Enter item name to sell:");
                if (itemName != null && !itemName.isEmpty()) {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity to sell:"));
                    if (sellStock(itemName, quantity)) {
                        JOptionPane.showMessageDialog(null, quantity + " units of " + itemName + " sold.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient stock for " + itemName + ".");
                    }
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea stockTextArea = new JTextArea(getStockReport());
                JOptionPane.showMessageDialog(null, new JScrollPane(stockTextArea), "Stock Report", JOptionPane.PLAIN_MESSAGE);
            }
        });

        setLayout(new FlowLayout());
        add(addButton);
        add(sellButton);
        add(viewButton);

        setTitle("Stock Management System");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addStock(String itemName, int quantity) {
        int currentStock = stockData.getOrDefault(itemName, 0);
        stockData.put(itemName, currentStock + quantity);
    }

    private boolean sellStock(String itemName, int quantity) {
        if (stockData.containsKey(itemName)) {
            int currentStock = stockData.get(itemName);
            if (currentStock >= quantity) {
                stockData.put(itemName, currentStock - quantity);
                return true;
            }
        }
        return false;
    }

    private String getStockReport() {
        StringBuilder report = new StringBuilder("Stock Report:\n");
        for (Map.Entry<String, Integer> entry : stockData.entrySet()) {
            report.append(entry.getKey()).append(": ").append(entry.getValue()).append(" units\n");
        }
        return report.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StockManagementSystem();
            }
        });
    }
}