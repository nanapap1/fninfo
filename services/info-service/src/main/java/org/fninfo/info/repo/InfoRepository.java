package org.fninfo.info.repo;

import org.fninfo.info.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoRepository extends JpaRepository<Info,Integer> {

    Info findByNameEventAndStatusOf(String name, boolean status);

}
