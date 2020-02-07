package com.sodabottle.stext.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sodabottle.stext.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	void findAllById(Long id);

	@Query(value = "SELECT * FROM user WHERE email = :email OR mobile = :mobile ORDER BY id limit 1", nativeQuery = true)
	User findByEmailOrMobile(@Param("email") String email, @Param("mobile") String mobile);

}
