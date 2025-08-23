import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppStreaming {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Midia> midias = new ArrayList<>();
        int opcao;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("(1) Adicionar novo Filme");
            System.out.println("(2) Adicionar nova Série");
            System.out.println("(3) Listar todas as mídias");
            System.out.println("(4) Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.print("Digite o título do filme: ");
                    String tituloFilme = sc.nextLine();
                    System.out.print("Digite a duração (em minutos): ");
                    long duracaoFilme = sc.nextLong();
                    sc.nextLine();
                    Filme filme = new Filme(tituloFilme, duracaoFilme);
                    midias.add(filme);
                    System.out.println("Filme adicionado com sucesso!");
                    break;

                case 2:
                    System.out.print("Digite o título da série: ");
                    String tituloSerie = sc.nextLine();
                    Serie serie = new Serie(tituloSerie);

                    for (int i = 1; i <= 2; i++) {
                        Temporada temporada = new Temporada(i);
                        System.out.println("Adicionando episódios para a Temporada " + i);
                        for (int j = 1; j <= 2; j++) {
                            System.out.print("Título do episódio " + j + ": ");
                            String tituloEp = sc.nextLine();
                            System.out.print("Duração do episódio (em minutos): ");
                            long duracaoEp = sc.nextLong();
                            sc.nextLine();
                            temporada.adicionar(new Episodio(tituloEp, duracaoEp));
                        }
                        serie.adicionar(temporada);
                    }

                    midias.add(serie);
                    System.out.println("Série adicionada com sucesso!");
                    break;

                case 3:
                    System.out.println("\n--- LISTA DE MÍDIAS ---");
                    for (Midia m : midias) {
                        System.out.println(m.info());
                    }
                    break;

                case 4:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 4);

        sc.close();
    }
}
