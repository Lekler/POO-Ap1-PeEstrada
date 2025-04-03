# Sistema de Folha de Pagamento - Transportadora "Pé na Estrada"

Sistema para gerenciamento de folha de pagamento desenvolvido em Java aplicando os conceitos de Programação Orientada a Objetos. Este projeto foi criado como parte da disciplina de Programação Orientada a Objetos do curso de Ciência de Dados e Inteligência Artificial.

## 📋 Índice

- [Descrição do Projeto](#-descrição-do-projeto)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Conceitos de POO Aplicados](#-conceitos-de-poo-aplicados)
- [Requisitos e Instalação](#-requisitos-e-instalação)
- [Como Executar](#-como-executar)
- [Funcionalidades Implementadas](#-funcionalidades-implementadas)
- [Testes_Unitários](#Testes-Unitários)
- [Exemplos de Persistência](#-exemplos-de-persistência)
- [Possíveis Extensões](#-possíveis-extensões)

## 📝 Descrição do Projeto

A Transportadora "Pé na Estrada" atua no ramo de transporte terrestre de cargas. Este sistema implementa a folha de pagamento da empresa, permitindo o cadastro de funcionários, registro de horas trabalhadas, cálculo de salários e emissão de contracheques e ordens de pagamento para bancos.

O projeto foi desenvolvido com foco na aplicação prática dos quatro pilares da Programação Orientada a Objetos: Abstração, Encapsulamento, Herança e Polimorfismo.

## 🏗️ Estrutura do Projeto

```
src/main/java/
  └── br/com/peestrada/
      ├── model/
      │   ├── pessoa/
      │   │   ├── Pessoa.java (classe abstrata)
      │   │   └── Funcionario.java
      │   ├── financeiro/
      │   │   ├── Banco.java
      │   │   ├── ContaCorrente.java
      │   │   ├── Contracheque.java
      │   │   └── OrdemPagamento.java
      │   └── empresa/
      │       ├── Setor.java
      │       └── FolhaFrequencia.java
      ├── service/
      │   ├── CalculadoraSalario.java (interface)
      │   └── ProcessadorFolhaPagamento.java
      │   └── ProcessadorFolhaPagamentoGerente.java
      ├── repository/
      │   ├── Repository.java (interface genérica)
      │   ├── FuncionarioRepository.java
      │   ├── BancoRepository.java
      │   └── SetorRepository.java
      ├── exemplos/
      │   └── db/
      │       ├── ConnectionFactory.java
      │       ├── FuncionarioRepositoryJDBC.java
      │       ├── BancoRepositoryJDBC.java
      │       └── SetorRepositoryJDBC.java
      └── App.java (classe principal)
      
src/test/java/
  └── br/com/peestrada/
      ├── model/
      │   ├── pessoa/
      │   │   └── FuncionarioTest.java
      │   └── financeiro/
      │       └── ContrachequeTest.java
      ├── service/
      │   └── ProcessadorFolhaPagamentoTest.java
      └── repository/
          └── FuncionarioRepositoryTest.java
```

## 🔍 Conceitos de POO Aplicados

### 1. Abstração
- **Classe Abstrata**: `Pessoa` com método abstrato `gerarIdentificacao()`
- **Interface**: `CalculadoraSalario` define operações para cálculo de salários sem especificar implementação
- **Repositórios**: Interfaces genéricas para manipulação de dados

### 2. Encapsulamento
- **Atributos Privados**: Todos os atributos das classes são declarados como `private`
- **Getters e Setters**: Controle de acesso aos atributos
- **Métodos Privados**: Encapsulamento de operações internas como `calcularSalarioLiquido()`

### 3. Polimorfismo
- **Sobrecarga de Métodos**: `calcularValorHorasExtras()` com diferentes parâmetros
- **Sobreposição de Métodos**: `calcularSalarioEspecifico()` sobreposto na classe `ProcessadorFolhaPagamentoGerente`

### 4. Herança
- **Superclasse/Subclasse**: `Pessoa` (superclasse) e `Funcionario` (subclasse)
- **Palavra-chave extends**: `Funcionario extends Pessoa`
- **Reuso de código**: Implementações base são herdadas e estendidas

## 💻 Requisitos e Instalação

### Pré-requisitos
- Java 14 ou superior
- Maven 3.8 ou superior
- IntelliJ IDEA (recomendado) ou outra IDE Java

### Configuração do Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/Lekler/POO-Ap1-PeEstrada.git
   cd POO-Ap1-PeEstrada
   ```

2. Importe o projeto no IntelliJ IDEA:
    - File > Open
    - Navegue até a pasta do projeto
    - Selecione o arquivo pom.xml e clique em "Open as Project"

3. Aguarde o Maven baixar as dependências necessárias.

## ▶️ Como Executar

1. No IntelliJ IDEA, localize a classe `App.java`
2. Clique com o botão direito e selecione "Run 'App.main()'"
3. O resultado será exibido no console da IDE

## ✅ Funcionalidades Implementadas

O sistema implementa todas as funcionalidades requisitadas:

1. **Cadastro de Funcionários**: Implementado na classe `Funcionario` com todos os atributos necessários
2. **Cadastro de Bancos**: Através da classe `Banco` e associação com contas correntes
3. **Registro de Frequência**: Classe `FolhaFrequencia` para controle de horas extras e faltas
4. **Cálculo de Salários**: Implementado via `CalculadoraSalario` e suas implementações
5. **Emissão de Contracheques**: Gerados em ordem de setor como solicitado
6. **Geração de Ordens de Pagamento**: Criadas por banco conforme especificado

## 🧪 Testes Unitários

O projeto inclui testes unitários abrangentes para validar o funcionamento das classes principais:

### Classes Testadas
- **FuncionarioTest**: Testa a classe Funcionario e suas operações (cálculo de horas extras, validações)
- **ContrachequeTest**: Verifica adição de proventos e descontos e cálculo correto do salário líquido
- **ProcessadorFolhaPagamentoTest**: Valida os cálculos de salário e benefícios
- **FuncionarioRepositoryTest**: Testa as operações CRUD do repositório

### Principais Casos de Teste
- Cálculo correto de horas extras (sobrecarga de métodos)
- Validação de dados (ex: salário não pode ser negativo)
- Cálculos salariais com diferentes regras de negócio
- Formatação correta de contracheques
- Operações de CRUD nos repositórios

### Executando os Testes
Para executar os testes unitários:

**Via Maven:**
   ```bash
  mvn test
   ```

**Via IntelliJ:**
1. Clique com o botão direito na pasta src/test/java
2. Selecione "Run 'All Tests'"

### Cobertura de Testes
Os testes unitários cobrem:
- Lógica de negócio crítica
- Validações de dados
- Operações de cálculo
- Persistência de dados

Estes testes garantem a robustez do sistema e facilitam futuras manutenções e evoluções.

## 💾 Exemplos de Persistência

O projeto inclui exemplos de como seria implementada uma versão com persistência em banco de dados físico:

- O pacote `br.com.peestrada.exemplos.db` contém implementações JDBC dos repositórios
- O arquivo `resources/exemplos/criar_tabelas.sql` contém os scripts para criação de tabelas
- Estes exemplos são apenas para referência e não afetam o funcionamento principal do sistema

Para utilizar a versão com banco de dados:
1. Descomente as dependências do MySQL no pom.xml
2. Execute o script SQL para criar o esquema do banco
3. Substitua os repositórios em memória pelos JDBC na classe App.java

## 🚀 Possíveis Extensões

Algumas sugestões para melhorar o sistema:

1. **Interface Gráfica**: Implementar uma interface com JavaFX ou Swing
2. **Persistência Real**: Integrar com um banco de dados como MySQL ou PostgreSQL
3. **Autenticação**: Adicionar sistema de login e níveis de acesso
4. **Relatórios**: Implementar geração de relatórios em PDF
5. **API REST**: Expor funcionalidades como uma API web
6. **Maior Cobertura de Testes**: Aumentar a cobertura de testes unitários e adicionar testes de integração

## 📱 Arquitetura

O projeto segue uma arquitetura em camadas:

- **Camada de Modelo**: Classes que representam entidades do domínio
- **Camada de Serviço**: Classes responsáveis pela lógica de negócio
- **Camada de Repositório**: Classes para persistência e acesso a dados
- **Camada de Aplicação**: Classe principal que orquestra o uso do sistema

Este padrão de arquitetura facilita a manutenção e evolução do código, permitindo substituir implementações específicas sem afetar o restante do sistema.

---

Projeto desenvolvido como trabalho da disciplina de Programação Orientada a Objetos do curso de Ciência de Dados e Inteligência Artificial.

Professor: Luando A. Veloso

Aluno: Alexandre R. Cordeiro

Data: 03/04/2025