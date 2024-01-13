import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Expense implements Serializable {
    private String date;
    private double amount;
    private String description;
    private String category;

    public Expense(String date, double amount, String description, String category) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }
    public String getCategory(){
        return category;
    }
    public double getAmount(){
        return amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Amount: " + amount + ", Description: " + description + ", Category: " + category;
    }
}

class ExpenseTracker implements Serializable {
    private List<Expense> expenses;

    public ExpenseTracker() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public double getCategoryTotal(String category) {
        double total = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                total += expense.getAmount();
            }
        }
        return total;
    }
}

public class ExpenseTrackerApp {
    private static ExpenseTracker expenseTracker = new ExpenseTracker();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Category-wise Summation");
            System.out.println("4. Save and Exit");

            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    categoryWiseSummation();
                    break;
                case 4:
                    saveAndExit();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void addExpense() {
        System.out.print("Enter expense date: ");
        String date = scanner.nextLine();
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter expense description: ");
        String description = scanner.nextLine();
        System.out.print("Enter expense category: ");
        String category = scanner.nextLine();

        Expense newExpense = new Expense(date, amount, description, category);
        expenseTracker.addExpense(newExpense);

        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses() {
        List<Expense> expenses = expenseTracker.getExpenses();
        System.out.println("\nExpense List:");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    private static void categoryWiseSummation() {
        System.out.print("Enter category to get summation: ");
        String category = scanner.nextLine();
        double total = expenseTracker.getCategoryTotal(category);
        System.out.println("Total expenses for category '" + category + "': " + total);
    }

    private static void saveAndExit() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("expense_tracker.dat"))) {
            oos.writeObject(expenseTracker);
            System.out.println("Expense data saved. Exiting...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
