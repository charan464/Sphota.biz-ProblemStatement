document.getElementById('loginForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const response = await fetch('http://localhost:8085/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
    });

    if (response.ok) {
        const data = await response.json();
        localStorage.setItem('jwtToken', data.token);
        const userRole = await getUserRole(data.token);
        
        // redirect to the dashboard according to the role

        if (userRole === 'ROLE_ADMIN') {
            window.location.href = 'admin-dashboard.html';
        } else if (userRole === 'ROLE_USER') {
            window.location.href = 'user-dashboard.html';
        }
    } else {
        toastr.error('Unable to Login!');
    }
});

async function getUserRole(token) {
    const decodedToken = jwt_decode(token);
    const roles = decodedToken.roles;
    return roles[0];
}
