package com.sodabottle.stext.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sodabottle.stext.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	void findAllById(Long id);

}
