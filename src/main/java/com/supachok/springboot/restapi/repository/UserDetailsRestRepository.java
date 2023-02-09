package com.supachok.springboot.restapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.supachok.springboot.restapi.entity.UserDetails;

//@RepositoryRestResource(path = "userDetails")
public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails, Long>{

}
