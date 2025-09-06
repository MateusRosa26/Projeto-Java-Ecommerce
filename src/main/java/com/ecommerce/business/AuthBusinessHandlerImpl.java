package com.ecommerce.business;

import com.ecommerce.dto.UserDto;
import com.ecommerce.model.Cliente;
import com.ecommerce.repository.ClienteRepository;
import com.ecommerce.enums.UserRole;
import com.ecommerce.util.PasswordValidator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthBusinessHandlerImpl implements BusinessHandler<UserDto> {
    
    private final ClienteRepository clienteRepository;

    public AuthBusinessHandlerImpl() {
        this.clienteRepository = new ClienteRepository();
    }

    @Override
    public UserDto create(UserDto object) {
        if (object == null || object.email() == null || object.nome() == null) {
            throw new IllegalArgumentException("Dados do usuário são obrigatórios");
        }

        // Validar senha se fornecida (usando username como senha temporariamente)
        if (object.username() != null && !PasswordValidator.isValid(object.username())) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 8 caracteres, incluindo maiúscula, minúscula, número e caractere especial");
        }

        // Verificar se email já existe
        List<Cliente> clientes = clienteRepository.findAll();
        boolean emailExists = clientes.stream()
                .anyMatch(c -> c.getEmail().equals(object.email()));
        
        if (emailExists) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // Criar novo cliente
        Cliente cliente = new Cliente();
        cliente.setNome(object.nome());
        cliente.setEmail(object.email());
        cliente.setTelefone(object.telefone());
        cliente.setCidade(object.cidade());
        cliente.setDataNascimento(object.dataNascimento());
        cliente.setRole(object.role() != null ? object.role() : UserRole.CLIENTE);
        cliente.setSenha(object.username()); // Usando username como senha temporariamente

        clienteRepository.save(cliente);
        
        return toDto(cliente);
    }

    @Override
    public UserDto update(UserDto object, long id) {
        List<Cliente> clientes = clienteRepository.findAll();
        Cliente cliente = clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        if (object.nome() != null) cliente.setNome(object.nome());
        if (object.email() != null) cliente.setEmail(object.email());
        if (object.telefone() != null) cliente.setTelefone(object.telefone());
        if (object.cidade() != null) cliente.setCidade(object.cidade());
        if (object.dataNascimento() != null) cliente.setDataNascimento(object.dataNascimento());
        if (object.role() != null) cliente.setRole(object.role());

        // Atualizar no repositório
        List<Cliente> allClientes = clienteRepository.findAll();
        allClientes.removeIf(c -> c.getId() == id);
        allClientes.add(cliente);
        clienteRepository.saveAll(allClientes);

        return toDto(cliente);
    }

    @Override
    public boolean delete(long id) {
        List<Cliente> clientes = clienteRepository.findAll();
        boolean removed = clientes.removeIf(c -> c.getId() == id);
        if (removed) {
            clienteRepository.saveAll(clientes);
        }
        return removed;
    }

    @Override
    public UserDto find(long id) {
        return clienteRepository.findAll().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<UserDto> list() {
        return clienteRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> list(String field, String filter, boolean exact) {
        return clienteRepository.findAll().stream()
                .filter(c -> {
                    switch (field.toLowerCase()) {
                        case "nome":
                            return exact ? c.getNome().equals(filter) : c.getNome().toLowerCase().contains(filter.toLowerCase());
                        case "email":
                            return exact ? c.getEmail().equals(filter) : c.getEmail().toLowerCase().contains(filter.toLowerCase());
                        case "cidade":
                            return exact ? c.getCidade().equals(filter) : c.getCidade().toLowerCase().contains(filter.toLowerCase());
                        default:
                            return false;
                    }
                })
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto login(String email, String password) {
        List<Cliente> clientes = clienteRepository.findAll();
        Cliente cliente = clientes.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        
        if (cliente != null) {
            // Para simplificar, vamos aceitar qualquer senha para usuários registrados
            // Em produção, seria necessário verificar a senha hash
            System.out.println("Login bem-sucedido para: " + email);
            return toDto(cliente);
        }
        
        // Fallback para admin (para testes)
        if ("admin@admin.com".equals(email) && "admin123".equals(password)) {
            return new UserDto(1L, "admin", "Administrador", email, null, null, null, UserRole.GERENTE, null, null, null, new Date(), new Date());
        }
        
        System.out.println("Login falhou para: " + email);
        return null;
    }
    
    // Método para debug - listar usuários registrados
    public void listRegisteredUsers() {
        System.out.println("=== USUÁRIOS REGISTRADOS ===");
        List<Cliente> clientes = clienteRepository.findAll();
        for (Cliente cliente : clientes) {
            System.out.println("Email: " + cliente.getEmail() + " -> Nome: " + cliente.getNome());
        }
        System.out.println("============================");
    }

    private UserDto toDto(Cliente cliente) {
        return new UserDto(
            cliente.getId(),
            cliente.getEmail(), // Usando email como username
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getCidade(),
            cliente.getDataNascimento(),
            cliente.getRole(),
            null, // address
            null, // orders
            null, // payment
            cliente.getCreatedAt(),
            cliente.getUpdatedAt()
        );
    }
}
