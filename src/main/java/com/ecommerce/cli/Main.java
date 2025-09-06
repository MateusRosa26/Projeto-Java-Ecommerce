package com.ecommerce.cli;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Main {
    private static final String API_BASE_URL = "http://localhost:8080/api";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);
    
    // Estado da sessão
    private static String currentUserToken = null;
    private static String currentUserEmail = null;
    private static String currentUserRole = null; // "CLIENTE", "GERENTE", "ENTREGADOR"

    public static void main(String[] args) {
        System.out.println("🛒 === SISTEMA E-COMMERCE === 🛒");
        System.out.println("Bem-vindo ao sistema de gerenciamento de pedidos!");
        
        // Verificar se a API está rodando
        if (!checkApiHealth()) {
            System.out.println("\n❌ ERRO: API REST não está rodando!");
            System.out.println("💡 Para usar a CLI, você precisa:");
            System.out.println("   1. Abrir o IntelliJ IDEA");
            System.out.println("   2. Executar a classe EcommerceApplication");
            System.out.println("   3. Aguardar a mensagem 'Started EcommerceApplication'");
            System.out.println("   4. Executar a CLI novamente");
            System.out.println("\n🌐 URL esperada: http://localhost:8080");
            System.out.println("\nPressione ENTER para sair...");
            scanner.nextLine();
            System.exit(1);
        }
        
        System.out.println("✅ API REST conectada com sucesso!");
        
        while (true) {
            if (currentUserToken == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }
    
    private static boolean checkApiHealth() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/health"))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n=== MENU DE ACESSO ===");
        System.out.println("1. Login");
        System.out.println("2. Cadastro");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> login();
            case "2" -> register();
            case "3" -> {
                System.out.println("Obrigado por usar o sistema! 👋");
                System.exit(0);
            }
            default -> System.out.println("❌ Opção inválida!");
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("👤 Usuário: " + currentUserEmail + " (" + currentUserRole + ")");
        System.out.println();
        
        if ("CLIENTE".equals(currentUserRole)) {
            showClienteMenu();
        } else if ("GERENTE".equals(currentUserRole)) {
            showGerenteMenu();
        } else if ("ENTREGADOR".equals(currentUserRole)) {
            showEntregadorMenu();
        }
    }

    private static void showClienteMenu() {
        System.out.println("1. 📦 Ver produtos disponíveis");
        System.out.println("2. 🛒 Adicionar produto ao carrinho");
        System.out.println("3. 🛍️ Ver carrinho");
        System.out.println("4. 📋 Fazer pedido");
        System.out.println("5. 📊 Acompanhar meus pedidos");
        System.out.println("6. ⭐ Avaliar pedido entregue");
        System.out.println("7. 🏠 Gerenciar endereços");
        System.out.println("8. 🚪 Logout");
        System.out.print("Escolha uma opção: ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> listProducts();
            case "2" -> addToCart();
            case "3" -> viewCart();
            case "4" -> createOrder();
            case "5" -> trackOrders();
            case "6" -> evaluateOrder();
            case "7" -> manageAddresses();
            case "8" -> logout();
            default -> System.out.println("❌ Opção inválida!");
        }
    }

    private static void showGerenteMenu() {
        System.out.println("1. 📦 Gerenciar produtos");
        System.out.println("2. 📋 Ver todos os pedidos");
        System.out.println("3. 🚚 Alterar status do pedido");
        System.out.println("4. ❌ Cancelar pedido");
        System.out.println("5. 📊 Gerar relatório de vendas");
        System.out.println("6. 🚪 Logout");
        System.out.print("Escolha uma opção: ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> manageProducts();
            case "2" -> listAllOrders();
            case "3" -> updateOrderStatus();
            case "4" -> cancelOrder();
            case "5" -> generateReport();
            case "6" -> logout();
            default -> System.out.println("❌ Opção inválida!");
        }
    }

    private static void showEntregadorMenu() {
        System.out.println("1. 📋 Ver pedidos para entrega");
        System.out.println("2. 🚚 Marcar pedido como entregue");
        System.out.println("3. ⭐ Ver minhas avaliações");
        System.out.println("4. 🚪 Logout");
        System.out.print("Escolha uma opção: ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> viewDeliveryOrders();
            case "2" -> markOrderAsDelivered();
            case "3" -> viewMyRatings();
            case "4" -> logout();
            default -> System.out.println("❌ Opção inválida!");
        }
    }

    // === MÉTODOS DE AUTENTICAÇÃO ===
    
    private static void login() {
        System.out.print("📧 Email: ");
        String email = scanner.nextLine();
        System.out.print("🔒 Senha: ");
        String password = scanner.nextLine();
        
        String jsonBody = String.format("""
            {
                "username": "%s",
                "email": "%s"
            }
            """, email, password);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/users/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                // Simular resposta de login bem-sucedida
                currentUserToken = "token-" + System.currentTimeMillis();
                currentUserEmail = email;
                currentUserRole = "CLIENTE"; // Por padrão, mas deveria vir da resposta
                System.out.println("✅ Login realizado com sucesso!");
            } else {
                System.out.println("❌ Falha no login: " + response.body());
            }
        } catch (Exception e) {
            if (e.getMessage() == null || e.getMessage().contains("Connection refused") || e.getMessage().contains("Impossível conectar")) {
                System.out.println("❌ ERRO: API REST não está rodando!");
                System.out.println("💡 Certifique-se de que a API está rodando no IntelliJ (EcommerceApplication)");
                System.out.println("🌐 URL esperada: http://localhost:8080");
            } else {
                System.out.println("❌ Erro ao fazer login: " + e.getMessage());
            }
        }
    }

    private static void register() {
        System.out.print("📧 Email: ");
        String email = scanner.nextLine();
        System.out.print("🔒 Senha: ");
        String password = scanner.nextLine();
        System.out.print("👤 Nome: ");
        String name = scanner.nextLine();
        System.out.print("📱 Telefone: ");
        String phone = scanner.nextLine();
        
        String jsonBody = String.format("""
            {
                "username": "%s",
                "nome": "%s",
                "email": "%s"
            }
            """, email, name, email);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/users/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 201) {
                System.out.println("✅ Cadastro realizado com sucesso! Faça login para continuar.");
            } else {
                System.out.println("❌ Falha no cadastro: " + response.body());
            }
        } catch (Exception e) {
            if (e.getMessage() == null || e.getMessage().contains("Connection refused") || e.getMessage().contains("Impossível conectar")) {
                System.out.println("❌ ERRO: API REST não está rodando!");
                System.out.println("💡 Certifique-se de que a API está rodando no IntelliJ (EcommerceApplication)");
                System.out.println("🌐 URL esperada: http://localhost:8080");
            } else {
                System.out.println("❌ Erro ao fazer cadastro: " + e.getMessage());
            }
        }
    }

    private static void logout() {
        currentUserToken = null;
        currentUserEmail = null;
        currentUserRole = null;
        System.out.println("👋 Logout realizado com sucesso!");
    }

    // === MÉTODOS DO CLIENTE ===
    
    private static void listProducts() {
        System.out.println("\n📦 === PRODUTOS DISPONÍVEIS ===");
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/products"))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("❌ Erro ao buscar produtos: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar produtos: " + e.getMessage());
        }
    }

    private static void addToCart() {
        System.out.print("📦 Código do produto: ");
        String productCode = scanner.nextLine();
        System.out.print("🔢 Quantidade: ");
        String quantity = scanner.nextLine();
        
        String jsonBody = String.format("""
            {
                "productCode": "%s",
                "quantity": %s
            }
            """, productCode, quantity);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/products/addToCart"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("✅ Produto adicionado ao carrinho!");
            } else {
                System.out.println("❌ Erro ao adicionar ao carrinho: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao adicionar ao carrinho: " + e.getMessage());
        }
    }

    private static void viewCart() {
        System.out.println("\n🛒 === MEU CARRINHO ===");
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders/cart"))
                .header("Authorization", "Bearer " + currentUserToken)
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("❌ Erro ao buscar carrinho: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar carrinho: " + e.getMessage());
        }
    }

    private static void createOrder() {
        System.out.println("\n📋 === FAZER PEDIDO ===");
        
        // Primeiro, mostrar endereços disponíveis
        System.out.println("Seus endereços:");
        System.out.println("1. Rua das Flores, 123 - Centro, São Paulo/SP");
        System.out.println("2. Av. Paulista, 1000 - Bela Vista, São Paulo/SP");
        
        System.out.print("🏠 ID do endereço de entrega: ");
        String addressId = scanner.nextLine();
        
        System.out.println("💳 Métodos de pagamento disponíveis:");
        System.out.println("1. Cartão de crédito");
        System.out.println("2. Cartão de débito");
        System.out.println("3. PIX");
        System.out.println("4. Boleto");
        System.out.println("5. Dinheiro");
        System.out.print("Escolha o método de pagamento: ");
        String paymentMethod = scanner.nextLine();
        
        String jsonBody = String.format("""
            {
                "addressId": %s,
                "paymentMethod": "%s"
            }
            """, addressId, paymentMethod);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders/finishOrder"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + currentUserToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 201) {
                System.out.println("✅ Pedido criado com sucesso!");
                System.out.println("📧 Você receberá notificações sobre o status do pedido.");
            } else {
                System.out.println("❌ Erro ao criar pedido: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao criar pedido: " + e.getMessage());
        }
    }

    private static void trackOrders() {
        System.out.println("\n📊 === MEUS PEDIDOS ===");
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders"))
                .header("Authorization", "Bearer " + currentUserToken)
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("❌ Erro ao buscar pedidos: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar pedidos: " + e.getMessage());
        }
    }

    private static void evaluateOrder() {
        System.out.print("📋 ID do pedido para avaliar: ");
        String orderId = scanner.nextLine();
        
        System.out.print("⭐ Nota para o entregador (1-5): ");
        String deliveryRating = scanner.nextLine();
        System.out.print("💬 Comentário sobre a entrega: ");
        String deliveryComment = scanner.nextLine();
        
        System.out.print("⭐ Nota para os produtos (1-5): ");
        String productRating = scanner.nextLine();
        System.out.print("💬 Comentário sobre os produtos: ");
        String productComment = scanner.nextLine();
        
        String jsonBody = String.format("""
            {
                "deliveryRating": %s,
                "deliveryComment": "%s",
                "productRating": %s,
                "productComment": "%s"
            }
            """, deliveryRating, deliveryComment, productRating, productComment);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders/" + orderId + "/evaluate"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + currentUserToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("✅ Avaliação enviada com sucesso!");
            } else {
                System.out.println("❌ Erro ao enviar avaliação: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao enviar avaliação: " + e.getMessage());
        }
    }

    private static void manageAddresses() {
        System.out.println("\n🏠 === GERENCIAR ENDEREÇOS ===");
        System.out.println("1. Ver meus endereços");
        System.out.println("2. Adicionar novo endereço");
        System.out.println("3. Voltar");
        System.out.print("Escolha uma opção: ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> listAddresses();
            case "2" -> addAddress();
            case "3" -> { /* Voltar */ }
            default -> System.out.println("❌ Opção inválida!");
        }
    }

    private static void listAddresses() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/addresses/user/" + currentUserEmail))
                .header("Authorization", "Bearer " + currentUserToken)
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("❌ Erro ao buscar endereços: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar endereços: " + e.getMessage());
        }
    }

    private static void addAddress() {
        System.out.print("🏠 Rua: ");
        String street = scanner.nextLine();
        System.out.print("🔢 Número: ");
        String number = scanner.nextLine();
        System.out.print("🏘️ Bairro: ");
        String neighborhood = scanner.nextLine();
        System.out.print("🏙️ Cidade: ");
        String city = scanner.nextLine();
        System.out.print("🌍 Estado: ");
        String state = scanner.nextLine();
        System.out.print("📮 CEP: ");
        String zipCode = scanner.nextLine();
        System.out.print("📝 Complemento (opcional): ");
        String complement = scanner.nextLine();
        
        String jsonBody = String.format("""
            {
                "street": "%s",
                "number": "%s",
                "neighborhood": "%s",
                "city": "%s",
                "state": "%s",
                "zipCode": "%s",
                "complement": "%s"
            }
            """, street, number, neighborhood, city, state, zipCode, complement);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/addresses"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + currentUserToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 201) {
                System.out.println("✅ Endereço adicionado com sucesso!");
            } else {
                System.out.println("❌ Erro ao adicionar endereço: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao adicionar endereço: " + e.getMessage());
        }
    }

    // === MÉTODOS DO GERENTE ===
    
    private static void manageProducts() {
        System.out.println("\n📦 === GERENCIAR PRODUTOS ===");
        System.out.println("1. Ver todos os produtos");
        System.out.println("2. Adicionar produto");
        System.out.println("3. Editar produto");
        System.out.println("4. Remover produto");
        System.out.println("5. Voltar");
        System.out.print("Escolha uma opção: ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> listProducts();
            case "2" -> addProduct();
            case "3" -> editProduct();
            case "4" -> removeProduct();
            case "5" -> { /* Voltar */ }
            default -> System.out.println("❌ Opção inválida!");
        }
    }

    private static void addProduct() {
        System.out.print("📦 Nome do produto: ");
        String name = scanner.nextLine();
        System.out.print("💰 Preço: ");
        String price = scanner.nextLine();
        System.out.print("📝 Descrição: ");
        String description = scanner.nextLine();
        System.out.print("🏷️ Código de barras: ");
        String barcode = scanner.nextLine();
        System.out.print("🖼️ URL da imagem: ");
        String imageUrl = scanner.nextLine();
        
        String jsonBody = String.format("""
            {
                "name": "%s",
                "price": %s,
                "description": "%s",
                "barcode": "%s",
                "imageUrl": "%s"
            }
            """, name, price, description, barcode, imageUrl);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/products"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + currentUserToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 201) {
                System.out.println("✅ Produto adicionado com sucesso!");
            } else {
                System.out.println("❌ Erro ao adicionar produto: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao adicionar produto: " + e.getMessage());
        }
    }

    private static void editProduct() {
        System.out.print("📦 ID do produto para editar: ");
        String productId = scanner.nextLine();
        
        System.out.print("📦 Novo nome (deixe vazio para manter): ");
        String name = scanner.nextLine();
        System.out.print("💰 Novo preço (deixe vazio para manter): ");
        String price = scanner.nextLine();
        System.out.print("📝 Nova descrição (deixe vazio para manter): ");
        String description = scanner.nextLine();
        
        String jsonBody = String.format("""
            {
                "name": "%s",
                "price": %s,
                "description": "%s"
            }
            """, name.isEmpty() ? "null" : name, 
                 price.isEmpty() ? "null" : price, 
                 description.isEmpty() ? "null" : description);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/products/" + productId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + currentUserToken)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("✅ Produto atualizado com sucesso!");
            } else {
                System.out.println("❌ Erro ao atualizar produto: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao atualizar produto: " + e.getMessage());
        }
    }

    private static void removeProduct() {
        System.out.print("📦 ID do produto para remover: ");
        String productId = scanner.nextLine();
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/products/" + productId))
                .header("Authorization", "Bearer " + currentUserToken)
                .DELETE()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("✅ Produto removido com sucesso!");
            } else {
                System.out.println("❌ Erro ao remover produto: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao remover produto: " + e.getMessage());
        }
    }

    private static void listAllOrders() {
        System.out.println("\n📋 === TODOS OS PEDIDOS ===");
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders"))
                .header("Authorization", "Bearer " + currentUserToken)
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("❌ Erro ao buscar pedidos: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar pedidos: " + e.getMessage());
        }
    }

    private static void updateOrderStatus() {
        System.out.print("📋 ID do pedido: ");
        String orderId = scanner.nextLine();
        
        System.out.println("🔄 Status disponíveis:");
        System.out.println("1. Preparando");
        System.out.println("2. Saindo para entrega");
        System.out.println("3. Entregue");
        System.out.println("4. Cancelado");
        System.out.print("Escolha o novo status: ");
        String status = scanner.nextLine();
        
        String statusValue = switch (status) {
            case "1" -> "PREPARANDO";
            case "2" -> "SAINDO_PARA_ENTREGA";
            case "3" -> "ENTREGUE";
            case "4" -> "CANCELADO";
            default -> "PREPARANDO";
        };
        
        String jsonBody = String.format("""
            {
                "status": "%s"
            }
            """, statusValue);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders/" + orderId + "/status"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + currentUserToken)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("✅ Status do pedido atualizado com sucesso!");
            } else {
                System.out.println("❌ Erro ao atualizar status: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao atualizar status: " + e.getMessage());
        }
    }

    private static void cancelOrder() {
        System.out.print("📋 ID do pedido para cancelar: ");
        String orderId = scanner.nextLine();
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders/" + orderId))
                .header("Authorization", "Bearer " + currentUserToken)
                .DELETE()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("✅ Pedido cancelado com sucesso!");
            } else {
                System.out.println("❌ Erro ao cancelar pedido: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao cancelar pedido: " + e.getMessage());
        }
    }

    private static void generateReport() {
        System.out.println("\n📊 === GERAR RELATÓRIO DE VENDAS ===");
        System.out.print("📅 Data de início (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("📅 Data de fim (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();
        System.out.print("🏷️ Código de barras do produto (opcional): ");
        String productBarcode = scanner.nextLine();
        
        String jsonBody = String.format("""
            {
                "startDate": "%s",
                "endDate": "%s",
                "productBarcode": "%s"
            }
            """, startDate, endDate, productBarcode.isEmpty() ? "" : productBarcode);
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/reports/generate"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + currentUserToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("📊 === RELATÓRIO DE VENDAS ===");
                System.out.println(response.body());
            } else {
                System.out.println("❌ Erro ao gerar relatório: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao gerar relatório: " + e.getMessage());
        }
    }

    // === MÉTODOS DO ENTREGADOR ===
    
    private static void viewDeliveryOrders() {
        System.out.println("\n📋 === PEDIDOS PARA ENTREGA ===");
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders?status=SAINDO_PARA_ENTREGA"))
                .header("Authorization", "Bearer " + currentUserToken)
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("❌ Erro ao buscar pedidos: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar pedidos: " + e.getMessage());
        }
    }

    private static void markOrderAsDelivered() {
        System.out.print("📋 ID do pedido entregue: ");
        String orderId = scanner.nextLine();
        
        String jsonBody = """
            {
                "status": "ENTREGUE"
            }
            """;
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders/" + orderId + "/status"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + currentUserToken)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("✅ Pedido marcado como entregue com sucesso!");
            } else {
                System.out.println("❌ Erro ao marcar como entregue: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao marcar como entregue: " + e.getMessage());
        }
    }

    private static void viewMyRatings() {
        System.out.println("\n⭐ === MINHAS AVALIAÇÕES ===");
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/orders/delivery-ratings"))
                .header("Authorization", "Bearer " + currentUserToken)
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            } else {
                System.out.println("❌ Erro ao buscar avaliações: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar avaliações: " + e.getMessage());
        }
    }
}
