function submitRegistration(data) {
  fetch("https://mockapi.io/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  })
    .then(res => res.json())
    .then(response => console.log("Success:", response))
    .catch(error => console.error("Error:", error));
}
