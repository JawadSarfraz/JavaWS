package com.test;

import org.modelmapper.ModelMapper;

public class DtoUtil {

	public DTOEntity convertToDto(Object obj, DTOEntity mapper) {
		return new ModelMapper().map(obj, mapper.getClass());
	}
	
	public Object convertToEntity(Object obj, DTOEntity mapper) {
		return new ModelMapper().map(mapper, obj.getClass());
	}
}