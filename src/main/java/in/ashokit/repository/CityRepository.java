package in.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.CitiesMasterEntity;

public interface CityRepository extends JpaRepository<CitiesMasterEntity, Integer>{

	public List<CitiesMasterEntity> findByStateId(Integer stateId);
}
