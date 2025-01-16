import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
                jogadores[i] = new Jogador(nomeJogador, 2558);
            }

            List<Propriedade> propriedades = inicializarPropriedades(); //Todas Propriedades em uma Lista

            propriedadesDisponiveis(propriedades);












        } catch (NumberFormatException e) {
            System.out.println("That's not a valid number.");
        }
    }

    private static List<Propriedade> inicializarPropriedades() {
        List<Propriedade> propriedades = new ArrayList<>();
        propriedades.add(new Propriedade("Avenida Paulista", 500));
        propriedades.add(new Propriedade("Copacabana", 300));
        propriedades.add(new Propriedade("Leblon", 700));
        propriedades.add(new Propriedade("Ipanema", 650));
        propriedades.add(new Propriedade("Jardins", 450));
        return propriedades;
    }

    private static void propriedadesDisponiveis(List<Propriedade> propriedades){
        System.out.println("\nPropriedades disponíveis para compra:");
        for (int i = 0; i < propriedades.size(); i++) {
            Propriedade propriedade = propriedades.get(i);
            System.out.printf("%d. Nome: %s, Valor: %.2f\n", i + 1, propriedade.getNome(), propriedade.getValor());
        }
    }
}