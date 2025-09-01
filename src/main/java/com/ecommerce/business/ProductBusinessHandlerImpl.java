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
        Produto produto = new Produto(object.id(), object.name(), object.price(), "", "", true);
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
        // Implementação básica - filtra por nome
        return repository.findAll().stream()
                .filter(p -> p.getNome().toLowerCase().contains(filter.toLowerCase()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> listProducts() {
        return list();
    }

    private ProductDto toDto(Produto produto) {
        return new ProductDto(produto.getId(), produto.getNome(), produto.getPreco());
    }
}
