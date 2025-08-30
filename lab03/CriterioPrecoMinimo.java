public class CriterioPrecoMinimo implements CriterioBusca {
    @Override
    public boolean testar(Produto p, String valor) {
        try {
            double precoMinimo = Double.parseDouble(valor);
            return p.getPreco() >= precoMinimo;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
