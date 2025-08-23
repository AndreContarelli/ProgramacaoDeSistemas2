public class Episodio extends Midia {
    private long duracao;

    public Episodio(String titulo, long duracao){
        super(titulo);
        this.duracao = duracao;
    }

    public long getDuracao(){
        return duracao;
    }

    public String info(){
        return "Episódio: " + super.info() + "; Duração: " + duracao;
    }
}   