public class Filme extends Midia {
    private long duracao;

    public Filme(String titulo, long duracao) {      
        super(titulo);
        this.duracao = duracao;
    }

    @Override
    public long getDuracao() {
        return duracao;
    }

    @Override
    public String info() {
        return "Filme: " + super.info() + "; Duração: " + duracao;
    }
}