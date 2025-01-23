import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputUtility {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    //Metodo para mostrar Menu de Opções do Jogador
    public static int getOption(String playerName) {
        System.out.printf("\n%s, escolha sua próxima ação:\n", playerName);
        System.out.println("1. Comprar Propriedade");
        System.out.println("2. Vender Propriedade");
        System.out.println("3. Hipotecar Propriedade");
        System.out.println("4. Comprar OU Vender Casa/Hotel");
        System.out.println("5. Fazer Empréstimo");
        System.out.println("6. Pagar Empréstimo OU Aluguel");
        System.out.println("7. Visualizar Meu Saldo e Minhas Propriedades");
        System.out.println("8. Visualizar Todas as Propriedades");
        System.out.println("9. Ranking");
        System.out.println("0. Encerrar Turno!");

        return getIntInput("Sua escolha: ");
    }


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
                System.out.println("Invalid input. Please enter a Integer number.");
            }
        }
    }


    // Metodo para ler um Float
    public static float getFloatInput(String prompt){
        while(true){
            System.out.print(prompt);
            try {
                return Float.parseFloat(reader.readLine());
            }catch (NumberFormatException | IOException e){
                System.out.println("Invalid input. Please enter a Float number.");
            }
        }
    }


    // Metodo para ler Sim ou Não
    public static boolean getYesOrNoInput() {
        while (true) {
            String input = getStringInput("[S] Sim      [N] Não\n").trim().toUpperCase();
            if (input.equals("S")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.println("Entrada inválida. Por favor, digite [S] para Sim ou [N] para Não.");
            }
        }
    }
}
