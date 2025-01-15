import java.util.List;

public class Main {
    public static void main(String[] args) {

        Jogador[] jogador = new Jogador[2];

        jogador[0] = new Jogador();
        jogador[1] = new Jogador();

        jogador[0].setDinheiro(1000);
        jogador[1].setDinheiro(1000);

        Propriedade propriedade = new Propriedade();
        propriedade.setValor(289);
        propriedade.setValorAluguel(43);

        System.out.println("valor aluguel" + propriedade.getValorAluguel());

        System.out.println("Dinheiro jogador 0: " + jogador[0].getDinheiro());
        System.out.println("Dinheiro jogador 1: " + jogador[1].getDinheiro());
        System.out.println("Propriedade jogador 0: " + jogador[0].getMinhasPropriedades());
        System.out.println("Propriedade jogador 1: " + jogador[1].getMinhasPropriedades());

        System.out.println("====================================================");



        jogador[0].comprarPropriedade(propriedade, jogador[1]);
        jogador[1].pagarAluguel(propriedade, jogador[0]);

        System.out.println("=========Jogador0 comprou propri e Jogador1 pagou aluguel===============");

        System.out.println("Dinheiro jogador 0: " + jogador[0].getDinheiro());
        System.out.println("Dinheiro jogador 1: " + jogador[1].getDinheiro());
        System.out.println("Propriedade jogador 0: " + jogador[0].getMinhasPropriedades());
        System.out.println("Propriedade jogador 1: " + jogador[1].getMinhasPropriedades());

        jogador[0].venderPropriedade(propriedade);

        System.out.println("==================Jogador0 vendeu propri============");

        System.out.println("Dinheiro jogador 0: " + jogador[0].getDinheiro());
        System.out.println("Dinheiro jogador 1: " + jogador[1].getDinheiro());
        System.out.println("Propriedade jogador 0: " + jogador[0].getMinhasPropriedades());













    }
}