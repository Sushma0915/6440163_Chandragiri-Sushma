async function fetchEvents() {
  try {
    const response = await fetch("https://mockapi.io/events");
    const data = await response.json();
    console.log("Fetched Events:", data);
  } catch (error) {
    console.error("Fetch error:", error);
  }
}
