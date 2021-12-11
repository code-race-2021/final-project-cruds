package com.coderace.service;

import com.coderace.model.dtos.ProductRequestDTO;
import com.coderace.model.dtos.ProductResponseDTO;
import com.coderace.model.entities.Product;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@DisplayName("ProductService test | unit")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductService service;

    @Test
    @DisplayName("create | ok")
    void createOk() {
        final ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("name");
        requestDTO.setSku("sku");
        requestDTO.setPrice(50d);

        final Product Product = this.defaultProduct();

        when(repository.save(any(Product.class))).thenReturn(Product);

        final ProductResponseDTO responseDTO = service.create(requestDTO);

        assertEquals(service.buildProductResponseDTO(Product), responseDTO);
    }

    @Test
    @DisplayName("create | given invalid price | should throw BadRequestException")
    void createInvalidPriceOk() {
        final ProductRequestDTO requestDTO = new ProductRequestDTO();

        requestDTO.setSku("sku");
        requestDTO.setPrice(-5d);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Product price must be positive", exception.getMessage())
        );
    }

    @Test
    @DisplayName("create | without sku | should throw BadRequestException")
    void createWithoutSkuError() {
        final ProductRequestDTO requestDTO = new ProductRequestDTO();

        requestDTO.setSku(null);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Product sku is invalid", exception.getMessage())
        );
    }

    @Test
    @DisplayName("create | given invalid sku | should throw BadRequestException")
    void createInvalidSkuError() {
        final ProductRequestDTO requestDTO = new ProductRequestDTO();

        requestDTO.setSku("//&$@");

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Product sku is invalid", exception.getMessage())
        );
    }

    @Test
    @DisplayName("buildProductResponseDTO | ok")
    void buildProductResponseDTO() {
        final int id = 5;
        final String name = "name";
        final String sku = "sku";
        final double price = 50d;

        final Product Product = new Product(id, name, sku, price);

        final ProductResponseDTO dto = service.buildProductResponseDTO(Product);

        assertAll("Expected dto",
                () -> assertEquals(id, dto.getId()),
                () -> assertEquals(name, dto.getName()),
                () -> assertEquals(sku, dto.getSku()),
                () -> assertEquals(price, dto.getPrice())
        );
    }

    @Test
    @DisplayName("getAll | ok")
    void getAllOk() {
        final Product Product = this.defaultProduct();

        final List<Product> all = Collections.singletonList(Product);

        when(repository.findAll()).thenReturn(all);

        final List<ProductResponseDTO> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals(service.buildProductResponseDTO(Product), result.get(0));
    }

    @Test
    @DisplayName("getBySku | ok")
    void getBySkuOk() {
        final Product product = this.defaultProduct();

        final String sku = product.getSku();

        when(repository.getBySku(sku)).thenReturn(Optional.of(product));

        final ProductResponseDTO result = service.getBySku(sku);

        assertEquals(service.buildProductResponseDTO(product), result);
    }

    @Test
    @DisplayName("getBySku | not found | should throw BadRequestException")
    void getBySkuNotFound() {
        final String sku = "sku";

        when(repository.getBySku(sku)).thenReturn(Optional.empty());

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.getBySku(sku));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode()),
                () -> assertEquals(String.format("Product with sku [%s] not found", sku), exception.getMessage())
        );
    }

    private Product defaultProduct() {
        return new Product("name", "sku", 100d);
    }
}