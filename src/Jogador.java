import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private float dinheiro;
    private List<Propriedade> MinhasPropriedades;
    private List<Emprestimo> emprestimosConcebidos;
    private List<Emprestimo> dividasEmprestimo;

    public Jogador(){
        this.MinhasPropriedades = new ArrayList<>();
    }

    public void comprarPropriedade(Propriedade propriedade, Jogador dono, float valorPropriedade){
        this.MinhasPropriedades.add(propriedade);
        dono.MinhasPropriedades.remove(propriedade);
        dono.setDinheiro(dono.getDinheiro() + valorPropriedade);
        this.setDinheiro(this.getDinheiro() - valorPropriedade);
    }

    public void venderPropriedade(Propriedade propriedade, Jogador comprador){

    }

    public void pagarAluguel(float valorAluguel, Jogador dono){

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
        return MinhasPropriedades;
    }

    public void setMinhasPropriedades(List<Propriedade> MinhasPropriedades) {
        this.MinhasPropriedades = MinhasPropriedades;
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
