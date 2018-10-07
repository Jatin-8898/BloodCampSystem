package core;

import static java.lang.System.exit;
import java.util.Scanner;


/****************************************************************
   PROGRAM:     Main Application
   AUTHOR:      Jatin Varlyani
   CREATED AT:  06/10/2018

   FUNCTION:    This is the main controller for the blood camp system.

   INPUT:       Standard Input

   OUTPUT:      The system processes and outputs the appropriate result based
                on the inputs provided to the system.
****************************************************************/

public class MainApplication {

   
    Register register  = new Register();
    Login login  = new Login();
    Donor donor  = new Donor();
    Seeker seeker  = new Seeker();
    Admin admin  = new Admin();
    BloodCamp camp  = new BloodCamp();
    ContactUs contact  = new ContactUs();
    Complaint complaint  = new Complaint();

    
    /****************************************************************
        FUNCTION:   main()

        ARGUMENTS:  None

        RETURNS:    It just calls the printMenu()

        NOTES:      This function attempts to create object and call the printMenu.
    ****************************************************************/  
    public static void main(String[] args) {
        System.out.println("*******************BLOOD CAMP SYSTEM********************************");

        MainApplication obj = new MainApplication();
        obj.printMenu();
        
    }    
       
    
    
    
    /****************************************************************
        FUNCTION:   printMenu()

        ARGUMENTS:  None

         RETURNS:    It displays the options based upon the user input.

        NOTES:      This function attempts to display the options present in the
                    system.
    ****************************************************************/  
    public  void printMenu(){
        int choice;
        Scanner s = new Scanner(System.in);
        System.out.println("1. REGISTER\n"+ 
                            "2. LOGIN\n" + 
                            "3. ADMIN\n" +
                            "4. CONATCT US\n" +
                            "5. EXIT\n");
        boolean quit = false;
        while(!quit){
            System.out.println("ENTER YOUR DESIRED CHOICE:  ");
            choice = s.nextInt();
            switch(choice){

                case 1:
                    register.processRegister();
                    printLoginMenu();
                    break;


                case 2:
                    login.processLogin();
                    printLoginMenu();
                    printMenu();
                    break;


                case 3:
                    admin.processAdminInfo();
                    printMenu();
                    break;  


                case 4:
                    contact.contactUsDetails();
                    printMenu();
                    break;       

                case 5:
                    System.out.println("\nEXITING FROM THE SYSTEM....");
                    System.out.println("***************************************************************");
                    exit(0);
                    break;

                default:
                    System.out.println("\nYOU HAVE ENTERED WRONG INPUT, EXITING THE BLOOD CAMP SYSTEM....");
                    System.out.println("***************************************************************");
                    exit(0);
                    break;
            }
        }    
        
    }
    
    
    
    
    
    /****************************************************************
        FUNCTION:   printLoginMenu()

        ARGUMENTS:  None

        RETURNS:    It displays the options based upon the user input

        NOTES:      This function attempts to display the options present in the
                    login menu of the system.
    ****************************************************************/  
    public  void printLoginMenu(){
        Scanner s = new Scanner(System.in);
        
        System.out.println("1. DONOR\n"
                         + "2. SEEKER \n"
                         + "3. VIEW BLOOD CAMPS\n"
                         + "4. COMPLAINT\n"
                         + "5. CONATCT US\n"
                         + "6. EXIT");    
        
        System.out.println("\nENTER THE OPTION FROM THE AVAIALBLE LIST: ");
        int ans = s.nextInt();

        switch(ans){
            case 1:
                    System.out.println("WELCOME DEAR DONOR DO YOU WANT TO\n"
                                + "1. DONATE BLOOD\n"
                                + "2. VIEW BLOOD CAMPS");
                    int res = s.nextInt();
                    switch(res){
                        case 1:
                            donor.donateBlood();
                            printLoginMenu();
                            break;
                        case 2:
                            camp.displayCamps();
                            printLoginMenu();
                            break;
                        default:
                            System.out.println("ENTERED INCORRECT OPTION");
                            printLoginMenu();
                            break;
                    }
                break;   

            case 2:
                    System.out.println("WELCOME DEAR SEEKER DO YOU WANT TO\n"
                                + "1. SEEK BLOOD\n"
                                + "2. VIEW BLOOD CAMPS\n"
                                + "3. FIND DONORS");
                    int res2 = s.nextInt();
                    switch(res2){
                        case 1:
                            seeker.seekBlood();
                            printLoginMenu();
                            break;
                        case 2:
                            camp.displayCamps();
                            printLoginMenu();
                            break;
                        case 3:
                            
                            System.out.println("DO YOU WANT TO FIND BY \n"
                                               + "1. LOCATION\n"
                                               + "2. BLOOD TYPE");
                            int value = s.nextInt();
                            switch(value){
                                case 1:
                                    donor.findByLocation();
                                    break;
                                case 2:
                                    donor.findByBloodType();
                                    break;
                                default:
                                    System.out.println("YOU ENTERED INCORRECT INPUT, PLEASE TRY AGAIN");
                                    break;
                            }            
                            printLoginMenu();
                            break;
                        default:
                            System.out.println("ENTERED INCORRECT OPTION");
                            printLoginMenu();
                            break;
                    }
                break; 


            case 3:
                camp.displayCamps();
                printLoginMenu();
                break;

            case 4:
                complaint.complaintDetails();
                printLoginMenu();
                break;

            case 5:
                contact.contactUsDetails();
                printLoginMenu();
                break;

            case 6:
                System.out.println("EXITING FROM THE LOGIN MENU..");
                System.out.println("******************************************************");
                printMenu();
                break;

            default:
                System.out.println("ENTERED INCORRECT INPUT, LOGGING OUT OF THE SYSTEM....");
                System.out.println("******************************************************");
                printMenu();
                break;

        }
    }
        
}
