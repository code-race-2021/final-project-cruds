package com.coderace.service;

import com.coderace.model.dtos.ProductRequestDTO;
import com.coderace.model.dtos.ProductResponseDTO;
import com.coderace.model.entities.Product;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

        final Product productBeforePersistence = new Product(requestDTO.getName(), requestDTO.getSku(), requestDTO.getPrice());

        final Product productAfterPersistence = repository.save(productBeforePersistence);

        return buildProductResponseDTO(productAfterPersistence);
    }

    public List<ProductResponseDTO> getAll() {
        return this.repository.findAll().stream().map(this::buildProductResponseDTO).collect(Collectors.toList());
    }

    private void validate(ProductRequestDTO requestDTO) {
        if (this.containsInvalidCharacters(requestDTO)) {
            throw new BadRequestException("Product sku is invalid");
        }

        if (requestDTO.getPrice() <= 0) {
            throw new BadRequestException("Product price must be positive");
        }
    }

    private boolean containsInvalidCharacters(ProductRequestDTO requestDTO) {
        final List<String> chars = Arrays.asList(requestDTO.getSku().split(""));

        return chars.stream().anyMatch(c -> !Character.isLetterOrDigit(c.charAt(0)));
    }

    protected ProductResponseDTO buildProductResponseDTO(Product product) {
        final ProductResponseDTO responseDTO = new ProductResponseDTO();

        responseDTO.setName(product.getName());
        responseDTO.setSku(product.getSku());
        responseDTO.setPrice(product.getPrice());

        return responseDTO;
    }

    public ProductResponseDTO getBySku(String sku) {
        return this.repository.getBySku(sku)
                .map(this::buildProductResponseDTO)
                .orElseThrow(() -> new BadRequestException(HttpStatus.NOT_FOUND.value(),
                        String.format("Product with sku [%s] not found", sku)));
    }
}
