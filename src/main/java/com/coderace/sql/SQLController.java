package com.coderace.sql;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/sql")
public class SQLController {

    @PersistenceContext
    private EntityManager em;

    @PostMapping("/query")
    public ResponseEntity<Object> query(@RequestBody String query) {
        final Query emQuery = em.createNativeQuery(query, Tuple.class);
        final List<Tuple> queryRows = emQuery.getResultList();

        final List<Map<String, Object>> formattedRows = new ArrayList<>();

        queryRows.forEach(row -> {
            final Map<String, Object> formattedRow = new HashMap<>();

            row.getElements().forEach(column -> {
                final String columnName = column.getAlias();
                final Object columnValue = row.get(column);

                formattedRow.put(columnName, columnValue);
            });

            formattedRows.add(formattedRow);
        });

        return ResponseEntity.ok().body(formattedRows);
    }

    @PostMapping("/script")
    @Transactional
    public ResponseEntity<Object> script(@RequestBody String query) {
        final Query emQuery = em.createNativeQuery(query);
        final int totalRowsUpdated = emQuery.executeUpdate();

        String message = "Executed successfully.";

        if (query.toLowerCase().contains("update")) {
            message += " Total rows updated: " + totalRowsUpdated;
        }

        return ResponseEntity.ok().body(message);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleException(SQLException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
