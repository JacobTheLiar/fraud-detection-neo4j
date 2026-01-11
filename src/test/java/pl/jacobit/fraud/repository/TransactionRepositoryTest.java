package pl.jacobit.fraud.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.jacobit.fraud.model.AccountNode;
import pl.jacobit.fraud.model.TransactionNode;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@SpringBootTest
@Transactional
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    private final UUID accountId1 = UUID.randomUUID();
    private final UUID accountId2 = UUID.randomUUID();
    private final UUID transactionId = UUID.randomUUID();

    private final AccountNode account1 = new AccountNode(accountId1, "Account-1", BigDecimal.valueOf(1000), Instant.now());
    private final AccountNode account2 = new AccountNode(accountId2, "Account-2", BigDecimal.valueOf(1000), Instant.now());

    @Test
    void shouldCreateTransactionBetweenAccounts() {
        // given
        var timestamp = Instant.now();
        var amount = BigDecimal.valueOf(500);
        var transaction = new TransactionNode(transactionId, amount, timestamp, account2, account1);

        // when
        accountRepository.save(account1);
        accountRepository.save(account2);

        var result = transactionRepository.save(transaction);

        // then
        var soft = new SoftAssertions();
        soft.assertThat(result).isNotNull();
        soft.assertThat(result.transactionId()).isEqualTo(transactionId);
        soft.assertThat(result.targetAccount()).isEqualTo(account2);
        soft.assertThat(result.sourceAccount()).isEqualTo(account1);
        soft.assertThat(result.timestamp()).isEqualTo(timestamp);
        soft.assertThat(result.amount()).isEqualTo(amount);
        soft.assertAll();
    }

    @Test
    void shouldFindAllTransactionsBetween() {
        // given
        var notRelevantAccountId = UUID.randomUUID();
        var notRelevantAccount = new AccountNode(notRelevantAccountId, "notRelevantAccount", BigDecimal.valueOf(1000), Instant.now());
        accountRepository.save(notRelevantAccount);
        accountRepository.save(account1);
        accountRepository.save(account2);

        transactionRepository.save(new TransactionNode(UUID.randomUUID(), BigDecimal.valueOf(100), Instant.now(), notRelevantAccount, account2));
        transactionRepository.save(new TransactionNode(UUID.randomUUID(), BigDecimal.valueOf(200), Instant.now(), account2, notRelevantAccount));
        transactionRepository.save(new TransactionNode(UUID.randomUUID(), BigDecimal.valueOf(300), Instant.now(), account1, account2));
        transactionRepository.save(new TransactionNode(UUID.randomUUID(), BigDecimal.valueOf(300), Instant.now(), account2, account1));

        // when
        var result = transactionRepository.findTransactionsBetweenBiDirectional(accountId1, accountId2);

        // then
        var soft = new SoftAssertions();
        soft.assertThat(result).isNotNull();
        soft.assertThat(result.size()).isEqualTo(2);
        soft.assertThat(result.stream().anyMatch(t -> t.sourceAccount().equals(account1) && t.targetAccount().equals(account2))).isTrue();
        soft.assertThat(result.stream().anyMatch(t -> t.sourceAccount().equals(account2) && t.targetAccount().equals(account1))).isTrue();
        soft.assertAll();
    }

    @Test
    void shouldFindOneDirectionTransactionsBetween() {
        // given
        var notRelevantAccountId = UUID.randomUUID();
        var notRelevantAccount = new AccountNode(notRelevantAccountId, "notRelevantAccount", BigDecimal.valueOf(1000), Instant.now());
        accountRepository.save(notRelevantAccount);
        accountRepository.save(account1);
        accountRepository.save(account2);

        transactionRepository.save(new TransactionNode(UUID.randomUUID(), BigDecimal.valueOf(100), Instant.now(), notRelevantAccount, account2));
        transactionRepository.save(new TransactionNode(UUID.randomUUID(), BigDecimal.valueOf(200), Instant.now(), account2, notRelevantAccount));
        transactionRepository.save(new TransactionNode(UUID.randomUUID(), BigDecimal.valueOf(300), Instant.now(), account1, account2));
        transactionRepository.save(new TransactionNode(UUID.randomUUID(), BigDecimal.valueOf(300), Instant.now(), account2, account1));

        // when
        var result = transactionRepository.findTransactionsBetween(accountId1, accountId2);

        // then
        var soft = new SoftAssertions();
        soft.assertThat(result).isNotNull();
        soft.assertThat(result.size()).isEqualTo(1);
        soft.assertThat(result.getFirst().sourceAccount()).isEqualTo(account1);
        soft.assertThat(result.getFirst().targetAccount()).isEqualTo(account2);
        soft.assertAll();
    }
}