package com.furkanasikdev.courier.tracking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ObjectMapperService {

	private final ObjectMapper objectMapper;

	public String toJson(Object object) throws JsonProcessingException {
		return this.objectMapper.writeValueAsString(object);
	}

	public <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
		return this.objectMapper.readValue(json, clazz);
	}

	public <T> T fromJson(String json, TypeReference<T> typeReference) throws JsonProcessingException {
		return this.objectMapper.readValue(json, typeReference);
	}

	public <T> List<T> read(Resource resource, TypeReference<List<T>> typeReference) throws IOException {
		return this.objectMapper.readValue(resource.getInputStream(), typeReference);
	}

	public <T> T read(Resource resource, Class<T> clazz) throws IOException {
		return this.objectMapper.readValue(resource.getInputStream(), clazz);
	}
}