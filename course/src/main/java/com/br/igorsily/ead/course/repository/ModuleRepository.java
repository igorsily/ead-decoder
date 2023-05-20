package com.br.igorsily.ead.course.repository;

import com.br.igorsily.ead.course.entity.Module;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID>, JpaSpecificationExecutor<Module> {


    @Query("SELECT m FROM Module m WHERE m.course.id = :courseId")
    List<Module> findAllByCourseId(@Param("courseId") UUID courseId);

    @Query(value = "SELECT m FROM Module m where m.course.id = :courseId and m.id = :moduleId")
    Optional<Module> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);
}