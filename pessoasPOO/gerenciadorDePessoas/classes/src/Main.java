import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Lista de cadastros
    static ArrayList<Pessoa> pessoas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Cadastro de Pessoas ===");
            System.out.println("1. Cadastrar nova pessoa");
            System.out.println("2. Listar todas as pessoas");
            System.out.println("3. Buscar pessoa por nome");
            System.out.println("4. Atualizar dados de uma pessoa");
            System.out.println("5. Remover pessoa pelo nome");
            System.out.println("6. Sair");
            System.out.print("Digite uma opção: ");

            // lê a opção e consome a quebra de linha
            while (!sc.hasNextInt()) { sc.nextLine(); System.out.print("Opção inválida. Digite um número: "); }
            opcao = sc.nextInt();
            sc.nextLine(); // consome o '\n' que ficou

            switch (opcao) {
                case 1 -> cadastrarPessoa(sc);
                case 2 -> listarPessoas();
                case 3 -> buscarPessoaPorNome(sc);
                case 4 -> atualizarPessoa(sc);
                case 5 -> removerPessoa(sc);
                case 6 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida! Escolha entre 1 e 6.");
            }
        } while (opcao != 6);

        sc.close();
    }

    // 1) Cadastrar
    static void cadastrarPessoa(Scanner sc) {
        Pessoa p = new Pessoa();

        System.out.print("Nome: ");
        p.nome = sc.nextLine();

        System.out.print("Idade: ");
        while (!sc.hasNextInt()) { sc.nextLine(); System.out.print("Digite uma idade válida: "); }
        p.idade = sc.nextInt();
        sc.nextLine(); // consome o '\n'

        System.out.print("Telefone: ");
        p.telefone = sc.nextLine();

        System.out.print("Email: ");
        p.email = sc.nextLine();

        pessoas.add(p);
        System.out.println("✅ Pessoa cadastrada!");
    }

    // 2) Listar
    static void listarPessoas() {
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }
        System.out.println("\n--- Lista de Pessoas ---");
        for (int i = 0; i < pessoas.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, pessoas.get(i)); // usa toString()
        }
    }

    // 3) Buscar por nome (mostra todas as correspondências)
    static void buscarPessoaPorNome(Scanner sc) {
        System.out.print("Digite o nome para buscar: ");
        String termo = sc.nextLine();

        ArrayList<Pessoa> resultados = new ArrayList<>();
        for (Pessoa p : pessoas) {
            if (p.nome.toLowerCase().contains(termo.toLowerCase())) {
                resultados.add(p);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma pessoa encontrada com esse nome.");
        } else {
            System.out.println("\nResultados da busca:");
            for (Pessoa p : resultados) System.out.println(p);
        }
    }

    // 4) Atualizar (procura pelo nome e atualiza o primeiro encontrado)
    static void atualizarPessoa(Scanner sc) {
        System.out.print("Digite o nome exato da pessoa a atualizar: ");
        String nomeBusca = sc.nextLine();
        int idx = findIndexByName(nomeBusca);

        if (idx == -1) {
            System.out.println("Pessoa não encontrada.");
            return;
        }

        Pessoa atual = pessoas.get(idx);
        System.out.println("Registro atual: " + atual);
        System.out.println("Pressione ENTER para manter o valor atual.");

        System.out.print("Novo nome (" + atual.nome + "): ");
        String novoNome = sc.nextLine();
        if (!novoNome.isBlank()) atual.nome = novoNome;

        System.out.print("Nova idade (" + atual.idade + "): ");
        String idadeStr = sc.nextLine();
        if (!idadeStr.isBlank()) {
            try { atual.idade = Integer.parseInt(idadeStr); }
            catch (NumberFormatException e) { System.out.println("Idade inválida. Mantido valor anterior."); }
        }

        System.out.print("Novo telefone (" + atual.telefone + "): ");
        String novoTel = sc.nextLine();
        if (!novoTel.isBlank()) atual.telefone = novoTel;

        System.out.print("Novo email (" + atual.email + "): ");
        String novoEmail = sc.nextLine();
        if (!novoEmail.isBlank()) atual.email = novoEmail;

        System.out.println("✅ Dados atualizados!");
    }

    // 5) Remover por nome (remove o primeiro que bater)
    static void removerPessoa(Scanner sc) {
        System.out.print("Digite o nome exato da pessoa a remover: ");
        String nomeBusca = sc.nextLine();
        int idx = findIndexByName(nomeBusca);

        if (idx == -1) {
            System.out.println("Pessoa não encontrada.");
        } else {
            Pessoa removida = pessoas.remove(idx);
            System.out.println("✅ Removido: " + removida.nome);
        }
    }

    // Utilitário: retorna o índice da primeira pessoa com nome igual (ignorando maiúsc./minúsc.)
    static int findIndexByName(String nome) {
        for (int i = 0; i < pessoas.size(); i++) {
            if (pessoas.get(i).nome.equalsIgnoreCase(nome)) return i;
        }
        return -1;
    }
}
