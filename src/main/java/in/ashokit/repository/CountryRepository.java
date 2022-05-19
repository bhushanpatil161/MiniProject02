package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.CountryMasterEntity;

public interface CountryRepository extends JpaRepository<CountryMasterEntity, Integer> {

}
