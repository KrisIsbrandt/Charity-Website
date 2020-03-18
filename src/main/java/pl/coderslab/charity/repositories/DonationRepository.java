package pl.coderslab.charity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.User;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("select sum(d.quantity) from Donation d")
    Long getSumOfDonationQuantity();

    @Query("select count(distinct d.institution) from Donation d")
    Long getCountOfInstitutionWithDonations();

    @Query("select d from Donation d where d.user = ?1 order by d.pickedUp desc, d.created desc")
    List<Donation> findAllByUser(User user);
}

