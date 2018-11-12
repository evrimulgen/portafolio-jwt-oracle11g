package cl.safe.service;

import java.util.List;

import cl.safe.dto.CapacitacionRequestDto;
import cl.safe.entity.AsistenciaTrabajadorEntity;
import cl.safe.entity.CapacitacionEntity;
public interface CapacitacionService {
	public CapacitacionEntity findOneSP(Long id); 
	public List<CapacitacionEntity> findAllByEmpresaExaminadorSP(Long empresaId, Long examinadorId);
	public List<CapacitacionEntity> findAllByEmpresaSupervisorSP(Long empresaId, Long supervisorId);

	public List<AsistenciaTrabajadorEntity> findAllAsistentesByCapacitacionId(Long capacitacionId);
	Long crearCapacitacion(CapacitacionRequestDto capacitacionEntity);
	Long cerrarCapacitacion(Long capacitacionId);
	
}
