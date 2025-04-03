-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS peestrada;
USE peestrada;

-- Tabela de Setores
CREATE TABLE IF NOT EXISTS setores (
                                       codigo VARCHAR(10) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL
    );

-- Tabela de Funcionários
CREATE TABLE IF NOT EXISTS funcionarios (
                                            matricula VARCHAR(10) PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    salario_base DECIMAL(10, 2) NOT NULL,
    data_admissao DATE NOT NULL,
    codigo_setor VARCHAR(10) NOT NULL,
    FOREIGN KEY (codigo_setor) REFERENCES setores(codigo)
    );

-- Tabela de Bancos
CREATE TABLE IF NOT EXISTS bancos (
                                      codigo VARCHAR(5) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
    );

-- Tabela de Contas Correntes
CREATE TABLE IF NOT EXISTS contas_correntes (
                                                id INT AUTO_INCREMENT PRIMARY KEY,
                                                banco_codigo VARCHAR(5) NOT NULL,
    agencia VARCHAR(10) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    funcionario_matricula VARCHAR(10) NOT NULL,
    FOREIGN KEY (banco_codigo) REFERENCES bancos(codigo),
    FOREIGN KEY (funcionario_matricula) REFERENCES funcionarios(matricula),
    UNIQUE (banco_codigo, agencia, numero)
    );

-- Tabela de Folhas de Frequência
CREATE TABLE IF NOT EXISTS folhas_frequencia (
                                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                                 funcionario_matricula VARCHAR(10) NOT NULL,
    mes_referencia DATE NOT NULL,
    horas_extras INT NOT NULL DEFAULT 0,
    horas_nao_trabalhadas INT NOT NULL DEFAULT 0,
    observacoes TEXT,
    FOREIGN KEY (funcionario_matricula) REFERENCES funcionarios(matricula),
    UNIQUE (funcionario_matricula, mes_referencia)
    );