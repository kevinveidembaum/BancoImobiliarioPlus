import java.util.List;

public class Jogador {
    private float dinheiro;
    private List<Propriedade> propriedade;
    private List<Emprestimo> emprestimosConcebidos;
    private List<Emprestimo> dividasEmprestimo;

    public void comprarProprieda(Propriedade propriedade, Jogador dono){

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

    public List<Propriedade> getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(List<Propriedade> propriedade) {
        this.propriedade = propriedade;
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
