package com.ecommerce.model;

class ClienteMethodTest {

    public static void main(String[] args) {
        System.out.println("🧪 Testando métodos do Cliente...");
        
        // Teste 1: realizarPedido
        System.out.println("\n1. Testando realizarPedido():");
        Cliente cliente1 = new Cliente(1L, "João Silva", "joao@email.com");
        cliente1.setTelefone("11999999999");
        cliente1.setCidade("São Paulo");
        cliente1.realizarPedido();
        
        // Teste 2: avaliarEntrega
        System.out.println("\n2. Testando avaliarEntrega():");
        Cliente cliente2 = new Cliente(2L, "Maria Santos", "maria@email.com");
        cliente2.setDataNascimento(new java.util.Date());
        cliente2.setRole(1);
        cliente2.setCreatedAt(new java.util.Date());
        cliente2.setUpdatedAt(new java.util.Date());
        cliente2.avaliarEntrega();
        
        // Teste 3: demonstrarFuncionalidades
        System.out.println("\n3. Testando demonstrarFuncionalidades():");
        Cliente cliente3 = new Cliente(3L, "Pedro Costa", "pedro@email.com");
        cliente3.setTelefone("11888888888");
        cliente3.setCidade("Rio de Janeiro");
        cliente3.setDataNascimento(new java.util.Date());
        cliente3.setRole(2);
        cliente3.setCreatedAt(new java.util.Date());
        cliente3.setUpdatedAt(new java.util.Date());
        cliente3.demonstrarFuncionalidades();
        
        System.out.println("\n✅ Todos os testes executados com sucesso!");
    }
}
