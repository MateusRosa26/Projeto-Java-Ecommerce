package com.ecommerce.business;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ItemPedidoDto;
import com.ecommerce.dto.AddressDto;
import com.ecommerce.dto.PaymentDto;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.Avaliacao;
import com.ecommerce.repository.PedidoRepository;
import com.ecommerce.repository.ItemPedidoRepository;
import com.ecommerce.repository.AvaliacaoRepository;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.util.OrderNumberGenerator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class OrderBusinessHandlerImpl implements BusinessHandler<OrderDto> {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    public OrderBusinessHandlerImpl() {
        this.pedidoRepository = new PedidoRepository();
        this.itemPedidoRepository = new ItemPedidoRepository();
        this.avaliacaoRepository = new AvaliacaoRepository();
    }

    @Override
    public OrderDto create(OrderDto object) {
        if (object == null || object.clienteId() <= 0) {
            throw new IllegalArgumentException("Dados do pedido são obrigatórios");
        }

        Pedido pedido = new Pedido();
        
        // Gerar ID se não existir
        if (object.id() == 0) {
            List<Pedido> allPedidos = pedidoRepository.findAll();
            long nextId = allPedidos.stream()
                    .mapToLong(p -> p.getId())
                    .max()
                    .orElse(0) + 1;
            pedido.setId(nextId);
        } else {
            pedido.setId(object.id());
        }
        
        pedido.setNumero(object.numero() != null ? object.numero() : OrderNumberGenerator.generate());
        pedido.setClienteId(object.clienteId());
        pedido.setStatus(object.status() != null ? object.status() : OrderStatus.PREPARANDO);
        pedido.setTotal(object.total());
        pedido.setComment(object.comment());
        pedido.setRating(object.rating());

        pedidoRepository.save(pedido);
        return toDto(pedido);
    }

    @Override
    public OrderDto update(OrderDto object, long id) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        if (object.status() != null) {
            pedido.setStatus(object.status());
        }
        if (object.comment() != null) {
            pedido.setComment(object.comment());
        }
        if (object.rating() > 0) {
            pedido.setRating(object.rating());
        }

        // Atualizar no repositório
        List<Pedido> allPedidos = pedidoRepository.findAll();
        allPedidos.removeIf(p -> p.getId() == id);
        allPedidos.add(pedido);
        pedidoRepository.saveAll(allPedidos);

        return toDto(pedido);
    }

    @Override
    public boolean delete(long id) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        
        if (pedido == null) {
            return false;
        }

        // Verificar se pode ser cancelado
        if (!pedido.podeSerCancelado()) {
            throw new IllegalStateException("Pedido não pode ser cancelado");
        }

        pedido.cancelarPedido();
        
        // Atualizar no repositório
        List<Pedido> allPedidos = pedidoRepository.findAll();
        allPedidos.removeIf(p -> p.getId() == id);
        allPedidos.add(pedido);
        pedidoRepository.saveAll(allPedidos);

        return true;
    }

    @Override
    public OrderDto find(long id) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<OrderDto> list() {
        return pedidoRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> list(String field, String filter, boolean exact) {
        return pedidoRepository.findAll().stream()
                .filter(p -> {
                    switch (field.toLowerCase()) {
                        case "status":
                            return exact ? p.getStatus().name().equals(filter) : p.getStatus().name().toLowerCase().contains(filter.toLowerCase());
                        case "numero":
                            return exact ? p.getNumero().equals(filter) : p.getNumero().toLowerCase().contains(filter.toLowerCase());
                        case "clienteid":
                            return String.valueOf(p.getClienteId()).equals(filter);
                        default:
                            return false;
                    }
                })
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public boolean finishOrder(List<ProductDto> products) {
        // Implementação básica - finalizar pedido
        System.out.println("Pedido finalizado com " + products.size() + " produtos");
        return true;
    }

    public boolean evaluateOrder(long id, String comment, long rating) {
        Pedido pedido = pedidoRepository.findAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        
        if (pedido == null) {
            return false; // Pedido não encontrado
        }

        if (pedido.getStatus() != OrderStatus.ENTREGUE) {
            return false; // Pedido não está entregue
        }

        // Criar avaliação
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setPedidoId(id);
        avaliacao.setClienteId(pedido.getClienteId());
        avaliacao.setNotaProduto((int) rating);
        avaliacao.setComentarioProduto(comment);
        avaliacao.setDataAvaliacao(new Date());

        avaliacaoRepository.save(avaliacao);

        // Atualizar rating do pedido
        pedido.setRating((int) rating);
        List<Pedido> allPedidos = pedidoRepository.findAll();
        allPedidos.removeIf(p -> p.getId() == id);
        allPedidos.add(pedido);
        pedidoRepository.saveAll(allPedidos);

        System.out.println("Pedido " + id + " avaliado com nota " + rating + ": " + comment);
        return true;
    }

    public List<OrderDto> getOrdersByStatus(OrderStatus status) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getStatus() == status)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByClient(long clienteId) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getClienteId() == clienteId)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public boolean updateOrderStatus(long id, OrderStatus newStatus) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        pedido.atualizarStatus(newStatus);
        
        // Atualizar no repositório
        List<Pedido> allPedidos = pedidoRepository.findAll();
        allPedidos.removeIf(p -> p.getId() == id);
        allPedidos.add(pedido);
        pedidoRepository.saveAll(allPedidos);

        return true;
    }

    private OrderDto toDto(Pedido pedido) {
        // Buscar itens do pedido
        List<ItemPedido> itens = itemPedidoRepository.findAll().stream()
                .filter(item -> item.getPedidoId() != null && item.getPedidoId().equals(pedido.getId()))
                .collect(Collectors.toList());

        // Buscar endereço de entrega (simplificado)
        AddressDto endereco = null;
        
        // Buscar pagamento (simplificado)
        PaymentDto pagamento = null;

        return new OrderDto(
            pedido.getId(),
            pedido.getNumero(),
            pedido.getDataPedido(),
            pedido.getStatus(),
            pedido.getTotal(),
            pedido.getComment(),
            pedido.getRating(),
            pedido.getClienteId(),
            new ArrayList<>(), // products
            itens.stream().map(this::toItemDto).collect(Collectors.toList()), // itens
            endereco,
            pagamento,
            pedido.getCreatedAt(),
            pedido.getUpdatedAt()
        );
    }

    private ItemPedidoDto toItemDto(ItemPedido item) {
        return new ItemPedidoDto(
            item.getId(),
            item.getProdutoId(),
            item.getPedidoId(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getSubtotal(),
            null, // produto
            null, // createdAt
            null  // updatedAt
        );
    }
}
