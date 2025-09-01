# 🛒 Sistema E-Commerce - Interface de Linha de Comando (CLI)

## 📋 Descrição

Esta é a Interface de Linha de Comando (CLI) do Sistema E-Commerce que interage com o backend através de uma API REST. A CLI permite que diferentes tipos de usuários (Clientes, Gerentes e Entregadores) realizem suas operações específicas de forma intuitiva.

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven
- API REST rodando em `http://localhost:8080`

### Windows
```bash
# Execute o script batch
run-cli.bat
```

### Linux/Mac
```bash
# Torne o script executável (primeira vez)
chmod +x run-cli.sh

# Execute o script
./run-cli.sh
```

### Execução Manual
```bash
# Compilar o projeto
mvn clean compile

# Executar a CLI
java -cp "target/classes" com.ecommerce.cli.Main
```

## 👥 Tipos de Usuário

### 🛍️ Cliente
- **Login/Cadastro**: Acesso ao sistema com email e senha
- **Visualizar Produtos**: Lista todos os produtos disponíveis
- **Adicionar ao Carrinho**: Adiciona produtos ao carrinho de compras
- **Ver Carrinho**: Visualiza itens no carrinho
- **Fazer Pedido**: Finaliza a compra com endereço e pagamento
- **Acompanhar Pedidos**: Visualiza status dos pedidos realizados
- **Avaliar Pedido**: Avalia entregador e produtos após entrega
- **Gerenciar Endereços**: Adiciona e gerencia endereços de entrega

### 👨‍💼 Gerente
- **Gerenciar Produtos**: CRUD completo de produtos
- **Ver Todos os Pedidos**: Visualiza todos os pedidos do sistema
- **Alterar Status**: Muda status de "Preparando" para "Saindo para entrega"
- **Cancelar Pedido**: Cancela pedidos quando necessário
- **Gerar Relatórios**: Emite relatórios de vendas com filtros

### 🚚 Entregador
- **Ver Pedidos para Entrega**: Visualiza pedidos prontos para entrega
- **Marcar como Entregue**: Confirma entrega alterando status
- **Ver Avaliações**: Visualiza avaliações recebidas dos clientes

## 🔧 Funcionalidades Implementadas

### ✅ Autenticação
- Login com email e senha
- Cadastro de novos usuários
- Logout seguro
- Controle de sessão

### ✅ Gestão de Produtos
- Listagem de produtos
- Adição de produtos (Gerente)
- Edição de produtos (Gerente)
- Remoção de produtos (Gerente)
- Busca por código de barras

### ✅ Carrinho de Compras
- Adicionar produtos
- Visualizar carrinho
- Gerenciar quantidades

### ✅ Pedidos
- Criação de pedidos
- Acompanhamento de status
- Cancelamento de pedidos
- Avaliação pós-entrega

### ✅ Endereços
- Cadastro de endereços
- Listagem de endereços
- Gerenciamento de endereços

### ✅ Pagamentos
- Múltiplos métodos de pagamento
- Processamento via API externa
- Reprocessamento em caso de falha

### ✅ Relatórios
- Relatórios de vendas
- Filtros por data e produto
- Exportação de dados

## 🎯 Casos de Uso Implementados

### RF01 - Login e Cadastro
- ✅ Validação de email e senha
- ✅ Cadastro com validações

### RF02 - Visualização de Produtos
- ✅ Lista produtos com detalhes
- ✅ Exibe nome, preço, descrição, código de barras

### RF03 - Carrinho de Compras
- ✅ Adicionar produtos por código de barras
- ✅ Gerenciar quantidades

### RF04 - Endereço de Entrega
- ✅ Campos obrigatórios validados
- ✅ Complemento opcional

### RF05 - Métodos de Pagamento
- ✅ Múltiplas opções disponíveis
- ✅ Integração com sistema externo

### RF06 - Comunicação com Sistema de Pagamento
- ✅ API REST para processamento
- ✅ Tratamento de respostas

### RF07 - Registro de Pedidos
- ✅ Número único por pedido
- ✅ Confirmação após pagamento

### RF08 - Notificações
- ✅ Notificações de mudança de status
- ✅ Interface para acompanhamento

### RF09 - Reprocessamento de Pagamento
- ✅ Nova tentativa em caso de falha
- ✅ Solicitação de método alternativo

### RF10 - Alteração de Status
- ✅ Gerente: "Preparando" → "Saindo para entrega"
- ✅ Entregador: "Saindo para entrega" → "Entregue"

### RF11 - Cancelamento de Pedidos
- ✅ Cliente e Gerente podem cancelar
- ✅ Apenas antes da entrega

### RF12 - Avaliações
- ✅ Avaliação de entregador (1-5)
- ✅ Avaliação de produtos (1-5)
- ✅ Comentários opcionais

### RF13 - Relatórios de Vendas
- ✅ Filtros por data
- ✅ Filtros por produto
- ✅ Relatórios consolidados

## 🔒 Segurança

- Validação de entrada de dados
- Autenticação por token
- Controle de acesso por perfil
- Validação de senhas fortes

## 📱 Interface

- Menu interativo por perfil de usuário
- Navegação intuitiva
- Mensagens de feedback claras
- Tratamento de erros amigável

## 🧪 Testes

Para testar a CLI:

1. **Inicie a API REST**:
   ```bash
   mvn spring-boot:run
   ```

2. **Em outro terminal, execute a CLI**:
   ```bash
   ./run-cli.sh  # Linux/Mac
   # ou
   run-cli.bat   # Windows
   ```

3. **Teste os fluxos**:
   - Cadastre um usuário
   - Faça login
   - Explore as funcionalidades do seu perfil

## 📊 Status do Projeto

✅ **API REST**: Implementada e testada  
✅ **CLI**: Implementada e funcional  
✅ **Casos de Uso**: Todos implementados  
✅ **Requisitos Funcionais**: Todos atendidos  
✅ **Requisitos Não Funcionais**: Todos atendidos  

## 🎉 Projeto Completo!

O sistema está **100% funcional** com:
- Backend em Java com Spring Boot
- API REST completa
- Interface CLI interativa
- Todos os casos de uso implementados
- Testes passando
- Documentação completa

**Pronto para entrega!** 🚀
