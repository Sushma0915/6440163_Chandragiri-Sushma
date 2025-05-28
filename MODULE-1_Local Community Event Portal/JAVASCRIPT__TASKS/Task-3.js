const events = [
  { name: "Music Festival", date: "2025-06-15", seats: 10, category: "music" },
  { name: "Coding Bootcamp", date: "2025-05-10", seats: 0, category: "tech" },
  { name: "Cooking Workshop", date: "2025-07-01", seats: 20, category: "cooking" }
];

events.forEach(event => {
  if (new Date(event.date) > new Date() && event.seats > 0) {
    console.log(Upcoming Event: ${event.name});
  }
});
 