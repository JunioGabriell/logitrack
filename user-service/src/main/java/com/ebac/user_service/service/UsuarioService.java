package com.ebac.user_service.service;

import com.ebac.user_service.dto.UsuarioRequest;
import com.ebac.user_service.dto.UsuarioResponse;
import com.ebac.user_service.entity.Usuario;
import com.ebac.user_service.exception.UsuarioNaoEncontradoException;
import com.ebac.user_service.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository repository;

    public UsuarioService(final UsuarioRepository repository) {
        this.repository = repository;
    }

    public Page<UsuarioResponse> listarTodos(Pageable pageable) {
        log.info("Listando usuários — página: {}", pageable.getPageNumber());
        return repository.findAll(pageable).map(this::toResponse);
    }

    public UsuarioResponse buscarPorId(Long id) {
        log.info("Buscando usuário por ID: {}", id);
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        log.info("Atualizando usuário ID: {}", id);

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setRole(Usuario.Role.valueOf(request.role().toUpperCase()));

        repository.save(usuario);
        log.info("Usuário atualizado com sucesso: {}", id);

        return toResponse(usuario);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Deletando usuário ID: {}", id);

        if (!repository.existsById(id)) {
            throw new UsuarioNaoEncontradoException(id);
        }

        repository.deleteById(id);
        log.info("Usuário deletado com sucesso: {}", id);
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole().name()
        );
    }
}