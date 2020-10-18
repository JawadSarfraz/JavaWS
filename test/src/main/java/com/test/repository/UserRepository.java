package com.test.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.test.entities.User;

public interface UserRepository extends CrudRepository<User,Integer> {

	//BRING USER ON EMAIL AND CORRECT PASSWORD ENTERING MIT STATUS EIN
	@Query(value = "SELECT * FROM user u WHERE u.email = email AND u.password = password AND u.user_flag = 1",nativeQuery = true)
	User getUserByEmailAndPassword(String email,String password);
	//USER STATUS UPDATION ON DELETE CALL
	/*@Modifying
	@Query(value = "UPDATE exchange.user u SET user_flag = 0 WHERE u.id = userid",nativeQuery = true)
	User userStatusUpdate(@Param("userid")int userid);	
	*/
	@Query(value = "UPDATE exchange.user u SET user_flag = 0 WHERE u.id = :userid",nativeQuery = true)
	void userStatusUpdate(@Param("userid")int userid);	
	//BRING USERS ACTIVE USERS--> HAT STATUS EIN
	@Query(value = "SELECT * FROM user u WHERE u.user_flag = 1",nativeQuery = true)
	Iterable<User> findAllActiveUser();
	
	@Query(value = "SELECT * FROM user u WHERE u.email = :email",nativeQuery = true)
	User getUserByEmail(@Param("email") String email);
}
