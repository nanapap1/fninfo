package org.fninfo.info.repo;

import org.fninfo.info.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Integer, Storage> {
}
