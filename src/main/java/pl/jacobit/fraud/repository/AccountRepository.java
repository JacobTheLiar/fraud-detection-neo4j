package pl.jacobit.fraud.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pl.jacobit.fraud.model.AccountNode;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends Neo4jRepository<AccountNode, UUID> {
    Optional<AccountNode> findByAccountId(UUID accountId);
}
