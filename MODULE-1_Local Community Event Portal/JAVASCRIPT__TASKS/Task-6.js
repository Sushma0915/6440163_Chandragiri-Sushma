const eventList = [...events];
eventList.push({ name: "Art Fair", date: "2025-08-20", seats: 30, category: "art" });

const musicEvents = eventList.filter(event => event.category === "music");
const displayCards = eventList.map(event =>  ${event.name} - ${event.date});
