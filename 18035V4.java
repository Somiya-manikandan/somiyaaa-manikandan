import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

// Pizza class to hold details about the pizza
class Pizza {
    private String size;
    private String type;
    private String flavor;
    private double price;

    public Pizza(String size, String type, String flavor, double price) {
        this.size = size;
        this.type = type;
        this.flavor = flavor;
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getFlavor() {
        return flavor;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return "Pizza Size: " + size + ", Type: " + type + ", Flavor: " + flavor + ", Price: ₹" + price;
    }
}

// Order class to manage pizzas and database operations
class Order {
    private Connection connection;

    public Order(Connection connection) {
        this.connection = connection;
    }

    public void addPizza(Pizza pizza) throws SQLException {
        String insertPizzaSQL = "INSERT INTO pizzas (size, type, flavor, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertPizzaSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pizza.getSize());
            stmt.setString(2, pizza.getType());
            stmt.setString(3, pizza.getFlavor());
            stmt.setDouble(4, pizza.getPrice());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int pizzaId = generatedKeys.getInt(1);

                String insertOrderSQL = "INSERT INTO orders (pizza_id, order_id) VALUES (?, 1)"; // Order ID is fixed as 1
                try (PreparedStatement orderStmt = connection.prepareStatement(insertOrderSQL)) {
                    orderStmt.setInt(1, pizzaId);
                    orderStmt.executeUpdate();
                }
            }
        }
    }

    public String displayOrder() throws SQLException {
        StringBuilder orderDetails = new StringBuilder("=== Current Order ===\n");
        String query = "SELECT * FROM pizzas INNER JOIN orders ON pizzas.id = orders.pizza_id WHERE orders.order_id = 1";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (!rs.isBeforeFirst()) {
                return "No pizzas in the order.";
            }
            while (rs.next()) {
                String size = rs.getString("size");
                String type = rs.getString("type");
                String flavor = rs.getString("flavor");
                double price = rs.getDouble("price");
                orderDetails.append("Pizza Size: ").append(size)
                        .append(", Type: ").append(type)
                        .append(", Flavor: ").append(flavor)
                        .append(", Price: ₹").append(price).append("\n");
            }
        }
        return orderDetails.toString();
    }

    public double calculateTotal() throws SQLException {
        String query = "SELECT SUM(price) AS total FROM pizzas INNER JOIN orders ON pizzas.id = orders.pizza_id WHERE orders.order_id = 1";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
}

// GUI Application
public class PizzaOrderSystem {
    private JFrame frame;
    private JTextField sizeField;
    private JTextField typeField;
    private JTextField flavorField;
    private JTextField priceField;
    private JTextArea orderArea;
    private Connection connection;
    private Order order;

    public PizzaOrderSystem() {
        setupDatabase();
        order = new Order(connection);
        createGUI();
    }

    private void setupDatabase() {
        try {
            String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306";
            String user = "sql12752978";
            String password = "sDEIADXG5a";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void createGUI() {
        frame = new JFrame("Pizza Order System");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Size (Small/Medium/Large):"));
        sizeField = new JTextField();
        inputPanel.add(sizeField);

        inputPanel.add(new JLabel("Type (Veg/Non-Veg):"));
        typeField = new JTextField();
        inputPanel.add(typeField);

        inputPanel.add(new JLabel("Flavor (Paneer/Pepperoni/BBQ Chicken/Veggie):"));
        flavorField = new JTextField();
        inputPanel.add(flavorField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(new PlaceOrderListener());
        inputPanel.add(placeOrderButton);

        JButton displayOrderButton = new JButton("Display Order");
        displayOrderButton.addActionListener(new DisplayOrderListener());
        inputPanel.add(displayOrderButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        orderArea = new JTextArea(10, 30);
        frame.add(new JScrollPane(orderArea), BorderLayout.CENTER);

        JButton calculateTotalButton = new JButton("Calculate Total");
        calculateTotalButton.addActionListener(new CalculateTotalListener());
        frame.add(calculateTotalButton, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private class PlaceOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String size = sizeField.getText();
                String type = typeField.getText();
                String flavor = flavorField.getText();
                double price = Double.parseDouble(priceField.getText());

                Pizza pizza = new Pizza(size, type, flavor, price);
                order.addPizza(pizza);

                JOptionPane.showMessageDialog(frame, "Pizza added to the order!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to add pizza. Check inputs!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DisplayOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String orderDetails = order.displayOrder();
                orderArea.setText(orderDetails);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to display order!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class CalculateTotalListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double total = order.calculateTotal();
                JOptionPane.showMessageDialog(frame, "Total Price: ₹" + total, "Total", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to calculate total!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PizzaOrderSystem::new);
    }
}