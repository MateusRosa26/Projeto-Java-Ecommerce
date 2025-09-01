# 🚀 Como Executar a CLI no IntelliJ IDEA

## 📋 Pré-requisitos
- ✅ API REST rodando (EcommerceApplication)
- ✅ Projeto compilado

## 🎯 Método 1: Executar Diretamente (Mais Fácil)

1. **Abra o arquivo**: `src/main/java/com/ecommerce/cli/Main.java`
2. **Clique no ícone ▶️** ao lado da linha `public static void main(String[] args)`
3. **Ou clique com botão direito** no arquivo → "Run 'Main.main()'"

## 🎯 Método 2: Configurar Run Configuration

1. **Menu**: Run → Edit Configurations...
2. **Clique**: "+" → Application
3. **Configure**:
   ```
   Name: Ecommerce CLI
   Main class: com.ecommerce.cli.Main
   Module: [seu-projeto]
   ```
4. **OK** → Execute com ▶️

## 🎯 Método 3: Terminal Integrado

1. **Abra**: View → Tool Windows → Terminal
2. **Execute**:
   ```bash
   java -cp "target/classes" com.ecommerce.cli.Main
   ```

## 🎯 Método 4: Atalho de Teclado

1. **Pressione**: `Ctrl + Shift + F10` (Windows/Linux) ou `Ctrl + Shift + R` (Mac)
2. **Selecione**: `Main.main()`

## 🔧 Solução de Problemas

### ❌ Erro: "Cannot find main class"
- **Solução**: Compile o projeto primeiro (Build → Rebuild Project)

### ❌ Erro: "API REST não está rodando"
- **Solução**: Execute `EcommerceApplication` primeiro

### ❌ Erro: "Class not found"
- **Solução**: Verifique se o classpath está correto

## 🎉 Resultado Esperado

```
🛒 === SISTEMA E-COMMERCE === 🛒
Bem-vindo ao sistema de gerenciamento de pedidos!
✅ API REST conectada com sucesso!

=== MENU DE ACESSO ===
1. Login
2. Cadastro
3. Sair
Escolha uma opção:
```

## 💡 Dica Pro

**Configure duas janelas do IntelliJ**:
- **Janela 1**: API REST (EcommerceApplication)
- **Janela 2**: CLI (Main)

Assim você pode ver ambos rodando simultaneamente! 🚀
