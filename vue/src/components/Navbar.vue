<template>
  <nav>
    <v-app-bar class="" app color="white">
      <v-app-bar-nav-icon>
        <v-img
          class="mx-auto"
          src="../assets/caduceus-transparent-background-8.png"
          max-width="50px"
          max-height="100px"
        >
        </v-img>
      </v-app-bar-nav-icon>
      <v-app-bar-title> MocDoc </v-app-bar-title>
      <v-spacer></v-spacer>
      <v-list class="d-flex" dense nav>
        <v-list-item
          ><router-link class="rl" v-bind:to="{ name: 'home' }"
            >Home</router-link
          ><br
        /></v-list-item>
        <v-list-item
          ><router-link class="rl" v-bind:to="{ name: 'calendar' }"
            >Calendar</router-link
          ></v-list-item
        >
        <v-list-item
          ><router-link class="rl" v-bind:to="{ name: 'reviews' }"
            >Reviews</router-link
          ></v-list-item
        >
        <v-list-item
          ><router-link
            class="rl"
            v-bind:to="{ name: 'logout' }"
            v-if="$store.state.token != ''"
            >Logout</router-link
          ></v-list-item
        >
      </v-list>
    </v-app-bar>
  </nav>
</template>

<script>
export default {
  name: "nav-bar",
  data() {
    return {
      drawer: false,
    };
  },
};
</script>

<style scoped>
.rl {
  color: black;
}
</style>
