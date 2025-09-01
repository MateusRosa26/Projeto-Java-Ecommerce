package com.ecommerce.business;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductBusinessHandlerImpl implements BusinessHandler<ProductDto> {
    private final ProdutoRepository repository;

    public ProductBusinessHandlerImpl() {
        this.repository = new ProdutoRepository();
    }

    @Override
    public ProductDto create(ProductDto object) {
        Produto produto = new Produto(object.id(), object.name(), object.price(), 
            object.barcode() != null ? object.barcode() : "", 
            object.description() != null ? object.description() : "", 
            object.imageUrl() != null ? object.imageUrl() : "", 
            true);
        repository.save(produto);
        return object;
    }

    @Override
    public ProductDto update(ProductDto object, long id) {
        // Implementação básica - em produção seria mais robusta
        return object;
    }

    @Override
    public boolean delete(long id) {
        // Implementação básica
        return true;
    }

    @Override
    public ProductDto find(long id) {
        return repository.findAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<ProductDto> list() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> list(String field, String filter, boolean exact) {
        return repository.findAll().stream()
                .filter(p -> {
                    return switch (field.toLowerCase()) {
                        case "name" -> exact ? p.getNome().equals(filter)
                                            : p.getNome().toLowerCase().contains(filter.toLowerCase());
                        case "barcode" -> exact ? p.getCodigoBarras().equals(filter)
                                                : p.getCodigoBarras().toLowerCase().contains(filter.toLowerCase());
                        case "description" -> exact ? p.getDescricao().equals(filter)
                                                    : p.getDescricao().toLowerCase().contains(filter.toLowerCase());
                        default -> p.getNome().toLowerCase().contains(filter.toLowerCase());
                    };
                })
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> listProducts() {
        return list();
    }

    private ProductDto toDto(Produto produto) {
        return new ProductDto(produto.getId(), produto.getNome(), produto.getPreco(),
            produto.getCodigoBarras(), produto.getDescricao(), produto.getImagem());
    }
    
    /**
     * Busca produto por código de barras
     */
    public ProductDto findByBarcode(String barcode) {
        return repository.findAll().stream()
                .filter(p -> p.getCodigoBarras().equals(barcode))
                .findFirst()
                .map(this::toDto)
                .orElse(null);
    }
    
    /**
     * Busca produtos por código de barras (pode retornar múltiplos)
     */
    public List<ProductDto> searchByBarcode(String barcode) {
        return repository.findAll().stream()
                .filter(p -> p.getCodigoBarras().toLowerCase().contains(barcode.toLowerCase()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
