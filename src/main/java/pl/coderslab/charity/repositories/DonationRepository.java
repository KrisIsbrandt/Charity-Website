package pl.coderslab.charity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entities.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("select sum(d.quantity) from Donation d")
    Long getSumOfDonationQuantity();

    @Query("select count(distinct d.institution) from Donation d")
    Long getCountOfInstitutionWithDonations();
}

