let isbnToDelete = null;

function showModal(isbn) {
    isbnToDelete = isbn;
    document.getElementById('modal').showModal();
}

function confirmDelete() {
    if (isbnToDelete) {
        window.location.href = `excluir?isbn=${isbnToDelete}`;
    }
}
