import axios from 'axios';
/* eslint-disable */
var userId = 5;
const http = axios.create({
    baseURL: "http://localhost:9000"
});

export default {
    getAllDoctors() {
        return http.get('/doctors');
    },
    createDoctor(doctor) {
        return http.post('/doctors', doctor);
    },

    updateDoctor(id, doctor) {
        return http.put(`/doctors/${id}`, doctor);
    },

    deleteDoctor(id) {
        return http.delete(`/doctors/${id}`);
    },
}