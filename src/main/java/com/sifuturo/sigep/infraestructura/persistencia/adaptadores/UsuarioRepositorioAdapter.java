package com.sifuturo.sigep.infraestructura.persistencia.adaptadores;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.sifuturo.sigep.dominio.entidades.Usuario;
import com.sifuturo.sigep.dominio.repositorios.IUsuarioRepositorio;
import com.sifuturo.sigep.infraestructura.persistencia.jpa.UsuarioEntity;
import com.sifuturo.sigep.infraestructura.persistencia.mapeadores.IUsuarioMapper;
import com.sifuturo.sigep.infraestructura.repositorios.IUsuarioJpaRepository;

@Component
public class UsuarioRepositorioAdapter implements IUsuarioRepositorio {

	private final IUsuarioJpaRepository jpaRepository;
	private final IUsuarioMapper mapper;

	public UsuarioRepositorioAdapter(IUsuarioJpaRepository jpaRepository, IUsuarioMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}

	@Override
	public Usuario guardar(Usuario usuario) {
		UsuarioEntity entity = mapper.toEntity(usuario);
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
}