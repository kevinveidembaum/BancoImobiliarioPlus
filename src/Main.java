import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Controle de flow do Jogo
    public void realizarTurno(List<Propriedade> propriedades, Jogador[] jogadores) {
        while(true){
            for (Jogador jogador : jogadores) {
                System.out.printf("\nÉ a vez do(a) %s!\n", jogador.getNome());

                while (true) {
                    int escolha = InputUtility.getOption(jogador.getNome());


                    //Encerra o turno do Jogador Atual
                    if (escolha == 0) {
                        System.out.println("\nVez do próximo Jogador!");
                        break;
                    }


                    //Encerra o Jogo independente do momento
                    if(escolha == 999){
                        System.out.println("\nJogo Encerrado! Até a Próxima!");
                        return;
                    }

                    //todo fazer emprestimos

                    //todo fazer ranking

                    switch (escolha) {
                        case 1 -> jogador.comprarPropriedade(propriedades);
                        case 2 -> jogador.venderPropriedade(jogador.getMinhasPropriedades());
                        case 3 -> jogador.hipotecar(jogador.getMinhasPropriedades());
                        case 4 -> jogador.gerenciarVenderComprar(jogador);
                        case 5 -> System.out.println("\nVocê escolheu fazer empréstimo.");
                        case 6 -> jogador.gerenciarEmprestimoAluguel(propriedades, jogador);
                        case 7 -> jogador.visualizarSaldoPropriedades(jogador);
                        case 8 -> jogador.visualizarTodasAsPropriedades(propriedades);
                        case 9 -> Utility.visualizarRanking(jogadores);
                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                }
            }
        }
    }


    //Gerar Jogadores
    public Jogador[] gerarJogadores(){
        //Quantos Players
        int numJogadores = InputUtility.getIntInput("Quantos Jogadores irão participar: ");
        Jogador jogadores[] = new Jogador[numJogadores];


        //Nome de cada Jogador
        for(int i = 0; i < numJogadores; i++){
            int n = i+1;
            String nomeJogador = InputUtility.getStringInput("Nome do " + n + "° Jogador: ");
            jogadores[i] = new Jogador(nomeJogador, 2558);
        }

        return jogadores;
    }


    public static void main(String[] args){
        Main main = new Main();


        Jogador[] jogadores = main.gerarJogadores();


        //Inicializando Todas Propriedades em uma Lista
        List<Propriedade> propriedades = Utility.inicializarPropriedades();


        main.realizarTurno(propriedades, jogadores);
    }
}