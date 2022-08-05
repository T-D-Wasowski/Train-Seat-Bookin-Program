package trainseatbookingporgram;

 /*
 * @author T. Dawid Wasowski - w1684891
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TrainSeatBookingPorgram {
    
    static final int SEATING_CAPACITY = 8;
    
    public static void main(String[] args) {
        String[] train = initialise();
        entryParagraph();
        printMenu();
        menu(train);       
    }
    
    //--- Program Methods --------------------------------------------------------------------------
    
    //Creates the train array & fills it with "e"
    private static String[] initialise(){
        
        String[] train = new String[SEATING_CAPACITY];       
        
        for (int i = 0; i < SEATING_CAPACITY; i++) {
            train[i] = "e";
        }
        
        return train;      
    }
    
    //Menu interface for user interaction
    private static void menu(String[] train) {

        Scanner scanner = new Scanner(System.in);
        
        String command;
        String customer;
        String file;
        int seat;
        
        do {
            
            command = collectInputStr(scanner, "Enter command: ").toLowerCase();
            
            switch(command) {
                
                case "v":   
                    viewAllSeats(train);
                    break;
                    
                case "a":
                    //TODO create a method for this  
                    try {
                        customer = collectInputStr(scanner, "Please enter the passenger's name: ");
                        seat = collectInputInt(scanner, "Please enter the seat (1-8) for this given"
                                + " passenger (Enter 0 for automatic assignment): ");

                        if (seat >= 1 && seat <= 8) {
                            addCustomerToSeat(train, customer, seat);
                        } else if (seat == 0) {
                            addCustomerToSeat(train, customer);
                        } else {
                            System.out.println("Invalid seat number.");
                        }
                    } catch (Exception e) {
                        System.out.println("You have entered an invalid input.");
                        scanner.nextLine();
                    }
                    
                    break;
                    
                case "e":
                    viewEmptySeats(train);
                    break;
                    
                case "q":
                    System.out.println("You've quit the program.");
                    break;
                    
                case "d":
                    //TODO again, create a method that works both for this and add
                    try {
                        seat = collectInputInt(scanner, "Please enter the seat (1-8) from which "
                                + "you would like to remove the passenger (Enter 0 to remove the "
                                + "passenger by name instead): ");

                        if (seat >= 1 && seat <=8) {
                            deleteCustomerFromSeat(train, seat);
                        } else if (seat == 0) {
                            customer = collectInputStr(scanner, 
                                    "Please enter the passenger's name: ");
                            deleteCustomerFromSeat(train, customer);
                        } else {
                            System.out.println("Invalid seat number.");
                        }
                    } catch (Exception e) {
                        System.out.println("You have entered an invalid input.");
                        scanner.nextLine();
                    }
                    
                    break;
                    
                case "f":
                    customer = collectInputStr(scanner, "Please enter the passenger's name: ");
                    findCustomerSeat(train, customer);
                    break;
                    
                case "o":
                    viewOrderedSeats(train);
                    break;
                    
                case "t":
                    test(train);
                    break;
                    
                case "s":
                    file = collectInputStr(scanner, "Please enter the name under which your "
                            + "file will be saved: ");
                    saveToFile(train, file);
                    break;
                    
                case "l":
                    file = collectInputStr(scanner, "Please enter the name of the file you would "
                            + "like to load: ");
                    loadFromFile(train, file);
                    break;
                    
                case "p":
                    printMenu();
                    break;
                    
                case "c":
                    clearTrain(train);
                    break;
                    
                default:
                    System.out.println("Incorrect command.");        
            }    
            
        } while (!command.equals("q"));
    }
    
    //Function to output text and collect user response as string
    private static String collectInputStr(Scanner scanner, String output) {      
        System.out.print(output);
        String input = scanner.nextLine();

        return input;
    }
       
    //Similar to above, but collects user response as int
    private static int collectInputInt(Scanner scanner, String output) {
        
        System.out.print(output);
        int input = scanner.nextInt();
        scanner.nextLine();

        return input;       
    }
    
    //Printed at the beginning of the program, made into method for ease of editting
    private static void entryParagraph() {
        
        System.out.println("Welcome to the Train Seat Booking system!\nPlease use the commands "
                + "below to manage the seats on the current train.");
        
    }
    
    //Prints the available menu commands for the user
    private static void printMenu() {
        
        System.out.println("Available commands:\n"
                + "  A - Add customer to seat\n"
                + "  D - Delete customer from seat\n"
                + "  C - Clear train (Makes all seats empty)\n"
                + "  V - View all seats\n"
                + "  E - View empty seats\n"
                + "  O - View seats ordered by customer name\n"
                + "  F - Find customer's seat\n"
                + "  S - Save train data to file\n"
                + "  L - Load train data from file\n"
                + "  P - Print menu commands\n"
                + "  T - Load test data\n"
                + "  Q - Quit program");        
    }
    
    //Below just serves testing purposes until the save/load feature is implemented
    private static void test(String[] train) {       
        train[0] = "Steve";
        train[1] = "Mary";
        train[2] = "David";
        train[3] = "John";
        train[4] = "Robert"; 
        train[5] = "Yerobe";
        train[6] = "Anika";
        train[7] = "George"; 
        
        System.out.println("Test data has been loaded.");
    }
    
    //--- Functionality methods --------------------------------------------------------------------
    
    //Display contents of array
    private static void viewAllSeats(String[] train) {
        
        for (int i = 0; i < SEATING_CAPACITY; i++) {
            System.out.println("Seat " + (i+1) + ": " + train[i]);
        }       
    }
    
    //Automatic assignment
    private static void addCustomerToSeat(String[] train, String customer) {    
        
        for (int i = 0; i < SEATING_CAPACITY; i++) {
            if (train[i].equals("e")) {
                train[i] = customer;
                System.out.println(customer + " has been allocated to seat " + (i+1) + ".");
                break;
            }  else if (i == (SEATING_CAPACITY-1)) {
                System.out.println("The train is fully booked, there are no more seats!"); 
            }        
        }         
    }
    
    //Overload of method above - specific assignment
    private static void addCustomerToSeat(String[] train, String customer, int seat) {
        
        int i = seat-1; //Translates seat to index (i.e. seat 1 assigned to index 0)
        
        if (train[i].equals("e")) {
            train[i] = customer;
            System.out.println(customer + " has been assigned to seat " + seat + ".");
        } else {
            System.out.println("This seat is already occupied by " + train[i] + ".");
        }
    }
    
    //Views all empty seats on the train
    private static void viewEmptySeats(String[] train) {
        
        int count = 0;
        
        for (int i = 0; i < SEATING_CAPACITY; i++) {
            if (train[i].equals("e")) {
                System.out.println("Seat " + (i+1) + ": " + train[i]);
                count++;
            }
        } 
        
        if (count > 0) {
                System.out.println("There are " + count + " empty seats remaining.");
        } else {
            System.out.println("There are no empty seats remaining.");
        }       
    }
  
    //Deletes customer based on the customer name
    private static void deleteCustomerFromSeat(String[] train, String customer) {
        
        for (int i = 0; i < SEATING_CAPACITY; i++) {
            if (train[i].equals(customer)) {
                train[i] = "e";
                System.out.println(customer + " has been removed from seat "
                + (i+1) + ".");
                break;
            } else if (i == (SEATING_CAPACITY-1)) {
                System.out.println(customer + " cannot be removed as they do not currently "
                        + "occupy any seat.");
            }
        }   
    }

    //Overload of the above - deletes based on seat number
    private static void deleteCustomerFromSeat(String[] train, int seat) {
        
        int i = seat-1;
        
        if (train[i].equals("e")) {
            System.out.println("This seat is already empty.");
        } else {
            String customer = train[i];
            train[i] = "e";
            System.out.println(customer + " has been removed from the seat " + seat + ".");
        }      
    }
    
    //Below can be mate into an int-returning function
    private static void findCustomerSeat(String[] train, String customer) { 
        
        for (int i = 0; i < SEATING_CAPACITY; i++) {
            if (train[i].equals(customer)) {
                System.out.println(customer + " is currently booked in seat " + (i+1) + ".");
                break;
            } else if(i == (SEATING_CAPACITY-1)){
                System.out.println(customer + " isn't courently booked into any seat.");
            }
        }       
    }
    
    //Creates new array which is an ordered equivalent of the original train, then displays
    //all passangers (not including empty seats) in alphabetical order.
    private static void viewOrderedSeats(String[] train) {
        
        String[] orderedTrain = new String[SEATING_CAPACITY];
        
        //Copies array to another one for ordering, to preserve initial seats
        System.arraycopy(train, 0, orderedTrain, 0, SEATING_CAPACITY);
        
        int compare;
        String temp;
        
        //Bubble sort orders in descending alphabetical order
        for (int i = 0; i < SEATING_CAPACITY; i++) {
            for (int j = 1; j < (SEATING_CAPACITY-i); j++) {
                
                //Compares string alphabetically (Takes capitals over lower case!)
                compare = orderedTrain[j-1].compareTo(orderedTrain[j]);
                
                //If orderedTrain[j-1] is larger than orderedTrain[j]
                if (compare > 0) {
                    temp = orderedTrain[j-1];
                    orderedTrain[j-1] = orderedTrain[j];
                    orderedTrain[j] = temp;
                }           
            }           
        }
        
        //This count checks if there are any passengers; as an empty train produces no output
        int count = 0;
        
        //Displays the customers in alphabetical order, while still showing original seat numbers
        for (int i = 0; i < SEATING_CAPACITY; i++) {
            for (int j = 0; j < SEATING_CAPACITY; j++) {
                if (!orderedTrain[i].equals("e") && orderedTrain[i].equals(train[j])) {
                    System.out.println("Seat " + (j+1) + ": " + orderedTrain[i]);
                    count++;
                } 
            }
        }  
        
        
        //If the count is 0 it means that the train is empty.
        if (count == 0) {
            System.out.println("There are currently no passengers booked onto this train "
                    + "to order by.");
        }
    }
    
    //Saves contents of train array to designated file
    private static void saveToFile(String[] train, String file) {
        
        //When written like this the writer is closed automatically, so no need for .close()
        try (PrintWriter writer = new PrintWriter(file + ".txt")) {
            
            writer.print(""); //Clears file before writing
            
            for (int i = 0; i < SEATING_CAPACITY; i++) {
                writer.println(train[i]);
            }
            
            System.out.println("The train data has been saved to file.");
            
        } catch(IOException e) {           
            System.out.println(e);     
        }       
    }
    
    //Loads contents of designated file into train array
    private static void loadFromFile(String[] train, String file) {
        
        //Same for this reader with the .close()
        try (BufferedReader reader = new BufferedReader(new FileReader(file + ".txt"))) {
            
            for (int i = 0; i < SEATING_CAPACITY; i++) {
                train[i] = reader.readLine();
            }
            
            System.out.println("The train data has been loaded from file.");
            
        } catch (IOException e) {
            System.out.println(e);
        }     
    }  
    
    //Clears the train, by making all seats "e" - Similar to initialise(), but doesn't return
    //array address, and gives feedback that the train has been cleared.
    private static void clearTrain(String[] train) {
        
        for (int i = 0; i < SEATING_CAPACITY; i++) {
            train[i] = "e";
        }
        
        System.out.println("The train has been cleared.");   
    }  
}
