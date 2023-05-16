<template>
  <v-container>
    <v-layout>
      <v-container v-if="getRoleAdmin">
        <v-btn @click="showDashboard">Continue to Dashboard</v-btn>
        <v-btn @click="registerDoctor">Add Doctor?</v-btn>
      </v-container>
      <v-container v-else-if="getRoleDoctor">
        <v-btn @click="showDashboard">Existing Doctor?</v-btn>
        <v-btn @click="showDoctorForm">New Doctor?</v-btn>
      </v-container>
      <v-container v-else>
        <v-btn @click="showDashboard">Existing Patient?</v-btn>
        <v-btn @click="showPatientForm">New Patient?</v-btn>
      </v-container>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "additional-info",
  methods: {
    showDashboard() {
      this.$router.push("/");
    },
    showPatientForm() {
      this.$router.push('/patientForm');
    },
    showDoctorForm() {
      this.$router.push('/doctorForm');
    },
    registerDoctor() {
      this.$router.push('/registerDoctor');
    }
  },
  computed: {
    getRoleAdmin() {
      return this.$store.state.user.authorities[0].name === "ROLE_ADMIN";
    },
    getRolePatient() {
      return this.$store.state.user.authorities[0].name === "ROLE_USER";
    },
    getRoleDoctor() {
      return this.$store.state.user.authorities[0].name === "ROLE_DOCTOR";
    },
  },
};
</script>

<style></style>
