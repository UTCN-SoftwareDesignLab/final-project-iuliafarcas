<template>
  <v-card>
    <v-card-title>
      Bookings
      <v-spacer></v-spacer>

      <v-form>
        <v-text-field v-model="booking.date" label="YYYY-MM-DD" v-on:keyup.enter="submit" ></v-text-field>
      </v-form>

      <v-btn @click="addBooking">Add Booking</v-btn>
    </v-card-title>

    <div class="float-container">
      <div class="float-child2">
        <v-data-table
            :headers="headers"
            :items="bookings"
            :search="search"
        ></v-data-table>
      </div>

      <div class="float-child">
        <iframe
            width="450"
            height="250"
            frameborder="0" style="border:0"
            src="https://www.google.com/maps/embed/v1/place?key=AIzaSyDm2dDz0L2tcU3FstCI0hUtnprBw0S7HW8&q=Innovia+Tenis,Cluj-Napoca+Romania" allowfullscreen>
        </iframe>
      </div>

      <div class="float-child">
        <iframe
            width="450"
            height="250"
            frameborder="0" style="border:0"
            src="https://www.google.com/maps/embed/v1/streetview?key=AIzaSyDm2dDz0L2tcU3FstCI0hUtnprBw0S7HW8&location=46.7572242,23.5403182&heading=210&pitch=5&fov=90" allowfullscreen>
        </iframe>
      </div>
    </div>

    <BookingDialog
      :opened="dialogVisible"
      @refresh="refreshList"
    ></BookingDialog>
  </v-card>
</template>

<style>
.float-container {
  border: 3px;
  padding: 20px;
}

.float-child {
  width: 40%;
  float: left;
  padding: 30px;
  border: 2px;
}
.float-child2 {
  padding: 10px;
  border: 2px;
}

</style>

<script>
import api from "../api";
import BookingDialog from "../components/BookingDialog";
import format from "date-fns"

export default {
  name: "BookingList",
  components: { BookingDialog },

  data(){
    return{
      bookings: [],
      search: "",
      headers: [
        {
          text: "Start Date",
          align: "start",
          sortable: false,
          value: "startDate",
        },
        { text: "End Date", value: "endDate" },
        { text: "Court", value: "court" },
      ],
      dialogVisible: false,
      booking:{date: "",},
    };
  },
  methods: {
    /*editBooking(booking) {
      this.selectedItem = booking;
      this.dialogVisible = true;
    },*/
    addBooking() {
      this.dialogVisible = true;
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
    search_booking(){
      this.bookings = api.bookings.allBookingsPerDate({date: this.booking.date}).then(() => this.$emit("refresh"));
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      //this.items = await api.bookings.allBookings();
      this.bookings = await api.bookings.allBookingsPerDate({date: this.booking.date});
    },
  },
  created() {
    this.refreshList();
  },
  computed:{
    formattedDate: function (){
      return this.date ? format(this.date, "DD-MM-YYYY") : " "
    }
  },
};
</script>

<style scoped></style>
