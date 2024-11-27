import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Pizza class to hold details about the pizza
class Pizza {
    private String size;
    private String type;
    private String flavor;
    private double price;

    // Constructor to set pizza size, type, flavor, and price
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
        return "Pizza Size: " + size + ", Type: " + type + ", Flavor: " + flavor + ", Price: $" + price;
    }
}

// Order class to handle the current pizza order
class Order {
    private Pizza pizza;

    // Method to set the pizza in the order
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    // Method to get the pizza in the order
    public Pizza getPizza() {
        return pizza;
    }

    // Method to display the order details
    public void displayOrder() {
        if (pizza != null) {
            System.out.println("Your Order:");
            System.out.println(pizza.toString());
        } else {
            System.out.println("No order placed.");
        }
    }
}

public class PizzaOrderSystemGUI {
    private JFrame frame;
    private JTextField sizeField;
    private JTextField typeField;
    private JTextField flavorField;
    private JTextField priceField;
    private JTextArea orderArea;

    public PizzaOrderSystemGUI() {
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Pizza Order System");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Size:"));
        sizeField = new JTextField();
        inputPanel.add(sizeField);

        inputPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        inputPanel.add(typeField);

        inputPanel.add(new JLabel("Flavor:"));
        flavorField = new JTextField();
        inputPanel.add(flavorField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(new PlaceOrderListener());
        inputPanel.add(placeOrderButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetListener());
        inputPanel.add(resetButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Order area
        orderArea = new JTextArea(10, 20);
        frame.add(new JScrollPane(orderArea), BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private class PlaceOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String size = sizeField.getText();
            String type = typeField.getText();
            String flavor = flavorField.getText();
            double price = Double.parseDouble(priceField.getText());

            Pizza pizza = new Pizza(size, type, flavor, price);
            Order order = new Order();
            order.setPizza(pizza);

            orderArea.setText(order.getPizza().toString());
        }
    }

    private class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sizeField.setText("");
            typeField.setText("");
            flavorField.setText("");
            priceField.setText("");
            orderArea.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PizzaOrderSystemGUI();
            }
        });
    }
}