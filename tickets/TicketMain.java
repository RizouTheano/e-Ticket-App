/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

/**
 *
 * @author User
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;

import java.util.NoSuchElementException;
import java.util.Scanner;
import tickets.Ticket.Kind;
import tickets.Ticket.Type;

public class TicketMain {

    //create the list that will keep the tickets
    private static final ArrayList<Ticket> tickets = new ArrayList<>();

    public static Ticket addTicket(String name, String password, Type type, int dur, int routes, double price, String email, Kind kind) {
        int code = tickets.size() + 1;
        Ticket t = null;
        tickets.add(t);
        Scanner sc = new Scanner(System.in);
        if (type == Type.Discounted || dur == 4) {

            if (name.isEmpty()) {
                System.out.println("Name: ");
                name = sc.next();
            }
            if (email.isEmpty()) {
                System.out.println("Email: ");
                email = sc.next();
            }
            if (password.isEmpty()) {
                System.out.println("Password: ");
                password = sc.next();
            }
            LocalDate date = dur == 4 ? LocalDate.now().plusDays(30) : null;

            t = new SpecialTicket(name, password, date, code, kind, type, price, dur, email);
            if (kind == Kind.Electronic) {
                System.out.println("Your ticket has been sent to: " + email);
            }
        } else {
            t = new Ticket(code, kind, type, price, dur);
            if (t.geteidos() == Kind.Electronic) {
                System.out.println("Give your email: ");
                email = sc.next();
                System.out.println("Your ticket has been sent to: " + email);
            }
        }
        tickets.add(t);
        return t;
    } //end of addTicket method

    private static Ticket search(int code) {

        Ticket result = null;
        try {
            result = tickets.stream().filter((Ticket t) -> t.getcode() == code).findFirst().get();
        } catch (NoSuchElementException e) {
        }
        return result;
    }

    //returns the position of a ticket in the list given its code or -1 if it doesnt exist
    private static int search(int code, boolean index) {
        Ticket t = search(code);
        if (t != null) {
            return tickets.indexOf(t);
        }
        return -1;
    }

    public static void routesMenu() {
        System.out.println("1. 90 min");
        System.out.println("2. 1-day ticket");
        System.out.println("3. 5-days ticket");
        System.out.println("4. 30-days ticket");
        System.out.println("5. 1 route");
        System.out.println("6 5 routes");
        System.out.println("7. 11 routes");
        System.out.println("Please select a number from 1 to 7. Any other option will exit the program");
    } //end of routesMenu method

    public static void ticketInitialization(Type t, int r, double p, Ticket nt) {
        nt.setType(t);
        nt.setdur(r);
        nt.setprice(p);
    }//end of ticketInitialization method

    public static double makeThePurchase(double p1, Ticket nt, int routes, Type type, Kind kind) {
        String name = "";
        double p = p1;
        int dur = routes;
        String password = "";
        String email = "";
        if (type == Type.Discounted) { //if it is discounted
            p = p1 / 2;
        }
        ticketInitialization(type, routes, p, nt);

        Ticket addedTicket = addTicket(name, password, type, dur, routes, p, email, kind); //add the ticket to the list
        System.out.println("Your ticket code is " + addedTicket.getcode()); //prints the code of his ticker for future use
        return p;
    } //end of makeThePurchase method

    public static double Buy(Type type, Ticket nt, Kind kind) { //a method that is called from main class whenever the user wants to buy a new ticket

        double p = 0;
        Scanner input = new Scanner(System.in);
        nt.setType(type);                         //to set the type that will be choosen
        routesMenu();                             //printing th menu of the routes
        int routes = 0;
        try {
            routes = input.nextInt();            //the coice of the duration or number of routes
        } catch (InputMismatchException e) {
            input.next();
            System.out.println("That's not an integer. Please try again:");
        }
        nt.setdur(routes); //calling setter for duration

        switch (routes) {
            case 1: {

                // 90mins
                p = 1.20;
                makeThePurchase(p, nt, routes, type, kind);
                return p;

            }
            case 2: {
                // 1-day ticket
                p = 5.00;
                makeThePurchase(p, nt, routes, type, kind);
                return p;
            }
            case 3: {
                //5-day ticket

                p = 9.00;
                makeThePurchase(p, nt, routes, type, kind);
                return p;
            }
            case 4: {
                //if the coice is monthly ticket we need extra information
                if (type == Type.Regular) { //if it is not discounted
                    p = 30.00;
                } else if (type == Type.Discounted) {
                    p = 15.00;
                }
                SpecialTicket nt1 = new SpecialTicket();
                nt.setType(type); //initialize the type that had been given
                nt.setdur(routes); //initialize the monthly duration of the ticket

                int code = nt.getcode();

                nt.setprice(p);

                int dur = routes;
                // checksIfPrinted();
                Ticket t = addTicket("", "", type, dur, routes, p, "", kind); //points 
                System.out.println("Your ticket code is " + t.getcode());
                return p;
            }
            case 5: {
                // 1-route
                p = 1.20;
                makeThePurchase(p, nt, routes, type, kind);
                return p;
            }
            case 6: {
                //5-routes

                p = 1.20 * 5;
                makeThePurchase(p, nt, routes, type, kind);
                return p;
            }
            case 7: {
                //11-routes

                p = 1.20 * 11;
                makeThePurchase(p, nt, routes, type, kind);
                return p;
            }
            default:
                System.out.println("Oops wrong choice. Bye!");
                return 0;

        }
    } //end of Buy method

    public static Kind Refresh(int code, double newp, int routes, Type type) {   //take as parameters the code of the ticket and the object of the ticket we want to refresh
        Kind kind = null;
        Scanner input = new Scanner(System.in);
        Ticket t = search(code);
        if (t.getType() == Type.Regular && t.getdur() != 4
                && (type == Type.Discounted || routes == 4)) {
            System.out.println("Name: ");
            String name = input.next();

            System.out.println("Email: ");
            String email = input.next();

            System.out.println("password: ");
            String password = input.next();
            LocalDate date = routes == 4 ? LocalDate.now().plusDays(30) : null; //got help from internet
            t = new SpecialTicket(t, name, email, password, date);
        } else if ((t.getType() == Type.Discounted || t.getdur() == 4)
                && type == Type.Regular && routes != 4) {
            t = new Ticket(t.code, kind, type, newp, routes);
        } else if (t.getType() == Type.Discounted && t.getdur() != 4 && routes == 4) {
            ((SpecialTicket) t).setdate(LocalDate.now().plusDays(30));
        }
        t.setprice(newp);
        t.setdur(routes);
        t.setType(type);

        System.out.println("The refresh has been completed!");
        while (true) {
            System.out.println("Do you want your ticket to be printed or to be sent by email? Select 1 for printed or 2 for email...");
            int tanswer = 0;
            try {
                tanswer = input.nextInt();
            } catch (InputMismatchException e) {
                input.next();
                System.out.println("That's not an integer. Please try again:");
            }

            if (tanswer == 1 | tanswer == 2) {
                kind = tanswer == 1 ? Kind.Printed : Kind.Electronic; //
                break;
            } else {
                System.out.println("Oops wrong selection please try again!");
            }
        }
        t.setKind(kind);
        tickets.set(search(code, true), t);
        return kind;
    } //end of refresh method

    public static void ShowMyTicket(int code) {
        System.out.println("Your ticket is: " + tickets.get(code));

    }

    public static void CashOrNot() {
        System.out.println("Do you want to pay with cash or with a credit card? ");
        System.out.println("Select 1 for cash or 2 for credit card :\n");
        int c = 0;
        Scanner input = new Scanner(System.in);
        try {
            c = input.nextInt();
        } catch (InputMismatchException e) {
            input.next();
            System.out.println("That's not an integer. Please try again:");
        }
        if(c==2){
            System.out.println("Please give the number of your credit card(this number will not be kept by the system , for security reasons!):");
            int number;
            try{
                number=input.nextInt();
            }catch(InputMismatchException e){
                input.next();
                System.out.println("That's not an integer. Please try again:");
            }
        }
    }

    public static void PrintedOrNotTicket(Ticket nt, Type type) { //method to check wheather the user want his ticket printed or to be sent by email and shows the price that he has to pay
        Scanner input = new Scanner(System.in);
        Kind kind;
        while (true) { // keeps asking whether the user wants his ticket printed or sented by email

            System.out.println("Do you want your ticket to be print or to be sent by email? Select 1 for printed or 2 for email...");
            int tanswer = 0;
            try {
                tanswer = input.nextInt();
            } catch (InputMismatchException e) {
                input.next();
                System.out.println("That's not an integer. Please try again:");
            }
            if (tanswer == 1) {
                kind = Kind.Printed;
                break;
            } else if (tanswer == 2) {
                kind = Kind.Electronic;
                break;
            } else {
                System.out.println("Oops wrong selection please try again!");
            }
        }
        double priceToPay = Buy(type, nt, kind);
        System.out.println("Your ticket  costs:" + priceToPay);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int c = 0;
        boolean done = false;
        while (!done) {
            System.out.println("-------------Menu-------------");
            System.out.println("------------------------------");
            System.out.println("1. Purchase");
            System.out.println("2. Refresh");
            System.out.println("3. Print ticket by code");
            System.out.println("4. Exit");
            System.out.println("------------------------------");
            System.out.println("\n\nEnter a number from 1 to 4");
            System.out.print("> ");
            try {
                c = input.nextInt();
            } catch (InputMismatchException e) {
                input.next();
                System.out.println("That's not an integer. Please try again:");
            }
            if (c == 1) {                                               //if he want to buy a ticket

                System.out.println("Please select the type of the ticket you want to buy:");
                System.out.println("---------------------------------------------");
                System.out.println("1. Full price");
                System.out.println("2. Discounted");
                System.out.println("Please select 1 or 2. Any other option will exit the program");
                double priceToPay;
                int type = 0;
                try {
                    type = input.nextInt();                            //the coice if the ticket is discounted or not 
                } catch (InputMismatchException e) {
                    input.next();
                    System.out.println("That's not an integer. Please try again:");
                }
                switch (type) {
                    case 1: {
                        //if he wants full price
                        Ticket nt = new Ticket(); //initialize in 0
                        PrintedOrNotTicket(nt, Type.Regular); //check wheather the user want his ticket printed or to be sent by email and shows the price that he has to pay
                        CashOrNot();
                        break;
                    }
                    case 2: {
                        ////if he wants discounted ticket
                        Ticket nt = new Ticket(); //initialize in 0
                        PrintedOrNotTicket(nt, Type.Discounted); //check wheather the user want his ticket printed or to be sent by email and shows the price that he has to pay
                        CashOrNot();
                        break;
                    }
                    default:
                        System.out.println("exit..."); // Terminate JVM 

                        System.exit(0);
                }

            } else if (c == 2) {// end of purchase
                System.out.println("Please give your code of the ticket you want to refresh:");
                int code = 0;
                try {
                    code = input.nextInt();
                } catch (InputMismatchException e) {
                    input.next();
                    System.out.println("That's not an integer. Please try again:");
                }
                Ticket t = search(code);
                if (t == null) {
                    System.err.println("There is no ticket with the given code!");
                    continue;
                }
                System.out.println("Select what you want to do:");
                routesMenu();
                int routes = 0;
                try {

                    routes = input.nextInt();            //the coice of the duration or number of routes
                } catch (InputMismatchException e) {
                    input.next();
                    System.out.println("That's not an integer. Please try again:");
                }
                double newp = 0;
                System.out.println("Please select the type of the ticket you want to buy:");
                System.out.println("---------------------------------------------");
                System.out.println("1. Full price");
                System.out.println("2. Discounted");
                System.out.println("Please select 1 or 2. Any other option will exit the process");
                int answer = 0;
                try {
                    answer = input.nextInt();
                } catch (InputMismatchException e) {
                    input.next();
                    System.out.println("That's not an integer. Please try again:");
                }
                Type type;
                if (answer == 1) {
                    type = Type.Regular;
                } else if (answer == 2) {
                    type = Type.Discounted;
                } else {
                    continue;
                }

                boolean flag = false;
                while (flag) {                  // set in the newp , the price that will add to the existed ticket
                    if (type == Type.Regular) {
                        switch (routes) {
                            case 1:
                                newp = 1.20;
                                flag = true;
                                break;

                            case 2:
                                newp = 4.50;
                                flag = true;
                                break;
                            case 3:
                                newp = 9.00;
                                flag = true;
                                break;
                            case 4:
                                newp = 30.00;
                                flag = true;
                                break;
                            case 5:
                                newp = 1.20;
                                flag = true;
                                break;
                            case 6:
                                newp = 1.20 * 5;
                                flag = true;
                                break;
                            case 7:
                                newp = 1.20 * 11;
                                flag = true;
                                break;
                            default:
                                System.out.println("Oops! Wrong selection..please try again!");
                                break;
                        }

                    } else {
                        switch (routes) {
                            case 1:
                                newp = 0.60;
                                flag = true;
                                break;

                            case 2:
                                newp = 2.25;
                                flag = true;
                                break;
                            case 3:
                                newp = 4.50;
                                flag = true;
                                break;
                            case 4:
                                newp = 15.00;
                                flag = true;
                                break;
                            case 5:
                                newp = 0.60;
                                flag = true;
                                break;
                            case 6:
                                newp = 0.60 * 5;
                                flag = true;
                                break;
                            case 7:
                                newp = 0.60 * 11;
                                flag = true;
                                break;
                            default:
                                System.out.println("Oops! Wrong selection..please try again!");
                                break;
                        }
                    }

                }
                Refresh(code, newp, routes, type);// calling the refreshing method
                CashOrNot();
                System.out.println("Done!");
            } else if (c == 3) {
                System.out.println("Please give the code of the ticket you want to preview:");
                int code = 0;
                try {
                    code = input.nextInt(); //we take the code of the ticket, which is actually its position in the list
                } catch (InputMismatchException e) {
                    input.next();
                    System.out.println("That's not an integer. Please try again:");
                }
                Ticket t = search(code);
                if (t != null) {
                    if (t.getType() == Type.Discounted || t.getdur() == 4) {
                        String printedTicket = ((SpecialTicket) t).toString();
                        System.out.println(printedTicket);
                    } else {
                        System.out.println(t.toString());
                    }
                } else {
                    System.out.println("Couldnt find ticket with the given code!");
                }
            } else if (c == 4) {
                System.out.println("Goodbye!");
                done = true;
            }
        }//end of menu

    } //end of main

} //end of class
