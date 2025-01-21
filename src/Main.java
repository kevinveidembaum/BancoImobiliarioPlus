import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        //Quantos Players
        int numJogadores = InputUtility.getIntInput("Quantos Jogadores irão participar: ");
        Jogador jogadores[] = new Jogador[numJogadores];


        //Nome de cada Jogador
        for(int i = 0; i < numJogadores; i++){
            int n = i+1;
            String nomeJogador = InputUtility.getStringInput("Nome do " + n + "° Jogador: ");
            jogadores[i] = new Jogador(nomeJogador, 2558);
        }


        //Inicializando Todas Propriedades em uma Lista
        List<Propriedade> propriedades = inicializarPropriedades();


        // Turno por Jogador
        for (Jogador jogador : jogadores) {
            System.out.printf("\nÉ a vez do(a) %s!\n", jogador.getNome());

            while(true){
                int escolha = InputUtility.getOption(jogador.getNome());

                switch (escolha) {
                    case 0:
                        System.out.println("\nVez do próximo Jogador!");
                        break;
                    case 1:
                        System.out.println("\nVocê escolheu comprar uma propriedade!");

                        propriedadesDisponiveis(propriedades);
                        int escolhaPropriedade = InputUtility.getIntInput("Digite o número da Propriedade desejada: ");
                        jogador.comprarPropriedade(propriedades, escolhaPropriedade);

                        break;
                    case 2:
                        System.out.println("\nVocê escolheu vender uma propriedade!");

                        //Verifica se Jogador possui alguma Propriedade
                        if(!jogador.isThereProperty(jogador)){
                            break;
                        }


                        propriedadesDisponiveis(jogador.getMinhasPropriedades());
                        int venderPropriedade = InputUtility.getIntInput("Digite o número da Propriedade a ser vendida: ");
                        jogador.venderPropriedade(jogador.getMinhasPropriedades(), venderPropriedade);

                        break;
                    case 3:
                        System.out.println("\nVocê escolheu hipotecar uma propriedade!");

                        //Verifica se Jogador possui alguma Propriedade
                        if(!jogador.isThereProperty(jogador)){
                            break;
                        }


                        for(Propriedade propriedadesHipotecadas : jogador.getMinhasPropriedades()){
                            if (propriedadesHipotecadas.isHipotecado()){

                            }

                        }



                        break;
                    case 4:
                        System.out.println("\nVocê escolheu pagar aluguel.");
                        // Skip turn
                        break;
                    case 5:
                        System.out.println("\nVocê escolheu fazer empréstimo.");
                        // Skip turn
                        break;
                    case 6:
                        System.out.println("\nVocê escolheu pagar empréstimo.");
                        // Skip turn
                        break;
                    case 7:
                        System.out.println("\nVocê escolheu visualizar status.");
                        // Skip turn
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }


                if(escolha == 0) break;
            }
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
        System.out.println("\nPropriedades disponíveis:");
        for (int i = 0; i < propriedades.size(); i++) {
            Propriedade propriedade = propriedades.get(i);

            if(propriedade.isHipotecado()){
                System.out.printf("%d. Nome: %s, Valor: $%.2f (Hipotecado por %s)\n", i + 1, propriedade.getNome(), propriedade.getValor(), propriedade.getDono().getNome());
                continue;
            }

            if(propriedade.getDono() != null){
                System.out.printf("%d. Nome: %s, Valor: $%.2f (Dono Atual: %s)\n", i + 1, propriedade.getNome(), propriedade.getValor(), propriedade.getDono().getNome());
                continue;
            }

            System.out.printf("%d. Nome: %s, Valor: $%.2f\n", i + 1, propriedade.getNome(), propriedade.getValor());
        }
    }
}