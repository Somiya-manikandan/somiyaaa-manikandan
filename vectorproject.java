import java.util.Scanner;
import java.util.Vector;

// Pizza class
class Pizza {
    private String size;
    private String type;
    private double price;

    public Pizza(String size, String type, double price) {
        this.size = size;
        this.type = type;
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return "Pizza Size: " + size + ", Type: " + type + ", Price: $" + price;
    }
}

// Order class to handle the current pizza order
class Order {
    private Vector<Pizza> pizzas;

    public Order() {
        pizzas = new Vector<>();
    }

    // Method to add pizza to the order
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
        System.out.println("Pizza added to the order: " + pizza);
    }

    // Method to display the current pizza order
    public void displayOrder() {
        if (pizzas.isEmpty()) {
            System.out.println("No pizzas in the order.");
        } else {
            System.out.println("Current Order:");
            for (int i = 0; i < pizzas.size(); i++) {
                System.out.println((i + 1) + ". " + pizzas.get(i));
            }
        }
    }

    // Method to calculate the total price of the order
    public void calculateTotal() {
        if (pizzas.isEmpty()) {
            System.out.println("No pizzas in the order to calculate the total.");
        } else {
            double total = 0;
            for (Pizza pizza : pizzas) {
                total += pizza.getPrice();
            }
            System.out.println("Total Price: $" + total);
        }
    }
}

// Main class to test the Pizza Hut management system
public class PizzaHutManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Order order = new Order();

        while (true) {
            System.out.println("\nPizza Hut Management System");
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
                    System.out.print("Enter Pizza Type (Veg/Non-Veg): ");
                    String type = scanner.nextLine();
                    double price;

                    // Set price based on size and type (for simplicity)
                    if (size.equalsIgnoreCase("Small")) {
                        price = type.equalsIgnoreCase("Veg") ? 5.00 : 6.00;
                    } else if (size.equalsIgnoreCase("Medium")) {
                        price = type.equalsIgnoreCase("Veg") ? 7.00 : 8.00;
                    } else {
                        price = type.equalsIgnoreCase("Veg") ? 9.00 : 10.00;
                    }

                    Pizza pizza = new Pizza(size, type, price);
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
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}