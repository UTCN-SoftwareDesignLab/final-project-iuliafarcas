<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark >
          <!-- {{ isNew ? "Create item" : "Edit item" }}-->
          Create Booking
          <v-btn  @click="close_dialog" >X</v-btn>
        </v-toolbar>
        <v-form>
          <v-text-field v-model="booking.username" label="Username" />
          <v-text-field v-model="booking.name" label="Name" />
          <v-text-field v-model="booking.startDate" label="Start Date (YYYY-MM-DD HH:mm)" />
          <v-text-field v-model="booking.endDate" label="End Date (YYYY-MM-DD HH:mm)" />
          <v-text-field v-model="booking.court" label="Court (Tx)" />
          <v-text-field v-model="booking.details" label="Details (optional)" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            <!--{{ isNew ? "Create" : "Save" }}-->
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookingDialog",
  props: {
    opened: Boolean,
  },
  data:() => ({
    booking:{
      username: "",
      name: "",
      startDate: "",
      endDate: "",
      court: "",
      details: "",
    },
  }),
  methods: {
    persist() {
      //if (this.isNew) {
        api.bookings
          .create({
            player_username: this.booking.username,
            player_name: this.booking.name,
            startDate: this.booking.startDate,
            endDate: this.booking.endDate,
            court: this.booking.court,
            details: this.booking.details,
          })
          .then(() => this.$emit("refresh"));
      //}
      /*else {
        api.items
          .edit({
            id: this.item.id,
            name: this.item.name,
            description: this.item.description,
          })
          .then(() => this.$emit("refresh"));
      }*/
    },
    close_dialog(){
      this.$emit("refresh");
    },
  },
};
</script>

<style scoped></style>
