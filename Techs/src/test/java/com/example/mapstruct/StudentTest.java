package com.example.mapstruct;

import com.example.mapstruct.sameType.Student;
import com.example.mapstruct.sameType.StudentDto;
import com.example.mapstruct.sameType.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StudentTest {
    @Resource
    private StudentMapper studentMapper;

    @Test
    public void testToDTO() {
        Integer studentId = 123;
        String studentName = "小王";

        Student student = new Student();
        student.setId(studentId);
        student.setName(studentName);

        StudentDto studentDTO = studentMapper.toDTO(student);

        assertEquals(studentId, studentDTO.getSId());
        assertEquals(studentName, studentDTO.getName());
    }
}
