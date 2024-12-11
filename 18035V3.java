import java.sql.*;
import java.util.Scanner;

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

            // Get the generated pizza ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int pizzaId = generatedKeys.getInt(1);
                System.out.println("Pizza added to the order: " + pizza);

                // Insert the pizza into the order
                String insertOrderSQL = "INSERT INTO orders (pizza_id, order_id) VALUES (?, 1)"; // Assume 1 is the order ID for simplicity
                try (PreparedStatement orderStmt = connection.prepareStatement(insertOrderSQL)) {
                    orderStmt.setInt(1, pizzaId);
                    orderStmt.executeUpdate();
                }
            }
        }
    }

    public void displayOrder() throws SQLException {
        String query = "SELECT * FROM pizzas INNER JOIN orders ON pizzas.id = orders.pizza_id WHERE orders.order_id = 1";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("No pizzas in the order.");
                return;
            }
            System.out.println("=== Current Order ===");
            while (rs.next()) {
                String size = rs.getString("size");
                String type = rs.getString("type");
                String flavor = rs.getString("flavor");
                double price = rs.getDouble("price");
                System.out.println("Pizza Size: " + size + ", Type: " + type + ", Flavor: " + flavor + ", Price: ₹" + price);
            }
        }
    }

    public void calculateTotal() throws SQLException {
        String query = "SELECT SUM(price) AS total FROM pizzas INNER JOIN orders ON pizzas.id = orders.pizza_id WHERE orders.order_id = 1";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                double total = rs.getDouble("total");
                System.out.println("Total Price: ₹" + total);
            }
        }
    }
}

public class PizzaHut {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        // Database connection details
        String url = "jdbc:mysql://localhost:3307/pizza_hut";
        String user = "root";
        String password = "";

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(url, user, password);
            Order order = new Order(connection);

            while (true) {
                System.out.println("\n=== Pizza Hut Management System ===");
                System.out.println("1. Place New Pizza Order");
                System.out.println("2. Display Current Order");
                System.out.println("3. Calculate Total Price");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Take pizza order details from the user
                        System.out.print("Enter Pizza Size (Small/Medium/Large): ");
                        String size = scanner.nextLine();

                        // Validate size
                        if (!size.equalsIgnoreCase("Small") && 
                            !size.equalsIgnoreCase("Medium") && 
                            !size.equalsIgnoreCase("Large")) {
                            System.out.println("Invalid size entered! Please choose Small, Medium, or Large.");
                            break; // Exit this iteration
                        }

                        System.out.print("Enter Pizza Type (Veg/Non-Veg): ");
                        String type = scanner.nextLine();

                        System.out.print("Enter Pizza Flavor (Paneer/Pepperoni/BBQ Chicken/Veggie): ");
                        String flavor = scanner.nextLine();

                        // Validate flavor
                        if (!flavor.equalsIgnoreCase("Paneer") && 
                            !flavor.equalsIgnoreCase("Pepperoni") && 
                            !flavor.equalsIgnoreCase("BBQ Chicken") && 
                            !flavor.equalsIgnoreCase("Veggie")) {
                            System.out.println("Invalid flavor entered! Please choose Paneer, Pepperoni, BBQ Chicken, or Veggie.");
                            break; // Exit this iteration
                        }

                        // Set base price based on size and type
                        double price = 0;
                        if (size.equalsIgnoreCase("Small")) {
                            price = type.equalsIgnoreCase("Veg") ? 200 : 250;
                        } else if (size.equalsIgnoreCase("Medium")) {
                            price = type.equalsIgnoreCase("Veg") ? 300 : 350;
                        } else if (size.equalsIgnoreCase("Large")) {
                            price = type.equalsIgnoreCase("Veg") ? 400 : 450;
                        }

                        // Add flavor price
                        if (flavor.equalsIgnoreCase("Paneer")) {
                            price += 50;
                        } else if (flavor.equalsIgnoreCase("Pepperoni")) {
                            price += 70;
                        } else if (flavor.equalsIgnoreCase("BBQ Chicken")) {
                            price += 100;
                        } else if (flavor.equalsIgnoreCase("Veggie")) {
                            price += 30;
                        }

                        // Create a new pizza and add it to the order
                        Pizza pizza = new Pizza(size, type, flavor, price);
                        order.addPizza(pizza);
                        break;

                    case 2:
                        // Display the current pizza order
                        order.displayOrder();
                        break;

                    case 3:
                        // Calculate the total price of the order
                        order.calculateTotal();
                        break;

                    case 4:
                        // Exit the program
                        System.out.println("Exiting the system...");
                        connection.close();
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}