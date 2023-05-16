<template>
  <v-container class="ml-16 mr-16 mt-10">
    <v-container class="ml-16 mr-16 test">
      <v-row class="ml-16 mr-16">
        <v-col class="ml-16 mr-16">
          <h1 class="mb-6">
            Welcome, {{ this.$store.state.currentPatient.firstName }}
          </h1>
          <v-divider class="mb-16"> </v-divider>
        </v-col>
      </v-row>
    </v-container>

    <v-container class="ml-16 mr-16 test">
      <v-row class="ml-16 mr-16">
        <v-col>
        <v-card
          class="mx-auto my-12"
          max-height="auto"
          v-for="doctor in this.doctors"
          :key="doctor.doctorId"
        >
          <v-img cover height="250" src=""></v-img>
          <v-card-item>
            <v-card-title>{{
              doctor.firstName + " " + doctor.lastName
            }}</v-card-title>
            <v-card-subtitle
              >{{ doctor.specialty }} <br />
              Suite: {{ doctor.suiteNumber }} <br />
              Extension: {{ doctor.phoneNumber }}</v-card-subtitle
            >
          </v-card-item>
          <v-rating
            color="amber"
            density="compact"
            half-increments
            readonly
            size="small"
          ></v-rating>
          <v-card-text>
            <v-row align="center"> </v-row>
          </v-card-text>
                <v-btn class="mt-6 ml-16 mr-5" @click="openAppointmentDialog()">
                  Book Appointment
                </v-btn>
        </v-card>
      </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import doctorService from "../services/DoctorService.js";
import PatientService from "../services/PatientService";
import AppointmentService from "../services/AppointmentService";

export default {
  name: "patient-dashboard",
  data() {
    return {
      doctors: [],
      appointments: [],
      userId: 0,
      showAppointmentDialog: false,
    };
  },
  computed: {
    headers() {
      return [
        { text: "First Name", value: "firstName" },
        { text: "Last Name", value: "lastName" },
        { text: "Specialty", value: "specialty" },
        { text: "Suite", value: "suiteNumber" },
        { text: "Cost Per Hour", value: "costPerHour" },
        { text: "Phone Extension", value: "phoneNumber" },
      ];
    },
    myAppointments() {
      return this.appointments
        .filter(
          (appt) =>
            appt.patientId === this.$store.state.currentPatient.patientId
        )
        .sort();
    },
  },
  methods: {
    serviceCalls() {
      this.getDoctors();
      this.setPatient();
      this.getAppointments();
      this.myAppointments();
    },
    getDoctors() {
      doctorService.getAllDoctors().then((response) => {
        this.$store.commit("SET_DOCTORS", response.data);
        this.doctors = this.$store.state.doctors;
      });
    },
    setPatient() {
      PatientService.getPatientByUserId(this.$store.state.user.id).then(
        (response) => {
          this.$store.commit("SET_PATIENT", response.data);
        }
      );
    },
    getAppointments() {
      AppointmentService.getAppointments().then((response) => {
        this.$store.commit("SET_APPOINTMENTS", response.data);
        this.appointments = this.$store.state.appointments;
      });
    },
  },

  created() {
    this.serviceCalls();
  },
};
</script>
<style scoped>
.test {
  border: solid;
}
</style>
