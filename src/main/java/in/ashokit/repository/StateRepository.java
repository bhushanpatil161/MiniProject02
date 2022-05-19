package in.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.StateMasterEntity;

public interface StateRepository extends JpaRepository<StateMasterEntity, Integer>{

	public List<StateMasterEntity> findByCountryId(Integer countryId);
}
