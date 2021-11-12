package com.coderace.service;

import com.coderace.model.dtos.ExampleRequestDTO;
import com.coderace.model.dtos.ExampleResponseDTO;
import com.coderace.model.entities.Example;
import com.coderace.model.enums.ExampleEnum;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.ExampleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExampleService2 {

    private final ExampleRepository repository;

    public ExampleService2(ExampleRepository repository) {
        this.repository = repository;
    }

    public ExampleResponseDTO create(ExampleRequestDTO requestDTO) {
        this.validate(requestDTO);

        final LocalDateTime dateValue = resolveDateValue(requestDTO.getDateValue());
        final ExampleEnum enumValue = resolveEnumValue(requestDTO.getEnumValue());

        final Example example = new Example(requestDTO.getLongValue(), requestDTO.getDoubleValue(),
                requestDTO.getStringValue(), dateValue, enumValue);

        return buildExampleResponseDTO(repository.save(example));
    }

    public boolean bo() {
        return true;
    }

    public boolean bo2() {
        return true;
    }


    public boolean bo3() {
        return true;
    }


    public boolean bo4() {
        return true;
    }


    public boolean bo5() {
        return true;
    }

    public boolean bo6() {
        return true;
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
}
