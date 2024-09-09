document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('fetchSales')?.addEventListener('click', fetchSales);
});

async function fetchSales() {
    const date = document.getElementById('salesDate').value;
    const token = localStorage.getItem('jwtToken');
    const response = await fetch(`http://localhost:8085/admin/sales/${date}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    const sales = await response.json();
    const salesTableBody = document.querySelector('#salesTable tbody');
    salesTableBody.innerHTML = '';

    // add details of each sale item as a row to the table

    sales.forEach(sale => {
        sale.items.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${sale.id}</td>
                <td>${item.product.name}</td>
                <td>${item.product.price}</td>
                <td>${item.taxAmount/item.quantity}</td>
                <td>${item.quantity}</td>
                <td>${item.totalAmount}</td>
            `;
            salesTableBody.appendChild(row);
        });
    });
}