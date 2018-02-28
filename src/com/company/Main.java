package com.company;

import java.util.Scanner;

public class Main {

    //to be simplified, we will divide canada into west and east
    //we assume west-to-west/east-to-east to be regional mailing
    //east-to-west/west-to-east to be national mailing
    public enum Location {
        WEST, EAST
    }
    public enum Type {
        STANDARD, LETTER, PACK, OVERSIZE
    }
    public static void main(String[] args) {
        //variables
        String from_pc;
        String to_pc;
        Double length;
        Double width;
        Double height;
        Double weight;
        String postal_type;
        Double calculated_rate;
        //CLI
        System.out.println("Welcome to rate calculator!\n");
        System.out.println("Please enter the 'From(Postal code)/To(Postal code)/Length(CM)/Width(CM)/Height(CM)/Weight(KG)/Post Type' in order to calculate postal rate:\n");
        System.out.println("Please enter From(Postal code Format: 'H3F 1E2'):\n");
        Scanner scanner = new Scanner(System.in);
        from_pc = scanner.nextLine();
        System.out.println("Please enter To(Postal code Format: 'H3F 1E2'):\n");
        to_pc = scanner.nextLine();
        System.out.println("Please enter Length(CM):\n");
        length = scanner.nextDouble();
        System.out.println("Please enter Width(CM):\n");
        width = scanner.nextDouble();
        System.out.println("Please enter Height(CM):\n");
        height = scanner.nextDouble();
        System.out.println("Please enter Weight(KG):\n");
        weight = scanner.nextDouble();
        System.out.println("Please enter Post Type (Please enter one of 'Regular/Xpress/Priority'):\n");
        postal_type = scanner.next();
        System.out.println("checking...\n");
        isValidEntries(from_pc, to_pc, length, width, height, weight, postal_type);
        //Determine whether depart and arrive location is in eastern or western Canada
        Location departLoc = determineLocation(from_pc);
        Location arriveLoc = determineLocation(to_pc);
        Type mail_type = determineType(length, width, height, weight);
        calculated_rate = calculateRate(departLoc, arriveLoc, mail_type, length, width, height, weight, postal_type);
        System.out.print("Rate in total is "+calculated_rate+".");
    }

    public static boolean isValidEntries(String fromPostal, String toPostal, Double len, Double wid, Double height, Double weight, String type){
        boolean isValid = false;
        String lowercase_type = type.toLowerCase();
        //assume the calculator is to calculate rate within in Canada only
        if(fromPostal.equals("")||fromPostal.length()!=7||!Character.isLetter(fromPostal.charAt(0))){
            throw new IllegalArgumentException("ERROR: Input 'From' is cannot be empty/in wrong format");
        }
        if(toPostal.equals("")||toPostal.length()!=7||!Character.isLetter(toPostal.charAt(0))){
            throw new IllegalArgumentException("ERROR: Input 'To' is cannot be empty/in wrong format");
        }
        if(len==0||wid ==0||height==0||weight==0){
            throw new IllegalArgumentException("ERROR: Input Length/Width/Height/Weight cannot be 0");
        }
        if(!lowercase_type.equals("regular")&&!lowercase_type.equals("xpress")&&!lowercase_type.equals("priority")){
            throw new IllegalArgumentException("ERROR: Input Type does not match any option");
        }
        isValid = true;
        return isValid;
    }

    public static Location determineLocation(String post_code){
        Location loc;
        //since the first letter of postal code represents the province within canada
        char first_letter = post_code.toLowerCase().charAt(0);
        if(first_letter=='x'||first_letter=='y'||first_letter=='v'||first_letter=='t'||first_letter=='s'||first_letter=='r'){
            loc = Location.WEST;
        }
        else if(first_letter=='a'||first_letter=='b'||first_letter=='c'||first_letter=='e'||first_letter=='j'||first_letter=='p'||
                first_letter=='g'||first_letter=='h'||first_letter=='k'||first_letter=='m'||first_letter=='n'||first_letter=='l')
        {
            loc = Location.EAST;
        }
        else{
            throw new IllegalArgumentException("ERROR: The FROM/TO postal code is not in Canada");
        }
        return loc;
    }

    public static Type determineType(Double len, Double wid, Double height, Double weight){
        Type mail;
        if (len > 1 && len <= 26 &&
                wid > 0.7 && wid <= 15.9 &&
                height > 0.01 && height <= 1.5 &&
                weight > 0 && weight <= 0.5)
        {
            mail = Type.STANDARD;
        }
        else if (len <= 31.8 &&
                wid <= 24.1 &&
                height <= 1.5 &&
                weight <= 0.5)
        {
            mail = Type.LETTER;
        }
        else if (len <= 39.4 &&
                wid <= 31.4 &&
                height <= 3 &&
                weight <= 1)
        {
            mail = Type.PACK;
        }
        else if (len <= 50 &&
                wid <= 50 &&
                height <= 100 &&
                weight <= 30)
        {
            mail = Type.OVERSIZE;
        }
        else{
            throw new IllegalArgumentException("ERROR: Size is invalid");
        }
        return mail;
    }

