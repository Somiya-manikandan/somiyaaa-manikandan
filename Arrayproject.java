import java.util.Scanner;
import java.util.HashMap;

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
        System.out.println("Pizza added to the order: " + pizza);
    }

    // Method to display the current pizza order
    public void displayOrder() {
        if (pizza != null) {
            System.out.println("Current Order: " + pizza);
        } else {
            System.out.println("No pizza in the order.");
        }
    }

    // Method to calculate the total price
    public void calculateTotal() {
        if (pizza != null) {
            System.out.println("Total Price: $" + pizza.getPrice());
        } else {
            System.out.println("No pizza in the order to calculate the total.");
        }
    }
}

// Main class to run the Pizza Hut management system
public class PizzaHutManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Order order = new Order();

        // HashMap to store flavors and their additional prices
        HashMap<String, Double> flavorPriceMap = new HashMap<>();
        flavorPriceMap.put("Paneer", 2.00);
        flavorPriceMap.put("Pepperoni", 3.00);
        flavorPriceMap.put("BBQ Chicken", 3.50);
        flavorPriceMap.put("Veggie", 1.50);

        while (true) {
            System.out.println("\n=== Pizza Hut Management System ===");
            System.out.println("1. Place New Pizza Order");
            System.out.println("2. Display Current Order");
            System.out.println("3. Calculate Total Price");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Take pizza order details from the user
                    System.out.print("Enter Pizza Size (Small/Medium/Large): ");
                    String size = scanner.nextLine();
                    System.out.print("Enter Pizza Type (Veg/Non-Veg): ");
                    String type = scanner.nextLine();

                    // Take flavor from the user
                    System.out.print("Enter Pizza Flavor (Paneer/Pepperoni/BBQ Chicken/Veggie): ");
                    String flavor = scanner.nextLine();
                    double price;

                    // Set base price based on size and type
                    if (size.equalsIgnoreCase("Small")) {
                        price = type.equalsIgnoreCase("Veg") ? 5.00 : 6.00;
                    } else if (size.equalsIgnoreCase("Medium")) {
                        price = type.equalsIgnoreCase("Veg") ? 7.00 : 8.00;
                    } else {
                        price = type.equalsIgnoreCase("Veg") ? 9.00 : 10.00;
                    }

                    // Add flavor price from the map
                    if (flavorPriceMap.containsKey(flavor)) {
                        price += flavorPriceMap.get(flavor);
                    } else {
                        System.out.println("Invalid flavor! Defaulting to Veggie.");
                        price += flavorPriceMap.get("Veggie");
                        flavor = "Veggie";
                    }

                    Pizza pizza = new Pizza(size, type, flavor, price);
                    order.setPizza(pizza);
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