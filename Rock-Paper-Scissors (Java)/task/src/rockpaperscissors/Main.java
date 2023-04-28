package rockpaperscissors;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static String[] choices = {"rock", "paper", "scissors"};
    static int numberOfChoices = 3;
    static Scanner scanner;
    static String fileName = "rating.txt";

    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name);
        int rating = getRating(name);

        String[] newChoices = scanner.nextLine().toLowerCase().split(",");
        if (newChoices.length >= 3 && (newChoices.length % 2 == 1)) {
            choices = newChoices;
            numberOfChoices = newChoices.length;
        }

        System.out.println("Okay, let's start");

        while (true) {
            String userInp = scanner.nextLine();

            if (userInp.equalsIgnoreCase("!exit")) break;
            if (userInp.equalsIgnoreCase("!rating")) {
                System.out.println("Your rating: " + rating);
                continue;
            }

            int userIndx = -1;
            for(int i = 0; i < numberOfChoices; i++) {
                if (choices[i].equalsIgnoreCase(userInp)) {
                    userIndx = i;
                    break;
                }
            }

            if (userIndx == -1) System.out.println("Invalid input");
            else {
                Random rng = new Random();
                int myIndx = rng.nextInt(numberOfChoices);
                int result = (userIndx - myIndx + numberOfChoices) % numberOfChoices;

                if (result == 0) {
                    rating += 50;
                    System.out.println("There is a draw (" + choices[myIndx] + ")");
                } else if (result > numberOfChoices / 2) {
                    System.out.println("Sorry, but the computer chose " + choices[myIndx]);
                } else {
                    rating += 100;
                    System.out.println("Well done. The computer chose " + choices[myIndx] + " and failed");
                }

            }
        }
        System.out.println("Bye!");
    }

    private static int getRating(String name) {
        File file = new File(fileName);
        if (!file.exists()) return 0;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNext()) {
                String[] line = fileScanner.nextLine().split(" ");
                if (line.length !=2) return 0;
                if (line[0].equals(name)) {
                    return Integer.parseInt(line[1]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
        return 0;
    }
}
