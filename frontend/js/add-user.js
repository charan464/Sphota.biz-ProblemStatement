document.getElementById('addUserForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const token = localStorage.getItem('jwtToken');
    const response = await fetch('http://localhost:8085/admin/addUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ username, password }),
    });

    const result = await response.json();

    if (response.ok) {
        toastr.success(result.message);
    } else {
        toastr.error(result.message || 'Unable to add user');
    }
});