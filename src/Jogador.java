import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private float dinheiro;
    private List<Propriedade> minhasPropriedades;
    private List<Emprestimo> emprestimosConcebidos;
    private List<Emprestimo> dividasEmprestimo;
    private String nome;


    public Jogador(String nome, float dinheiro){
        this.minhasPropriedades = new ArrayList<>();
        this.nome = nome;
        this.dinheiro = dinheiro;
    }


    public void comprarPropriedade(List<Propriedade> propriedades, int index){
        Propriedade propriedade = propriedades.get(index-1);

        if(! (this.getDinheiro() > propriedade.getValor())){
            System.out.println("Dinheiro insuficiente!");
            return;
        }

        if(propriedade.getDono() == null){
            //Propriedade NÃO tem dono
            this.getMinhasPropriedades().add(propriedade);
            propriedade.setDono(this);
            this.setDinheiro(this.getDinheiro() - propriedade.getValor());
            propriedades.remove(propriedade);
            System.out.printf("\n%s comprou a propriedade %s por %.2f!\n", this.nome, propriedade.getNome(), propriedade.getValor());
            return;
        }

        //Propriedade TEM dono
        Jogador donoAtual = propriedade.getDono();
        donoAtual.getMinhasPropriedades().remove(propriedade);
        donoAtual.setDinheiro(donoAtual.getDinheiro() + propriedade.getValor());

        this.getMinhasPropriedades().add(propriedade);
        propriedade.setDono(this);
        this.setDinheiro(this.getDinheiro() - propriedade.getValor());
        propriedades.remove(propriedade);
        System.out.printf("\n%s comprou a propriedade %s de %s por %.2f!\n", this.nome, propriedade.getNome(), donoAtual.getNome(), propriedade.getValor());
    }


    public void hipotecar(Propriedade propriedade){
        if(propriedade.getQntCasas() != 0){
            System.out.println("Para Hipotecar é necessário NÃO ter Casas na Propriedade!");
            return;
        }

        if(!propriedade.getDono().equals(this)){
            System.out.println("Não é possível Hipotecar Propriedade que não lhe pertence!");
            return;
        }

        if(!this.getMinhasPropriedades().contains(propriedade)){
            System.out.println("A propriedade não está na sua Lista de Propriedades!");
            return;
        }

        this.getMinhasPropriedades().remove(propriedade);
        this.setDinheiro(this.getDinheiro() + propriedade.getValor());
        propriedade.setDono(null);
        System.out.printf("\n%s foi Hipotecacda pelo(a) %s por %.2f!\n", propriedade.getNome(), this.getNome(), propriedade.getValor());
    }


    public void venderPropriedade(Propriedade propriedade){
        float valorVenda = (float) (propriedade.getValor() * 0.75); //75% do valor da Propriedade

        this.setDinheiro(this.getDinheiro() + valorVenda);
        this.minhasPropriedades.remove(propriedade);
        propriedade.setDono(null);
    }


    public void pagarAluguel(Propriedade propriedade, Jogador dono){
        if(!(this.getDinheiro() >= propriedade.getValorAluguel())){
            System.out.println("Dinheiro Insuficiente para Pagar Aluguel!");
            return;
        }

        this.setDinheiro(this.getDinheiro() - propriedade.getValorAluguel());
        dono.setDinheiro(dono.getDinheiro() + propriedade.getValorAluguel());
    }


    public void fazerEmprestimo(Jogador credor, float valorEmprestar, Propriedade garantia){

    }


    public void pagarEmprestimo(Emprestimo emprestimo, float valorPagar){

    }


    public void receberPagamentoEmprestimo(Emprestimo pagamento){

    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public float getDinheiro() {
        return dinheiro;
    }


    public void setDinheiro(float dinheiro) {
        this.dinheiro = dinheiro;
    }


    public List<Propriedade> getMinhasPropriedades() {
        return minhasPropriedades;
    }


    public void setMinhasPropriedades(List<Propriedade> minhasPropriedades) {
        this.minhasPropriedades = minhasPropriedades;
    }


    public List<Emprestimo> getDividasEmprestimo() {
        return dividasEmprestimo;
    }


    public void setDividasEmprestimo(List<Emprestimo> dividasEmprestimo) {
        this.dividasEmprestimo = dividasEmprestimo;
    }


    public List<Emprestimo> getEmprestimosConcebidos() {
        return emprestimosConcebidos;
    }


    public void setEmprestimosConcebidos(List<Emprestimo> emprestimosConcebidos) {
        this.emprestimosConcebidos = emprestimosConcebidos;
    }
}
