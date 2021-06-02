import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBookings() {
    return HTTP.get(BASE_URL + "/bookings", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  allBookingsPerDate(booking) {
        return HTTP.get(BASE_URL + `/bookings/${booking.date}`, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    create(booking) {
    return HTTP.post(BASE_URL + "/bookings", booking, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  edit(booking) {
    return HTTP.patch(BASE_URL + `/bookings/${booking.id}`, booking, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  delete(booking) {
    return HTTP.delete(BASE_URL + `/bookings/${booking.id}`, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
    pdf(booking) {
        return HTTP.get(BASE_URL + `/bookings/export/${booking.date}`, {
            responseType: "arraybuffer",
            headers: authHeader()
        }).then((response) => {
            console.log(response.data);
            console.log(response.headers);
            //let blob = new Blob()
            const url = window.URL.createObjectURL(new Blob([response.data],));
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", "Bookings.pdf");
            document.body.appendChild(link);
            link.click();
            link.parentNode.removeChild(link);
            window.open(url);
        });
    },

};
