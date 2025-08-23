import java.util.List;
import java.util.ArrayList;

public class Temporada extends Midia {
    private List<Episodio> episodios;

    public Temporada(int numero){
        super("Número: " + numero);
        episodios = new ArrayList<>();
    }

    public void adicionar(Episodio ep) {
        episodios.add(ep);
    }

    public long getDuracao(){
        long duracao = 0;
        for (int i = 0; i < episodios.size(); i++) {
            duracao += episodios.get(i).getDuracao();
        }
        return duracao;
    }

    public String info(){
        return "Temporada: " + super.info() + "; Duração: " + getDuracao();
    }
}   