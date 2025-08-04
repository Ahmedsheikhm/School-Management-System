package com.school.repository;

import com.school.entity.Class;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    
    List<Class> findBySubject(Subject subject);
    
    List<Class> findByTeacher(Teacher teacher);
    
    List<Class> findBySemesterAndAcademicYear(String semester, String academicYear);
    
    @Query("SELECT c FROM Class c JOIN c.students s WHERE s.id = :studentId")
    List<Class> findByStudentId(@Param("studentId") Long studentId);
    
    @Query("SELECT c FROM Class c WHERE c.capacity > (SELECT COUNT(s) FROM c.students s)")
    List<Class> findAvailableClasses();
} 