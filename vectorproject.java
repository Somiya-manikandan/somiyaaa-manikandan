import java.util.Scanner;
import java.util.Arrays;
import java.util.Vector;

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

// Order class to manage multiple pizzas
class Order {
    private Vector<Pizza> pizzas; // Vector to hold pizza objects

    public Order() {
        pizzas = new Vector<>(); // Initializes the vector
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza); // Add pizza to the vector
        System.out.println("Pizza added to the order: " + pizza);
    }

    public void displayOrder() {
        if (pizzas.isEmpty()) {
            System.out.println("No pizzas in the order.");
        } else {
            System.out.println("=== Current Order ===");
            for (int i = 0; i < pizzas.size(); i++) {
                System.out.println((i + 1) + ". " + pizzas.get(i));
            }
        }
    }

    public void generateBill() {
        if (pizzas.isEmpty()) {
            System.out.println("No pizzas in the order to generate a bill.");
        } else {
            double total = 0;
            System.out.println("\n=== Final Bill ===");
            for (int i = 0; i < pizzas.size(); i++) {
                System.out.println((i + 1) + ". " + pizzas.get(i));
                total += pizzas.get(i).getPrice();
            }
            System.out.println("-------------------");
            System.out.println("Total Price: ₹" + total);
            System.out.println("Thank you for coming to our shop!");
        }
    }
}

public class PizzaHut1  {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("====================================");
        System.out.println("       Welcome to Our Pizza Shop!   ");
        System.out.println("====================================");

        Order order = new Order(); // Order object using Vector

        // List of valid flavors
        String[] validFlavors = {
            "Paneer", "Pepperoni", "BBQ Chicken", "Veggie", "Margherita", "Mushroom", 
            "Chicken Tikka", "Hawaiian", "Sausage", "Spinach & Feta", "Four Cheese", 
            "Jalapeno & Cheese", "Peri Peri Chicken", "Corn & Capsicum", "Pesto", 
            "Alfredo", "Garlic & Herb", "Truffle", "Mediterranean", "Buffalo Chicken", 
            "Sun-Dried Tomato & Basil", "Cheddar Delight", "Spicy Chipotle", "Smokey BBQ Veg"
        };

        while (true) {
            System.out.println("\n=== Pizza Hut Management System ===");
            System.out.println("1. Place New Pizza Order");
            System.out.println("2. Display Current Order");
            System.out.println("3. Generate Final Bill and Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Pizza Size (Small/Medium/Large): ");
                    String size = scanner.nextLine();
                    if (!size.equalsIgnoreCase("Small") && 
                        !size.equalsIgnoreCase("Medium") && 
                        !size.equalsIgnoreCase("Large")) {
                        System.out.println("Invalid size! Choose Small, Medium, or Large.");
                        break;
                    }

                    System.out.print("Enter Pizza Type (Veg/Non-Veg): ");
                    String type = scanner.nextLine();
                    if (!type.equalsIgnoreCase("Veg") && !type.equalsIgnoreCase("Non-Veg")) {
                        System.out.println("Invalid type! Choose Veg or Non-Veg.");
                        break;
                    }

                    System.out.println("Available Flavors:");
                    for (int i = 0; i < validFlavors.length; i++) {
                        System.out.println((i + 1) + ". " + validFlavors[i]);
                    }

                    // Ask for flavor and validate
                    String flavor = null;
                    while (true) {
                        System.out.print("Enter Pizza Flavor: ");
                        flavor = scanner.nextLine();

                        if (Arrays.asList(validFlavors).contains(flavor)) {
                            break; // valid flavor, exit loop
                        } else {
                            System.out.println("Invalid flavor entered! Please choose a valid flavor.");
                        }
                    }

                    double flavorPrice;
                    switch (flavor) {
                        case "Paneer": flavorPrice = 50; break;
                        case "Pepperoni": flavorPrice = 70; break;
                        case "BBQ Chicken": flavorPrice = 100; break;
                        case "Veggie": flavorPrice = 30; break;
                        case "Margherita": flavorPrice = 40; break;
                        case "Mushroom": flavorPrice = 60; break;
                        case "Chicken Tikka": flavorPrice = 90; break;
                        case "Hawaiian": flavorPrice = 80; break;
                        case "Sausage": flavorPrice = 70; break;
                        case "Spinach & Feta": flavorPrice = 50; break;
                        case "Four Cheese": flavorPrice = 90; break;
                        case "Jalapeno & Cheese": flavorPrice = 60; break;
                        case "Peri Peri Chicken": flavorPrice = 100; break;
                        case "Corn & Capsicum": flavorPrice = 40; break;
                        case "Pesto": flavorPrice = 50; break;
                        case "Alfredo": flavorPrice = 60; break;
                        case "Garlic & Herb": flavorPrice = 30; break;
                        case "Truffle": flavorPrice = 120; break;
                        case "Mediterranean": flavorPrice = 80; break;
                        case "Buffalo Chicken": flavorPrice = 90; break;
                        case "Sun-Dried Tomato & Basil": flavorPrice = 70; break;
                        case "Cheddar Delight": flavorPrice = 60; break;
                        case "Spicy Chipotle": flavorPrice = 100; break;
                        case "Smokey BBQ Veg": flavorPrice = 50; break;
                        default: 
                            flavorPrice = 0;
                    }

                    double price = size.equalsIgnoreCase("Small") ? (type.equalsIgnoreCase("Veg") ? 200 : 250) :
                                  size.equalsIgnoreCase("Medium") ? (type.equalsIgnoreCase("Veg") ? 300 : 350) :
                                  (type.equalsIgnoreCase("Veg") ? 400 : 450);

                    price += flavorPrice;
                    Pizza pizza = new Pizza(size, type, flavor, price);
                    order.addPizza(pizza);
                    break;

                case 2:
                    order.displayOrder();
                    break;

                case 3:
                    order.generateBill();
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}