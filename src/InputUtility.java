import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputUtility {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Metodo para ler uma String
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred while reading input. Please try again.");
            return getStringInput(prompt);
        }
    }

    // Metodo para ler um Int
    public static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(reader.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
