package com.coderace.service;

import com.coderace.model.dtos.ProductRequestDTO;
import com.coderace.model.dtos.ProductResponseDTO;
import com.coderace.model.entities.Product;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.ProductRepository;
import com.coderace.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@DisplayName("ProductService test | unit")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    /*@Mock
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
    void createInvalidLongValueOk() {
        final ProductRequestDTO requestDTO = new ProductRequestDTO().setPrice(-5d);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("longValue must be greater than 0", exception.getMessage())
        );
    }

    @Test
    @DisplayName("create | given invalid date value | should throw BadRequestException")
    void createInvalidDateValueOk() {
        final String invalidDateString = "not-a-date";

        final ProductRequestDTO requestDTO = new ProductRequestDTO()
                .setLongValue(1L)
                .setDateValue(invalidDateString);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Invalid dateValue: " + invalidDateString, exception.getMessage())
        );
    }

    @Test
    @DisplayName("create | given invalid enum value | should throw BadRequestException")
    void createInvalidEnumValueOk() {
        final String invalidEnumString = "c";

        final ProductRequestDTO requestDTO = new ProductRequestDTO()
                .setLongValue(5L)
                .setDateValue(LocalDateTime.MAX.toString())
                .setEnumValue(invalidEnumString);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Invalid enumValue: " + invalidEnumString, exception.getMessage())
        );
    }

    @Test
    @DisplayName("buildProductResponseDTO | ok")
    void buildProductResponseDTO() {
        final long longValue = 1L;
        final double doubleValue = 2D;
        final String stringValue = "a";
        final LocalDateTime dateValue = LocalDateTime.MIN;
        final ProductEnum enumValue = ProductEnum.B;

        final Product Product = new Product(longValue, doubleValue, stringValue, dateValue, enumValue);

        final ProductResponseDTO dto = service.buildProductResponseDTO(Product);

        assertAll("Expected dto",
                () -> assertEquals(longValue, dto.getLongValue()),
                () -> assertEquals(doubleValue, dto.getDoubleValue()),
                () -> assertEquals(stringValue, dto.getStringValue()),
                () -> assertEquals(dateValue.toString(), dto.getDateValue()),
                () -> assertEquals(enumValue.getCode(), dto.getEnumValue())
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
    @DisplayName("getByLongValue | ok")
    void getByLongValueOk() {
        final Product Product = this.defaultProduct();

        when(repository.getByLongValue(1L)).thenReturn(Optional.of(Product));

        final ProductResponseDTO result = service.getByLongValue(1L);

        assertEquals(service.buildProductResponseDTO(Product), result);
    }

    @Test
    @DisplayName("getByLongValue | not found | should throw BadRequestException")
    void getByLongValueNotFound() {
        when(repository.getByLongValue(1L)).thenReturn(Optional.empty());

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.getByLongValue(1L));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode()),
                () -> assertEquals(String.format("Product with long_value [%s] not found", 1L), exception.getMessage())
        );
    }

    private Product defaultProduct() {
        return new Product(1L, 2D, "a", LocalDateTime.MIN, ProductEnum.B);
    }*/
}