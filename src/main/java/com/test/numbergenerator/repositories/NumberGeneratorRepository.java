package com.test.numbergenerator.repositories;

import com.test.numbergenerator.models.CarNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberGeneratorRepository extends JpaRepository<CarNumber, Long> {

    boolean existsBySerialNumberAndRegistryNumber(Integer serialNumber, Integer registryNumber);

    CarNumber findTopByOrderByIdDesc();
}
