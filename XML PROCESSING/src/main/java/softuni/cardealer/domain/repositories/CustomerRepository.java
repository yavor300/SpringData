package softuni.cardealer.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.cardealer.domain.entities.Customer;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Set<Customer> getAllByOrderByBirthDateAscYoungDriverAsc();

    @Query("select c from Customer  c order by c.birthDate, c.youngDriver desc")
    Set<Customer> findAllAndSort();
}
