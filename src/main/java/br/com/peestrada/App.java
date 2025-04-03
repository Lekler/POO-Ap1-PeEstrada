package br.com.peestrada;

import br.com.peestrada.model.empresa.FolhaFrequencia;
import br.com.peestrada.model.empresa.Setor;
import br.com.peestrada.model.financeiro.Banco;
import br.com.peestrada.model.financeiro.ContaCorrente;
import br.com.peestrada.model.financeiro.Contracheque;
import br.com.peestrada.model.financeiro.OrdemPagamento;
import br.com.peestrada.model.pessoa.Funcionario;
import br.com.peestrada.repository.BancoRepository;
import br.com.peestrada.repository.FuncionarioRepository;
import br.com.peestrada.service.ProcessadorFolhaPagamento;
import br.com.peestrada.service.ProcessadorFolhaPagamentoGerente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe principal que demonstra o uso do sistema com repositórios
 */
public class App {

    // Repositórios
    private static final FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
    private static final BancoRepository bancoRepository = new BancoRepository();

    public static void main(String[] args) {
        // Inicializar dados base
        inicializarDados();

        // Processar folha de pagamento
        processarFolhaPagamento();
    }

    /**
     * Inicializa os dados básicos do sistema
     */
    private static void inicializarDados() {
        // Criação de setores
        Setor rh = new Setor("RH001", "Recursos Humanos", "Administrativo");
        Setor marketing = new Setor("MKT001", "Marketing", "Comercial");
        Setor operacoes = new Setor("OPE001", "Operações", "Logística");

        // Criação e cadastro de bancos
        Banco banco1 = new Banco("001", "Banco do Brasil");
        Banco banco2 = new Banco("341", "Itaú");

        bancoRepository.salvar(banco1);
        bancoRepository.salvar(banco2);

        // Criação e cadastro de funcionários
        Funcionario func1 = new Funcionario(
                "João Silva",
                "123.456.789-00",
                LocalDate.of(1980, 5, 15),
                "F001",
                5000.0,
                LocalDate.of(2010, 3, 10),
                rh
        );

        Funcionario func2 = new Funcionario(
                "Maria Souza",
                "987.654.321-00",
                LocalDate.of(1985, 8, 25),
                "F002",
                6000.0,
                LocalDate.of(2012, 7, 5),
                marketing
        );

        Funcionario func3 = new Funcionario(
                "Pedro Santos",
                "456.789.123-00",
                LocalDate.of(1990, 2, 8),
                "F003",
                4500.0,
                LocalDate.of(2015, 11, 20),
                operacoes
        );

        // Salvar funcionários no repositório
        funcionarioRepository.salvar(func1);
        funcionarioRepository.salvar(func2);
        funcionarioRepository.salvar(func3);

        // Criação de contas correntes
        ContaCorrente cc1 = new ContaCorrente(banco1, "1234", "56789-0", func1);
        ContaCorrente cc2 = new ContaCorrente(banco2, "5678", "12345-6", func2);
        ContaCorrente cc3 = new ContaCorrente(banco1, "9876", "54321-0", func3);

        // Associar contas aos funcionários
        func1.setContaCorrente(cc1);
        func2.setContaCorrente(cc2);
        func3.setContaCorrente(cc3);

        // Atualizar funcionários no repositório após associar contas
        funcionarioRepository.salvar(func1);
        funcionarioRepository.salvar(func2);
        funcionarioRepository.salvar(func3);
    }

