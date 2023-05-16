<template>
  <v-app>
    <v-main>
      <router-view/>
    </v-main>
    <v-footer
    app
    tag="footer"
    inset
    >
    <v-row justify="center"></v-row>
    <v-col>
      <v-icon>mdi-copyright</v-icon> 2023 Medical Bros
    </v-col>
    </v-footer>
  </v-app>
</template>

<script>

export default {
  name: 'App',
  data() {
    return {

    }
  }
};
</script>

<style >
  #app {
    background-color: #BAC6CE  ;

  }

  .v-data-table .v-data-table-header tr th {
  font-size: 24px !important;
}

</style>
