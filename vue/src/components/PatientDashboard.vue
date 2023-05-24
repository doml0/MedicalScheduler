<template>
  <v-container class="ml-16 mr-16 mt-10">
    <v-container class="ml-16 mr-16 test">
      <v-row class="ml-16 mr-16">
        <v-col class="ml-16 mr-16">
          <h1 class="mb-6">
            Welcome, {{ this.$store.state.currentPatient.firstName }}
          </h1>
          <p>Find the right doctor that fits your needs!</p>
        </v-col>
      </v-row>
    </v-container>

    <v-container class="ml-16 mr-16 test">
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col
                v-for="doctor in this.doctors"
                :key="doctor.doctorId"
                cols="12"
                md="4"
                class="d-flex justify-center"
              >
                <v-card class="" max-height="auto">
                  <v-img
                    class="d-flex justify-center"
                    cover
                    max-height="250px "
                    max-width="250px"
                    src="../assets/Doctor2.jpeg"
                  ></v-img>

                  <v-card-title class="d-flex justify-center">{{
                    doctor.firstName + " " + doctor.lastName
                  }}</v-card-title>
                  <v-card-subtitle class="d-flex justify-center"
                    >{{ doctor.specialty }} <br />
                    Suite: {{ doctor.suiteNumber }} <br />
                    Extension: {{ doctor.phoneNumber }}</v-card-subtitle
                  >

                  <v-rating
                    class="d-flex justify-center"
                    color="amber"
                    density="compact"
                    half-increments
                    readonly
                    size="small"
                  ></v-rating>
                  <v-card-text>
                    <v-row align="center"> </v-row>
                  </v-card-text>
                  <v-row>
                    <v-btn
                      @click="viewAvailability(doctor)"
                      class="d-flex justify-center"
                      >Book Online</v-btn
                    >
                    <v-btn @click="viewReviews(doctor)" class="d-flex justify-center">Read Reviews</v-btn>
                  </v-row>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import doctorService from "../services/DoctorService.js";
import PatientService from "../services/PatientService";
import AppointmentService from "../services/AppointmentService";
import reviewService from "../services/ReviewService";

export default {
  name: "patient-dashboard",
  data() {
    return {
      doctors: [],
      appointments: [],
      userId: 0,
      showAppointmentDialog: false,
      reviews: [],
      doctorRating: 3,
      doctorId: 0,
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
    filteredDoctorRating() {
      return (doctorId) => {
        const filteredReviews = this.reviews.filter(
          (review) => review.doctorId === doctorId
        );
        const totalRating = filteredReviews.reduce(
          (acc, review) => acc + review.rating,
          0
        );
        const averageRating = totalRating / filteredReviews.length;
        return averageRating;
      };
    },
  },
  methods: {
    serviceCalls() {
      this.getDoctors();
      this.setPatient();
      this.getAppointments();
      this.getDoctorReviews();
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
    getDoctorReviews() {
      reviewService.getAllReviews().then((response) => {
        this.$store.commit("SET_REVIEWS", response.data);
        this.reviews = this.$store.state.reviews;
      });
    },
    viewAvailability(doctor) {
      this.$router.push({
        path: `/availability/${doctor.doctorId}`,
        query: { doctor: JSON.stringify(doctor) },
      });
    },
    viewReviews(doctor) {
      this.$router.push({
        path: `/reviews/${doctor.doctorId}`,
        query: { doctor: JSON.stringify(doctor) },
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
