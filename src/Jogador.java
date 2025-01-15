import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private float dinheiro;
    private List<Propriedade> minhasPropriedades;
    private List<Emprestimo> emprestimosConcebidos;
    private List<Emprestimo> dividasEmprestimo;

    public Jogador(){
        this.minhasPropriedades = new ArrayList<>();
    }

    public void comprarPropriedade(Propriedade propriedade, Jogador dono){
        this.minhasPropriedades.add(propriedade);
        dono.minhasPropriedades.remove(propriedade);
        dono.setDinheiro(dono.getDinheiro() + propriedade.getValor());
        this.setDinheiro(this.getDinheiro() - propriedade.getValor());
    }

    public void venderPropriedade(Propriedade propriedade){
        float valorVenda = (float) (propriedade.getValor() * 0.75); //75% do valor da Propriedade

        this.setDinheiro(this.getDinheiro() + valorVenda);
        this.minhasPropriedades.remove(propriedade);
    }

    public void pagarAluguel(Propriedade propriedade, Jogador dono){
        if(this.getDinheiro() >= propriedade.getValorAluguel()){
            this.setDinheiro(this.getDinheiro() - propriedade.getValorAluguel());
            dono.setDinheiro(dono.getDinheiro() + propriedade.getValorAluguel());
        }else {
            System.out.println("Dinheiro Insuficiente!");
        }
    }

    public void fazerEmprestimo(Jogador credor, float valorEmprestar, Propriedade garantia){

    }

    public void pagarEmprestimo(Emprestimo emprestimo, float valorPagar){

    }

    public void receberPagamentoEmprestimo(Emprestimo pagamento){

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
