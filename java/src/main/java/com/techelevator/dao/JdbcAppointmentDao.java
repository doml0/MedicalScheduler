package com.techelevator.dao;

import com.techelevator.model.Appointment;
import com.techelevator.model.Doctor;
import com.techelevator.model.Patient;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JdbcAppointmentDao implements AppointmentDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAppointmentDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List <Appointment> getAppointments(){
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * " + "FROM appointment;" ;
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()){
            appointments.add(mapRowToAppointment(results));
        }
        return appointments;
    }

    @Override
    public Appointment getAppointmentById(int appointmentId){
        Appointment appointment = null;
        String sql = "SELECT * " + "FROM appointment " + "WHERE appointment_id = ?" ;
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, appointmentId);

        if(results.next()){
            appointment = mapRowToAppointment(results);
        }
        return appointment;
    }


    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment JOIN patient ON appointment.patient_id=patient.patient_id WHERE patient.patient_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while(result.next()) {
                appointments.add(mapRowToAppointment(result));
            }
        } catch (NullValueInNestedPathException | EmptyResultDataAccessException e) {
            throw new RuntimeException("No appointment found");
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment JOIN doctor ON appointment.doctor_id=doctor.doctor_id WHERE doctor.doctor_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, doctorId);
            while(result.next()) {
                appointments.add(mapRowToAppointment(result));
            }
        } catch (NullValueInNestedPathException | EmptyResultDataAccessException e) {
            throw new RuntimeException("No appointment found");
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorIdDate(int doctorId, LocalDate appointmentDate) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment JOIN doctor ON appointment.doctor_id=doctor.doctor_id WHERE doctor.doctor_id = ? AND appointment.appointment_date = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, doctorId, appointmentDate);
            while(result.next()) {
                appointments.add(mapRowToAppointment(result));
            }
        } catch (NullValueInNestedPathException | EmptyResultDataAccessException e) {
            throw new RuntimeException("No appointment found");
        }
        return appointments;
    }

    @Override
    public void createAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointment(patient_id, doctor_id, appointment_duration, description, appointment_date, appointment_time) VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, appointment.getPatientId(), appointment.getDoctorId(), appointment.getAppointmentDuration(), appointment.getDescription(), appointment.getAppointmentDate(), appointment.getAppointmentTime());
    }


    @Override
    public void updateAppointment(int appointmentId, Appointment appointment) {
        String sql = "UPDATE public.appointment\n" +
                "\tSET appointment_id=?, patient_id=?, doctor_id=?, appointment_duration=?, description=?, appointment_date=?, appointment_time=?\n" +
                "\tWHERE appointment_id = ?;";
        jdbcTemplate.update(sql,appointment.getAppointmentId(), appointment.getPatientId(), appointment.getDoctorId(), appointment.getAppointmentDuration(), appointment.getDescription(),
                appointment.getAppointmentDate(), appointment.getAppointmentTime(), appointmentId);

    }

    @Override
    public void deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM appointment WHERE appointment_id=?;";
        jdbcTemplate.update(sql,appointmentId);
    }



    private Appointment mapRowToAppointment(SqlRowSet results) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(results.getInt("appointment_id"));
        appointment.setPatientId(results.getInt("patient_id"));
        appointment.setDoctorId(results.getInt("doctor_id"));
        appointment.setAppointmentDuration(results.getInt("appointment_duration"));
        appointment.setDescription(results.getString("description"));
        appointment.setAppointmentTime((results.getTime("appointment_time").toLocalTime()));
        appointment.setAppointmentDate(LocalDate.parse(results.getString("appointment_date")));
        return appointment;

    }
}

