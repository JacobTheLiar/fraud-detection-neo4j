package pl.jacobit.fraud.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Node("Transaction")
public record TransactionNode(
        @Id
        UUID transactionId,
        BigDecimal amount,
        Instant timestamp,

        @Relationship(type = "TO_ACCOUNT", direction = Relationship.Direction.OUTGOING)
        AccountNode targetAccount,

        @Relationship(type = "FROM_ACCOUNT", direction = Relationship.Direction.INCOMING)
        AccountNode sourceAccount
) {
}

