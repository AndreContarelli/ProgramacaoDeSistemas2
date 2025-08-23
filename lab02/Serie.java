import java.util.ArrayList;
import java.util.List;

public class Serie extends Midia {
    private List<Temporada> temporadas;

    public Serie(String titulo) {
        super(titulo);
        temporadas = new ArrayList<>();
    }

    public void adicionar(Temporada temporada) {
        temporadas.add(temporada);
    }

    @Override
    public long getDuracao() {
        long duracao = 0;
        for (Temporada t : temporadas) {
            duracao += t.getDuracao();
        }
        return duracao;
    }

    @Override
    public String info() {
        return "Série: " + super.info() + "; Duração Total: " + getDuracao();
    }
}
