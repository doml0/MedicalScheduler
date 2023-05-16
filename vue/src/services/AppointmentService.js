import axios from 'axios';
const http = axios.create({
    baseURL:"http://localhost:9000"
});

export default{
    getAppointments() {
        return http.get(`/appointments`);
    },
    getAppointmentById(id) {
        return http.get(`/appointments/${id}`);
    },
    createAppointment(appointment) {
        return http.post('/appointments', appointment);
    },
    updateAppointment(id, appointment) {
        return http.put(`/appointments/${id}`, appointment);
    },
    deleteAppointment(id) {
        return http.delete(`/appointments/${id}`)
    }
}