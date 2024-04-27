package jyothi.it.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jyothi.it.entity.Enquiry;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Integer> {

	@Query(value = "select count(*) from enquiry where counsellor  =:id", nativeQuery = true)
	public Long getTotalEnquiries(Integer id);

	@Query(value = "select count(*) from enquiry where counsellor =:id and status = :status", nativeQuery = true)
	public Long getOpenEnquiries(Integer id, String status);
	
	@Query(value = "select count(*) from enquiry where counsellor =:id and status =:status", nativeQuery = true)
	public Long getLostEnquiries(Integer id, String status);
	
	@Query(value = "select count(*) from enquiry where counsellor =:id and status =:status", nativeQuery = true)
	public Long getEnrolledEnquiries(Integer id, String status);

	public Enquiry findByEnqId(Integer enqId);
	
	public boolean existsByEnqId(Integer enqId);
	
//	 @Modifying
//	    @Query(value="update enquiry u set u.deliveryStatus = :deliveryStatus where u.eventId = :eventId", nativeQuery = true
//		public Long updateEnquiry(Integer enqId);
}
