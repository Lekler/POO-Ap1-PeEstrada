# Exemplo de Implementação com Banco de Dados

Este pacote contém exemplos não funcionais que demonstram como seria a implementação
do sistema utilizando um banco de dados físico (MySQL).

## Arquivos Incluídos:
- ConnectionFactory.java: Demonstra como gerenciar conexões com o banco
- FuncionarioRepositoryJDBC.java: Implementação do repositório de funcionários usando JDBC
- BancoRepositoryJDBC.java: Implementação do repositório de bancos usando JDBC

## Para Utilizar Este Exemplo:
1. Descomente as dependências no pom.xml
2. Execute o script SQL em src/main/resources/exemplos/criar_tabelas.sql
3. Modifique a classe App.java para usar as implementações JDBC dos repositórios

## Observações:
Este é apenas um exemplo didático. Em um ambiente de produção, considere:
- Usar um pool de conexões
- Implementar tratamento adequado de transações
- Utilizar um framework ORM como Hibernate
- Implementar tratamento robusto de exceções