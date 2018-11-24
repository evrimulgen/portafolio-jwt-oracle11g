package cl.safe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.safe.config.Const;
import cl.safe.config.Utils;
import cl.safe.dto.CapacitacionParaTrabajadorRequestDto;
import cl.safe.dto.CapacitacionParaTrabajadorResponseDto;
import cl.safe.dto.ConsultaMedicaTrabajadorResponseDto;
import cl.safe.dto.ResponseDto;
import cl.safe.entity.ConsultaMedicaTrabajadorEntity;
import cl.safe.entity.EmpresaEntity;
import cl.safe.entity.UserEntity;
import cl.safe.service.CertificadoService;
import cl.safe.service.EmpresaService;
import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {
	
	@Autowired
	CertificadoService certificadoService;
	
	@Autowired
	EmpresaService empresaService;

	@GetMapping("/empresas")
	public ResponseEntity<ResponseDto<List<EmpresaEntity>>> getAllEmpresas() {
		ResponseDto<List<EmpresaEntity>> rdto = new ResponseDto<>();
		
		final List<EmpresaEntity> cleanEmpreas = new ArrayList<>();
		
		// limpiamos las empresas al exponerlas de manera publica
		empresaService.getAllSP().forEach(new Consumer<EmpresaEntity>() {
			@Override
			public void accept(EmpresaEntity t) {
				t.setDireccion("");
				t.setEmail("");
				t.setTelefono("");
				cleanEmpreas.add(t);
			}
		});
		
		rdto.setObj(cleanEmpreas);
		rdto.setMessage("OK");
		rdto.setStatus(HttpStatus.OK);
		return new ResponseEntity<>(rdto, HttpStatus.OK);
	}
	
	@PostMapping("/trabajador/capacitacion")
	public ResponseEntity<ResponseDto<List<CapacitacionParaTrabajadorResponseDto>>> getAllControlById(
			@RequestBody @Valid final CapacitacionParaTrabajadorRequestDto capacitacionRequest
			) {
		ResponseDto<List<CapacitacionParaTrabajadorResponseDto>> rdto = new ResponseDto<>();
		rdto.setObj(certificadoService.getAllCetificados(capacitacionRequest.getRun(), capacitacionRequest.getEmpresaId()));
		rdto.setMessage("OK");
		rdto.setStatus(HttpStatus.OK);
		return new ResponseEntity<>(rdto, HttpStatus.OK);
	}
	
	
	@PostMapping("/trabajador/consultas")
	public ResponseEntity<ResponseDto<List<ConsultaMedicaTrabajadorResponseDto>>> getAllConsultasById(
			@RequestBody @Valid final CapacitacionParaTrabajadorRequestDto capacitacionRequest
			) {
		ResponseDto<List<ConsultaMedicaTrabajadorResponseDto>> rdto = new ResponseDto<>();
		rdto.setObj(certificadoService.getAllConsultas(capacitacionRequest.getRun(), capacitacionRequest.getEmpresaId()));
		rdto.setMessage("OK");
		rdto.setStatus(HttpStatus.OK);
		return new ResponseEntity<>(rdto, HttpStatus.OK);
	}

}