    public static Double calculateRate(Location depart, Location arrive, Type mail, Double len, Double wid, Double height, Double weight, String postal_type){
        String lowercase_type = postal_type.toLowerCase();
        Double totalRate = 0.0;
        //regional
        if(depart == arrive){
            if(mail == Type.STANDARD){
                if(lowercase_type.equals("xpress")){
                    totalRate += 12.35 + 1.5;
                }
                else if(lowercase_type.equals("priority")){
                    throw new IllegalArgumentException("ERROR: There is no STANDERD option for Priority");
                }
                else{
                    totalRate += 1.5;
                }
                //Collect on Delivery + Signature Hard Copy + Surcharge for Cylindrical Mailing Tube
                totalRate += 7.25 + 7.25 + 1.5;
            }
            else if(mail == Type.LETTER){
                if(lowercase_type.equals("xpress")){
                    totalRate += 13.80 + 1.5;
                }
                else if(lowercase_type.equals("priority")){
                    totalRate += 27.90;
                }
                else{
                    totalRate += 1.5;
                }
                //Collect on Delivery + Signature Hard Copy + Surcharge for Cylindrical Mailing Tube
                totalRate += 7.25 + 7.25 + 1.5;
            }
            else if(mail == Type.PACK){
                if(lowercase_type.equals("xpress")){
                    totalRate += 17.30 + 1.5;
                }
                else if(lowercase_type.equals("priority")){
                    totalRate += 28.95;
                }
                else{
                    totalRate += 1.5;
                }
                //Collect on Delivery + Signature Hard Copy + Surcharge for Cylindrical Mailing Tube
                totalRate += 7.25 + 7.25 + 1.5;
            }
            //for every 1m exceeded in girth = 2 * len + 2 * wid, it will charge 10 dollar extra
            else if(mail == Type.OVERSIZE){
                if(lowercase_type.equals("xpress")){
                    totalRate += 17.30 + 1.5 + (((2 * len + 2 * wid)/100 - 1) * 10);
                }
                else if(lowercase_type.equals("priority")){
                    totalRate += 28.95 + (((2 * len + 2 * wid)/100 - 1) * 10);
                }
                else{
                    totalRate += 1.5 + (((2 * len + 2 * wid)/100 - 1) * 10);
                }
                //Collect on Delivery + Signature Hard Copy + Surcharge for Cylindrical Mailing Tube
                totalRate += 7.25 + 7.25 + 1.5;
            }
        }
        //national
        else{
            if(mail == Type.STANDARD){
                if(lowercase_type.equals("xpress")){
                    totalRate += 19.5 + 1.5;
                }
                else if(lowercase_type.equals("priority")){
                    throw new IllegalArgumentException("ERROR: There is no STANDERD option for Priority");
                }
                else{
                    totalRate += 1.5;
                }
                //Collect on Delivery + Signature Hard Copy + Surcharge for Cylindrical Mailing Tube
                totalRate += 7.25 + 7.25 + 1.5;
            }
            else if(mail == Type.LETTER){
                if(lowercase_type.equals("xpress")){
                    totalRate += 20.15 + 1.5;
                }
                else if(lowercase_type.equals("priority")){
                    totalRate += 41.10;
                }
                else{
                    totalRate += 1.5;
                }
                //Collect on Delivery + Signature Hard Copy + Surcharge for Cylindrical Mailing Tube
                totalRate += 7.25 + 7.25 + 1.5;
            }
            else if(mail == Type.PACK){
                if(lowercase_type.equals("xpress")){
                    totalRate += 30.80 + 1.5;
                }
                else if(lowercase_type.equals("priority")){
                    totalRate += 47.05;
                }
                else{
                    totalRate += 1.5;
                }
                //Collect on Delivery + Signature Hard Copy + Surcharge for Cylindrical Mailing Tube
                totalRate += 7.25 + 7.25 + 1.5;
            }
            //for every 1m exceeded in girth = 2 * len + 2 * wid, it will charge 10 dollar extra
            else if(mail == Type.OVERSIZE){
                if(lowercase_type.equals("xpress")){
                    totalRate += 30.80 + 1.5 + (((2 * len + 2 * wid)/100 - 1) * 10);
                }
                else if(lowercase_type.equals("priority")){
                    totalRate += 47.05 + (((2 * len + 2 * wid)/100 - 1) * 10);
                }
                else{
                    totalRate += 1.5 + (((2 * len + 2 * wid)/100 - 1) * 10);
                }
                //Collect on Delivery + Signature Hard Copy + Surcharge for Cylindrical Mailing Tube
                totalRate += 7.25 + 7.25 + 1.5;
            }

        }

        return totalRate;
    }
}