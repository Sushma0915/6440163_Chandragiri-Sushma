const listContainer = document.getElementById("event-list");
eventList.forEach(event => {
  const card = document.createElement("div");
  card.textContent = ${event.name} (${event.date}) - Seats: ${event.seats};
  listContainer.appendChild(card);
});
