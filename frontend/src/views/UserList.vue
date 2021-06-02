<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-btn @click="addUser">Add Admin</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      @click:row="editUser"
    ></v-data-table>

    <v-form>
      <v-text-field v-model="booking.date" label="YYYY-MM-DD" v-on:keyup.enter="submit" ></v-text-field>
    </v-form>
    <v-btn @click="addBooking">Add Booking</v-btn>
    <v-btn @click="export_pdf">PDF</v-btn>

    <v-data-table
        :headers="headers2"
        :items="bookings"
        @click:row="editBooking"
    ></v-data-table>
    <UserDialog
        :opened="dialogVisible"
        :user ="selectedUser"
        @refresh="refreshList">
    </UserDialog>

    <BookingDialog2
        :opened="dialogVisible2"
        :booking ="selectedBooking"
        @refresh="refreshList"
    ></BookingDialog2>

  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import BookingDialog2 from "../components/BookingDialog2";

export default {
  name: "UserList",
  components: {UserDialog, BookingDialog2},
  data() {
    return {
      users: [],
      bookings: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "Name", value: "name" },
        { text: "Email", value: "email" },
        { text: "Role", value: "role" },
      ],

      headers2: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "player_username",
        },
        { text: "Name", value: "player_name" },
        { text: "Start Date", value: "startDate" },
        { text: "End Date", value: "endDate" },
        { text: "Court", value: "court" },
      ],

      dialogVisible: false,
      dialogVisible2: false,
      selectedUser: {},
      selectedBooking: {},
      booking:{date: "",},
    };
  },
  methods: {
    editUser(user){
      if(user.role == "ADMIN"){
        this.selectedUser = user;
        this.dialogVisible = true;
      }
    },
    addUser(){
      this.dialogVisible = true;
    },
    editBooking(booking) {
      this.selectedBooking = booking;
      this.dialogVisible2 = true;
    },
    addBooking() {
      this.dialogVisible2 = true;
    },
    export_pdf(){
      console.log(this.booking.date);
      api.bookings
          .pdf({date: this.booking.date})
    },
    submit(e) {
      this.booking.date = e.target.value;
      //console.log(this.booking.date);
      this.refreshList();
    },


    async refreshList(){
      this.dialogVisible = false;
      this.selectedUser = {};
      this.dialogVisible2 = false;
      this.selectedBooking = {};
      this.bookings = await api.bookings.allBookingsPerDate({date: this.booking.date});
      this.users = await api.users.allUsers();
    },
  },
  created() {
    this.refreshList();
    //this.users = await api.users.allUsers();
  },
};
</script>

<style scoped></style>
