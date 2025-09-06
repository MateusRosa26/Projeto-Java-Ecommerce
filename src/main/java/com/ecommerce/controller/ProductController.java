package com.ecommerce.controller;

import com.ecommerce.business.ProductBusinessHandlerImpl;
import com.ecommerce.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para operações de produtos
 * Baseado no diagrama de controllers do sistema
 */
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController extends AbstractController<ProductDto> {

    private final ProductBusinessHandlerImpl businessHandler;

    @Autowired
    public ProductController(ProductBusinessHandlerImpl businessHandler) {
        this.businessHandler = businessHandler;
    }

    /**
     * Lista todos os produtos
     */
    @Override
    @GetMapping
    public ResponseEntity<List<ProductDto>> get() {
        List<ProductDto> products = businessHandler.listProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Obtém um produto por ID
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        ProductDto product = businessHandler.find(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Cria um novo produto
     */
    @Override
    @PostMapping
    public ResponseEntity<ProductDto> post(@RequestBody ProductDto product) {
        ProductDto created = businessHandler.create(product);
        return ResponseEntity.ok(created);
    }

    /**
     * Atualiza um produto existente
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> put(@PathVariable Long id, @RequestBody ProductDto product) {
        ProductDto updated = businessHandler.update(product, id);
        return ResponseEntity.ok(updated);
    }

    /**
     * Remove um produto
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean deleted = businessHandler.delete(id);
        return ResponseEntity.ok(deleted);
    }

    /**
     * Adiciona produto ao carrinho
     * Baseado no diagrama: addToCart(ProductDto product): ResponseEntity<List<ProductDto>>
     */
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody Map<String, Object> request) {
        try {
            String productCode = (String) request.get("productCode");
            Integer quantity = (Integer) request.get("quantity");
            
            if (productCode == null || quantity == null || quantity <= 0) {
                return ResponseEntity.badRequest().body("Código do produto e quantidade são obrigatórios");
            }
            
            ProductDto product = businessHandler.findByBarcode(productCode);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            
            System.out.println("Produto adicionado ao carrinho: " + product.name() + " - Quantidade: " + quantity);
            return ResponseEntity.ok("Produto adicionado ao carrinho com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar produto ao carrinho: " + e.getMessage());
        }
    }

    /**
     * Lista produtos com filtro
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> search(
            @RequestParam String field,
            @RequestParam String filter,
            @RequestParam(defaultValue = "false") boolean exact) {
        List<ProductDto> products = businessHandler.list(field, filter, exact);
        return ResponseEntity.ok(products);
    }
}
