package com.example.mapstruct;

import com.example.mapstruct.sameProps.Doctor;
import com.example.mapstruct.sameProps.DoctorDto;
import com.example.mapstruct.sameProps.DoctorMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoctorTest {
    @Test
    public void testToDTO() {
        Long doctorId = 9527L;
        String doctorName = "mghio";

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setName(doctorName);

        DoctorDto doctorDTO = DoctorMapper.INSTANCE.toDTO(doctor);

        assertEquals(doctorId, doctorDTO.getId());
        assertEquals(doctorName, doctorDTO.getName());
    }
}
