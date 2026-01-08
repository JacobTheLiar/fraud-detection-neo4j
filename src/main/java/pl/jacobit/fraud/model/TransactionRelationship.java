package pl.jacobit.fraud.model;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RelationshipProperties
public record TransactionRelationship(
        @RelationshipId
        UUID transactionId,

        @TargetNode
        AccountNode targetAccount,

        BigDecimal amount,
        Instant timestamp
) {}
