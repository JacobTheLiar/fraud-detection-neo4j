package pl.jacobit.fraud.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.jacobit.fraud.model.AccountNode;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Transactional
@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void shouldCreateAccount() {
        // given
        var id = UUID.randomUUID();
        var accountName = "test account";
        var balance = new BigDecimal("123.45");
        var creationTime = Instant.now();

        // when
        var result = accountRepository.save(new AccountNode(id, accountName, balance, creationTime));

        // then
        var soft = new SoftAssertions();
        soft.assertThat(result).isNotNull();
        soft.assertThat(result.accountId()).isEqualTo(id);
        soft.assertThat(result.name()).isEqualTo(accountName);
        soft.assertThat(result.balance()).isEqualTo(balance);
        soft.assertThat(result.creationDate()).isEqualTo(creationTime);
        soft.assertAll();
    }

    @Test
    void shouldFindCreatedAccount() {
        // given
        var id = UUID.randomUUID();
        var accountName = "test account";
        var balance = new BigDecimal("123.45");
        var creationTime = Instant.now();

        // when
        accountRepository.save(new AccountNode(id, accountName, balance, creationTime));
        var result = accountRepository.findByAccountId(id);

        // then
        var soft = new SoftAssertions();
        soft.assertThat(result).isPresent();
        soft.assertThat(result.get().accountId()).isEqualTo(id);
        soft.assertThat(result.get().name()).isEqualTo(accountName);
        soft.assertThat(result.get().balance()).isEqualTo(balance);
        soft.assertThat(result.get().creationDate()).isEqualTo(creationTime);
        soft.assertAll();
    }

    @Test
    void shouldFindNoAccount() {
        // when
        var result = accountRepository.findByAccountId(UUID.randomUUID());

        // then
        var soft = new SoftAssertions();
        soft.assertThat(result).isEmpty();
        soft.assertAll();
    }
}