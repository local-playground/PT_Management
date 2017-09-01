package org.rssb.phonetree.repository;


import org.rssb.phonetree.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigJpaRepository extends JpaRepository<Config,Integer>{
}
