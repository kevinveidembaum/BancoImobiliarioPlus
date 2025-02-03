import java.util.List;

public class Main {
    public static void main(String[] args){
        GameUtility jogo = new GameUtility();


        Jogador[] jogadores = jogo.gerarJogadores();


        //Inicializando Todas Propriedades em uma Lista
        List<Propriedade> propriedades = jogo.inicializarPropriedades();


        jogo.realizarTurno(propriedades, jogadores);
    }
}