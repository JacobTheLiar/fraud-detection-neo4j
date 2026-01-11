package pl.jacobit.fraud.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.jacobit.fraud.model.TransactionNode;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends Neo4jRepository<TransactionNode, UUID> {

    @Query("""
      MATCH allTransactions = (
      source:Account {accountId: $sourceAccountId})-[:FROM_ACCOUNT]
      ->(t:Transaction)-[:TO_ACCOUNT]
      ->(target:Account {accountId: $targetAccountId})
      RETURN allTransactions
      """)
    List<TransactionNode> findTransactionsBetween(UUID sourceAccountId, UUID targetAccountId);

    @Query("""
      MATCH oneDirection = (source:Account)-[:FROM_ACCOUNT]->(t:Transaction)-[:TO_ACCOUNT]->(target:Account)
      WHERE (source.accountId = $accountId1 AND target.accountId = $accountId2)
         OR (source.accountId = $accountId2 AND target.accountId = $accountId1)
      RETURN oneDirection
      """)
    List<TransactionNode> findTransactionsBetweenBiDirectional(UUID accountId1, UUID accountId2);

}
