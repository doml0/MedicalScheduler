<template>
  <v-container>
    <navbar />

      <v-card class="" max-height="auto" max-width="250px">
        <v-img
          class="d-flex justify-center"
          cover
          max-height="250px "
          max-width="250px"
          src="../assets/Doctor2.jpeg"
        ></v-img>

        <v-card-title class="d-flex justify-center">{{
          this.currentDoctor.firstName + " " + this.currentDoctor.lastName
        }}</v-card-title>
        <v-card-subtitle class="d-flex justify-center"
          >{{ this.currentDoctor.specialty }} <br />
          Suite: {{ this.currentDoctor.suiteNumber }} <br />
          Extension: {{ this.currentDoctor.phoneNumber }}</v-card-subtitle
        >
        <v-card-text>
          <v-row align="center"> </v-row>
        </v-card-text>
        <v-row> </v-row>
      </v-card>

  </v-container>
</template>

<script>
import Navbar from "../components/Navbar.vue";

export default {
  name: "availability",
  components: {
    Navbar,
  },
  data() {
    return {
      currentDoctor: {},
    };
  },
  methods: {},
  created() {
    this.currentDoctor = JSON.parse(this.$route.query.doctor);
  },
};
</script>

<style></style>
