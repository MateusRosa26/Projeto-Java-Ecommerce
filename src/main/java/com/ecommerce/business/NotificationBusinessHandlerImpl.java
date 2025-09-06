package com.ecommerce.business;

import com.ecommerce.dto.NotificationDto;
import com.ecommerce.model.Notificacao;
import com.ecommerce.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationBusinessHandlerImpl implements BusinessHandler<NotificationDto> {
    
    private final NotificacaoRepository notificacaoRepository;

    public NotificationBusinessHandlerImpl() {
        this.notificacaoRepository = new NotificacaoRepository();
    }

    @Override
    public NotificationDto create(NotificationDto object) {
        if (object == null || object.clienteId() == null) {
            throw new IllegalArgumentException("Dados da notificação são obrigatórios");
        }

        Notificacao notificacao = new Notificacao(
                object.clienteId(),
                object.pedidoId(),
                object.titulo(),
                object.mensagem(),
                object.tipo()
        );

        notificacaoRepository.save(notificacao);
        return toDto(notificacao);
    }

    @Override
    public NotificationDto update(NotificationDto object, long id) {
        List<Notificacao> notificacoes = notificacaoRepository.findAll();
        Notificacao existing = notificacoes.stream()
                .filter(n -> n.getId() == id)
                .findFirst()
                .orElse(null);
        if (existing == null) {
            throw new IllegalArgumentException("Notificação não encontrada");
        }

        existing.setClienteId(object.clienteId());
        existing.setPedidoId(object.pedidoId());
        existing.setTitulo(object.titulo());
        existing.setMensagem(object.mensagem());
        existing.setTipo(object.tipo());
        existing.setLida(object.lida());
        existing.setDataEnvio(object.dataEnvio());

        // Atualizar no repositório
        List<Notificacao> allNotificacoes = notificacaoRepository.findAll();
        allNotificacoes.removeIf(n -> n.getId() == id);
        allNotificacoes.add(existing);
        notificacaoRepository.saveAll(allNotificacoes);
        return toDto(existing);
    }

    @Override
    public boolean delete(long id) {
        List<Notificacao> allNotificacoes = notificacaoRepository.findAll();
        boolean removed = allNotificacoes.removeIf(n -> n.getId() == id);
        if (removed) {
            notificacaoRepository.saveAll(allNotificacoes);
        }
        return removed;
    }

    @Override
    public NotificationDto find(long id) {
        List<Notificacao> notificacoes = notificacaoRepository.findAll();
        Notificacao notificacao = notificacoes.stream()
                .filter(n -> n.getId() == id)
                .findFirst()
                .orElse(null);
        return notificacao != null ? toDto(notificacao) : null;
    }

    @Override
    public List<NotificationDto> list() {
        return notificacaoRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> list(String field, String filter, boolean exact) {
        return notificacaoRepository.findAll().stream()
                .filter(notification -> {
                    switch (field.toLowerCase()) {
                        case "clienteid":
                            return exact ? String.valueOf(notification.getClienteId()).equals(filter)
                                       : String.valueOf(notification.getClienteId()).contains(filter);
                        case "pedidoid":
                            return exact ? String.valueOf(notification.getPedidoId()).equals(filter)
                                       : String.valueOf(notification.getPedidoId()).contains(filter);
                        case "tipo":
                            return exact ? notification.getTipo().equals(filter)
                                       : notification.getTipo().toLowerCase().contains(filter.toLowerCase());
                        case "lida":
                            return String.valueOf(notification.isLida()).equals(filter);
                        default:
                            return false;
                    }
                })
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Métodos específicos para notificações
    public List<NotificationDto> getNotificationsByClient(Long clienteId) {
        return notificacaoRepository.findAll().stream()
                .filter(n -> n.getClienteId() != null && n.getClienteId().equals(clienteId))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificationDto> getUnreadNotifications(Long clienteId) {
        return notificacaoRepository.findAll().stream()
                .filter(n -> n.getClienteId() != null && n.getClienteId().equals(clienteId))
                .filter(n -> !n.isLida())
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public boolean markAsRead(Long id) {
        List<Notificacao> notificacoes = notificacaoRepository.findAll();
        Notificacao notificacao = notificacoes.stream()
                .filter(n -> n.getId() == id)
                .findFirst()
                .orElse(null);
        if (notificacao != null) {
            notificacao.marcarComoLida();
            // Atualizar no repositório
            notificacoes.removeIf(n -> n.getId() == id);
            notificacoes.add(notificacao);
            notificacaoRepository.saveAll(notificacoes);
            return true;
        }
        return false;
    }

    public boolean markAsUnread(Long id) {
        List<Notificacao> notificacoes = notificacaoRepository.findAll();
        Notificacao notificacao = notificacoes.stream()
                .filter(n -> n.getId() == id)
                .findFirst()
                .orElse(null);
        if (notificacao != null) {
            notificacao.marcarComoNaoLida();
            // Atualizar no repositório
            notificacoes.removeIf(n -> n.getId() == id);
            notificacoes.add(notificacao);
            notificacaoRepository.saveAll(notificacoes);
            return true;
        }
        return false;
    }

    public void sendOrderStatusNotification(Long clienteId, Long pedidoId, String status) {
        String titulo = "Atualização do Pedido";
        String mensagem = String.format("Seu pedido #%d teve o status alterado para: %s", pedidoId, status);
        
        Notificacao notificacao = new Notificacao(clienteId, pedidoId, titulo, mensagem, "STATUS_PEDIDO");
        notificacaoRepository.save(notificacao);
    }

    public void sendPaymentNotification(Long clienteId, Long pedidoId, String status) {
        String titulo = "Atualização do Pagamento";
        String mensagem = String.format("O pagamento do pedido #%d foi %s", pedidoId, status);
        
        Notificacao notificacao = new Notificacao(clienteId, pedidoId, titulo, mensagem, "PAGAMENTO");
        notificacaoRepository.save(notificacao);
    }

    private NotificationDto toDto(Notificacao notificacao) {
        return new NotificationDto(
                notificacao.getId(),
                notificacao.getClienteId(),
                notificacao.getPedidoId(),
                notificacao.getTitulo(),
                notificacao.getMensagem(),
                notificacao.getTipo(),
                notificacao.isLida(),
                notificacao.getDataEnvio(),
                notificacao.getCreatedAt(),
                notificacao.getUpdatedAt()
        );
    }
}
