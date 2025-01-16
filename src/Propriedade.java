public class Propriedade {
    private float valor;
    private Jogador dono;
    private boolean hipotecado;
    private int qntCasas;
    private float valorAluguel;
    private String nome;

    public Propriedade(String nome, float valor){
        this.nome = nome;
        this.valor = valor;
    }

    private void calcularAluguel(int qntCasas){

    }


    public void hipotecar(Propriedade propriedade){
        if(this.qntCasas == 0){

            if(dono.getMinhasPropriedades().contains(propriedade)){
                dono.getMinhasPropriedades().remove(propriedade);
                dono.setDinheiro(dono.getDinheiro() + propriedade.getValor());
            }

        }





    }



    public Jogador getDono() {
        return dono;
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
    }

    public boolean isHipotecado() {
        return hipotecado;
    }

    public void setHipotecado(boolean hipotecado) {
        this.hipotecado = hipotecado;
    }

    public int getQntCasas() {
        return qntCasas;
    }

    public void setQntCasas(int qntCasas) {
        this.qntCasas = qntCasas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValorAluguel() {
        return valorAluguel;
    }


    public void setValorAluguel(float valorAluguel) {
        this.valorAluguel = valorAluguel;
    }


    public float getValor() {
        return valor;
    }


    public void setValor(float valor) {
        this.valor = valor;
    }
}
