package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.dominio.repositorios.IUsuarioRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.UsuarioEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IUsuarioMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IUsuarioJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositorioAdapter implements IUsuarioRepositorio {

	private final IUsuarioJpaRepository jpaRepository;
	private final IUsuarioMapper mapper;

	@Override
	@Transactional
	public Usuario guardar(Usuario usuario) {
	    UsuarioEntity entity = mapper.toEntity(usuario);
	    
	    // Si el ID existe, forzamos a Hibernate a que lo recupere primero o lo marque como 'dirty'
	    if (entity.getId() != null) {
	        UsuarioEntity existente = jpaRepository.findById(entity.getId()).orElse(null);
	        if (existente != null) {
	            existente.setEstado(usuario.getEstado()); // Actualizamos solo el estado
	            return mapper.toDomain(jpaRepository.save(existente));
	        }
	    }
	    
	    return mapper.toDomain(jpaRepository.save(entity));
	}

	@Override
	public Optional<Usuario> buscarPorUsername(String username) {
		return jpaRepository.findByUsername(username).map(mapper::toDomain);
	}

	@Override
	public boolean existePorUsername(String username) {
		return jpaRepository.existsByUsername(username);
	}

	@Override
	public List<Usuario> listarTodos() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Usuario> listarActivos() {
		return jpaRepository.findByEstadoTrue().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public Optional<Usuario> buscarPorId(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

}