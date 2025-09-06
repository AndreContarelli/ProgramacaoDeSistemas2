import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    // === Ajuste aqui se quiser usar outra conexão ===
    private static final String URL = "jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.hywstzdzjftkkwrhpqjl&password=Acl150304!";

    // Modelo simples
    public static class Conta {
        public long nroConta;
        public double saldo;

        public Conta(long nroConta, double saldo) {
            this.nroConta = nroConta;
            this.saldo = saldo;
        }

        @Override
        public String toString() {
            return "Conta{nro_conta=" + nroConta + ", saldo=" + saldo + "}";
        }
    }

    public static void main(String[] args) {
        System.out.println("CRUD interativo de CONTAS (PostgreSQL)");
        try (Connection conn = DriverManager.getConnection(URL);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conexão realizada com sucesso.");
            boolean sair = false;
            while (!sair) {
                printMenu();
                System.out.print("Escolha uma opção: ");
                String opc = scanner.nextLine().trim();
                switch (opc) {
                    case "1":
                        opcCreate(conn, scanner);
                        break;
                    case "2":
                        opcReadOne(conn, scanner);
                        break;
                    case "3":
                        opcReadAll(conn);
                        break;
                    case "4":
                        opcUpdate(conn, scanner);
                        break;
                    case "5":
                        opcDelete(conn, scanner);
                        break;
                    case "6":
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
                System.out.println();
            }
            System.out.println("Saindo... conexão será fechada.");
        } catch (SQLException e) {
            System.err.println("Erro de conexão ou SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("=== MENU ===");
        System.out.println("1 - Criar nova conta (CREATE)");
        System.out.println("2 - Ler conta por número (READ one)");
        System.out.println("3 - Listar todas as contas (READ all)");
        System.out.println("4 - Atualizar saldo (UPDATE)");
        System.out.println("5 - Deletar conta (DELETE)");
        System.out.println("6 - Sair");
    }

    private static void opcCreate(Connection conn, Scanner scanner) {
        try {
            System.out.print("Número da nova conta (nro_conta): ");
            long nro = Long.parseLong(scanner.nextLine().trim());
            System.out.print("Saldo inicial: ");
            double saldo = Double.parseDouble(scanner.nextLine().trim());
            createConta(conn, nro, saldo);
            System.out.println("Conta criada com sucesso.");
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida: número ou saldo inapropriado.");
        } catch (SQLException ex) {
            System.out.println("Erro ao criar conta: " + ex.getMessage());
        }
    }

    private static void opcReadOne(Connection conn, Scanner scanner) {
        try {
            System.out.print("Número da conta a consultar: ");
            long nro = Long.parseLong(scanner.nextLine().trim());
            Conta c = readConta(conn, nro);
            if (c == null) {
                System.out.println("Conta não encontrada.");
            } else {
                System.out.println("Conta encontrada: " + c);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Número inválido.");
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar: " + ex.getMessage());
        }
    }

    private static void opcReadAll(Connection conn) {
        try {
            List<Conta> todas = readAllContas(conn);
            if (todas.isEmpty()) {
                System.out.println("Nenhuma conta encontrada.");
            } else {
                System.out.println("Lista de contas:");
                for (Conta c : todas) {
                    System.out.println("Número: " + c.nroConta + " - R$ " + c.saldo);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar contas: " + ex.getMessage());
        }
    }

    private static void opcUpdate(Connection conn, Scanner scanner) {
        try {
            System.out.print("Número da conta a atualizar: ");
            long nro = Long.parseLong(scanner.nextLine().trim());
            System.out.print("Novo saldo: ");
            double novoSaldo = Double.parseDouble(scanner.nextLine().trim());
            updateSaldo(conn, nro, novoSaldo);
            System.out.println("Saldo atualizado com sucesso.");
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida.");
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private static void opcDelete(Connection conn, Scanner scanner) {
        try {
            System.out.print("Número da conta a deletar: ");
            long nro = Long.parseLong(scanner.nextLine().trim());
            System.out.print("Tem certeza que deseja deletar a conta " + nro + "? (s/n): ");
            String confirma = scanner.nextLine().trim().toLowerCase();
            if (confirma.equals("s") || confirma.equals("sim")) {
                deleteConta(conn, nro);
                System.out.println("Conta deletada com sucesso.");
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Número inválido.");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar: " + ex.getMessage());
        }
    }

    public static void createConta(Connection c, long nroConta, double saldoInicial) throws SQLException {
        String sql = "INSERT INTO CONTAS (nro_conta, saldo) VALUES (?, ?)";
        try (PreparedStatement stm = c.prepareStatement(sql)) {
            stm.setLong(1, nroConta);
            stm.setDouble(2, saldoInicial);
            int linhas = stm.executeUpdate();
            if (linhas == 0) {
                throw new SQLException("Falha ao inserir: nenhuma linha afetada.");
            }
        }
    }

    public static Conta readConta(Connection c, long nroConta) throws SQLException {
        String sql = "SELECT nro_conta, saldo FROM CONTAS WHERE nro_conta = ?";
        try (PreparedStatement stm = c.prepareStatement(sql)) {
            stm.setLong(1, nroConta);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    long nro = rs.getLong("nro_conta");
                    double saldo = rs.getDouble("saldo");
                    return new Conta(nro, saldo);
                } else {
                    return null;
                }
            }
        }
    }

    public static List<Conta> readAllContas(Connection c) throws SQLException {
        String sql = "SELECT nro_conta, saldo FROM CONTAS ORDER BY nro_conta";
        List<Conta> lista = new ArrayList<>();
        try (PreparedStatement stm = c.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                long nro = rs.getLong("nro_conta");
                double saldo = rs.getDouble("saldo");
                lista.add(new Conta(nro, saldo));
            }
        }
        return lista;
    }

    public static void updateSaldo(Connection c, long nroConta, double novoSaldo) throws SQLException {
        String sql = "UPDATE CONTAS SET saldo = ? WHERE nro_conta = ?";
        try (PreparedStatement stm = c.prepareStatement(sql)) {
            stm.setDouble(1, novoSaldo);
            stm.setLong(2, nroConta);
            int linhas = stm.executeUpdate();
            if (linhas == 0) {
                throw new SQLException("Conta não encontrada para atualizar: " + nroConta);
            }
        }
    }

    public static void deleteConta(Connection c, long nroConta) throws SQLException {
        String sql = "DELETE FROM CONTAS WHERE nro_conta = ?";
        try (PreparedStatement stm = c.prepareStatement(sql)) {
            stm.setLong(1, nroConta);
            int linhas = stm.executeUpdate();
            if (linhas == 0) {
                throw new SQLException("Conta não encontrada para deletar: " + nroConta);
            }
        }
    }
}

