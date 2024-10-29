package com.crud_demo.services;

import com.crud_demo.DTO.CourseDTOSimple;
import com.crud_demo.DTO.StudentDTO;
import com.crud_demo.DTO.StudentRegisterDTO;
import com.crud_demo.entities.Student;
import com.crud_demo.exceptions.ResourceNotFoundException;
import com.crud_demo.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;


    public Student createStudent(StudentRegisterDTO studentDTO, String username, String email){
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setAddress(studentDTO.getAddress());
        student.setUsername(username);
        student.setEmail(email);
        student.setDateOfEnrollment(LocalDate.now());
        student.setTimeOfCreation(LocalDate.now());
        student.setTimeOfUpdate(LocalDate.now());
        student.setDeleted(false);
        return studentRepository.save(student);
    }

    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with" + id));
        if(student.isDeleted()) throw new ResourceNotFoundException("Student not found with" + id);
        return student;
    }

    public Student updateStudent(Long id, Student studentDetails){
        Student student = getStudentById(id);
        if(student.isDeleted()) throw new IllegalArgumentException("Student is Deleted");

        student.setName(studentDetails.getName());
        student.setUsername(studentDetails.getUsername());
        student.setAge(studentDetails.getAge());
        student.setEmail(studentDetails.getEmail());
        student.setAddress(studentDetails.getAddress());
        student.setDateOfEnrollment(studentDetails.getDateOfEnrollment());
        student.setTimeOfUpdate(LocalDate.now());

        return studentRepository.save(student);
    }

    public Long getStudentIdByUserName(String username){
        return studentRepository.findStudentIdByUserName(username);
    }

    public StudentDTO getStudentDTO(String studentEmail){
        Student student = studentRepository.findStudentByEmail(studentEmail);

        List<Object[]> results = studentRepository.fetchCourseDTOSimpleNative(student.getId());
        List<CourseDTOSimple> courses = results.stream()
                .map(result -> new CourseDTOSimple((Long) result[0], (String) result[1], (String) result[2]))
                .collect(Collectors.toList());

        StudentDTO studentDTO = new StudentDTO(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getEmail(),
                student.getAddress(),
                student.getDateOfEnrollment(),
                courses
        );

        return studentDTO;
    }

    public String getEmailByStudentId(Long studentId){
        return studentRepository.findEmailByStudentId(studentId);
    }

    public void deleteStudent(Long id){
        Student student = getStudentById(id);
//        studentRepository.delete(student);
        student.setDeleted(true);
    }
}
