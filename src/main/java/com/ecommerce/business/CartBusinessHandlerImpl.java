package com.ecommerce.business;

import com.ecommerce.dto.CartDto;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.ItemPedido;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CartBusinessHandlerImpl implements BusinessHandler<CartDto> {
    
    // Armazenamento temporário de carrinhos (em produção seria um banco de dados)
    private static final Map<Long, CartDto> carts = new ConcurrentHashMap<>();
    private static final Map<Long, List<ItemPedido>> cartItems = new ConcurrentHashMap<>();
    private static long nextCartId = 1L;
    
    @Override
    public CartDto create(CartDto object) {
        if (object != null) {
            CartDto newCart = new CartDto(
                nextCartId++,
                LocalDateTime.now(),
                "Ativo",
                object.idCliente()
            );
            carts.put(newCart.id(), newCart);
            cartItems.put(newCart.id(), new ArrayList<>());
            System.out.println("🛒 Carrinho criado para cliente " + object.idCliente());
            return newCart;
        }
        return null;
    }

    @Override
    public CartDto update(CartDto object, long id) {
        if (carts.containsKey(id)) {
            carts.put(id, object);
            return object;
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        boolean removed = carts.remove(id) != null;
        cartItems.remove(id);
        if (removed) {
            System.out.println("🛒 Carrinho " + id + " removido");
        }
        return removed;
    }

    @Override
    public CartDto find(long id) {
        return carts.get(id);
    }

    @Override
    public List<CartDto> list() {
        return new ArrayList<>(carts.values());
    }

    @Override
    public List<CartDto> list(String field, String filter, boolean exact) {
        return carts.values().stream()
            .filter(cart -> {
                switch (field.toLowerCase()) {
                    case "status":
                        return exact ? cart.status().equals(filter)
                                   : cart.status().toLowerCase().contains(filter.toLowerCase());
                    case "idcliente":
                        return cart.idCliente().equals(Long.parseLong(filter));
                    default:
                        return false;
                }
            })
            .toList();
    }
    
    /**
     * Obtém carrinho ativo de um cliente
     */
    public CartDto getActiveCart(Long clienteId) {
        return carts.values().stream()
            .filter(cart -> cart.idCliente().equals(clienteId) && "Ativo".equals(cart.status()))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Cria carrinho para um cliente se não existir
     */
    public CartDto getOrCreateCart(Long clienteId) {
        CartDto existingCart = getActiveCart(clienteId);
        if (existingCart != null) {
            return existingCart;
        }
        
        CartDto newCart = new CartDto(nextCartId++, LocalDateTime.now(), "Ativo", clienteId);
        carts.put(newCart.id(), newCart);
        cartItems.put(newCart.id(), new ArrayList<>());
        System.out.println("🛒 Novo carrinho criado para cliente " + clienteId);
        return newCart;
    }
    
    /**
     * Adiciona produto ao carrinho
     */
    public boolean addProductToCart(Long cartId, ProductDto product, int quantidade) {
        if (quantidade <= 0) {
            System.out.println("❌ Quantidade deve ser maior que zero");
            return false;
        }
        
        CartDto cart = carts.get(cartId);
        if (cart == null) {
            System.out.println("❌ Carrinho não encontrado");
            return false;
        }
        
        List<ItemPedido> items = cartItems.getOrDefault(cartId, new ArrayList<>());
        
        // Verifica se o produto já está no carrinho
        Optional<ItemPedido> existingItem = items.stream()
            .filter(item -> item.getProdutoId() == product.id())
            .findFirst();
        
        if (existingItem.isPresent()) {
            // Atualiza quantidade
            ItemPedido item = existingItem.get();
            ItemPedido updatedItem = new ItemPedido(item.getProdutoId(), item.getQuantidade() + quantidade, item.getPrecoUnitario());
            items.remove(item);
            items.add(updatedItem);
            System.out.println("🛒 Quantidade atualizada: " + product.name() + " = " + updatedItem.getQuantidade());
        } else {
            // Adiciona novo item
            ItemPedido newItem = new ItemPedido((int) product.id(), quantidade, product.price());
            items.add(newItem);
            System.out.println("🛒 Produto adicionado: " + product.name() + " x" + quantidade);
        }
        
        cartItems.put(cartId, items);
        return true;
    }
    
    /**
     * Remove produto do carrinho
     */
    public boolean removeProductFromCart(Long cartId, Long productId) {
        CartDto cart = carts.get(cartId);
        if (cart == null) {
            System.out.println("❌ Carrinho não encontrado");
            return false;
        }
        
        List<ItemPedido> items = cartItems.get(cartId);
        if (items == null) {
            return false;
        }
        
        boolean removed = items.removeIf(item -> item.getProdutoId() == productId);
        if (removed) {
            System.out.println("🛒 Produto removido do carrinho");
        }
        
        return removed;
    }
    
    /**
     * Atualiza quantidade de um produto no carrinho
     */
    public boolean updateProductQuantity(Long cartId, Long productId, int novaQuantidade) {
        if (novaQuantidade < 0) {
            System.out.println("❌ Quantidade não pode ser negativa");
            return false;
        }
        
        CartDto cart = carts.get(cartId);
        if (cart == null) {
            System.out.println("❌ Carrinho não encontrado");
            return false;
        }
        
        List<ItemPedido> items = cartItems.get(cartId);
        if (items == null) {
            return false;
        }
        
        Optional<ItemPedido> item = items.stream()
            .filter(i -> i.getProdutoId() == productId)
            .findFirst();
        
        if (item.isPresent()) {
            if (novaQuantidade == 0) {
                items.remove(item.get());
                System.out.println("🛒 Produto removido do carrinho");
            } else {
                ItemPedido updatedItem = new ItemPedido(item.get().getProdutoId(), novaQuantidade, item.get().getPrecoUnitario());
                items.remove(item.get());
                items.add(updatedItem);
                System.out.println("🛒 Quantidade atualizada: " + novaQuantidade);
            }
            return true;
        }
        
        return false;
    }
    
    /**
     * Obtém itens do carrinho
     */
    public List<ItemPedido> getCartItems(Long cartId) {
        return cartItems.getOrDefault(cartId, new ArrayList<>());
    }
    
    /**
     * Calcula total do carrinho
     */
    public double calculateCartTotal(Long cartId) {
        List<ItemPedido> items = getCartItems(cartId);
        return items.stream()
            .mapToDouble(item -> item.getQuantidade() * item.getPrecoUnitario())
            .sum();
    }
    
    /**
     * Limpa carrinho
     */
    public boolean clearCart(Long cartId) {
        CartDto cart = carts.get(cartId);
        if (cart == null) {
            return false;
        }
        
        cartItems.put(cartId, new ArrayList<>());
        System.out.println("🛒 Carrinho limpo");
        return true;
    }
    
    /**
     * Finaliza carrinho (muda status para Inativo)
     */
    public boolean finalizeCart(Long cartId) {
        CartDto cart = carts.get(cartId);
        if (cart == null) {
            return false;
        }
        
        CartDto finalizedCart = new CartDto(
            cart.id(),
            cart.dataCriacao(),
            "Inativo",
            cart.idCliente()
        );
        
        carts.put(cartId, finalizedCart);
        System.out.println("🛒 Carrinho finalizado");
        return true;
    }
    
    /**
     * Obtém carrinho com itens
     */
    public CartDto getCartWithItems(Long cartId) {
        CartDto cart = carts.get(cartId);
        if (cart == null) {
            return null;
        }
        
        List<ItemPedido> items = getCartItems(cartId);
        double total = calculateCartTotal(cartId);
        
        System.out.println("🛒 Carrinho " + cartId + " - " + items.size() + " itens - Total: R$ " + String.format("%.2f", total));
        return cart;
    }
}
