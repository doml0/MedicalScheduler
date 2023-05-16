<template>
  <v-container>
    <navbar />
    <v-divider></v-divider>
    <h1>Upcoming Appointments</h1>
    <v-spacer></v-spacer>
    <v-row class="fill-height">
      <v-col>
        <v-sheet height="64">
          <v-toolbar flat>
            <v-btn
              variant="outlined"
              class="me-4"
              color="grey-darken-2"
              @click="setToday"
            >
              Today
            </v-btn>
            <v-btn
              fab
              variant="text"
              size="small"
              color="grey-darken-2"
              @click="prev"
            >
              <v-icon size="small"> mdi-chevron-left </v-icon>
            </v-btn>
            <v-btn
              fab
              variant="text"
              size="small"
              color="grey-darken-2"
              @click="next"
            >
              <v-icon size="small"> mdi-chevron-right </v-icon>
            </v-btn>
            <v-toolbar-title v-if="$refs.calendar">
              {{ $refs.calendar.title }}
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-menu location="bottom end">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  variant="outlined"
                  color="grey-darken-2"
                  v-bind="attrs"
                  v-on="on"
                >
                  <span>{{ typeToLabel[type] }}</span>
                  <v-icon end> mdi-menu-down </v-icon>
                </v-btn>
              </template>
              <v-list>
                <v-list-item @click="type = 'day'">
                  <v-list-item-title>Day</v-list-item-title>
                </v-list-item>
                <v-list-item @click="type = 'week'">
                  <v-list-item-title>Week</v-list-item-title>
                </v-list-item>
                <v-list-item @click="type = 'month'">
                  <v-list-item-title>Month</v-list-item-title>
                </v-list-item>
                <v-list-item @click="type = '4day'">
                  <v-list-item-title>4 days</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-toolbar>
        </v-sheet>
        <v-sheet height="600">
          <v-calendar
            ref="calendar"
            v-model="focus"
            color="primary"
            :events="filteredAppointments"
            :event-color="getEventColor"
            :type="type"
            @click:event="showEvent"
            @click:more="viewDay"
            @click:date="viewDay"
            @change="updateRange"
          ></v-calendar>
          <v-menu
            v-model="selectedOpen"
            :close-on-content-click="false"
            :activator="selectedElement"
            offset-x
          >
            <v-card color="grey-lighten-4" min-width="350px" flat>
              <v-toolbar :color="selectedEvent.color" dark>
                <v-btn @click="updateAppointment" icon>
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <v-toolbar-title>{{ selectedEvent.name }}</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn @click="deleteAppointment" icon>
                  <v-icon>mdi-trash-can</v-icon>
                </v-btn>
                <v-btn icon>
                  <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
              </v-toolbar>
              <v-card-text>
                <span v-html="selectedEvent.details"></span>
              </v-card-text>
              <v-card-actions>
                <v-btn
                  variant="text"
                  color="secondary"
                  @click="selectedOpen = false"
                >
                  Cancel
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-menu>
        </v-sheet>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import Navbar from "../components/Navbar.vue";
import AppointmentService from "../services/AppointmentService";

export default {
  name: "calendar",
  components: {
    Navbar,
  },
  data: () => ({
    focus: "",
    type: "month",
    typeToLabel: {
      month: "Month",
      week: "Week",
      day: "Day",
      "4day": "4 Days",
    },
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
  }),
  mounted() {
    this.$refs.calendar.checkChange();
  },
  methods: {
    getAppointments() {
      AppointmentService.getAppointments().then((response) => {
        this.$store.commit("SET_APPOINTMENTS", response.data);
        this.appointments = this.$store.state.appointments;
        // this.getEvents();
      });
    },
    updateAppointment() {
      this.$router.push({
        path: `/appointments/${this.selectedEvent.appointmentId}`,
        query: { appointment: this.selectedEvent },
      });
    },
    deleteAppointment() {
      AppointmentService.deleteAppointment(
        this.selectedEvent.appointmentId
      ).then((response) => {
        if (response.status == 200) {
          this.selectedOpen = false;
          setTimeout(() => {
            this.$router.push("/");
          }, "1000");
        }
      });
    },
    getEvents() {
      for (let i = 0; i < this.appointments.length; i++) {
        let temp = this.appointments[i];
        let time = this.appointments[i].appointmentTime;
        let endTime;
        if (time.slice(3, 5) === "30") {
          endTime = time.slice(0, 2);
          parseInt(endTime);
          endTime++;
          endTime = endTime.toString() + ":00:00";
        } else {
          endTime = time.slice(0, 2) + ":30:00";
        }
        let event = {
          appointmentId: this.appointments[i].appointmentId,
          patientId: this.appointments[i].patientId,
          docId: this.appointments[i].doctorId,
          name: "Appointment",
          start: temp.appointmentDate + "T" + temp.appointmentTime,
          end: (temp.appointmentDate += "T" + endTime),
          color: "blue",
          timed: false,
          description: this.appointments[i].description
        };
        this.events.push(event);
      }
    },
    viewDay({ date }) {
      this.focus = date;
      this.type = "day";
    },
    setToday() {
      this.focus = "";
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        requestAnimationFrame(() =>
          requestAnimationFrame(() => (this.selectedOpen = true))
        );
      };
      if (this.selectedOpen) {
        this.selectedOpen = false;
        requestAnimationFrame(() => requestAnimationFrame(() => open()));
      } else {
        open();
      }
      nativeEvent.stopPropagation();
    },
  },
  computed: {
    filteredAppointments() {
      return this.events.filter(
        (appt) => appt.patientId === this.$store.state.currentPatient.patientId
      );
    },
  },
  created() {
    this.getEvents();
    // this.getAppointments();
    this.doctors = this.$store.state.doctors;
  },
};
</script>
