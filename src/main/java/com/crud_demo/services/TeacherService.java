package com.crud_demo.services;

import com.crud_demo.DTO.CourseInfoDTO;
import com.crud_demo.DTO.TeacherDTO;
import com.crud_demo.DTO.TeacherRegisterDTO;
import com.crud_demo.entities.Course;
import com.crud_demo.entities.Teacher;
import com.crud_demo.exceptions.ResourceNotFoundException;
import com.crud_demo.repos.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Autowired
    public TeacherRepository teacherRepository;

//    public Teacher createTeacher(Teacher teacher){
//        teacher.setTimeOfCreation(LocalDate.now());
//        teacher.setTimeOfUpdate(LocalDate.now());
//        teacher.setDeleted(false);
//
//        return teacherRepository.save(teacher);
//    }

    public Teacher createTeacher(TeacherRegisterDTO teacherDTO, String username, String email){
        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setSubject(teacherDTO.getSubject());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());
        teacher.setUsername(username);
        teacher.setEmail(email);
        teacher.setTimeOfCreation(LocalDate.now());
        teacher.setTimeOfUpdate(LocalDate.now());
        teacher.setDeleted(false);

        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id){
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with" + id));
        if(teacher.isDeleted()) throw new ResourceNotFoundException("Teacher not found with" + id);
        return teacher;
    }

    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = getTeacherById(id);
        if(teacher.isDeleted()) throw new IllegalArgumentException("Teacher is Deleted");

        teacher.setName(teacherDetails.getName());
        teacher.setUsername(teacherDetails.getUsername());
        teacher.setSubject(teacherDetails.getSubject());
        teacher.setEmail(teacherDetails.getEmail());
        teacher.setPhoneNumber(teacherDetails.getPhoneNumber());
        teacher.setTimeOfUpdate(LocalDate.now());

        return teacherRepository.save(teacher);
    }
    public TeacherDTO getTeacherDTO(Long teacherId){
        Teacher teacher = getTeacherById(teacherId);
        List<Course> courses = teacher.getCourses().stream().collect(Collectors.toList());

        List<CourseInfoDTO> courseInfos = courses.stream()
                .map(course -> new CourseInfoDTO(
                        course.getId(),
                        course.getName(),
                        course.getStartDate(),
                        course.getEndDate()
                ))
                .collect(Collectors.toList());

        TeacherDTO teacherDTO = new TeacherDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getSubject(),
                teacher.getEmail(),
                teacher.getPhoneNumber(),
                courseInfos
        );

        return teacherDTO;
    }

    public Long getIdByUserName(String username){
        return teacherRepository.findTeacherIdByUserName(username);
    }

    public void deleteTeacher(Long id) {
        Teacher teacher = getTeacherById(id);
//        teacherRepository.delete(teacher);
        teacher.setDeleted(true);
    }
}
