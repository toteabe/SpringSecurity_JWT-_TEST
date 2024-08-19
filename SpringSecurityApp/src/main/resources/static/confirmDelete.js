document.addEventListener('DOMContentLoaded', function() {
    const deleteButtons = document.querySelectorAll('.deleteButton');

    deleteButtons.forEach(function(deleteButton) {
        deleteButton.addEventListener('click', function(event) {
            event.preventDefault(); // Previene la acción predeterminada del enlace
            const url = deleteButton.getAttribute('href');

            // Muestra la confirmación de SweetAlert
            Swal.fire({
                title: '¿Estás seguro de que deseas eliminar esta persona?',
                text: "¡No podrás revertir esto!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Sí, eliminar'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = url;
                }
            });
        });
    });
});