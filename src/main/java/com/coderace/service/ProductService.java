package com.coderace.service;

import com.coderace.model.dtos.ProductRequestDTO;
import com.coderace.model.dtos.ProductResponseDTO;
import com.coderace.model.entities.Product;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponseDTO create(ProductRequestDTO requestDTO) {
        this.validate(requestDTO);

        final Product ProductBeforePersistence = new Product(requestDTO.getName(), requestDTO.getSku(), requestDTO.getPrice());

        final Product ProductAfterPersistence = repository.save(ProductBeforePersistence);

        return buildProductResponseDTO(ProductAfterPersistence);
    }

    public List<ProductResponseDTO> getAll() {
        return this.repository.findAll().stream().map(this::buildProductResponseDTO).collect(Collectors.toList());
    }



    private LocalDateTime resolveDateValue(String dateValue) {
        try {
            return LocalDateTime.parse(dateValue);
        } catch (Exception e) {
            throw new BadRequestException("Invalid dateValue: " + dateValue);
        }
    }


    private void validate(ProductRequestDTO requestDTO) {

    }

    protected ProductResponseDTO buildProductResponseDTO(Product Product) {
        final ProductResponseDTO responseDTO = new ProductResponseDTO();

        return responseDTO;
    }

    public ProductResponseDTO getBySku(String sku) {
        return this.repository.getBySku(sku)
                .map(this::buildProductResponseDTO)
                .orElseThrow(() -> new BadRequestException(HttpStatus.NOT_FOUND.value(),
                        String.format("Product with sku [%s] not found", sku)));
    }
}
