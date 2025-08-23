public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        //Não funciona porque classes abstratas não podem ser instanciadas
        //Midia midia = new Midia("A volta dos que não foram");
        //System.out.println(midia.info());

        Midia midia = new Filme("Titanic 2 and Knuckles", 300);
        System.out.println(midia.info());
        System.out.println(midia.getDuracao());

        Temporada temporada = new Temporada(1);
        temporada.adicionar(new Episodio("Piloto", 60));
        temporada.adicionar(new Episodio("Surpresa", 45));
        temporada.adicionar(new Episodio("Continuação", 30));
        System.out.println(temporada.info());
        System.out.println(temporada.getDuracao());
    }
}