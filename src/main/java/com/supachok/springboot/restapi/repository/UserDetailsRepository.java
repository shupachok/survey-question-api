package com.supachok.springboot.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supachok.springboot.restapi.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{

}
