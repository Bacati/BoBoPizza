var modal = document.getElementById("myModal");
var btn = document.getElementById("myBtn");
var span = document.getElementsByClassName("close")[0];
btn.onclick = function() {
    modal.style.display = "block";
}
span.onclick = function() {
    modal.style.display = "none";
}
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
var today = new Date();
today.setHours(today.getHours() + 3);
var date = today.toISOString().slice(0, 16);
document.getElementById('heureCommande').min = document.getElementById('heureCommande').value = date;

