import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        try {
            System.out.print("Which filter do you want to implement: \n" +
                    "1. Bloom Filter(Enter 1)\n" +
                    "2. Counting Bloom Filter(Enter 2)\n" +
                    "3. Coded Bloom Filter(Enter 3)\n" +
                    "Your Choice: ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            switch (choice){
                case 1:
                    BloomFilter.stdInput();
                    writeNewFile();
                    BloomFilter.bloomFunction();
                    break;
                case 2:
                    CountingFilter.stdInput();
                    writeNewFile();
                    CountingFilter.countingBloomFunction();
                    break;
                case 3:
                    CodedBloomFilter.stdInput();
                    writeNewFile();
                    CodedBloomFilter.codedBloomFunction();
                    break;
                default:
                    System.out.println("Enter a valid choice (i.e. between 1 and 3)");
                    break;
            }

        } catch (InputMismatchException | FileNotFoundException | NoSuchAlgorithmException e){
            System.out.println("Bad numbers!! Please re-run the program and input valid numbers");
        }
    }
    public static void writeNewFile() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name for output file[format – output_typeOfHashTable]:");
        String fileName = sc.nextLine();

        System.out.println("--- Implementation in Progress\n" +
                "--- Please Check the Folder for the Output File\n" +
                "Thank you!!");

        PrintStream outputFile = new PrintStream(new File(fileName));
        System.setOut(outputFile);
    }
}
