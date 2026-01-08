package pl.jacobit.fraud.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Node
public record AccountNode(
        @Id
        UUID accountId,
        String name,
        BigDecimal balance,
        Instant creationDate
) {}
