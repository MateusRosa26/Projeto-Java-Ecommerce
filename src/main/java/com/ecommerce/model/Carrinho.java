package com.ecommerce.model;

import com.ecommerce.csv.CsvSerializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Carrinho implements CsvSerializable {
    private Long id;
    private Long clienteId;
    private List<ItemPedido> itens;
    private Date createdAt;
    private Date updatedAt;

    // Construtores
    public Carrinho() {
        this.itens = new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Carrinho(Long clienteId) {
        this();
        this.clienteId = clienteId;
    }

    public Carrinho(Long id, Long clienteId, List<ItemPedido> itens, Date createdAt, Date updatedAt) {
        this.id = id;
        this.clienteId = clienteId;
        this.itens = itens != null ? itens : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Métodos de negócio
    public void adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        
        // Verificar se o produto já existe no carrinho
        for (ItemPedido itemExistente : itens) {
            if (itemExistente.getProdutoId().equals(item.getProdutoId())) {
                // Atualizar quantidade do item existente
                itemExistente.atualizarQuantidade(itemExistente.getQuantidade() + item.getQuantidade());
                this.updatedAt = new Date();
                return;
            }
        }
        
        // Adicionar novo item
        itens.add(item);
        this.updatedAt = new Date();
    }

    public void removerItem(Long produtoId) {
        itens.removeIf(item -> item.getProdutoId().equals(produtoId));
        this.updatedAt = new Date();
    }

    public void atualizarQuantidade(Long produtoId, int novaQuantidade) {
        for (ItemPedido item : itens) {
            if (item.getProdutoId().equals(produtoId)) {
                item.atualizarQuantidade(novaQuantidade);
                this.updatedAt = new Date();
                return;
            }
        }
        throw new IllegalArgumentException("Produto não encontrado no carrinho");
    }

    public void limparCarrinho() {
        itens.clear();
        this.updatedAt = new Date();
    }

    public double getTotal() {
        return itens.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
    }

    public int getQuantidadeTotal() {
        return itens.stream()
                .mapToInt(ItemPedido::getQuantidade)
                .sum();
    }

    public boolean isEmpty() {
        return itens.isEmpty();
    }

    public int getTamanho() {
        return itens.size();
    }

    public ItemPedido getItemPorProdutoId(Long produtoId) {
        return itens.stream()
                .filter(item -> item.getProdutoId().equals(produtoId))
                .findFirst()
                .orElse(null);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
        this.updatedAt = new Date();
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();
        this.updatedAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String[] toCsvRow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Para o CSV, vamos serializar apenas os IDs dos itens separados por vírgula
        String itensIds = itens.stream()
                .map(item -> String.valueOf(item.getId()))
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        
        return new String[]{
            String.valueOf(id),
            String.valueOf(clienteId),
            itensIds,
            sdf.format(createdAt),
            sdf.format(updatedAt)
        };
    }

    public static Carrinho fromCsv(String[] row) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long id = Long.parseLong(row[0]);
            Long clienteId = Long.parseLong(row[1]);
            String itensIds = row[2];
            Date createdAt = sdf.parse(row[3]);
            Date updatedAt = sdf.parse(row[4]);
            
            List<ItemPedido> itens = new ArrayList<>();
            if (!itensIds.isEmpty()) {
                String[] ids = itensIds.split(",");
                for (String itemId : ids) {
                    if (!itemId.isEmpty()) {
                        ItemPedido item = new ItemPedido();
                        item.setId(Long.parseLong(itemId));
                        itens.add(item);
                    }
                }
            }
            
            return new Carrinho(id, clienteId, itens, createdAt, updatedAt);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao fazer parse da data: " + e.getMessage(), e);
        }
    }

    public static String[] csvHeader() {
        return new String[]{"id", "clienteId", "itensIds", "createdAt", "updatedAt"};
    }
}
