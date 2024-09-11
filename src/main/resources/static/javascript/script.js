document.querySelectorAll('.add-to-cart').forEach(button => {
    button.addEventListener('click', function() {
        const produitId = this.getAttribute('data-id');
        const modal = document.querySelector(`.myModal[data-id="${produitId}"]`);
        const produitNom = modal.querySelector('p').innerText;
        const produitPrix = modal.querySelector('p:nth-of-type(2)').innerText;
        const quantite = modal.querySelector(`#inputGenre-${produitId}`).value;

        let panier = JSON.parse(localStorage.getItem('panier')) || [];
        panier.push({ id: produitId, nom: produitNom, prix: produitPrix, quantite: quantite });
        localStorage.setItem('panier', JSON.stringify(panier));

        alert('Produit ajouté au panier !');
        modal.style.display = 'none'; // Fermer le modal après l'ajout
    });
});
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
