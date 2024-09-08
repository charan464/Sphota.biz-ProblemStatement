document.getElementById('addCategoryForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const gstRate = document.getElementById('gstRate').value;
    const token = localStorage.getItem('jwtToken');

    const response = await fetch('http://localhost:8085/admin/addCategory', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ name, gstRate }),
    });

    const result = await response.json();

    if (response.ok) {
        toastr.success(result.message);
    } else {
        toastr.error(result.message || 'Unable to add category');
    }
});