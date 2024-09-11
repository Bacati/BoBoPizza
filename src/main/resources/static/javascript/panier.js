document.addEventListener('DOMContentLoaded', function() {
    let panier = JSON.parse(localStorage.getItem('panier')) || [];
    const panierContainer = document.querySelector('.panier-container');

    panier.forEach(produit => {
        const produitElement = document.createElement('div');
        produitElement.innerHTML = `
      <p>${produit.nom} - ${produit.prix} € x ${produit.quantite}</p>
    `;
        panierContainer.appendChild(produitElement);
    });

    document.getElementById('commander').addEventListener('click', function() {
        window.location.href = 'commande.html';
    });
});
