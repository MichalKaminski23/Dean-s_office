function sayHello() {
  alert("Hello, welcome to our website!");
}

function toggleBackground() {
  const body = document.body;
  if (body.style.backgroundColor === 'rgb(240, 240, 240)' || body.style.backgroundColor === '') {
    body.style.backgroundColor = '#333';
    document.getElementById('welcomeMessage').style.color = '#fff';
  } else {
    body.style.backgroundColor = '#f0f0f0';
    document.getElementById('welcomeMessage').style.color = '#666';
  }
  console.log("Background color toggled.");
}