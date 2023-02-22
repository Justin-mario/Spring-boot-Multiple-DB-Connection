package com.multipledatasource.primary.repo;

import com.multipledatasource.primary.models.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;




public interface UserRepository extends JpaRepository<UserDetail, Long>{
}
