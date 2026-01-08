package pl.jacobit.fraud.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.jacobit.fraud.model.TransactionRelationship;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface TransactionRepository extends Neo4jRepository<TransactionRelationship, UUID> {


    @Query("""
            MATCH (from:AccountNode {accountId: $fromAccount})
            MATCH (to:AccountNode {accountId: $toAccount})
            CREATE (from)-[t:TRANSACTION {transactionId: $transactionId, amount: $amount, timestamp: $timestamp}]->(to)
            RETURN t
            """)
    TransactionRelationship createTransaction(UUID fromAccount, UUID toAccount, UUID transactionId, BigDecimal amount, Instant timestamp);

}
