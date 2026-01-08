package pl.jacobit.fraud.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import pl.jacobit.fraud.model.AccountNode;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends Neo4jRepository<AccountNode, UUID> {

    // Same as repository.save(new AccountNode(...)), Query used for learning purposes
    @Query("CREATE (a:AccountNode {accountId: $accountId, name: $name, balance: $balance, creationDate: $creationDate}) RETURN a")
    AccountNode createAccount(UUID accountId, String name, BigDecimal balance, Instant creationDate);

    // Same as repository.findById(UUID), Query used for learning purposes
    @Query("MATCH (a:AccountNode {accountId: $accountId}) RETURN a")
    // @Query("MATCH (a:AccountNode) WHERE a.accountId = $accountId RETURN a") more SQL-like
    Optional<AccountNode> findAccountById(UUID accountId);
}