    /**
     * Processa a folha de pagamento com base nos dados cadastrados
     */
    private static void processarFolhaPagamento() {
        // Obter todos os funcionários do repositório
        List<Funcionario> funcionarios = funcionarioRepository.listarTodos();

        // Criação das folhas de frequência para o mês corrente
        LocalDate mesAtual = LocalDate.now().withDayOfMonth(1);

        // Mapa para armazenar folhas de frequência
        Map<String, FolhaFrequencia> folhasFrequencia = new HashMap<>();

        // Criar folhas de frequência para cada funcionário
        for (Funcionario funcionario : funcionarios) {
            FolhaFrequencia folha = new FolhaFrequencia(funcionario, mesAtual);

            // Configurar dados de exemplo (em um sistema real, seriam informados pelo usuário)
            if (funcionario.getMatricula().equals("F001")) {
                folha.setHorasExtras(10);
                folha.setHorasNaoTrabalhadas(0);
            } else if (funcionario.getMatricula().equals("F002")) {
                folha.setHorasExtras(5);
                folha.setHorasNaoTrabalhadas(8);
            } else if (funcionario.getMatricula().equals("F003")) {
                folha.setHorasExtras(15);
                folha.setHorasNaoTrabalhadas(4);
            }

            folhasFrequencia.put(funcionario.getMatricula(), folha);
        }

        // Processamento da folha de pagamento
        ProcessadorFolhaPagamento processador = new ProcessadorFolhaPagamento();
        ProcessadorFolhaPagamentoGerente processadorGerente = new ProcessadorFolhaPagamentoGerente();

        // Geração de contracheques
        List<Contracheque> contracheques = new ArrayList<>();

        for (Funcionario funcionario : funcionarios) {
            FolhaFrequencia folha = folhasFrequencia.get(funcionario.getMatricula());

            Contracheque contracheque = new Contracheque(funcionario, mesAtual);
            contracheque.adicionarProvento("Salário Base", funcionario.getSalarioBase());
            contracheque.adicionarProvento("Horas Extras", funcionario.calcularValorHorasExtras(folha.getHorasExtras()));

            // Se for gerente (Maria), adicionar bônus gerencial
            if (funcionario.getMatricula().equals("F002")) {
                contracheque.adicionarProvento("Bônus Gerencial", funcionario.getSalarioBase() * 0.10);
            }

            contracheque.adicionarProvento("Benefícios", processador.calcularBeneficios(funcionario));
            contracheque.adicionarDesconto("INSS", funcionario.getSalarioBase() * 0.11);
            contracheque.adicionarDesconto("IR", funcionario.getSalarioBase() * 0.15);

            // Se tiver faltas, adicionar desconto
            if (folha.getHorasNaoTrabalhadas() > 0) {
                contracheque.adicionarDesconto("Faltas",
                        (funcionario.getSalarioBase() / 220) * folha.getHorasNaoTrabalhadas());
            }

            contracheques.add(contracheque);
        }

        // Ordenar contracheques por setor
        contracheques.sort((c1, c2) ->
                c1.getFuncionario().getSetor().getNome().compareTo(
                        c2.getFuncionario().getSetor().getNome()
                )
        );

        // Imprimir contracheques
        System.out.println("========== CONTRACHEQUES ==========");
        for (Contracheque contracheque : contracheques) {
            System.out.println(contracheque.imprimirContracheque());
        }

        // Geração de ordens de pagamento por banco
        Map<Banco, OrdemPagamento> ordensMap = new HashMap<>();

        for (Contracheque contracheque : contracheques) {
            Funcionario funcionario = contracheque.getFuncionario();
            ContaCorrente conta = funcionario.getContaCorrente();
            Banco banco = conta.getBanco();
            double valorLiquido = contracheque.getSalarioLiquido();

            // Verifica se já existe uma ordem para este banco
            if (!ordensMap.containsKey(banco)) {
                OrdemPagamento ordem = new OrdemPagamento(banco, LocalDate.now().withDayOfMonth(28));
                ordensMap.put(banco, ordem);
            }

            // Adiciona o pagamento à ordem
            ordensMap.get(banco).adicionarPagamento(conta, valorLiquido);
        }

        // Imprimir ordens de pagamento
        System.out.println("\n========== ORDENS DE PAGAMENTO ==========");
        for (OrdemPagamento ordem : ordensMap.values()) {
            System.out.println(ordem.gerarRelatorio());
        }
    }
}