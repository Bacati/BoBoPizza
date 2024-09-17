document.addEventListener('DOMContentLoaded', function() {
    const $selectedOption = document.getElementById('selectedEtatInAllCommandes');

    $selectedOption.addEventListener('change', function(){
        let tbody = document.getElementById('tbodyInAllCommandes');
        let rows = tbody.getElementsByTagName('tr');

        for (let i = 0; i < rows.length; i++) {
            rows[i].style.display = 'none';
        }

        let selectedValue = $selectedOption.value;
        console.log(selectedValue);

        if (selectedValue == 0){
            for (let i = 0; i < rows.length; i++) {
                rows[i].style.display = '';
            }
        } else {
            let etat = "etat-"+selectedValue;
            let rowData = document.getElementsByClassName(etat);

            for (let i = 0; i < rowData.length; i++) {
                if (rowData[i]){
                    rowData[i].style.display = '';
                }
            }
        }
    })
});
