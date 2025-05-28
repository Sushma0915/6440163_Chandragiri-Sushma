document.getElementById("registrationForm").addEventListener("submit", function(e) {
  e.preventDefault();
  const name = this.name.value;
  const email = this.email.value;
  const selectedEvent = this.event.value;
  registerUser(name, selectedEvent);
});
