package com.coderace.service;

import com.coderace.model.dtos.ExampleRequestDTO;
import com.coderace.model.dtos.ExampleResponseDTO;
import com.coderace.model.entities.Example;
import com.coderace.model.enums.ExampleEnum;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.ExampleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExampleService {

    private final ExampleRepository repository;

    public ExampleService(ExampleRepository repository) {
        this.repository = repository;
    }

    public ExampleResponseDTO create(ExampleRequestDTO requestDTO) {
        this.validate(requestDTO);

        final LocalDateTime dateValue = resolveDateValue(requestDTO.getDateValue());
        final ExampleEnum enumValue = resolveEnumValue(requestDTO.getEnumValue());

        final Example exampleBeforePersistence = new Example(requestDTO.getLongValue(), requestDTO.getDoubleValue(),
            requestDTO.getStringValue(), dateValue, enumValue);

        final Example exampleAfterPersistence = repository.save(exampleBeforePersistence);

        return buildExampleResponseDTO(exampleAfterPersistence);
    }

    public List<ExampleResponseDTO> getAll(Long greaterThan) {
        return this.repository.findAll().stream()
                .filter(example -> this.isLongValueGreaterThan(example.getLongValue(), greaterThan)) // filter es como un foreach que pregunta a cada example si cumple la condición
                .map(this::buildExampleResponseDTO) // armo response DTOs
                .collect(Collectors.toList()); // los junto en lista
    }

    private boolean isLongValueGreaterThan(Long longValue, Long greaterThan) {
        return greaterThan == null || longValue > greaterThan;
    }

    private LocalDateTime resolveDateValue(String dateValue) {
        try {
            return LocalDateTime.parse(dateValue);
        } catch (Exception e) {
            throw new BadRequestException("Invalid dateValue: " + dateValue);
        }
    }

    private ExampleEnum resolveEnumValue(String enumValue) {
        return ExampleEnum.fromCode(enumValue)
                .orElseThrow(() -> new BadRequestException("Invalid enumValue: " + enumValue));
    }

    private void validate(ExampleRequestDTO requestDTO) {
        if (requestDTO.getLongValue() <= 0) {
            throw new BadRequestException("longValue must be greater than 0");
        }
    }

    protected ExampleResponseDTO buildExampleResponseDTO(Example example) {
        final ExampleResponseDTO responseDTO = new ExampleResponseDTO();

        responseDTO
                .setLongValue(example.getLongValue())
                .setDoubleValue(example.getDoubleValue())
                .setStringValue(example.getStringValue())
                .setDateValue(example.getDateValue().toString())
                .setEnumValue(example.getEnumValue().getCode());

        return responseDTO;
    }

    public ExampleResponseDTO getByLongValue(long longValue) {
        return this.repository.getByLongValue(longValue)
                .map(this::buildExampleResponseDTO)
                .orElseThrow(() -> new BadRequestException(HttpStatus.NOT_FOUND.value(),
                        String.format("Example with long_value [%s] not found", longValue)));
    }
}
