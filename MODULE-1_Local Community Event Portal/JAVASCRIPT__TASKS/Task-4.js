function addEvent(newEvent) {
  events.push(newEvent);
}

function registerUser(name, eventName) {
  try {
    let event = events.find(e => e.name === eventName);
    if (!event || event.seats === 0) throw "Event full or not found!";
    event.seats--;
    console.log(${name} registered for ${event.name});
  } catch (err) {
    console.error(err);
  }
}

function filterEventsByCategory(category) {
  return events.filter(event => event.category === category);
}

// Closure example
function registrationCounter() {
  let count = 0;
  return function () {
    count++;
    return count;
  };
}
const countMusicRegs = registrationCounter();
