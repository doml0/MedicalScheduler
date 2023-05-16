<template>
  <v-container>
    <navbar />
    <v-layout>
      <admin-view v-if="getRoleAdmin" />
      <patient-list v-else-if="getRoleDoctor" />
      <patient-dashboard v-else />
    </v-layout>
  </v-container>
</template>

<script>
import PatientDashboard from '../components/PatientDashboard.vue'
import Navbar from '../components/Navbar.vue'

export default {
  name: 'home',
  components: {
    PatientDashboard,
    Navbar
  },
  methods: {

  },
  computed: {
    getRoleAdmin() {
      return this.$store.state.user.authorities[0].name === 'ROLE_ADMIN'
    },
    getRolePatient() {
      return this.$store.state.user.authorities[0].name === 'ROLE_USER'
    },
    getRoleDoctor() {
      return this.$store.state.user.authorities[0].name === 'ROLE_DOCTOR'
    }
  },
  created() {

  }
}
</script>