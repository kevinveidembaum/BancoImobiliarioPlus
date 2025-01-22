import java.util.List;
import java.util.Random;

public class Propriedade {
    private float valor;
    private Jogador dono;
    private boolean hipotecado;
    private boolean empresa;
    private int qntCasas;
    private float valorCasa;
    private float valorAluguel;
    private String nome;

    public Propriedade(String nome, float valor){
        this.nome = nome;
        this.valor = valor;
        this.valorCasa = generateRandomNumber(0, 50) + this.valor;
    }

    private void calcularAluguel(int qntCasas){

    }


    // Metodo para gerar números aleatórios, vai ser usado para calcular aluguel e valor Casas.
    private int generateRandomNumber(float min, float max) {
        return (int) (Math.random() * (max - min) + min);
    }


    public float getValorCasa() {
        return valorCasa;
    }

    public void setValorCasa(float valorCasa) {
        this.valorCasa = valorCasa;
    }

    public boolean isEmpresa() {
        return empresa;
    }

    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
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
