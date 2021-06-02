<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark >
          {{ isNew ? "Create item" : "Edit item" }}
          <v-btn  @click="close_dialog" >X</v-btn>
        </v-toolbar>
        <v-form>
          <v-text-field v-model="booking.player_username" label="Username" />
          <v-text-field v-model="booking.player_name" label="Name" />
          <v-text-field v-model="booking.startDate" label="Start Date (YYYY-MM-DD HH:mm)" />
          <v-text-field v-model="booking.endDate" label="End Date (YYYY-MM-DD HH:mm)" />
          <v-text-field v-model="booking.court" label="Court (Tx)" />
          <v-text-field v-model="booking.details" label="Details (optional)" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteBooking">Delete Booking</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookingDialog2",
  props: {
    booking: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
      api.bookings
          .create({
            player_username: this.booking.player_username,
            player_name: this.booking.player_name,
            startDate: this.booking.startDate,
            endDate: this.booking.endDate,
            court: this.booking.court,
            details: this.booking.details,
          })
          .then(() => this.$emit("refresh"));
      }
      else {
        api.bookings
          .edit({
            id: this.booking.id,
            player_username: this.booking.player_username,
            player_name: this.booking.player_name,
            startDate: this.booking.startDate,
            endDate: this.booking.endDate,
            court: this.booking.court,
            details: this.booking.details,
          })
          .then(() => this.$emit("refresh"));
      }
    },

    deleteBooking(){
      api.bookings
          .delete({
            id: this.booking.id,
            player_username: this.booking.player_username,
            player_name: this.booking.player_name,
            startDate: this.booking.startDate,
            endDate: this.booking.endDate,
            court: this.booking.court,
            details: this.booking.details,
          })
          .then(() => this.$emit("refresh"));
    },



    close_dialog(){
      this.$emit("refresh");
    },
  },
  computed: {
    isNew: function () {
      console.log(this.booking.id);
      return !this.booking.id;
    },
  },

};
</script>

<style scoped></style>
