import axios from 'axios';

/* eslint-disable */
var userId = 5;

const http = axios.create({
    baseURL: "http://localhost:9000"
});

export default {
    getPatientList() {
        return http.get('/patients');
    },
    update(id, patient) {
        return http.put(`/patients/${id}`, patient);
    },

    delete(id) {
        return http.delete(`/patients/${id}`);
    },

    create(patient) {
        return http.post(`/patients`, patient);
    },
    getPatientByUserId(id) {
        return http.get(`/patients/${id}`);
    }
}