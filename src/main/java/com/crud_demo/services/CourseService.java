package com.crud_demo.services;

import com.crud_demo.DTO.CourseDTO;
import com.crud_demo.DTO.CourseRegisterDTO;
import com.crud_demo.DTO.StudentDTOSimple;
import com.crud_demo.entities.Course;
import com.crud_demo.entities.Teacher;
import com.crud_demo.exceptions.ResourceNotFoundException;
import com.crud_demo.repos.CourseRepository;
import com.crud_demo.repos.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public Course createCourse(CourseRegisterDTO courseDTO) {
        // Validate start and end dates
        if (courseDTO.getStartDate().isAfter(courseDTO.getEndDate())) {
            throw new IllegalArgumentException("Course start date must be before end date");
        }
        Course course = new Course();
        course.setName(courseDTO.getCourseName());
        course.setDescription(courseDTO.getDescription());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        Long teacherID = courseDTO.getTeacherId();
        Teacher teach = teacherRepository.getReferenceById(teacherID);
        course.setTeacher(teach);
        course.setTimeOfCreation(LocalTime.now());
        course.setTimeOfUpdate(LocalTime.now());
        course.setDeleted(false);
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
        if(course.isDeleted()) throw new ResourceNotFoundException("Course not found with id " + id);
        return course;
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourseById(id);
        if(course.isDeleted()) throw new IllegalArgumentException("Course is Deleted");

        course.setName(courseDetails.getName());
        course.setDescription(courseDetails.getDescription());

        // Validate dates
        if (courseDetails.getStartDate().isAfter(courseDetails.getEndDate())) {
            throw new IllegalArgumentException("Course start date must be before end date");
        }
        course.setStartDate(courseDetails.getStartDate());
        course.setEndDate(courseDetails.getEndDate());

        // Assign teacher if provided
        if (courseDetails.getTeacher() != null) {
            Teacher teacher = teacherRepository.findById(courseDetails.getTeacher().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + courseDetails.getTeacher().getId()));
            course.setTeacher(teacher);
        } else {
            course.setTeacher(null);
        }
        course.setTimeOfUpdate(LocalTime.now());

        return courseRepository.save(course);
    }

    public CourseDTO getCourseDTO(Long courseId){
        Course course = getCourseById(courseId);
//          Doing Manually on the java side
//        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
//
//        List<StudentDTOSimple> students = enrollments.stream()
//                .map(enrollment -> new StudentDTOSimple(
//                        enrollment.getStudent(),
//                        studentService.getStudentById(enrollment.getStudent()).getName(),
//                        enrollment.getEnrollmentStatus()
//                ))
//                .collect(Collectors.toList());

//        By using Quary Annotation and Writing Native Quary
        List<Object[]> results = courseRepository.fetchStudentDTOSimpleNative(courseId);
        List<StudentDTOSimple> students = results.stream()
                .map(result -> new StudentDTOSimple((Long) result[0], (String) result[1], (String) result[2]))
                .collect(Collectors.toList());

        CourseDTO courseDTO = new CourseDTO(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getStartDate(),
                course.getEndDate(),
                course.getTeacher() != null ? course.getTeacher().getId() : null,
                students
        );

        return courseDTO;
    }

    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
        course.setDeleted(true);
    }
}
