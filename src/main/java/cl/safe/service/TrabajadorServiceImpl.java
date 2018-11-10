package cl.safe.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.safe.dto.TrabajadorRequestDto;
import cl.safe.entity.TrabajadorEntity;
import cl.safe.repository.TrabajadorRepository;

@Service
public class TrabajadorServiceImpl implements TrabajadorService{
	@Autowired
	TrabajadorRepository trabajadorRepository;
	
	@PersistenceContext
    private EntityManager em;

	@Override
	public List<TrabajadorEntity> getByEmpresaIdSP(Long id) {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("TRABAJADORES_BY_EMPRESA_FK");
		query.setParameter("P_EMPRESA_FK", id);
		return query.getResultList();
	}

	@Override
	public List<TrabajadorEntity> findAll() {
		return (List<TrabajadorEntity>) trabajadorRepository.findAll();
	}

	@Override
	public TrabajadorEntity findOne(Long id) {
		return trabajadorRepository.findOne(id);
	}

	@Override
	public TrabajadorEntity findByEmail(String email) {
		return trabajadorRepository.findByEmail(email);
	}

	@Override
	public Long crearTrabajadorSP(TrabajadorRequestDto trabajadorRequestDto) {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("trabajador_insert");
		query.setParameter("p_apellido_materno", trabajadorRequestDto.getApellidoMaterno());
		query.setParameter("p_apellido_paterno", trabajadorRequestDto.getApellidoPaterno());
		query.setParameter("p_email", trabajadorRequestDto.getEmail());
		query.setParameter("p_empresa_fk", trabajadorRequestDto.getEmpresa());
		query.setParameter("p_nombre", trabajadorRequestDto.getNombre());
		query.setParameter("p_run", trabajadorRequestDto.getRun());
		query.execute();
		return (Long) query.getOutputParameterValue("o_ID");
	}
}
