import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Quantos Jogadores irão participar: ");
            int number = Integer.parseInt(reader.readLine());

            Jogador jogadores[] = new Jogador[number];

            for(int i = 0; i < number; i++){
                System.out.printf("Nome do %d Jogador: ", i+1);
                String nomeJogador = reader.readLine();
                jogadores[i] = new Jogador(nomeJogador, 2000);
            }

            System.out.println("\nJogadores Participantes:");
            for (Jogador jogador : jogadores) {
                System.out.println(jogador.getNome());
            }

        } catch (NumberFormatException e) {
            System.out.println("That's not a valid number.");
        }
    }
}