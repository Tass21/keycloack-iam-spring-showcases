package com.tdksoft.demokeycloak.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void itShouldSelectCustomerByPhoneNumber() {

        // Given
        UUID id = UUID.randomUUID();
        String firstName = "Keng";
        String lastName = "Hen";
        String email = "kenghen@tdksoft.com";
        String phoneNumber = "0000";
        Integer age = 29;
        Customer customer = new Customer(id, firstName, lastName, email, age, phoneNumber);

        // When
        underTest.save(customer);

        // Then
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }

    @Test
    void itNotShouldSelectCustomerByPhoneNumberWhenNumberDoesNotExists() {

        // Given
        String phoneNumber = "0000";

        // When
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);

        // Then
        assertThat(optionalCustomer).isNotPresent();
    }

    @Test
    void itShouldSaveCustomer() {
        // Given
        UUID id = UUID.randomUUID();
        String firstName = "Djuit";
        String lastName = "Sor";
        String email = "djuitsor@tdksoft.com";
        String phoneNumber = "0000";
        Integer age = 29;
        Customer customer = new Customer(id, firstName, lastName, email, age, phoneNumber);

        // When
        underTest.save(customer);

        // Then
        Optional<Customer> optionalCustomer = underTest.findById(id);
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
//                    assertThat(c.getId()).isEqualTo(id);
//                    assertThat(c.getName()).isEqualTo("Abel");
//                    assertThat(c.getPhoneNumber()).isEqualTo("1111");
                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }

    @Test
    void itShouldNotSaveCustomerWhenFirstNameIsNull() {

        // Given
        UUID id = UUID.randomUUID();
        String firstName = null;
        String lastName = "alex";
        String email = "djuitalex@tdksoft.com";
        String phoneNumber = "0000";
        Integer age = 92;
        Customer customer = new Customer(id, firstName, lastName, email, age, phoneNumber);

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(customer))
                //.hasMessageContaining("not-null property references a null or transient value : com.tdksoft.testing.demo.customer.firstName")
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void itShouldNotSaveCustomerWhenPhoneNumberIsNull() {
        // Given
        UUID id = UUID.randomUUID();
        String firstName = "Arold";
        String lastName = "alex";
        String email = "aroldalex@tdksoft.com";
        String phoneNumber = null;
        Integer age = 92;
        Customer customer = new Customer(id, firstName, lastName, email, age, phoneNumber);

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(customer))
               // .hasMessageContaining("not-null property references a null or transient value : com.tdksoft.testing.demo.customer.phoneNumber")
                .isInstanceOf(DataIntegrityViolationException.class);

    }

}