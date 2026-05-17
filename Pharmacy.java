/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pharmcy;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Pharmacy {

    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        System.out.println("     -----");
        System.out.println("     |   |");
        System.out.println("     |___|      WELCOME TO WELLNESS PHARMACY");
        System.out.println("    /     \\");
        System.out.println("   /       \\");
        System.out.println("  /         \\");
        System.out.println(" /           \\");
        System.out.println("/_____________\\");
        System.out.println("");
        Customer customer = new Customer();
        try {

            File ReadFile = new File("customer.txt");

            Scanner file = new Scanner(ReadFile);
            String name = file.next();
            int cid = file.nextInt();
            customer.setId(cid);
            customer.setName(name);
            String phoneNumber = file.next();

            customer.setPhoneNumber(phoneNumber);
            String email = file.next();

            customer.setEmail(email);
            file.close();
        } catch (IOException ex) {
            System.out.println("File not exsist");
        }
        System.out.println("please enter (1) if you a customer or(2)if you a pharmacist");
        int num = k.nextInt();

        PharmacyProduct cp = new PharmacyProduct();
        switch (num) {
            case 1:
                int choice = 0;
                while (choice != 4) {

                    System.out.println("How we can help you?");
                    System.out.println("1-I want to register my information in the pharmacy");
                    System.out.println("2-I would like to make a complaint");
                    System.out.println("3-shop now");
                    System.out.println("4-Exit");

                    choice = k.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("please enter your name:");
                            String name = k.next();
                            System.out.println("please enter your phone number");
                            String ph = k.next();
                            System.out.println("please enter your id");
                            int id = k.nextInt();
                            System.out.println("please enter your email");
                            String email = k.next();
                            Customer customer1 = new Customer(name, ph, id, email);
                            Customer customer2 = new Customer(name, ph, id, email);
                            if (customer1.equals(customer2)) {
                                System.out.println("this customer already exists");
                            } else {
                                System.out.println("this is a new customer");
                            }
                            break;
                        case 2:
                            customer.aboutServices();
                            break;
                        case 3:
                            int num1 = 0;

                            while (num1 != 3) {

                                System.out.println("what kind of prodcuts do you want?");
                                System.out.println("1.care product");
                                System.out.println("2.medicine");
                                System.out.println("3.end");
                                num1 = k.nextInt();
                                if (num1 == 1) {
                                    cp.startShopping(1);

                                } else if (num1 == 2) {

                                    cp.startShopping(2);

                                }
                            }

                            Order order = new Order(21, customer, cp.getCart());

                            order.printOrder();

                            new Bill(4234, customer, cp).PrintBill();

                            break;
                    }
                }
                break;

            case 2:
                System.out.println("");
                System.out.println("please write your id:");
                int id = k.nextInt();
                Pharmacist pharmacist = new Pharmacist(id);
                int ids[] = pharmacist.ids();
                String names[] = pharmacist.names();

                for (int i = 0; i < ids.length; i++) {
                    if (id == ids[i]) {
                        System.out.println("Hello " + names[i]);
                        System.out.println("please choose one of the options:");
                        System.out.println("1-View my work schedule");
                        System.out.println("2-Request leave");
                        System.out.println("3-Register a new customer");
                        System.out.println("4-Inform pharmaceutical company of medication out of supply");
                        int choic = k.nextInt();
                        switch (choic) {
                            case 1:
                                pharmacist.workShifts(id);
                                break;
                            case 2:
                                pharmacist.askForLeave();
                                break;
                            case 3:
                                customer = pharmacist.newCustomer();
                                break;
                            case 4:
                                pharmacist.outOfSupply();
                            default:
                                if (choic > 5) {
                                    System.out.println("you only have 5 choices");
                                    break;
                                }

                        }

                    }//the end of the if

                }//the end of the for loop
                break;
        }
    }
}
