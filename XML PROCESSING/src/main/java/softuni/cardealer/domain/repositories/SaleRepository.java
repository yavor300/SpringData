package softuni.cardealer.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.cardealer.domain.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
