document.querySelectorAll('.myBtn').forEach(button => {
    button.addEventListener('click', function() {
        const produitId = this.getAttribute('data-id');
        const modal = document.querySelector(`.myModal[data-id="${produitId}"]`);
        modal.style.display = 'block';
    });
});

document.querySelectorAll('.close').forEach(span => {
    span.addEventListener('click', function() {
        const modal = this.closest('.myModal');
        modal.style.display = 'none';
    });
});

window.addEventListener('click', function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = 'none';
    }
});
