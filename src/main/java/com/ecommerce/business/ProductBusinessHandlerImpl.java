package com.ecommerce.business;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductBusinessHandlerImpl implements BusinessHandler<ProductDto> {
    private final ProdutoRepository repository;

    public ProductBusinessHandlerImpl() {
        this.repository = new ProdutoRepository();
    }

    @Override
    public ProductDto create(ProductDto object) {
        if (object == null || object.name() == null || object.name().isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        if (object.price() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }

        Produto produto = new Produto();
        produto.setId(object.id());
        produto.setNome(object.name());
        produto.setPreco(object.price());
        produto.setDescricao(object.description());
        produto.setImagemUrl(object.imageUrl());
        produto.setCodigoBarras(object.codigoBarras());
        produto.setDisponivel(object.disponivel());

        repository.save(produto);
        return toDto(produto);
    }

    @Override
    public ProductDto update(ProductDto object, long id) {
        List<Produto> produtos = repository.findAll();
        Produto produto = produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }

        if (object.name() != null && !object.name().isBlank()) {
            produto.setNome(object.name());
        }
        if (object.price() >= 0) {
            produto.setPreco(object.price());
        }
        if (object.description() != null) {
            produto.setDescricao(object.description());
        }
        if (object.imageUrl() != null) {
            produto.setImagemUrl(object.imageUrl());
        }
        if (object.codigoBarras() != null) {
            produto.setCodigoBarras(object.codigoBarras());
        }

        // Atualizar no repositório
        List<Produto> allProdutos = repository.findAll();
        allProdutos.removeIf(p -> p.getId() == id);
        allProdutos.add(produto);
        repository.saveAll(allProdutos);

        return toDto(produto);
    }

    @Override
    public boolean delete(long id) {
        List<Produto> produtos = repository.findAll();
        boolean removed = produtos.removeIf(p -> p.getId() == id);
        if (removed) {
            repository.saveAll(produtos);
        }
        return removed;
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
                    switch (field.toLowerCase()) {
                        case "nome":
                            return exact ? p.getNome().equals(filter) : p.getNome().toLowerCase().contains(filter.toLowerCase());
                        case "descricao":
                            return exact ? p.getDescricao().equals(filter) : p.getDescricao().toLowerCase().contains(filter.toLowerCase());
                        case "codigobarras":
                            return exact ? p.getCodigoBarras().equals(filter) : p.getCodigoBarras().toLowerCase().contains(filter.toLowerCase());
                        case "disponivel":
                            return String.valueOf(p.isDisponivel()).equals(filter);
                        default:
                            return false;
                    }
                })
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> listProducts() {
        return list();
    }

    public List<ProductDto> listAvailableProducts() {
        return repository.findAll().stream()
                .filter(Produto::isDisponivel)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto findByBarcode(String codigoBarras) {
        return repository.findAll().stream()
                .filter(p -> p.getCodigoBarras() != null && p.getCodigoBarras().equals(codigoBarras))
                .findFirst()
                .map(this::toDto)
                .orElse(null);
    }

    private ProductDto toDto(Produto produto) {
        return new ProductDto(
            produto.getId(), 
            produto.getNome(), 
            produto.getPreco(),
            produto.getDescricao(),
            produto.getImagemUrl(),
            produto.getCodigoBarras(),
            produto.isDisponivel(),
            produto.getCreatedAt(),
            produto.getUpdatedAt()
        );
    }
}
