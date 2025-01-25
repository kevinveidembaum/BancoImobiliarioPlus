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
        List<Propriedade> propriedades = Utility.inicializarPropriedades();

        //TODO fazer o jogo infinito até um Jogador não ter dinheiro

        //TODO refatorar este trecho de código, switch case, em metodos








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
                        jogador.comprarPropriedade(propriedades);

                        break;
                    case 2:
                        jogador.venderPropriedade(jogador.getMinhasPropriedades());

                        break;
                    case 3:
                        jogador.hipotecar(jogador.getMinhasPropriedades());

                        break;
                    case 4:
                        jogador.escolherVenderComprar(jogador);

                        break;
                    case 5:
                        System.out.println("\nVocê escolheu fazer empréstimo.");
                        // Skip turn
                        break;
                    case 6:
                        System.out.println("\nVocê escolheu pagar empréstimo OU aluguel.");
                        // Skip turn
                        break;
                    case 7:
                        jogador.visualizarSaldoPropriedades(jogador);

                        break;
                    case 8:
                        jogador.escolherInspecionar(propriedades);

                        break;
                    case 9:
                        System.out.println("\nVocê escolheu ver ranking.");
                        // TODO fazer o ranking
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }


                if(escolha == 0) break;
            }
        }
    }
}